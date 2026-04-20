package iss.nus.edu.sg.leave_application_processing_system.controller;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveEntitlementCreditDTO;
import iss.nus.edu.sg.leave_application_processing_system.helper.Role;
import iss.nus.edu.sg.leave_application_processing_system.model.Employee;
import iss.nus.edu.sg.leave_application_processing_system.service.EmployeeService;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.EmployeeServiceDTO;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final EmployeeService employeeService;

	AdminController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/employee-management")
	public String employeeManagement(HttpSession session, Model model,
			@RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String role) {  
	  	
		String extractedRoleData = (String) session.getAttribute("userRole");
	  	if (extractedRoleData == null || extractedRoleData.toString().isEmpty()) return "redirect:/";
		Role extractedRole = Role.valueOf(extractedRoleData);
	    if (!extractedRole.equals(Role.ADMIN)) {
	        return "redirect:/staff"; // Send them home if they aren't a admin
	    }
	    
	    int pageSize = 5;
	    
	    Page<EmployeeServiceDTO> employeePage = employeeService.getEmployeesPaged(page, pageSize, search, role);
	    
	    List<Employee> managers = employeeService.findByRole(Role.MANAGER);
        
	    model.addAttribute("managers", managers);
	    model.addAttribute("employee", new Employee());
	    model.addAttribute("employeePage", employeePage);
	    model.addAttribute("search", search);
	    model.addAttribute("role", role);
	    model.addAttribute("currentPage", page);
	    
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
	public String saveNewEmployee (@ModelAttribute Employee employee, HttpSession session, RedirectAttributes redirectAttrs) {

		String extractedRoleData = (String) session.getAttribute("userRole");
		
	  	if (extractedRoleData == null || extractedRoleData.toString().isEmpty()) return "redirect:/";

		Role role = Role.valueOf(extractedRoleData);	
		
	    if (!role.equals(Role.ADMIN)) {
	        return "redirect:/staff"; // Send them home if they aren't a admin
	    }

	    try {
	    	LeaveEntitlementCreditDTO leDTO = employeeService.save(employee);
	    	String name = leDTO.getEmployeeName();
	    	int annualDays = leDTO.getAnnualEntitlement();
	    	int medicalDays = leDTO.getMedicalEntitlement();
	    	
	    	String msg = String.format(
	                "New employee record for %s saved successfully. " + 
	                "Pro-rated leave entitlement has been credited: Annual: %d days, Medical: %d days.", 
	                name, annualDays, medicalDays
	            );
	    	
	    	redirectAttrs.addFlashAttribute("successMessage", msg);
	    } catch (Exception e) {
	    	redirectAttrs.addFlashAttribute("errorMessage", "Failed to create new employee: " + e.getMessage());
	    }
		
		return "redirect:/admin/employee-management";
	}
	
	@PostMapping("/employees/update")
	public String updateEmployee(@ModelAttribute("employee") EmployeeServiceDTO employeeDto, RedirectAttributes redirectAttrs) {
		
		try {
			employeeService.updateEmployee(employeeDto);
			redirectAttrs.addFlashAttribute("successMessage", "Employee details updated successfully.");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("errorMessage", "Could not update employee: " + e.getMessage());
		}

		return "redirect:/admin/employee-management";
	}
	
	@PostMapping("/employees/delete")
	public String softDeleteEmployee(@RequestParam Long id, RedirectAttributes redirectAttrs) {
	    try {
	        employeeService.softDeleteEmployee(id);
	        redirectAttrs.addFlashAttribute("successMessage", "Employee status set to INACTIVE.");
	    } catch (Exception e) {
	        redirectAttrs.addFlashAttribute("errorMessage", "Delete failed: " + e.getMessage());
	    }
	    // Redirect back to the management list page
	    return "redirect:/admin/employee-management";
	}

}
