package iss.nus.edu.sg.leave_application_processing_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/authenticate")
public class AuthenticateController {

	@GetMapping ("/")
	public String showLandingPage() {
		return "login";       
	}
	
	@GetMapping ("/login")
	public String loginPage() {	
		return "login";
	}
	
	@GetMapping ("/logout")
	public String logout(HttpSession session) {	
		session.invalidate();
		return "redirect:/";
	}
	
	@PostMapping ("/login") 
	public String validateLogin (@RequestParam String username, @RequestParam String password, HttpSession session) {
		if (username.equalsIgnoreCase("john@company.com") && password.equalsIgnoreCase("any")) {
			session.setAttribute("userRole", "staff");
			return "redirect:/staff";
		} else if (username.equalsIgnoreCase("sarah@company.com") && password.equalsIgnoreCase("any")) {
			session.setAttribute("userRole", "manager");
			return "redirect:/staff";
		} else if (username.equalsIgnoreCase("admin@company.com") && password.equalsIgnoreCase("any")) {
			session.setAttribute("userRole", "admin");
			return "redirect:/staff";
		}
		return "failure";
	}
	
}

