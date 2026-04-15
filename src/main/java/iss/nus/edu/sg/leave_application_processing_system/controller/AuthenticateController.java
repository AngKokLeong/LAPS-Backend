package iss.nus.edu.sg.leave_application_processing_system.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.AuthenticationControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.AuthenticationServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.implementation.AuthenticationService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/authenticate")
public class AuthenticateController {

	private final AuthenticationService authenticationService;

	public AuthenticateController(AuthenticationService authenticationService){
		this.authenticationService = authenticationService;
	}


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
		// Adding ?logout tells Thymeleaf to show the green box
	    return "redirect:/authenticate/?logout"; 

	}
	
	@PostMapping ("/login") 
	public String validateLogin (@ModelAttribute AuthenticationControllerDTO authenticationUser, @RequestParam String email, @RequestParam String password, HttpSession session) {

		AuthenticationServiceDTO authenticationServiceDTO = new AuthenticationServiceDTO();
		authenticationServiceDTO.setEmail(authenticationUser.getEmail());
		authenticationServiceDTO.setPassword(authenticationUser.getPassword());

		Optional<ControllerDTO> controllerDTO = authenticationService.authenticateUser(authenticationServiceDTO);

		if (controllerDTO.isPresent()){
			AuthenticationControllerDTO authenticationControllerDTO = (AuthenticationControllerDTO)controllerDTO.get().getAllAttribute();
			session.setAttribute("userRole", authenticationControllerDTO.getRole().toString());
			session.setAttribute("id", authenticationControllerDTO.getStaffId());	

			return "redirect:/staff";
		}

		return "failure";
	}
	
}

