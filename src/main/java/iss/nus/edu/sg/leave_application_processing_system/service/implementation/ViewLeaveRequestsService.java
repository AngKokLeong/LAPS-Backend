package iss.nus.edu.sg.leave_application_processing_system.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveEntitlementRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ViewLeaveRequestsServiceDTO;

@Service
public class ViewLeaveRequestsService {
    
    private final LeaveApplicationRepository leaveApplicationRepository;
    private final LeaveEntitlementRepository leaveEntitlementRepository;

    public ViewLeaveRequestsService(LeaveApplicationRepository leaveApplicationRepository, LeaveEntitlementRepository leaveEntitlementRepository){
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.leaveEntitlementRepository = leaveEntitlementRepository;
    }

    public List<ControllerDTO> retrieveAllLeaveRequest(ServiceDTO serviceDTO){
        
        ViewLeaveRequestsServiceDTO viewLeaveRequestsServiceDTO = (ViewLeaveRequestsServiceDTO) serviceDTO.getAllAttribute();

        //Retrieve the data from the database
		//need to pass the staffId into the method
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findByEmployeeId(viewLeaveRequestsServiceDTO.getStaffId());


        
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


        return null;
    }



}
