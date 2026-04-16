package iss.nus.edu.sg.leave_application_processing_system.service.implementation;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveApprovalControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.OTClaimControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.SubordinateLeaveBalanceControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.SubordinateLeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveEntitlement;
import iss.nus.edu.sg.leave_application_processing_system.model.OverTimeClaim;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveEntitlementRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.OverTimeClaimRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.ManagerService;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.LeaveApprovalServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ManagerQueryServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TeamManagementService implements ManagerService {

	private final OverTimeClaimRepository otClaimRepo;
	private final LeaveApplicationRepository laRepo;
	private final LeaveEntitlementRepository lEntitlementRepo;
	
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
	@Transactional
	public ControllerDTO processApproval(ServiceDTO serviceDTO) {
		
        LeaveApprovalServiceDTO request = (LeaveApprovalServiceDTO) serviceDTO.getAllAttribute();

        LeaveApprovalControllerDTO response = new LeaveApprovalControllerDTO();
        response.setApplicationId(request.getApplicationId());
        
     // Find the application
        LeaveApplication application = laRepo.findById(request.getApplicationId())
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));

        String actionTaken = request.getAction();

        if ("APPROVE".equalsIgnoreCase(actionTaken)) {
            // Handle Entitlement Update
            updateEntitlement(application);

            // Update Application Status
            application.setLeaveStatus(LeaveStatus.APPROVED);
            application.setMgrRemarks(request.getManagerRemarks());
            
            response.setNewStatus("APPROVED");
            response.setMessage("Application approved and entitlement updated.");
            response.setSuccess(true);
        } 
        else if ("REJECT".equalsIgnoreCase(actionTaken)) {
            application.setLeaveStatus(LeaveStatus.REJECTED);
            application.setMgrRemarks(request.getManagerRemarks());
            
            response.setNewStatus("REJECTED");
            response.setMessage("Application has been rejected.");
            response.setSuccess(true);
        } 
        else {
            response.setSuccess(false);
            response.setMessage("Error: Unknown action '" + actionTaken + "'");
            return response;
        }

        // 4. Save the updated application
        laRepo.save(application);
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
	
	// helper method to update leave entitlement
	private void updateEntitlement(LeaveApplication app) {
	    // Find entitlement by Employee, LeaveType, and Year
	    int year = app.getStartDate().getYear();
	    LeaveEntitlement entitlement = lEntitlementRepo
	            .findByEmployeeIdAndLeaveTypeAndYearApplied(app.getEmployee().getId(), app.getLeaveType(), year)
	            .orElseThrow(() -> new IllegalStateException("No entitlement record found for this employee/year"));

	    // Increment used days
	    int newUsedDays = entitlement.getUsedDays() + (int) calculateDuration(app.getStartDate(), app.getEndDate());
	    
	    // Safety check: Don't exceed total allowed
	    if (newUsedDays > entitlement.getTotalDays()) {
	        throw new IllegalArgumentException("Approval failed: Employee has insufficient leave balance.");
	    }

	    entitlement.setUsedDays(newUsedDays);
	    lEntitlementRepo.save(entitlement);
	}
	
}
