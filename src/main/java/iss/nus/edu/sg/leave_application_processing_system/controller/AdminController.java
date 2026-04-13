package iss.nus.edu.sg.leave_application_processing_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

  @GetMapping("/employee-management")
	public String employeeManagement() {     
		return "employee-management";       
	}
	
	@GetMapping("/leave-type-management")
	public String leaveTypeManagement() {
		return "leave-type-management";       
	}
	
	@GetMapping("/leave-entitlement-management")
	public String leaveEntitlementManagement() {
		return "leave-entitlement-management";       
	}

  @GetMapping("/email-template-management")
	public String emailTemplateManagement() {
		return "email-template-management";       
	}

  @GetMapping("/email-template")
  public String emailTemplate() {
		return "email-template";       
	}

}
