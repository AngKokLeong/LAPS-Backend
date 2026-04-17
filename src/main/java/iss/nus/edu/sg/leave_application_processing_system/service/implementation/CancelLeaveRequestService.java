package iss.nus.edu.sg.leave_application_processing_system.service.implementation;

import java.time.LocalDate;

import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.CancelLeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveEntitlementRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.CancelLeaveRequestServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;

@Service
public class CancelLeaveRequestService {

    private LeaveApplicationRepository leaveApplicationRepository;
	private LeaveEntitlementRepository leaveEntitlementRepository;

    public CancelLeaveRequestService(LeaveApplicationRepository leaveApplicationRepository, LeaveEntitlementRepository leaveEntitlementRepository){
        this.leaveApplicationRepository = leaveApplicationRepository;
		this.leaveEntitlementRepository = leaveEntitlementRepository;
    }
    
    public ControllerDTO cancelLeaveRequest(ServiceDTO serviceDTO){

		CancelLeaveRequestServiceDTO cancelLeaveRequestServiceDTO = (CancelLeaveRequestServiceDTO) serviceDTO.getAllAttribute();
		CancelLeaveRequestControllerDTO cancelLeaveRequestControllerDTO = new CancelLeaveRequestControllerDTO();

		if (cancelLeaveRequestServiceDTO.getStartDate().until(LocalDate.now(), ChronoUnit.DAYS) < -1 ){
			cancelLeaveRequestControllerDTO.setOperationResult(false);
			
			return cancelLeaveRequestControllerDTO;
		}
		


		// Find the leave application
		var leaveApplication = leaveApplicationRepository.findById(cancelLeaveRequestServiceDTO.getLeaveRequestId());

		


		if (leaveApplication.isEmpty()) {
			cancelLeaveRequestControllerDTO.setOperationResult(false);
			cancelLeaveRequestControllerDTO.setOperationComments("Leave request not found.");
			
			return cancelLeaveRequestControllerDTO;
		}

		
		// Check if it belongs to the current user and is approved
		if (!leaveApplication.get().getEmployee().getId().equals(cancelLeaveRequestServiceDTO.getEmployeeId()) || !leaveApplication.get().getLeaveStatus().equals(LeaveStatus.APPROVED)) {
			cancelLeaveRequestControllerDTO.setOperationComments("You can only cancel your own approved leave requests.");
			cancelLeaveRequestControllerDTO.setOperationResult(false);
			return cancelLeaveRequestControllerDTO;
		}

		// Update status to CANCELLED
		var leave = leaveApplication.get();
		leave.setLeaveStatus(LeaveStatus.CANCELLED);
		leave.setUpdatedDate(LocalDate.now());
		leave.setReason(cancelLeaveRequestServiceDTO.getReason());

		if (leaveApplicationRepository.save(leave) == null) {
			cancelLeaveRequestControllerDTO.setOperationResult(false);
			return cancelLeaveRequestControllerDTO;
		}

		// Restore leave balance only when cancellation occurs on or before the leave start date
		var entitlement = leave.getEntitlement();
		LocalDate today = LocalDate.now();
		if (entitlement != null && !today.isAfter(leave.getStartDate())) {
			int restoredDays = (int) ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate()) + 1;
			if (leave.isHalfDay()) {
				restoredDays = Math.max(restoredDays, 1);
			}

			int updatedUsedDays = entitlement.getUsedDays() - restoredDays;
			entitlement.setUsedDays(Math.max(updatedUsedDays, 0));
			leaveEntitlementRepository.save(entitlement);
		}

		cancelLeaveRequestControllerDTO.setOperationResult(true);

		return cancelLeaveRequestControllerDTO; 

    }


}
