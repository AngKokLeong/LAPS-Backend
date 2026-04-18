package iss.nus.edu.sg.leave_application_processing_system.service.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.jmapper.api.JMapperAPI;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveApplicationControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.model.Employee;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.repo.EmployeeRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.LeaveApplicationService;
import iss.nus.edu.sg.leave_application_processing_system.service.LeaveValidationService;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.AnnualLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;


import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.annual_leave_specification.AnnualLeavePeriodFromIsWorkingDaySpecification;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.annual_leave_specification.AnnualLeavePeriodLessThanOrEqualFourteenDaysSpecification;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.annual_leave_specification.AnnualLeavePeriodToIsWorkingDaySpecification;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.annual_leave_specification.AnnualLeavePeriodWithinTheMonthSpecification;

@Service
public class AnnualLeaveApplicationService implements LeaveApplicationService{
    

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;
    
    @Autowired
    private LeaveValidationService validationService;



    @Override
    public List<ControllerDTO> viewApplicationStatus(ServiceDTO serviceDTO) {
        throw new UnsupportedOperationException("Unimplemented method 'viewApplicationStatus'");
    }

    @Override
    public ControllerDTO submitApplication(ServiceDTO serviceDTO) {
        AnnualLeaveServiceDTO dto = (AnnualLeaveServiceDTO) serviceDTO.getAllAttribute();

        LeaveApplicationControllerDTO valDto = new LeaveApplicationControllerDTO();
        valDto.setStaffId(dto.getStaffId());
        valDto.setType(dto.getType());
        valDto.setStartDate(dto.getLeavePeriodStart());
        valDto.setEndDate(dto.getLeavePeriodEnd());
        valDto.setHalfDay(dto.isHalfDay());
        
        // Handles weekends + holidays
        double totalNumberOfLeaveApplied = validationService.calculateDays(
                dto.getLeavePeriodStart(), 
                dto.getLeavePeriodEnd(), 
                dto.isHalfDay()
            );

        // VALIDATION: Check balance and rules before proceeding
        // This replaces his boolean status check with a more detailed one
        String errorMessage = validationService.validate(valDto);
        
        if (errorMessage != null) {
            LeaveApplicationControllerDTO errorResponse = new LeaveApplicationControllerDTO(false);
            errorResponse.setLeaveApprovalReason(errorMessage);
            return errorResponse;
        }


        dto.setLeaveDuration(String.valueOf(totalNumberOfLeaveApplied));            
        
        dto.setLeaveAppliedOn(LocalDate.now());
        dto.setLeaveRequestStatus(LeaveStatus.APPLIED);

        boolean status = new AnnualLeavePeriodWithinTheMonthSpecification()
                    .and(new AnnualLeavePeriodLessThanOrEqualFourteenDaysSpecification())
                    .and(new AnnualLeavePeriodFromIsWorkingDaySpecification())
                    .and(new AnnualLeavePeriodToIsWorkingDaySpecification())
                    .toPredicate()
                    .test(dto, dto);

        if (!status) {
            //possible to add error message into the object
        	LeaveApplicationControllerDTO errorResponse = new LeaveApplicationControllerDTO(false);
            errorResponse.setLeaveApprovalReason("Leave request does not meet requirement.");
            return new LeaveApplicationControllerDTO(false);
        }


        Optional<Employee> employee = employeeRepository.findById(dto.getStaffId());

        if (employee.isPresent()){
            LeaveApplication leaveApplication = new LeaveApplication();
            
            leaveApplication.setLeaveType(dto.getType());
            leaveApplication.setEmployee(employee.get());


            leaveApplication.setStartDate(dto.getLeavePeriodStart());
            leaveApplication.setEndDate(dto.getLeavePeriodEnd());
            leaveApplication.setReason(dto.getReason());

            leaveApplication.setLeaveStatus(dto.getLeaveRequestStatus());
            leaveApplication.setAppliedDate(dto.getLeaveAppliedOn());
            leaveApplication.setHalfDay(dto.isHalfDay());

            LeaveApplication result = leaveApplicationRepository.save(leaveApplication);


            if (result != null) return new LeaveApplicationControllerDTO(dto.getType(), dto.getStaffId(), dto.getLeavePeriodStart(), dto.getLeavePeriodEnd(), dto.getLeaveRequestStatus(), dto.getLeaveDuration(), dto.getLeaveAppliedOn(), true);
        }


        return new LeaveApplicationControllerDTO(false);
    }


}
