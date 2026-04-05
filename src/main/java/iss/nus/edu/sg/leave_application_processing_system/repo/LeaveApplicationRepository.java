package iss.nus.edu.sg.leave_application_processing_system.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;


@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
	
	List<LeaveApplication> findByEmployeeId(Long employeeId);
	
	// For the Manager: "Show me everything waiting for approval"
	List<LeaveApplication> findByStatus(LeaveStatus status);
	
	// For Validation: "Is this employee already on leave during these dates?"
    // This prevents double-booking.
	@Query("SELECT l FROM LeaveApplication l " +
		       "WHERE l.employeeId = :employeeId " +
		       "AND l.startDate <= :endDate " +
		       "AND l.endDate >= :startDate " +
		       "AND l.status IN (LeaveStatus.PENDING, LeaveStatus.APPROVED)")
	List<LeaveApplication> findOverlappingLeaves(@Param("employeeId") Long employeeId, 
		    @Param("startDate") LocalDate startDate, 
		    @Param("endDate") LocalDate endDate);
	
}
