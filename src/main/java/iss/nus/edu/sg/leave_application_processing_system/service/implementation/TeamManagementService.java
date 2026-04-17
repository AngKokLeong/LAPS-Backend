package iss.nus.edu.sg.leave_application_processing_system.service.implementation;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveApprovalControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.OTClaimControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.SubordinateLeaveBalanceControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.SubordinateLeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;
import iss.nus.edu.sg.leave_application_processing_system.model.CompensationLedger;
import iss.nus.edu.sg.leave_application_processing_system.model.Employee;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveEntitlement;
import iss.nus.edu.sg.leave_application_processing_system.model.OverTimeClaim;
import iss.nus.edu.sg.leave_application_processing_system.repo.CompensationLedgerRepository;
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
	private final CompensationLedgerRepository clRepo;
	
	public TeamManagementService(OverTimeClaimRepository otClaimRepo,
			LeaveApplicationRepository laRepo, LeaveEntitlementRepository lEntitlementRepo,
			CompensationLedgerRepository clRepo) {
		this.otClaimRepo = otClaimRepo;
		this.laRepo = laRepo;
		this.lEntitlementRepo = lEntitlementRepo;
		this.clRepo = clRepo;
	}
	

	@Override
	public List<ControllerDTO> viewTeamLeaveBalances(ServiceDTO serviceDTO) {

        ManagerQueryServiceDTO input = (ManagerQueryServiceDTO) serviceDTO.getAllAttribute();
        Long managerId = input.getManagerId();
		
        int currentYear = LocalDate.now().getYear();
        
        // Fetch Entitlements (Annual/Medical)
        List<LeaveEntitlement> entitlements = lEntitlementRepo.findAllByManagerId(managerId, currentYear);
        
        // Fetch Compensation (OT)
        List<CompensationLedger> compLedgers = clRepo.findAllByManagerId(managerId);


        // Map to group by Employee ID
        Map<Long, SubordinateLeaveBalanceControllerDTO> balanceMap = new HashMap<>();

        // Process Entitlements (Annual/Medical)
        for (LeaveEntitlement ent : entitlements) {
            Employee emp = ent.getEmployeeId();
            Long empId = emp.getId();

            // Create DTO if not exists, and populate profile info
            SubordinateLeaveBalanceControllerDTO dto = balanceMap.computeIfAbsent(empId, id -> {
                SubordinateLeaveBalanceControllerDTO newDto = new SubordinateLeaveBalanceControllerDTO();
                newDto.setEmployeeName(emp.getName());
                newDto.setEmail(emp.getEmail());
                newDto.setDepartment(emp.getDepartment());
                return newDto;
            });

            // Calculate and set balances
            int remaining = ent.getTotalDays() - ent.getUsedDays();
            if (ent.getLeaveType() == LeaveType.ANNUAL) {
                dto.setAnnualBalance(remaining);
            } else if (ent.getLeaveType() == LeaveType.MEDICAL) {
                dto.setMedicalBalance(remaining);
            }
        }

        // Process Compensation Ledger (OT)
        for (CompensationLedger comp : compLedgers) {
            Long empId = comp.getEmployee().getId();
            SubordinateLeaveBalanceControllerDTO dto = balanceMap.get(empId);
            
            // If the employee didn't have entitlements, we create the DTO here too
            if (dto == null) {
                Employee emp = comp.getEmployee();
                dto = new SubordinateLeaveBalanceControllerDTO();
                dto.setEmployeeName(emp.getName());
                dto.setEmail(emp.getEmail());
                dto.setDepartment(emp.getDepartment());
                balanceMap.put(empId, dto);
            }

            double balance = comp.getEarnedDays() - comp.getUsedDays();
            dto.setCompensationBalance(balance);
        }

        // Final Calculation for Total Balance
        for (SubordinateLeaveBalanceControllerDTO dto : balanceMap.values()) {
            dto.setTotalBalance(dto.getAnnualBalance() + dto.getMedicalBalance() + dto.getCompensationBalance());
        }

        return new ArrayList<>(balanceMap.values());
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
            
        	if (application.getLeaveType() == LeaveType.COMPENSATION) {
                updateCompensationLedger(application);
            } else {
                updateEntitlement(application);
            }

            // Update Application Status
            application.setLeaveStatus(LeaveStatus.APPROVED);
            application.setMgrRemarks(request.getManagerRemarks());
            
            response.setNewStatus("APPROVED");
            response.setMessage("Application approved and balance updated.");
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
	
	private double calculateDuration(LocalDate start, LocalDate end, boolean isHalfDay) {
		
		double workingDaysCount = 0;
	    LocalDate current = start;

	    while (!current.isAfter(end)) {
	        DayOfWeek dow = current.getDayOfWeek();
	        
	        // Only count the day if it's NOT a weekend
	        if (dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY) {
	            workingDaysCount++;
	        }
	        current = current.plusDays(1);
	    }

	    // Apply the 0.5 multiplier if the half-day box is checked
	    if (isHalfDay) {
	        return workingDaysCount * 0.5;
	    }

	    return workingDaysCount;
	}
	
	// helper method to update leave entitlement
	private void updateEntitlement(LeaveApplication app) {
	    // Find entitlement by Employee, LeaveType, and Year
	    int year = app.getStartDate().getYear();
	    LeaveEntitlement entitlement = lEntitlementRepo
	            .findByEmployeeId_IdAndLeaveTypeAndYearApplied(app.getEmployee().getId(), app.getLeaveType(), year)
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
	
	// helper method to update compensation ledger
	private void updateCompensationLedger(LeaveApplication application) {
	    int year = application.getStartDate().getYear();
	    Long employeeId = application.getEmployee().getId();

	    // Fetch the ledger record
	    CompensationLedger ledger = clRepo.findByEmployeeIdAndYearApplied(employeeId, year)
	            .orElseThrow(() -> new EntityNotFoundException("Compensation Ledger not found for this employee"));

	    // Calculate the actual working days (excluding weekends)
	    double daysToDeduct = calculateDuration(
	        application.getStartDate(), 
	        application.getEndDate(), 
	        application.isHalfDay()
	    );
	    
	    // Increment used days
	    double newUsedDays = ledger.getUsedDays() + calculateDuration(application.getStartDate(), application.getEndDate());
	    
	    // Safety check: Don't exceed total allowed
	    if (newUsedDays > ledger.getEarnedDays()) {
	        throw new IllegalArgumentException("Approval failed: Employee has insufficient compensation leave balance.");
	    }

	    // Update the used days
	    ledger.setUsedDays(ledger.getUsedDays() + daysToDeduct);

	    // 4. Save to repository
	    clRepo.save(ledger);
	}
	
	
	
}
