package iss.nus.edu.sg.leave_application_processing_system.service.features.leave_application;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveApplicationControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.persistent.entity.Employee;
import iss.nus.edu.sg.leave_application_processing_system.persistent.entity.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.persistent.enumeration.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.persistent.enumeration.LeaveType;
import iss.nus.edu.sg.leave_application_processing_system.persistent.repository.EmployeeRepository;
import iss.nus.edu.sg.leave_application_processing_system.persistent.repository.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.MedicalLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;

import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.medical_leave_specification.MedicalLeavePeriodAreInChronologicalIncreasingOrder;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.medical_leave_specification.MedicalLeavePeriodWIthinCalendarYear;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.medical_leave_specification.MedicalLeavePeriodWithinSixtyDays;
import iss.nus.edu.sg.leave_application_processing_system.service.features.shared_service.LeaveValidationService;

@Service
public class MedicalLeaveApplicationService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    private LeaveValidationService validationService;

    public ControllerDTO submitApplication(ServiceDTO serviceDTO) {

        MedicalLeaveServiceDTO dto = (MedicalLeaveServiceDTO) serviceDTO.getAllAttribute();

        LeaveApplicationControllerDTO valDto = new LeaveApplicationControllerDTO();
        valDto.setStaffId(dto.getStaffId());
        valDto.setType(LeaveType.MEDICAL);
        valDto.setStartDate(dto.getLeavePeriodStart());
        valDto.setEndDate(dto.getLeavePeriodEnd());
        valDto.setHalfDay(dto.isHalfDay());

        double totalNumberOfLeaveApplied = validationService.calculateDays(
                valDto.getStartDate(),
                valDto.getEndDate(),
                valDto.isHalfDay());

        // 4. Run Universal Validation (Balance, Overlap, 14-day rule, etc.)
        String errorMessage = validationService.validate(valDto);

        if (errorMessage != null) {
            LeaveApplicationControllerDTO errorResponse = new LeaveApplicationControllerDTO(false);
            errorResponse.setLeaveApprovalReason(errorMessage);
            return errorResponse;
        }

        dto.setLeaveDuration(String.valueOf(totalNumberOfLeaveApplied));

        dto.setLeaveAppliedOn(LocalDate.now());
        dto.setLeaveRequestStatus(LeaveStatus.APPLIED);

        boolean status = new MedicalLeavePeriodAreInChronologicalIncreasingOrder()
                .and(new MedicalLeavePeriodWIthinCalendarYear())
                .and(new MedicalLeavePeriodWithinSixtyDays())
                .toPredicate()
                .test(dto, dto);

        if (!status) {
            // possible to add error message into the object
            LeaveApplicationControllerDTO errorResponse = new LeaveApplicationControllerDTO(false);
            errorResponse.setLeaveApprovalReason(errorMessage);
            return new LeaveApplicationControllerDTO(false);
        }

        Optional<Employee> employee = employeeRepository.findById(dto.getStaffId());

        if (employee.isPresent()) {
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

            if (result != null)
                return new LeaveApplicationControllerDTO(dto.getType(), dto.getStaffId(), dto.getLeavePeriodStart(),
                        dto.getLeavePeriodEnd(), dto.getLeaveRequestStatus(), dto.getLeaveDuration(),
                        dto.getLeaveAppliedOn(), true);
        }

        return new LeaveApplicationControllerDTO(false);
    }

}
