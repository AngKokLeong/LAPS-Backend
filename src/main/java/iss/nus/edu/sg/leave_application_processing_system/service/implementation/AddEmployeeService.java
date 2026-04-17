package iss.nus.edu.sg.leave_application_processing_system.service.implementation;

import iss.nus.edu.sg.leave_application_processing_system.repo.EmployeeRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.AddEmployeeServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.AddEmployeeControllerDTO;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.model.Employee;

@Service
public class AddEmployeeService {

	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;

	public AddEmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
		this.employeeRepository = employeeRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public ControllerDTO save(ServiceDTO serviceDTO) {
		AddEmployeeServiceDTO addEmployeeServiceDTO = (AddEmployeeServiceDTO) serviceDTO.getAllAttribute();
		AddEmployeeControllerDTO controllerDTO = new AddEmployeeControllerDTO();

		try {
			// Map DTO to entity
			Employee employee = new Employee();
			employee.setName(addEmployeeServiceDTO.getName());
			employee.setEmail(addEmployeeServiceDTO.getEmail());
			employee.setDepartment(null);
			employee.setDesignation(null);
			employee.setJoindate(null);
			

			// Password encoding 
			if (addEmployeeServiceDTO.getPassword() != null && !addEmployeeServiceDTO.getPassword().startsWith("$2")) {
				employee.setPassword(passwordEncoder.encode(addEmployeeServiceDTO.getPassword()));
			} else {
				employee.setPassword(addEmployeeServiceDTO.getPassword());
			}
			// Save entity
			Employee saved = employeeRepository.save(employee);

			// Map entity to controller DTO
			controllerDTO.setId(saved.getId());
			controllerDTO.setName(saved.getName());
			controllerDTO.setEmail(saved.getEmail());
			controllerDTO.setDepartment(saved.getDepartment());
			controllerDTO.setJoinDate(saved.getJoindate());
			controllerDTO.setStatus(saved.getStatus());
			controllerDTO.setRole(saved.getRole() != null ? saved.getRole().name() : null);
			controllerDTO.setDesignation(saved.getDesignation() != null ? saved.getDesignation().name() : null);
			controllerDTO.setManagerId(saved.getManager() != null ? saved.getManager().getId() : null);
			controllerDTO.setOperationResult(true);
			controllerDTO.setOperationComments("Employee added successfully");
		} catch (Exception e) {
			controllerDTO.setOperationResult(false);
			controllerDTO.setOperationComments("Failed to add employee: " + e.getMessage());
		}

		return controllerDTO;
	}
}
