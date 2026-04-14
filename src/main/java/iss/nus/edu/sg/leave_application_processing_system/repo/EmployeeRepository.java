package iss.nus.edu.sg.leave_application_processing_system.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iss.nus.edu.sg.leave_application_processing_system.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findById(Long id);
    Optional<Employee> findByEmail(String email);

    // Login authentication
    Optional<Employee> findByEmailAndPassword(String email, String password);

    // Manager-subordinate r/s
    List<Employee> findByManagerId(Long managerId);
}

