package iss.nus.edu.sg.leave_application_processing_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class ManagerController {
	
	@GetMapping("/team-leave-history")
	public String teamLeaveHistory() {     
		return "team-leave-history";       
	}
	
	@GetMapping("/view-team-members-leave")
	public String teamMembersLeave() {
		return "view-team-members-leave";       
	}
	
	@GetMapping("/manage-leave-requests")
	public String manageLeaveRequests() {
		return "manage-leave-requests";       
	}
}
