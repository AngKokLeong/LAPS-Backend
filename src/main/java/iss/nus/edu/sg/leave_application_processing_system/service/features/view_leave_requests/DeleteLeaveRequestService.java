package iss.nus.edu.sg.leave_application_processing_system.service.features.view_leave_requests;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.DeleteLeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.DeleteLeaveRequestServiceDTO;

@Service
public class DeleteLeaveRequestService {

    private final LeaveApplicationRepository leaveApplicationRepository;

    public DeleteLeaveRequestService(LeaveApplicationRepository leaveApplicationRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
    }

    public ControllerDTO deleteLeaveRequest(ServiceDTO serviceDTO) {
        DeleteLeaveRequestServiceDTO deleteDto = (DeleteLeaveRequestServiceDTO) serviceDTO.getAllAttribute();
        DeleteLeaveRequestControllerDTO controllerDTO = new DeleteLeaveRequestControllerDTO();

        var leaveApplicationOptional = leaveApplicationRepository.findById(deleteDto.getLeaveRequestId());
        if (leaveApplicationOptional.isEmpty()) {
            controllerDTO.setOperationResult(false);
            controllerDTO.setOperationComments("Leave request not found.");
            return controllerDTO;
        }

        LeaveApplication leaveApplication = leaveApplicationOptional.get();
        
        // Verify ownership
        if (!leaveApplication.getEmployee().getId().equals(deleteDto.getEmployeeId())) {
            controllerDTO.setOperationResult(false);
            controllerDTO.setOperationComments("You can only delete your own leave request.");
            return controllerDTO;
        }

        // Only allow deletion of APPLIED, UPDATED, 
        LeaveStatus currentStatus = leaveApplication.getLeaveStatus();
        if (currentStatus == LeaveStatus.APPROVED) {
            controllerDTO.setOperationResult(false);
            controllerDTO.setOperationComments("Approved leave requests cannot be deleted. Please cancel them first.");
            return controllerDTO;
        }

        if (currentStatus == LeaveStatus.DELETED) {
            controllerDTO.setOperationResult(false);
            controllerDTO.setOperationComments("This leave request has already been deleted.");
            return controllerDTO;
        }

        if (currentStatus == LeaveStatus.CANCELLED) {
            controllerDTO.setOperationResult(false);
            controllerDTO.setOperationComments("This leave request has already been cancelled.");
            return controllerDTO;
        }

        if (currentStatus == LeaveStatus.REJECTED) {
            controllerDTO.setOperationResult(false);
            controllerDTO.setOperationComments("This leave request has already been rejected.");
            return controllerDTO;
        }

        if (currentStatus == LeaveStatus.ARCHIVED) {
            controllerDTO.setOperationResult(false);
            controllerDTO.setOperationComments("This leave request has already been archived.");
            return controllerDTO;
        }

        

        if (deleteDto.getReason() != null && !deleteDto.getReason().isBlank()) {
            leaveApplication.setReason(deleteDto.getReason());
        }

        // Set status to DELETED
        leaveApplication.setLeaveStatus(LeaveStatus.DELETED);
        LeaveApplication savedLeave = leaveApplicationRepository.save(leaveApplication);

        if (savedLeave == null) {
            controllerDTO.setOperationResult(false);
            controllerDTO.setOperationComments("Failed to delete leave request.");
            return controllerDTO;
        }

        controllerDTO.setOperationResult(true);
        controllerDTO.setOperationComments("Leave request deleted successfully.");
        controllerDTO.setLeaveRequestId(savedLeave.getId());
        controllerDTO.setLeaveType(savedLeave.getLeaveType());
        controllerDTO.setLeaveStatus(savedLeave.getLeaveStatus());

        return controllerDTO;
    }
}