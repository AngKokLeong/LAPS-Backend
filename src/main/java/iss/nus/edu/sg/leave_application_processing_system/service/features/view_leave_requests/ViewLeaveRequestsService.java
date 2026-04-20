package iss.nus.edu.sg.leave_application_processing_system.service.features.view_leave_requests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;

import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ViewLeaveRequestsServiceDTO;

@Service
public class ViewLeaveRequestsService {
    

    private final LeaveApplicationRepository leaveApplicationRepository;


    public ViewLeaveRequestsService(LeaveApplicationRepository leaveApplicationRepository){
        this.leaveApplicationRepository = leaveApplicationRepository;
    }

    public List<ControllerDTO> retrieveAllLeaveRequestByEmployeeId(ServiceDTO serviceDTO){
        
        ViewLeaveRequestsServiceDTO viewLeaveRequestsServiceDTO = (ViewLeaveRequestsServiceDTO) serviceDTO.getAllAttribute();

        //Retrieve the data from the database
		//need to pass the staffId into the method
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findByEmployeeIdOrderByStartDateDesc(viewLeaveRequestsServiceDTO.getStaffId());

		List<ControllerDTO> leaveRequestControllerDTOList = new ArrayList<>();

	
		for (LeaveApplication leaveApplication : leaveApplicationList){
			
			LeaveRequestControllerDTO leaveRequestControllerDTO = new LeaveRequestControllerDTO();
			
			leaveRequestControllerDTO.setLeaveRequestId(leaveApplication.getId());

			leaveRequestControllerDTO.setLeaveType(leaveApplication.getLeaveType());
			leaveRequestControllerDTO.setLeaveStatus(leaveApplication.getLeaveStatus());
			
			String leavePeriod = leaveApplication.getStartDate().getMonth().name() + " " + leaveApplication.getStartDate().getDayOfMonth() + " - " + leaveApplication.getEndDate().getMonth().name() + " " + leaveApplication.getEndDate().getDayOfMonth() + " " + leaveApplication.getEndDate().getYear();
			leaveRequestControllerDTO.setLeavePeriod(leavePeriod);
			
			leaveRequestControllerDTO.setStartDate(leaveApplication.getStartDate());
			leaveRequestControllerDTO.setEndDate(leaveApplication.getEndDate());

			leaveRequestControllerDTO.setCurrentDate(LocalDate.now());

			long fullDays = java.time.temporal.ChronoUnit.DAYS.between(leaveApplication.getStartDate(), leaveApplication.getEndDate()) + 1;
			double duration = leaveApplication.isHalfDay() ? fullDays - 0.5 : fullDays;
			String leaveDuration = duration % 1 == 0 ? (int)duration + " days" : duration + " days";
			leaveRequestControllerDTO.setLeaveDuration(leaveDuration);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
			String leaveAppliedOn = leaveApplication.getAppliedDate().format(formatter);
			leaveRequestControllerDTO.setLeaveAppliedOn(leaveAppliedOn);
			
			leaveRequestControllerDTO.setReason(leaveApplication.getReason());
			
			
			
			if (leaveApplication.getEmployee().getManager() != null) {
				leaveRequestControllerDTO.setLeaveApprover(leaveApplication.getEmployee().getManager().getName());
			}
			
			String leaveApprovalTransactionDate = leaveApplication.getUpdatedDate() != null ? leaveApplication.getUpdatedDate().format(formatter) : "";
			leaveRequestControllerDTO.setLeaveApprovalTransactionDate(leaveApprovalTransactionDate);
			
			leaveRequestControllerDTO.setLeaveApprovalReason(leaveApplication.getMgrRemarks());
			
			leaveRequestControllerDTOList.add(leaveRequestControllerDTO);
			
		}


		// Leave Request Card Structure
			// Leave Application Id
			// Leave Type
			// Date of the Leave Request Submitted
			// Duration
				// Leave Date From - Leave Date To
			
			// Total Number of Leave Days
				// N days
			
			// Leave Status (Leave Request Status)
			
			// Applied , Updated
				// Show Edit Request button and Delete Request button
			
			// Approved
				// Show Cancel Request button when the Leave Period have started
			
			// Rejected

			// Cancelled

			// Deleted


        return leaveRequestControllerDTOList;
    }



}
