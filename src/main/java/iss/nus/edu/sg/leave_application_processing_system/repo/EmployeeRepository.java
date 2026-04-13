package iss.nus.edu.sg.leave_application_processing_system.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import iss.nus.edu.sg.leave_application_processing_system.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findById(Long id);
    Optional<Employee> findByEmail(String email);
}
