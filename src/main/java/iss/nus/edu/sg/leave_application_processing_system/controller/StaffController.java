package iss.nus.edu.sg.leave_application_processing_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping ("/staff")
public class StaffController {
	
	@GetMapping ({"", "/"})
	public String staffDashboard(HttpSession session) {
		String role = (String) session.getAttribute("userRole");
		
		if (role == null || role.toString().isEmpty()) return "redirect:/";
		
		return "dashboard";
	}
	
	@GetMapping ("/apply-leave") 
	public String applyForLeave(HttpSession session) {
		String role = (String) session.getAttribute("userRole");
		
		if (role == null || role.toString().isEmpty()) return "redirect:/";
	
		return "apply-leave";
	}
	
	@GetMapping ("/my-leave-requests")
	public String myLeaveRequests(HttpSession session) {
		String role = (String) session.getAttribute("userRole");
		
		if (role == null || role.toString().isEmpty()) return "redirect:/";
		
		return "my-leave-requests";
	}
	
	@GetMapping ("/movement-register") 
	public String movementRegister(HttpSession session) {
		String role = (String) session.getAttribute("userRole");
		
		if (role == null || role.toString().isEmpty()) return "redirect:/";
		
		return "movement-register";
	}
	
	@GetMapping ("/submit-ot-claim") 
	public String submitOvertimeClaim(HttpSession session) {
		String role = (String) session.getAttribute("userRole");
		
		if (role == null || role.toString().isEmpty()) return "redirect:/";
		
		return "submit-ot-claim";
	}

}
