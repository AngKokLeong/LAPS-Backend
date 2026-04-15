package iss.nus.edu.sg.leave_application_processing_system.service.implementation;


import java.util.Optional;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.AuthenticationControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.model.Employee;
import iss.nus.edu.sg.leave_application_processing_system.repo.EmployeeRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.AuthenticationServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;


@Service
public class AuthenticationService {
    private EmployeeRepository employeeRepository;

    public AuthenticationService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }



    public Optional<ControllerDTO> authenticateUser(ServiceDTO serviceDTO){

        AuthenticationServiceDTO authenticationServiceDTO = (AuthenticationServiceDTO) serviceDTO.getAllAttribute();

        Optional<Employee> employee = employeeRepository.findByEmailAndPassword(authenticationServiceDTO.getEmail(), authenticationServiceDTO.getPassword());
        
        return employee.isPresent() ? Optional.of(new AuthenticationControllerDTO(employee.get().getId(), employee.get().getEmail(), employee.get().getRole())) : Optional.empty();
    }

}
