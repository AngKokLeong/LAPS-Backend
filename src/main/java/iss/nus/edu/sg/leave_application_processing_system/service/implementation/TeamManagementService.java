package iss.nus.edu.sg.leave_application_processing_system.service.implementation;

import java.util.ArrayList;
import java.util.List;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.SubordinateLeaveBalanceControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.ManagerService;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;

public class TeamManagementService implements ManagerService {

	@Override
	public List<ControllerDTO> viewTeamLeaveBalances(ServiceDTO serviceDTO) {
		
		List<ControllerDTO> teamLeaveBalances = new ArrayList<>();

        // USE MOCK DATA HERE
        // Will need to call the Repository later
        SubordinateLeaveBalanceControllerDTO mock1 = new SubordinateLeaveBalanceControllerDTO();
        mock1.setEmployeeName("Alice Tan");
        mock1.setDepartment("IT");
        mock1.setAnnualBalance(12);
        mock1.setMedicalBalance(14);
        mock1.setCompensationBalance(2);
        mock1.setTotalBalance(28);

        teamLeaveBalances.add(mock1);

        return teamLeaveBalances;
	}

}
