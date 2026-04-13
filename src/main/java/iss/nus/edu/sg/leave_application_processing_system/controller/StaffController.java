package iss.nus.edu.sg.leave_application_processing_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/staff")
public class StaffController {
	
	@GetMapping ({"", "/"})
	public String staffDashboard() {
		return "dashboard";
	}
	
	@GetMapping ("/apply-for-leave") 
	public String applyForLeave() {
		return "apply-for-leave";
	}
	
	@GetMapping ("/my-leave-requests")
	public String myLeaveRequests() {
		return "my-leave-requests";
	}
	
	@GetMapping ("/movement-register") 
	public String movementRegister() {
		return "movement-register";
	}
	
	@GetMapping ("/submit-ot-claim") 
	public String submitOvertimeClaim() {
		return "submit-ot-claim";
	}

}
