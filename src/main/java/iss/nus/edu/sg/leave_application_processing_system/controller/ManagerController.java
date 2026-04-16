package iss.nus.edu.sg.leave_application_processing_system.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveApprovalControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.OTClaimControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.SubordinateLeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.TeamLeaveHistoryControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.helper.OTClaimStatus;
import iss.nus.edu.sg.leave_application_processing_system.helper.Role;
import iss.nus.edu.sg.leave_application_processing_system.security.ApplicationUserDetails;
import iss.nus.edu.sg.leave_application_processing_system.service.OverTimeClaimService;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.LeaveApprovalServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ManagerQueryServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.TeamLeaveHistoryServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.implementation.TeamManagementService;
import iss.nus.edu.sg.leave_application_processing_system.service.implementation.ViewTeamLeaveHistoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager")
public class ManagerController {

	private final TeamManagementService teamMngService;
	private final OverTimeClaimService overTimeClaimService;
	private final ViewTeamLeaveHistoryService viewTeamLeaveHistoryService;

	public ManagerController(TeamManagementService teamMngService, OverTimeClaimService overTimeClaimService, ViewTeamLeaveHistoryService viewTeamLeaveHistoryService) {
		this.teamMngService = teamMngService;
		this.overTimeClaimService = overTimeClaimService;
		this.viewTeamLeaveHistoryService = viewTeamLeaveHistoryService;
	}

	@GetMapping("/team-leave-history")
	public String teamLeaveHistory(Model model, HttpSession session) {

		String extractedRoleData = (String) session.getAttribute("userRole");

		if (extractedRoleData == null || extractedRoleData.toString().isEmpty())
			return "redirect:/";

		Role role = Role.valueOf(extractedRoleData);

		if (!role.equals(Role.MANAGER)) {
			return "redirect:/staff"; // Send them home if they aren't a manager
		}

		// use the current employeeId to find all subordinates' leave records
		Long managerId = (Long) session.getAttribute("id");
		TeamLeaveHistoryServiceDTO teamLeaveHistoryService = new TeamLeaveHistoryServiceDTO();
		teamLeaveHistoryService.setEmployeeId(managerId);

		List<ControllerDTO> subordinateLeaveRecords = viewTeamLeaveHistoryService.retrieveCurrentSubordinateLeaveRecords(teamLeaveHistoryService);

		List<TeamLeaveHistoryControllerDTO> subordinateLeaveHistory = subordinateLeaveRecords.stream()
			.map(dto -> (TeamLeaveHistoryControllerDTO) dto.getAllAttribute())
			.collect(Collectors.toList());

		model.addAttribute("subordinateLeaveHistory", subordinateLeaveHistory);

		return "team-leave-history";
	}

	@GetMapping("/view-team-members-leave")
	public String teamMembersLeave(HttpSession session, Model model) {

		// check session role
		String extractedRoleData = (String) session.getAttribute("userRole");

		if (extractedRoleData == null || extractedRoleData.toString().isEmpty())
			return "redirect:/";

		Role role = Role.valueOf(extractedRoleData);

		if (!role.equals(Role.MANAGER)) {
			return "redirect:/staff"; // Send them home if they aren't a manager
		}

		// Create the ServiceDTO
		// Later, we need to get the managerID from the Login Session
		Long currentManagerId = 1L;
		ManagerQueryServiceDTO sDTO = new ManagerQueryServiceDTO(currentManagerId);

		// passing the ServiceDTO to service
		// The service returns a List of ControllerDTOs
		List<ControllerDTO> teamBalances = teamMngService.viewTeamLeaveBalances(sDTO);

		// Add to the Model for Thymeleaf
		model.addAttribute("teamBalances", teamBalances);

		return "view-team-members-leave";
	}

	@GetMapping("/manage-leave-requests")
	@PreAuthorize("hasRole('MANAGER')")
	public String manageLeaveRequests(Authentication authentication, Model model) {

		ApplicationUserDetails userDetails = (ApplicationUserDetails) authentication.getPrincipal();
	    Long managerId = userDetails.getEmployee().getId();

		ManagerQueryServiceDTO query = new ManagerQueryServiceDTO(managerId);

		// Fetch the list from the service
		List<ControllerDTO> teamRequests = teamMngService.getSubordinateLeaveRequests(query);

		// Count those with "PENDING" status
		long pendingCount = teamRequests.stream()
				.map(req -> (SubordinateLeaveRequestControllerDTO) req)
	            .filter(req -> "APPLIED".equals(req.getStatus()) || "UPDATED".equals(req.getStatus()))
	            .count();

		// Add to the Model for Thymeleaf
		model.addAttribute("teamRequests", teamRequests);
		model.addAttribute("pendingCount", pendingCount);

		return "manage-leave-requests";
	}

	@PostMapping("/leave/{id}/approve")
	@PreAuthorize("hasRole('MANAGER')")
	public String approveLeave(@PathVariable Long id, @RequestParam(required = false) String remarks,
			@AuthenticationPrincipal ApplicationUserDetails userDetails, RedirectAttributes ra) {
		
		try {
			Long currentManagerId = userDetails.getEmployee().getId();
			LeaveApprovalServiceDTO requestDto = new LeaveApprovalServiceDTO(id, "APPROVE", currentManagerId);
			requestDto.setManagerRemarks(remarks);

			LeaveApprovalControllerDTO result = (LeaveApprovalControllerDTO) teamMngService.processApproval(requestDto);

			ra.addFlashAttribute("successMessage", result.getMessage());
		} catch (IllegalArgumentException | EntityNotFoundException e) {
			// The Service has already rolled back! Now we just inform the user.
			ra.addFlashAttribute("errorMessage", e.getMessage());
		} catch (Exception e) {
	        e.printStackTrace(); 
	        ra.addFlashAttribute("errorMessage", "DEBUG ERROR: " + e.toString());
		}

		return "redirect:/manager/manage-leave-requests";
	}

