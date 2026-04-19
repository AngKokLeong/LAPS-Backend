package iss.nus.edu.sg.leave_application_processing_system.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import iss.nus.edu.sg.leave_application_processing_system.helper.Role;
import iss.nus.edu.sg.leave_application_processing_system.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findById(Long id);
    Optional<Employee> findByEmail(String email);

    // Login authentication
    Optional<Employee> findByEmailAndPassword(String email, String password);

    // Manager-subordinate r/s
    List<Employee> findByManagerId(Long managerId);
    
    @Query("SELECT e FROM Employee e WHERE " +
            "(:search IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(e.email) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:role IS NULL OR e.role = :role)")
     Page<Employee> findBySearchAndRole(@Param("search") String search, @Param("role") Role role, Pageable pageable);
}

