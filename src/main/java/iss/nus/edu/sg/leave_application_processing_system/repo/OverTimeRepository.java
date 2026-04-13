package iss.nus.edu.sg.leave_application_processing_system.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iss.nus.edu.sg.leave_application_processing_system.helper.OTClaimStatus;
import iss.nus.edu.sg.leave_application_processing_system.model.OverTimeClaim;

@Repository
public interface OverTimeRepository extends JpaRepository<OverTimeClaim, Long> {

  /*
   List<OverTimeClaim> findByEmployee_EmployeeId(Long employeeId);
   List<OverTimeClaim> findByStatus(OTClaimStatus status);
   */
}