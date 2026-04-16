package iss.nus.edu.sg.leave_application_processing_system.service.implementation;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveApprovalControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.OTClaimControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.SubordinateLeaveBalanceControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.SubordinateLeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.model.OverTimeClaim;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.OverTimeClaimRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.ManagerService;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.LeaveApprovalServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ManagerQueryServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;

@Service
public class TeamManagementService implements ManagerService {

	private final OverTimeClaimRepository otClaimRepo;
	private final LeaveApplicationRepository laRepo;
	
	public TeamManagementService(OverTimeClaimRepository otClaimRepo,
			LeaveApplicationRepository laRepo) {
		this.otClaimRepo = otClaimRepo;
		this.laRepo = laRepo;
	}
	

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
		
	    ManagerQueryServiceDTO query = (ManagerQueryServiceDTO) serviceDTO.getAllAttribute();
	    Long managerId = query.getManagerId();

	    List<LeaveApplication> leaveApplications = laRepo.findSubordinateLeaves(managerId);

	    return leaveApplications.stream()
	    		.map(leave -> {
	                // Calculate duration on the fly
	                double duration = calculateDuration(leave.getStartDate(), leave.getEndDate());
	                
	                return new SubordinateLeaveRequestControllerDTO(
	                    leave.getId(),
	                    leave.getEmployee().getName(),
	                    leave.getEmployee().getDepartment(),
	                    leave.getLeaveType().toString(),
	                    leave.getStartDate(),
	                    leave.getEndDate(),
	                    duration, // Use the calculated value here
	                    leave.getReason(),
	                    leave.getAppliedDate(),
	                    leave.getLeaveStatus().toString()
	                );
	            }).collect(Collectors.toList());   
	    
	}

	public List<ControllerDTO> getSubordinateOTClaims(ServiceDTO serviceDTO) {
		ManagerQueryServiceDTO query = (ManagerQueryServiceDTO) serviceDTO.getAllAttribute();
		
		List<OverTimeClaim> claims = otClaimRepo.findSubordinateClaimsCustomSort(query.getManagerId()); 
		List<ControllerDTO> otList = new ArrayList<>();
		
		for (OverTimeClaim claim : claims) {
	        Duration d = Duration.between(claim.getStartDateTime(), claim.getEndDateTime());
	        long hours = d.toHours();
	        long mins = d.toMinutes() % 60;
	        String formattedDuration = hours + "h " + mins + "m";
	        
	        OTClaimControllerDTO dto = new OTClaimControllerDTO(
		            claim.getId(),
		            claim.getEmployee().getName(),
		            claim.getEmployee().getDepartment(),
		            claim.getStartDateTime(),
		            claim.getEndDateTime(),
		            formattedDuration,
		            claim.getStatus(),
		            claim.getOtDescription()
		    );

	        otList.add(dto);
	    }

	    return otList;
	}
	
	// helper method to calculate business day (PH not considered yet)
	// can replace with KL's method later
	private double calculateDuration(LocalDate start, LocalDate end) {
	    if (start == null || end == null || start.isAfter(end)) {
	        return 0.0;
	    }

	    long days = 0;
	    LocalDate current = start;

	    while (!current.isAfter(end)) {
	        DayOfWeek dow = current.getDayOfWeek();
	        // Check if the day is NOT Saturday or Sunday
	        if (dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY) {
	            days++;
	        }
	        current = current.plusDays(1);
	    }
	    
	    return (double) days;
	}
	
}
