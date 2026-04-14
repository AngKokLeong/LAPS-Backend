package iss.nus.edu.sg.leave_application_processing_system.service;

import java.util.List;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;

public interface ManagerService {

	List<ControllerDTO> viewTeamLeaveBalances(ServiceDTO serviceDTO);
	ControllerDTO processApproval(ServiceDTO serviceDTO);
	List<ControllerDTO> getSubordinateLeaveRequests(ServiceDTO serviceDTO);
}
