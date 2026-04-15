package iss.nus.edu.sg.leave_application_processing_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authenticate")
public class AuthenticateController {

	@GetMapping ("/")
	public String showLandingPage() {
		return "redirect:/";
	}
	
	@GetMapping ("/login")
	public String loginPage() {	
		return "redirect:/";
	}
	
}

