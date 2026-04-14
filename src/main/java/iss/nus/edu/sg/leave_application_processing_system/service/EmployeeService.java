package iss.nus.edu.sg.leave_application_processing_system.service;

import iss.nus.edu.sg.leave_application_processing_system.repo.EmployeeRepository;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.model.Employee;

@Service
public class EmployeeService {
	
	private final EmployeeRepository employeeRepository;
	
	public EmployeeService (EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	public void save(Employee employee) {
		employeeRepository.save(employee);
	}

}
