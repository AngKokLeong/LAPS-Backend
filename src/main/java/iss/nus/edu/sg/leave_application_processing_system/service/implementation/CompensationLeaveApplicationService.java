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
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.CompensationLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;

@Service
public class CompensationLeaveApplicationService implements LeaveApplicationService {

	private CompensationLedgerRepository compRepo;
	private LeaveApplicationRepository laRepo;
	
	
	public CompensationLeaveApplicationService(CompensationLedgerRepository compRepo,
			LeaveApplicationRepository laRepo) {
		this.compRepo = compRepo;
		this.laRepo = laRepo;
	}

	@Override
	public List<ControllerDTO> viewApplicationStatus(ServiceDTO serviceDTO) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'viewApplicationStatus'");
	}

	@Override
	public ControllerDTO submitApplication(ServiceDTO serviceDTO) {
		LeaveApplicationControllerDTO response = new LeaveApplicationControllerDTO();
		CompensationLeaveServiceDTO dto = (CompensationLeaveServiceDTO) serviceDTO.getAllAttribute();
        int year = dto.getLeavePeriodStart().getYear();
        
        //calculate leave duration, excluding weekends
        double daysRequested = calculateDuration(dto.getLeavePeriodStart(), dto.getLeavePeriodEnd(), dto.isHalfDay());
        
        // Check if counted days are zero ---
        if (daysRequested <= 0) {
            response.setApplicationResult(false);
            response.setLeaveApprovalReason("Cannot apply for leave only on weekends.");
            return response;
        }

        Optional<CompensationLedger> ledgerOpt = compRepo.findByEmployeeIdAndYearApplied(dto.getStaffId(), year);

        if (ledgerOpt.isPresent()) {
            CompensationLedger ledger = ledgerOpt.get();

            // Check balance
            if ((ledger.getEarnedDays() - ledger.getUsedDays()) >= daysRequested) {

                // create a new LeaveApplication record
                LeaveApplication newApp = new LeaveApplication();
                newApp.setEmployee(ledger.getEmployee());
                newApp.setStartDate(dto.getLeavePeriodStart());
                newApp.setEndDate(dto.getLeavePeriodEnd());
                newApp.setLeaveType(LeaveType.COMPENSATION);
                newApp.setLeaveStatus(LeaveStatus.APPLIED);
                newApp.setReason(dto.getReason());
                newApp.setHalfDay(dto.isHalfDay());
                
                laRepo.save(newApp);
                
                response.setApplicationResult(true);
                return response;
            }
        }

        response.setApplicationResult(false);
        response.setLeaveApprovalReason("Insufficient compensation balance.");
        return response;
	}

	private double calculateDuration(LocalDate start, LocalDate end, boolean isHalfDay) {
		
		double workingDaysCount = 0;
	    LocalDate current = start;

	    while (!current.isAfter(end)) {
	        DayOfWeek dow = current.getDayOfWeek();
	        
	        // Only count the day if it's NOT a weekend
	        if (dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY) {
	            workingDaysCount++;
	        }
	        current = current.plusDays(1);
	    }

	    // Apply the 0.5 multiplier if the half-day box is checked
	    if (isHalfDay) {
	        return workingDaysCount * 0.5;
	    }

	    return workingDaysCount;
	}
	
	
}