	@PostMapping("/leave/{id}/reject")
	@PreAuthorize("hasRole('MANAGER')")
	public String rejectLeave(@PathVariable Long id, @RequestParam(required = false) String remarks,
			@AuthenticationPrincipal ApplicationUserDetails userDetails, RedirectAttributes ra) {

		try {
	        // Mandatory Validation for Rejection
	        if (remarks == null || remarks.isBlank()) {
	            ra.addFlashAttribute("errorMessage", "A reason is required to reject a leave request.");
	            return "redirect:/manager/manage-leave-requests";
	        }

	        Long currentManagerId = userDetails.getEmployee().getId();
	        LeaveApprovalServiceDTO requestDto = new LeaveApprovalServiceDTO(id, "REJECT", currentManagerId);
	        requestDto.setManagerRemarks(remarks);

	        LeaveApprovalControllerDTO result = (LeaveApprovalControllerDTO) teamMngService.processApproval(requestDto);

	        ra.addFlashAttribute("successMessage", result.getMessage());

	    } catch (RuntimeException e) {
	        ra.addFlashAttribute("errorMessage", "Could not reject request: " + e.getMessage());
	    } catch (Exception e) {
	    	e.printStackTrace(); 
	        ra.addFlashAttribute("errorMessage", "DEBUG ERROR: " + e.toString());
	    }

		return "redirect:/manager/manage-leave-requests";
	}

	// OVERTIME WORKFLOW
	@GetMapping("/approve-ot-claim")
	public String approveOTClaim(HttpSession session, Model model) {
		// check session role
		String extractedRoleData = (String) session.getAttribute("userRole");
		if (extractedRoleData == null || extractedRoleData.toString().isEmpty())
			return "redirect:/";
		Role role = Role.valueOf(extractedRoleData);
		if (!role.equals(Role.MANAGER)) {
			return "redirect:/staff"; // Send them home if they aren't a manager
		}
		
		// 1. Get the current manager's ID 
	    // (For now hardcode this, later get it from Session/Security context)
		Long managerId = (Long) session.getAttribute("id");
		ManagerQueryServiceDTO query = new ManagerQueryServiceDTO(managerId);

	    // 2. Call the service to get the list of DTOs
	    List<ControllerDTO> otClaims = teamMngService.getSubordinateOTClaims(query);
	    long pendingCount = otClaims.stream()
				.filter(claim -> ((OTClaimControllerDTO) claim).getStatus() == OTClaimStatus.PENDING)
				.count();

	    // 3. Add the list to the Model
	    model.addAttribute("otClaims", otClaims);
	    model.addAttribute("pendingCount", pendingCount);

		return "approve-ot-claim";
	}

	// To View OT /manager/approve-ot-claim/list?status=PENDING
	@GetMapping("/approve-ot-claim/list")
	public String viewOTClaims(
			@RequestParam(defaultValue = "PENDING") OTClaimStatus status,
			HttpSession session,
			Model model) {

		String extractedRoleData = (String) session.getAttribute("userRole");

		if (extractedRoleData == null || extractedRoleData.toString().isEmpty())
			return "redirect:/";

		Role role = Role.valueOf(extractedRoleData);

		if (!role.equals(Role.MANAGER)) {
			return "redirect:/staff"; // Send them home if they aren't a manager
		}

		model.addAttribute(
				"otClaims",
				overTimeClaimService.findByStatus(status));
		model.addAttribute("selectedStatus", status);

		return "approve-ot-claim";
	}

	// APPROVE OT
	@PostMapping("/approve-ot-claim/{id}/approve")
	public String approveOT(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
		String extractedRoleData = (String) session.getAttribute("userRole");

		if (extractedRoleData == null || extractedRoleData.toString().isEmpty())
			return "redirect:/";

		Role role = Role.valueOf(extractedRoleData);

		if (!role.equals(Role.MANAGER)) {
			return "redirect:/staff"; // Send them home if they aren't a manager
		}
		
		ra.addFlashAttribute("successMessage", "OT Claim Approved");

		overTimeClaimService.approveOTClaim(id);

		return "redirect:/manager/approve-ot-claim";
	}

	// REJECT OT
	@PostMapping("/approve-ot-claim/{id}/reject")
	public String rejectOT(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
		String extractedRoleData = (String) session.getAttribute("userRole");

		if (extractedRoleData == null || extractedRoleData.toString().isEmpty())
			return "redirect:/";

		Role role = Role.valueOf(extractedRoleData);

		if (!role.equals(Role.MANAGER)) {
			return "redirect:/staff"; // Send them home if they aren't a manager
		}
		
		ra.addFlashAttribute("successMessage", "OT Claim Rejected");

		overTimeClaimService.rejectOTClaim(id);
		return "redirect:/manager/approve-ot-claim";
	}

}
