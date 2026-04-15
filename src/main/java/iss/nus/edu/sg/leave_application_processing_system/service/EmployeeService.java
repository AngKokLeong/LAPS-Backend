package iss.nus.edu.sg.leave_application_processing_system.service;

import iss.nus.edu.sg.leave_application_processing_system.repo.EmployeeRepository;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import iss.nus.edu.sg.leave_application_processing_system.model.Employee;

@Service
public class EmployeeService {
	
	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;
	
	public EmployeeService (EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
		this.employeeRepository = employeeRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public boolean save(Employee employee) {
		if (employee.getPassword() != null && !employee.getPassword().startsWith("$2")) {
			employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		}

		Employee result = employeeRepository.save(employee);
		if (result != null){
			return true;
		}

		return false;
	}

}
