package iss.nus.edu.sg.leave_application_processing_system.service.features.view_leave_requests;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.UpdateLeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.persistent.entity.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.persistent.enumeration.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.persistent.repository.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.UpdateLeaveRequestServiceDTO;

@Service
public class UpdateLeaveRequestService {

    private final LeaveApplicationRepository leaveApplicationRepository;

    public UpdateLeaveRequestService(LeaveApplicationRepository leaveApplicationRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
    }

    public ControllerDTO updateLeaveRequest(ServiceDTO serviceDTO) {
        UpdateLeaveRequestServiceDTO updateDto = (UpdateLeaveRequestServiceDTO) serviceDTO.getAllAttribute();
        UpdateLeaveRequestControllerDTO controllerDTO = new UpdateLeaveRequestControllerDTO();

        var leaveApplicationOptional = leaveApplicationRepository.findById(updateDto.getLeaveRequestId());
        if (leaveApplicationOptional.isEmpty()) {
            controllerDTO.setOperationResult(false);
            controllerDTO.setOperationComments("Leave request not found.");
            return controllerDTO;
        }

        LeaveApplication leaveApplication = leaveApplicationOptional.get();
        if (!leaveApplication.getEmployee().getId().equals(updateDto.getEmployeeId())) {
            controllerDTO.setOperationResult(false);
            controllerDTO.setOperationComments("You can only update your own leave request.");
            return controllerDTO;
        }

        if (leaveApplication.getLeaveStatus() == LeaveStatus.CANCELLED || leaveApplication.getLeaveStatus() == LeaveStatus.DELETED) {
            controllerDTO.setOperationResult(false);
            controllerDTO.setOperationComments("Cancelled or deleted leave requests cannot be updated.");
            return controllerDTO;
        }

        leaveApplication.setLeaveType(updateDto.getLeaveType());
        leaveApplication.setStartDate(updateDto.getStartDate());
        leaveApplication.setEndDate(updateDto.getEndDate());
        leaveApplication.setReason(updateDto.getReason());
        leaveApplication.setLeaveStatus(LeaveStatus.UPDATED);
        leaveApplication.setUpdatedDate(LocalDate.now());

        LeaveApplication savedLeave = leaveApplicationRepository.save(leaveApplication);
        if (savedLeave == null) {
            controllerDTO.setOperationResult(false);
            controllerDTO.setOperationComments("Failed to save leave request updates.");
            return controllerDTO;
        }

        controllerDTO.setOperationResult(true);
        controllerDTO.setOperationComments("Leave request updated successfully.");
        controllerDTO.setLeaveRequestId(savedLeave.getId());
        controllerDTO.setLeaveType(savedLeave.getLeaveType());
        controllerDTO.setLeaveStatus(savedLeave.getLeaveStatus());
        controllerDTO.setStartDate(savedLeave.getStartDate());
        controllerDTO.setEndDate(savedLeave.getEndDate());
        controllerDTO.setReason(savedLeave.getReason());

        return controllerDTO;
    }
}
