package iss.nus.edu.sg.leave_application_processing_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import iss.nus.edu.sg.leave_application_processing_system.model.Employee;
import iss.nus.edu.sg.leave_application_processing_system.service.EmployeeService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final EmployeeService employeeService;

	AdminController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

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
	
	@GetMapping("/employees/add")
	public String addNewEmployee (Model model) {
		model.addAttribute("employee", new Employee());
		return "add-new-employee.html";
	}
	
	@PostMapping("/employees/add")
	@ResponseBody
	public String saveNewEmployee (@ModelAttribute Employee employee) {
		employeeService.save(employee);
		return "for testing";
	}

}
