package iss.nus.edu.sg.leave_application_processing_system.service.implementation;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveApplicationControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;
import iss.nus.edu.sg.leave_application_processing_system.model.CompensationLedger;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.repo.CompensationLedgerRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.LeaveApplicationService;
import iss.nus.edu.sg.leave_application_processing_system.service.LeaveValidationService;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.CompensationLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;

@Service
public class CompensationLeaveApplicationService implements LeaveApplicationService {

	private CompensationLedgerRepository compRepo;
	private LeaveApplicationRepository laRepo;
	private LeaveValidationService validationService;
	
	
	public CompensationLeaveApplicationService(CompensationLedgerRepository compRepo,
			LeaveApplicationRepository laRepo, LeaveValidationService validationService) {
		this.compRepo = compRepo;
		this.laRepo = laRepo;
		this.validationService = validationService;
	}

	@Override
	public List<ControllerDTO> viewApplicationStatus(ServiceDTO serviceDTO) {
		throw new UnsupportedOperationException("Unimplemented method 'viewApplicationStatus'");
	}

	@Override
	public ControllerDTO submitApplication(ServiceDTO serviceDTO) {
		LeaveApplicationControllerDTO response = new LeaveApplicationControllerDTO();
        CompensationLeaveServiceDTO dto = (CompensationLeaveServiceDTO) serviceDTO.getAllAttribute();
        
        // Map to the Universal Validation DTO
        LeaveApplicationControllerDTO valDto = new LeaveApplicationControllerDTO();
        valDto.setStaffId(dto.getStaffId());
        valDto.setType(LeaveType.COMPENSATION);
        valDto.setStartDate(dto.getLeavePeriodStart());
        valDto.setEndDate(dto.getLeavePeriodEnd());
        valDto.setHalfDay(dto.isHalfDay());

        // Calculate duration using the shared logic (includes Public Holidays!)
        double daysRequested = validationService.calculateDays(
            valDto.getStartDate(), 
            valDto.getEndDate(), 
            valDto.isHalfDay()
        );

        // Run Universal Validation
        String errorMessage = validationService.validate(valDto);

        if (errorMessage != null) {
            response.setApplicationResult(false);
            response.setLeaveApprovalReason(errorMessage);
            return response;
        }

        // Fetch Ledger for persistence
        int year = dto.getLeavePeriodStart().getYear();
        Optional<CompensationLedger> ledgerOpt = compRepo.findByEmployeeIdAndYearApplied(dto.getStaffId(), year);

        if (ledgerOpt.isPresent()) {
            CompensationLedger ledger = ledgerOpt.get();

            // Create and Save LeaveApplication
            LeaveApplication newApp = new LeaveApplication();
            newApp.setEmployee(ledger.getEmployee());
            newApp.setStartDate(dto.getLeavePeriodStart());
            newApp.setEndDate(dto.getLeavePeriodEnd());
            newApp.setLeaveType(LeaveType.COMPENSATION);
            newApp.setLedger(ledger);
            newApp.setLeaveStatus(LeaveStatus.APPLIED);
            newApp.setReason(dto.getReason());
            newApp.setHalfDay(dto.isHalfDay());
            newApp.setAppliedDate(LocalDate.now());
            
            laRepo.save(newApp);
            
            response.setApplicationResult(true);
            return response;
        }

        response.setApplicationResult(false);
        response.setLeaveApprovalReason("Compensation ledger record not found for this year.");
        return response;
	}
	
}
