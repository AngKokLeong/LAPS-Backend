package iss.nus.edu.sg.leave_application_processing_system.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.helper.OTClaimStatus;
import iss.nus.edu.sg.leave_application_processing_system.service.OverTimeClaimService;
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
	public String manageLeaveRequests(HttpSession session) {
		String role = (String) session.getAttribute("userRole");
		
		if (role == null || role.toString().isEmpty()) return "redirect:/";

	    if (!"manager".equals(role)) {
	        return "redirect:/staff";
	    }
	    
		return "manage-leave-requests";       
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
