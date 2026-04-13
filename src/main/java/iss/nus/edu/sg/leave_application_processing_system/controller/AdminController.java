package iss.nus.edu.sg.leave_application_processing_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping("/employee-management")
	public String employeeManagement(HttpSession session) {  
	  	String role = (String) session.getAttribute("userRole");
		
	  	if (role == null || role.toString().isEmpty()) return "redirect:/";

	    if (!"admin".equals(role)) {
	        return "redirect:/staff"; // Send them home if they aren't a admin
	    }
	    
		return "employee-management";       
	}
	
	@GetMapping("/leave-type-management")
	public String leaveTypeManagement(HttpSession session) {
	  	String role = (String) session.getAttribute("userRole");
		
	  	if (role == null || role.toString().isEmpty()) return "redirect:/";

	    if (!"admin".equals(role)) {
	        return "redirect:/staff"; // Send them home if they aren't a admin
	    }
	    
		return "leave-type-management";       
	}
	
	@GetMapping("/leave-entitlement-management")
	public String leaveEntitlementManagement(HttpSession session) {
	  	String role = (String) session.getAttribute("userRole");
		
	  	if (role == null || role.toString().isEmpty()) return "redirect:/";

	    if (!"admin".equals(role)) {
	        return "redirect:/staff"; // Send them home if they aren't a admin
	    }
	    
		return "leave-entitlement-management";       
	}

	@GetMapping("/email-template-management")
	public String emailTemplateManagement(HttpSession session) {
	  	String role = (String) session.getAttribute("userRole");
		
	  	if (role == null || role.toString().isEmpty()) return "redirect:/";

	    if (!"admin".equals(role)) {
	        return "redirect:/staff"; // Send them home if they aren't a admin
	    }
	    
	    return "email-template-management";       
	}

	@GetMapping("/email-template")
	public String emailTemplate(HttpSession session) {
	  	String role = (String) session.getAttribute("userRole");
		
	  	if (role == null || role.toString().isEmpty()) return "redirect:/";

	    if (!"admin".equals(role)) {
	        return "redirect:/staff";
	    }
	    
	    return "email-template";       
	}

}
