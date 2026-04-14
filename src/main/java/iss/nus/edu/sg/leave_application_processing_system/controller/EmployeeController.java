package iss.nus.edu.sg.leave_application_processing_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import iss.nus.edu.sg.leave_application_processing_system.model.Employee;
import iss.nus.edu.sg.leave_application_processing_system.repo.EmployeeRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.EmployeeService;

@Controller
public class EmployeeController {


  @Autowired
  private EmployeeRepository repo;

	@GetMapping ("/employees")
	public String viewEmployees(Model model) {
		List<Employee> fakeEmployees = List.of(
            new Employee("John", "john@example.com"),
            new Employee("Sarah", "sarah@example.com"),
            new Employee("Michael", "michael@example.com")
        );

		 model.addAttribute("employees", "fakeEmployees");
		 return "employee-management";
	     // model.addAttribute("employees", repo.findAll());
	     // return "employee-management";
	}
	
	private final EmployeeService employeeService;
	
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
}
	
	
