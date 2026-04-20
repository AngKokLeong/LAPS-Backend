package iss.nus.edu.sg.leave_application_processing_system.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.persistent.repository.EmployeeRepository;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public ApplicationUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(username)
                .map(ApplicationUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
}
