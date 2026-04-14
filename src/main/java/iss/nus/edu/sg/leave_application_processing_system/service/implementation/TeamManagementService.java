package iss.nus.edu.sg.leave_application_processing_system.service.implementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveApprovalControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.SubordinateLeaveBalanceControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.SubordinateLeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.ManagerService;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.LeaveApprovalServiceDTO;
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
	
	@Override
	public ControllerDTO processApproval(ServiceDTO serviceDTO) {
		
        LeaveApprovalServiceDTO request = (LeaveApprovalServiceDTO) serviceDTO.getAllAttribute();

        LeaveApprovalControllerDTO response = new LeaveApprovalControllerDTO();
        response.setApplicationId(request.getApplicationId());
        
        // --- MOCK LOGIC START ---
        // Later, use request.getApplicationId() to find the record in the DB
        
        if (request.getApplicationId() == null) {
            response.setSuccess(false);
            response.setMessage("Error: Invalid Application ID.");
            return response;
        }

        // Simulate a successful update
        String actionTaken = request.getAction(); 

        response.setSuccess(true);
        
        if ("APPROVE".equalsIgnoreCase(actionTaken)) {
            response.setNewStatus("APPROVED");
            response.setMessage("Application #" + request.getApplicationId() + " has been successfully approved.");
        } else if ("REJECT".equalsIgnoreCase(actionTaken)) {
            response.setNewStatus("REJECTED");
            response.setMessage("Application #" + request.getApplicationId() + " has been rejected.");
        } else {
            // Fallback in case the string is something else (like the "REJECTE" typo)
            response.setSuccess(false);
            response.setMessage("Error: Unknown action '" + actionTaken + "'");
        }
        // --- MOCK LOGIC END ---

        return response;
	}
	
	@Override
	public List<ControllerDTO> getSubordinateLeaveRequests(ServiceDTO serviceDTO) {
		// Later, cast the ServiceDTO to filter by managerId
	    // ManagerQueryServiceDTO query = (ManagerQueryServiceDTO) serviceDTO.getAllAttribute();

	    List<ControllerDTO> mockList = new ArrayList<>();

	    // Staff 1: AhKau Tan (2 requests: Annual and Medical)
	    mockList.add(new SubordinateLeaveRequestControllerDTO(101L, "AhKau Tan", "Engineering", "Annual", 
	        LocalDate.of(2026, 5, 10), LocalDate.of(2026, 5, 12), 3.0, "Family Trip", LocalDate.of(2026, 4, 1), "PENDING"));
	    
	    mockList.add(new SubordinateLeaveRequestControllerDTO(102L, "AhKau Tan", "Engineering", "Medical", 
	        LocalDate.of(2026, 5, 20), LocalDate.of(2026, 5, 20), 1.0, "Dental Checkup", LocalDate.of(2026, 4, 5), "APPROVED"));

	    // Staff 2: AhLian Lee (Compensation - testing your half-day double)
	    mockList.add(new SubordinateLeaveRequestControllerDTO(103L, "AhLian Lee", "Product", "Compensation", 
	        LocalDate.of(2026, 6, 1), LocalDate.of(2026, 6, 1), 0.5, "Rest after weekend launch", LocalDate.of(2026, 4, 10), "PENDING"));

	    // Staff 3: AhBeng Lim (Medical)
	    mockList.add(new SubordinateLeaveRequestControllerDTO(104L, "AhBeng Lim", "Engineering", "Medical", 
	        LocalDate.of(2026, 4, 25), LocalDate.of(2026, 4, 27), 3.0, "Flu and fever", LocalDate.of(2026, 4, 12), "APPROVED"));

	    // Staff 4: AhHuat Ng (Annual)
	    mockList.add(new SubordinateLeaveRequestControllerDTO(105L, "AhHuat Ng", "Design", "Annual", 
	        LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 14), 10.0, "Overseas wedding", LocalDate.of(2026, 4, 14), "REJECTED"));

	    return mockList;
	}


}
