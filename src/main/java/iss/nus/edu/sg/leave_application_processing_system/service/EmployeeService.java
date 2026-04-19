package iss.nus.edu.sg.leave_application_processing_system.service;

import iss.nus.edu.sg.leave_application_processing_system.repo.EmployeeRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveEntitlementRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.EmployeeServiceDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import iss.nus.edu.sg.leave_application_processing_system.helper.Designation;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;
import iss.nus.edu.sg.leave_application_processing_system.helper.Role;
import iss.nus.edu.sg.leave_application_processing_system.model.Employee;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveEntitlement;

@Service
public class EmployeeService {
	
	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;
	private final LeaveEntitlementRepository leaveRepo;
	
	public EmployeeService (EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, LeaveEntitlementRepository leaveRepo) {
		this.employeeRepository = employeeRepository;
		this.passwordEncoder = passwordEncoder;
		this.leaveRepo = leaveRepo;
	}
	
	@Transactional
	public void save(Employee employee) {
		if (employee.getPassword() != null && !employee.getPassword().startsWith("$2")) {
			employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		}

		if (employee.getManager() != null) {
	        Employee manager = employeeRepository.findById(employee.getManager().getId()).orElse(null);
	        employee.setManager(manager);
	    } else {
	        // Explicitly set to null if no manager is assigned
	        employee.setManager(null);
	    }
		
		Employee savedEmp = employeeRepository.save(employee);
		
		// create leave entitlement
		int currentYear = LocalDate.now().getYear();
        int joinMonth = employee.getJoindate().getMonthValue();
        double proRateFactor = (13.0 - joinMonth) / 12.0;

        // Determine Annual Leave Days
        int annualBase = (employee.getDesignation() == Designation.PROFESSIONAL) ? 14 : 18;
        int annualEntitlement = (int) Math.round(annualBase * proRateFactor);

        // Create Annual Leave Record
        LeaveEntitlement annual = new LeaveEntitlement();
        annual.setEmployeeId(savedEmp);
        annual.setLeaveType(LeaveType.ANNUAL); // Ensure this exists in your Enum
        annual.setYearApplied(currentYear);
        annual.setTotalDays(annualEntitlement);
        annual.setUsedDays(0);
        leaveRepo.save(annual);

        // Create Medical Leave Record
        int medicalEntitlement = (int) Math.round(60 * proRateFactor);
        
        LeaveEntitlement medical = new LeaveEntitlement();
        medical.setEmployeeId(savedEmp);
        medical.setLeaveType(LeaveType.MEDICAL);
        medical.setYearApplied(currentYear);
        medical.setTotalDays(medicalEntitlement);
        medical.setUsedDays(0);
        leaveRepo.save(medical);
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
	
	public void softDeleteEmployee(Long id) {
	    Employee emp = employeeRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Employee not found"));
	    
	    // Just change the status
	    emp.setStatus("Inactive"); 
	    
	    emp.setManager(null);
	    
	    employeeRepository.save(emp);
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
	
	public List<Employee> findByRole(Role role) {
		return employeeRepository.findByRole(role);
	}

	
}
