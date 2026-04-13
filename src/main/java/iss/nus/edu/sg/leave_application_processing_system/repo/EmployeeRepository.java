package iss.nus.edu.sg.leave_application_processing_system.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iss.nus.edu.sg.leave_application_processing_system.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  Optional<Employee> findByEmployeeId(Long employeeId);
  Optional<Employee> findByEmail(String email);
}

