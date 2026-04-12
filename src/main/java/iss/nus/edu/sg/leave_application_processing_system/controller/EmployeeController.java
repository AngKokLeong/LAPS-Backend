package iss.nus.edu.sg.leave_application_processing_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import iss.nus.edu.sg.leave_application_processing_system.model.Employee;
import iss.nus.edu.sg.leave_application_processing_system.repo.EmployeeRepository;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeRepository repo;
	
	@GetMapping ("/employees")
	public String viewEmployees(Model model) {
		 List<Employee> fakeEmployees = List.of(
				 new Employee(1L, "John", "Doe"),
				 new Employee(2L, "Sarah", "Tan"),
				 new Employee(3L, "Michael", "Lee")
				 );
		 model.addAttribute("employees", fakeEmployees);
		 return "employee-management";
	     // model.addAttribute("employees", repo.findAll());
	     // return "employee-management";
	}
	
}
	
	
