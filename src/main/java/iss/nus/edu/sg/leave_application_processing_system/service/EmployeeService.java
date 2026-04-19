package iss.nus.edu.sg.leave_application_processing_system.service;

import iss.nus.edu.sg.leave_application_processing_system.repo.EmployeeRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.EmployeeServiceDTO;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import iss.nus.edu.sg.leave_application_processing_system.helper.Designation;
import iss.nus.edu.sg.leave_application_processing_system.helper.Role;
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
	
	public void updateEmployee(EmployeeServiceDTO dto) {
		// 1. Fetch the existing entity
	    Employee existing = employeeRepository.findById(dto.getId())
	        .orElseThrow(() -> new RuntimeException("Employee not found"));

	    // 2. Map only the allowed fields
	    existing.setName(dto.getName());
	    existing.setEmail(dto.getEmail());
	    existing.setDepartment(dto.getDepartment());
	    existing.setStatus(dto.getStatus());
	    
	    if (dto.getDesignation() != null) {
	        existing.setDesignation(Designation.valueOf(dto.getDesignation().toUpperCase()));
	    }

	    if (dto.getRole() != null) {
	        existing.setRole(Role.valueOf(dto.getRole().toUpperCase()));
	    }
	    
	    employeeRepository.save(existing);
	}
	
	public List<EmployeeServiceDTO> getAllEmployees() {
        
		return employeeRepository.findAll().stream()
            .map(emp -> new EmployeeServiceDTO(
                emp.getId(),         
                emp.getName(),       
                emp.getEmail(),
                emp.getDepartment(),
                emp.getRole().toString(),
                emp.getStatus(),
                emp.getDesignation() != null ? emp.getDesignation().toString() : "",
                emp.getJoindate() != null ? emp.getJoindate().toString() : ""
            ))
            .collect(Collectors.toList());
    }
	
	public Page<EmployeeServiceDTO> getEmployeesPaged(int page, int size, String search, String roleStr) {
	    Pageable pageable = PageRequest.of(page, size);
	    
	    // Convert empty strings to null for the query logic
	    String searchParam = (search == null || search.isEmpty()) ? null : search;
	    
	    // Convert String to Enum Safely
	    Role roleEnum = null;
	    if (roleStr != null && !roleStr.isEmpty()) {
	        try {
	            // This converts "ADMIN" -> Role.ADMIN
	            roleEnum = Role.valueOf(roleStr.toUpperCase());
	        } catch (IllegalArgumentException e) {
	            // If someone types a weird role in the URL, we just treat it as null (All Roles)
	            roleEnum = null;
	        }
	    }
	    
	    return employeeRepository.findBySearchAndRole(searchParam, roleEnum, pageable)
	        .map(emp -> new EmployeeServiceDTO(
	        		emp.getId(),         
	                emp.getName(),       
	                emp.getEmail(),
	                emp.getDepartment(),
	                emp.getRole().toString(),
	                emp.getStatus(),
	                emp.getDesignation() != null ? emp.getDesignation().toString() : "",
	                emp.getJoindate() != null ? emp.getJoindate().toString() : ""
	        ));
	}

}
