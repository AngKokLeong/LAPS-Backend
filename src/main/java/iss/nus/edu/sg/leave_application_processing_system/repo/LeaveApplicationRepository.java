package iss.nus.edu.sg.leave_application_processing_system.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;


@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
	
	List<LeaveApplication> findByEmployeeId(Long employeeId);
	
	// For the Manager: "Show me everything waiting for approval"
	List<LeaveApplication> findByStatus(LeaveStatus status);
		
}
