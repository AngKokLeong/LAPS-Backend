package iss.nus.edu.sg.leave_application_processing_system.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.SubordinateLeaveBalanceControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.ManagerService;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ManagerQueryServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;

@Service
public class TeamManagementService implements ManagerService {

	@Override
	public List<ControllerDTO> viewTeamLeaveBalances(ServiceDTO serviceDTO) {
		
		// Cast the serviceDTO input to get the Manager ID to find all subordinates under them
        // ManagerQueryServiceDTO input = (ManagerQueryServiceDTO) serviceDTO.getAllAttribute();
		
		List<ControllerDTO> teamLeaveBalances = new ArrayList<>();

        // USE MOCK DATA HERE
        // Will need to call the Repository later
        SubordinateLeaveBalanceControllerDTO mock1 = new SubordinateLeaveBalanceControllerDTO();
        mock1.setEmployeeName("AhBeng Tan");
        mock1.setEmail("ahbeng_tan@company.com");
        mock1.setDepartment("Engineering");
        mock1.setAnnualBalance(12 - 2); //simulate totalDays - usedDays
        mock1.setMedicalBalance(20 - 3);
        mock1.setCompensationBalance(2);
        int sum = mock1.getAnnualBalance() + mock1.getCompensationBalance() + mock1.getMedicalBalance();
        mock1.setTotalBalance(sum);
        teamLeaveBalances.add(mock1);
        
        SubordinateLeaveBalanceControllerDTO mock2 = new SubordinateLeaveBalanceControllerDTO();
        mock2.setEmployeeName("AhHuat Lim");
        mock2.setEmail("ahhuat_lim@company.com");
        mock2.setDepartment("Engineering");
        mock2.setAnnualBalance(14 - 2); //simulate totalDays - usedDays
        mock2.setMedicalBalance(40 - 5);
        mock2.setCompensationBalance(5);
        sum = mock2.getAnnualBalance() + mock2.getCompensationBalance() + mock2.getMedicalBalance();
        mock2.setTotalBalance(sum);
        teamLeaveBalances.add(mock2);
        
        SubordinateLeaveBalanceControllerDTO mock3 = new SubordinateLeaveBalanceControllerDTO();
        mock3.setEmployeeName("David Ong");
        mock3.setEmail("david_ong@company.com");
        mock3.setDepartment("IT");
        mock3.setAnnualBalance(18 - 5); //simulate totalDays - usedDays
        mock3.setMedicalBalance(40 - 3);
        mock3.setCompensationBalance(1);
        sum = mock3.getAnnualBalance() + mock3.getCompensationBalance() + mock3.getMedicalBalance();
        mock3.setTotalBalance(sum);
        teamLeaveBalances.add(mock3);

        return teamLeaveBalances;
	}

}
