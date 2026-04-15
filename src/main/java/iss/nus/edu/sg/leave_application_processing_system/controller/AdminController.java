package iss.nus.edu.sg.leave_application_processing_system.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import jakarta.servlet.http.HttpSession;
import iss.nus.edu.sg.leave_application_processing_system.helper.Role;
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
	  	
		String extractedRoleData = (String) session.getAttribute("userRole");
		

	  	if (extractedRoleData == null || extractedRoleData.toString().isEmpty()) return "redirect:/";

		Role role = Role.valueOf(extractedRoleData);

	    if (!role.equals(Role.ADMIN)) {
	        return "redirect:/staff"; // Send them home if they aren't a admin
	    }
	    
		return "employee-management";       
	}
	
	@GetMapping("/leave-type-management")
	public String leaveTypeManagement(HttpSession session) {
		String extractedRoleData = (String) session.getAttribute("userRole");
		
	  	if (extractedRoleData == null || extractedRoleData.toString().isEmpty()) return "redirect:/";

		Role role = Role.valueOf(extractedRoleData);	

	    if (!role.equals(Role.ADMIN)) {
	        return "redirect:/staff"; // Send them home if they aren't a admin
	    }
	    
		return "leave-type-management";       
	}
	
	@GetMapping("/leave-entitlement-management")
	public String leaveEntitlementManagement(HttpSession session) {
		String extractedRoleData = (String) session.getAttribute("userRole");
		
	  	if (extractedRoleData == null || extractedRoleData.toString().isEmpty()) return "redirect:/";

		Role role = Role.valueOf(extractedRoleData);	
		
	    if (!role.equals(Role.ADMIN)) {
	        return "redirect:/staff"; // Send them home if they aren't a admin
	    }
	    
		return "leave-entitlement-management";       
	}

	@GetMapping("/email-template-management")
	public String emailTemplateManagement(HttpSession session) {
		String extractedRoleData = (String) session.getAttribute("userRole");
		
	  	if (extractedRoleData == null || extractedRoleData.toString().isEmpty()) return "redirect:/";

		Role role = Role.valueOf(extractedRoleData);	
		
	    if (!role.equals(Role.ADMIN)) {
	        return "redirect:/staff"; // Send them home if they aren't a admin
	    }

	    return "email-template-management";       
	}

	@GetMapping("/email-template")
	public String emailTemplate(HttpSession session) {
		String extractedRoleData = (String) session.getAttribute("userRole");
		
	  	if (extractedRoleData == null || extractedRoleData.toString().isEmpty()) return "redirect:/";

		Role role = Role.valueOf(extractedRoleData);	
		
	    if (!role.equals(Role.ADMIN)) {
	        return "redirect:/staff"; // Send them home if they aren't a admin
	    }
	    
	    return "email-template";       
	}
	
	
	@PostMapping("/employee-management/add-employee")
	public String saveNewEmployee (@ModelAttribute Employee employee, HttpSession session) {

		String extractedRoleData = (String) session.getAttribute("userRole");
		
	  	if (extractedRoleData == null || extractedRoleData.toString().isEmpty()) return "redirect:/";

		Role role = Role.valueOf(extractedRoleData);	
		
	    if (!role.equals(Role.ADMIN)) {
	        return "redirect:/staff"; // Send them home if they aren't a admin
	    }

		employeeService.save(employee);
		
		return "employee-management";
	}

}
