package iss.nus.edu.sg.leave_application_processing_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@GetMapping ("/")
	public String showLandingPage() {
		return "login";       // returns login.html
	}
	
	@GetMapping ("/login")
	public String loginPage() {	
		return("login");
	}
	
	@PostMapping ("/validate") 
	public String validateLogin (@RequestParam String username, @RequestParam String password, Model model) {
		if (username.equalsIgnoreCase("john@company.com") && password.equalsIgnoreCase("any")) {
			return "success";
		}
		else {
			return "failure";
			}
	}
	
}

