package iss.nus.edu.sg.leave_application_processing_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class LoginController {

	@GetMapping ("/")
	public String showLandingPage() {
		return "login";       
	}
	
	@GetMapping ("/login")
	public String loginPage() {	
		return "login";
	}
	
	@PostMapping ("/validate") 
	public String validateLogin (@RequestParam String username, @RequestParam String password, Model model) {
		if (username.equalsIgnoreCase("john@company.com") && password.equalsIgnoreCase("any")) {
			model.addAttribute("username", username);
			return "redirect:/landing/";
		}
		else if (username.equalsIgnoreCase("admin@company.com") && password.equalsIgnoreCase("any")) {
			return "employee-management";
		}
		else {
			return "failure";
			}
	}
	
	@GetMapping ("/success/dashboard")
	public String viewDashboard() {	
		return("dashboard");
	}
	
	@GetMapping ("/success/applyleave")
	public String applyLeavePage() {	
		return("applyleave");
	}
	
	@PostMapping("/success/applyleave/apply")
	public String leaveApplication() {
		return("myleaves");
	}
	
	@GetMapping ("/success/myleaves")
	public String viewLeavePage() {	
		return("myleaves");
	}
	
	@GetMapping("/claim-ot")
	public String claimOT(@RequestParam(required = false) String role, HttpSession session) {	
		
		if (role != null) {
	        session.setAttribute("userRole", role.toLowerCase());
	    }
	    
	    // Default fallback if session is empty and no param is provided
	    if (session.getAttribute("userRole") == null) {
	        session.setAttribute("userRole", "staff"); 
	    }
	    
		return("claim-ot");
	}
}

