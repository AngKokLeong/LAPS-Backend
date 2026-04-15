package iss.nus.edu.sg.leave_application_processing_system.controller;

import java.util.List;

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
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.SubordinateLeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.helper.OTClaimStatus;
import iss.nus.edu.sg.leave_application_processing_system.service.OverTimeClaimService;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.LeaveApprovalServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ManagerQueryServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.implementation.TeamManagementService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager")
public class ManagerController {

	private final TeamManagementService teamMngService;
	private final OverTimeClaimService overTimeClaimService;
	
	public ManagerController(TeamManagementService teamMngService, OverTimeClaimService overTimeClaimService) {
		this.teamMngService = teamMngService;
		this.overTimeClaimService = overTimeClaimService;
	}
	
	@GetMapping("/team-leave-history")
	public String teamLeaveHistory(HttpSession session) {     
		String role = (String) session.getAttribute("userRole");
		
		if (role == null || role.toString().isEmpty()) return "redirect:/";

	    if (!"manager".equals(role)) {
	        return "redirect:/staff";
	    }
		
		return "team-leave-history";       
	}
	
	@GetMapping("/view-team-members-leave")
	public String teamMembersLeave(HttpSession session, Model model) {
		
		//check session role
		String role = (String) session.getAttribute("userRole");
		if (role == null || role.toString().isEmpty()) return "redirect:/";

	    if (!"manager".equals(role)) {
	        return "redirect:/staff";
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
	public String manageLeaveRequests(HttpSession session, Model model) {
		
		//check session role
		String role = (String) session.getAttribute("userRole");
		if (role == null || role.toString().isEmpty()) return "redirect:/";

	    if (!"manager".equals(role)) {
	        return "redirect:/staff";
	    }

	    // Create serviceDTO (Who is the manager?)
	    ManagerQueryServiceDTO query = new ManagerQueryServiceDTO(1L);

	    // Fetch the list from the service
	    List<ControllerDTO> teamRequests = teamMngService.getSubordinateLeaveRequests(query);
	    
	    // Count  those with "PENDING" status
	    long pendingCount = teamRequests.stream()
	        .filter(req -> "PENDING".equals(((SubordinateLeaveRequestControllerDTO)req).getStatus()))
	        .count();

	    // Add to the Model for Thymeleaf
	    model.addAttribute("teamRequests", teamRequests);
	    model.addAttribute("pendingCount", pendingCount);
	    
		return "manage-leave-requests";       
	}
	
	@PostMapping("/leave/{id}/approve")
	public String approveLeave(@PathVariable Long id, @RequestParam(required = false) String remarks,
			RedirectAttributes ra, HttpSession session) {

		// check session role
		String role = (String) session.getAttribute("userRole");
		if (role == null || role.toString().isEmpty())
			return "redirect:/";

		if (!"manager".equals(role)) {
			return "redirect:/staff";
		}

		// Create ServiceDTO
		// Later, we need to get the current manager's ID from the session/security context
		Long currentManagerId = 1L;
		LeaveApprovalServiceDTO requestDto = new LeaveApprovalServiceDTO(id, "APPROVE", currentManagerId);

		// Optional: Add the comment if your DTO supports it
		requestDto.setManagerRemarks(remarks);

		// Call the Service
		// The service returns a LeaveApprovalControllerDTO
		LeaveApprovalControllerDTO result = (LeaveApprovalControllerDTO) teamMngService.processApproval(requestDto);

		// 3. Handle the result and prepare feedback for the UI
		if (result.isSuccess()) {
			ra.addFlashAttribute("successMessage", result.getMessage());
		} else {
			ra.addFlashAttribute("errorMessage", "Failed to process leave: " + result.getMessage());
		}


		// 4. Redirect back to the pending list page
		return "redirect:/manager/manage-leave-requests";
	}
	
	@PostMapping("/leave/{id}/reject")
	public String rejectLeave(@PathVariable Long id, @RequestParam(required = false) String remarks, RedirectAttributes ra,
			HttpSession session) {

		// check session role
		String role = (String) session.getAttribute("userRole");
		if (role == null || role.toString().isEmpty())
			return "redirect:/";

		if (!"manager".equals(role)) {
			return "redirect:/staff";
		}
		
		// Extra safety check in case JS validation is bypassed
		if (remarks == null || remarks.isBlank()) {
			ra.addFlashAttribute("errorMessage", "Remarks are required for rejection.");
			return "redirect:/manager//manage-leave-requests";
		}

		// Create ServiceDTO
		// Later, we need to get the current manager's ID from the session/security
		// context
		Long currentManagerId = 1L;
		LeaveApprovalServiceDTO requestDto = new LeaveApprovalServiceDTO(id, "REJECT", currentManagerId);
		requestDto.setManagerRemarks(remarks);

	    LeaveApprovalControllerDTO result = (LeaveApprovalControllerDTO) teamMngService.processApproval(requestDto);

	    if (result.isSuccess()) {
	        ra.addFlashAttribute("successMessage", result.getMessage());
	    } else {
	        ra.addFlashAttribute("errorMessage", "Failed to reject leave: " + result.getMessage());
	    }
	    
	    return "redirect:/manager/manage-leave-requests";
	}
	

	// OVERTIME WORKFLOW
	@GetMapping("/approve-ot-claim")
	public String approveOTClaim(HttpSession session) {
		String role = (String) session.getAttribute("userRole");
		
		if (role == null || role.toString().isEmpty()) return "redirect:/";

	    if (!"manager".equals(role)) {
	        return "redirect:/staff";
	    }
	    
		return "approve-ot-claim";       
	}

	
	// To View OT /manager/approve-ot-claim/list?status=PENDING
	@GetMapping("/approve-ot-claim/list")
	public String viewOTClaims(
		@RequestParam(defaultValue = "PENDING") OTClaimStatus status,
		HttpSession session,
		Model model) {

    String role = (String) session.getAttribute("userRole");
    if (role == null || !"manager".equals(role)) return "redirect:/staff";

    model.addAttribute(
            "otClaims",
            overTimeClaimService.findByStatus(status)
    );
    model.addAttribute("selectedStatus", status);

    return "approve-ot-claim";
	} 
	
	// APPROVE OT
	@PostMapping("/approve-ot-claim/{id}/approve")
	public String approveOT(@PathVariable Long id, HttpSession session) {
		String role = (String) session.getAttribute("userRole");
		if (role == null || !"manager".equals(role))
			return "redirect:/staff";
		
		overTimeClaimService.approveOTClaim(id);
		
		return "redirect:/manager/approve-ot-claim/list?status=PENDING";
	}

	// REJECT OT
	@PostMapping("/approve-ot-claim/{id}/reject")
	public String rejectOT(@PathVariable Long id, HttpSession session) {
		String role = (String) session.getAttribute("userRole");
		if (role == null || !"manager".equals(role)) return "redirect:/staff";
		
		overTimeClaimService.rejectOTClaim(id);
		return "redirect:/manager/approve-ot-claim/list?status=PENDING";
	}




	
	


	


}
