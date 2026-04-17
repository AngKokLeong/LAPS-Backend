package iss.nus.edu.sg.leave_application_processing_system.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.AddEmployeeControllerDTO;

import iss.nus.edu.sg.leave_application_processing_system.helper.Role;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.AddEmployeeServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.implementation.AddEmployeeService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final AddEmployeeService addEmployeeService;

	AdminController(AddEmployeeService addEmployeeService) {
		this.addEmployeeService = addEmployeeService;
	}

	@GetMapping("/employee-management")
	public String employeeManagement(Model model, HttpSession session) {  
	  	
		String extractedRoleData = (String) session.getAttribute("userRole");
		

	  	if (extractedRoleData == null || extractedRoleData.toString().isEmpty()) return "redirect:/";

		Role role = Role.valueOf(extractedRoleData);

	    if (!role.equals(Role.ADMIN)) {
	        return "redirect:/staff"; // Send them home if they aren't a admin
	    }
		
		model.addAttribute("employee", new AddEmployeeControllerDTO());
		

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
    public String saveNewEmployee(@ModelAttribute("employee") AddEmployeeControllerDTO employeeDto, HttpSession session, RedirectAttributes redirectAttrs) {
        String extractedRoleData = (String) session.getAttribute("userRole");
        if (extractedRoleData == null || extractedRoleData.isEmpty()) return "redirect:/";
        Role role = Role.valueOf(extractedRoleData);
        if (!role.equals(Role.ADMIN)) {
            return "redirect:/staff"; // Send them home if they aren't a admin
        }

		AddEmployeeServiceDTO serviceDTO = new AddEmployeeServiceDTO();
        serviceDTO.setName(employeeDto.getName());
        serviceDTO.setEmail(employeeDto.getEmail());
        serviceDTO.setPassword(employeeDto.getPassword());
        serviceDTO.setDesignation(employeeDto.getDesignation());
        serviceDTO.setDepartment(employeeDto.getDepartment());
        serviceDTO.setRole(employeeDto.getRole());
        serviceDTO.setJoinDate(employeeDto.getJoinDate());
        serviceDTO.setStatus(employeeDto.getStatus());
        serviceDTO.setManagerId(employeeDto.getManagerId());

        AddEmployeeControllerDTO result = (AddEmployeeControllerDTO) addEmployeeService.save(serviceDTO);

        redirectAttrs.addFlashAttribute("successMessage", "New employee record saved successfully.");
		redirectAttrs.addFlashAttribute("data", result);
        return "redirect:/admin/employee-management";
    }


}
