package iss.nus.edu.sg.leave_application_processing_system.repo;

// import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;


@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {


    // Employee personal leave history
    List<LeaveApplication> findByEmployeeId(Long employeeId);

    // Pending approvals for managers
    List<LeaveApplication> findByLeaveStatus(LeaveStatus status);

    // All leaves within date range (for validation)
    List<LeaveApplication> findByEmployeeIdAndLeaveStatus(
            Long employeeId,
            LeaveStatus leaveStatus
    );
    
    @Query("SELECT l FROM LeaveApplication l " +
    	       "WHERE l.employee.manager.id = :managerId " +
    	       "ORDER BY CASE " +
    	       "  WHEN l.leaveStatus = 'APPLIED' OR l.leaveStatus = 'UPDATED' THEN 0 " +
    	       "  ELSE 1 END ASC, " +
    	       "l.appliedDate DESC")
    List<LeaveApplication> findSubordinateLeaves(@Param("managerId") Long managerId);


    /**
     * ✅ Movement Register (Reporting View)
     *
     * Approved leaves overlapping selected month
     */

    /* 
    @Query("""
        SELECT new sg.edu.nus.iss.laps.dto.MovementRegisterDTO(
            e.name,
            e.department,
            l.leaveType,
            l.startDate,
            l.endDate
        )
        FROM LeaveApplication l
        JOIN l.employee e
        WHERE l.leaveStatus = sg.edu.nus.iss.laps.model.enums.LeaveStatus.APPROVED
          AND l.startDate <= :endDate
          AND l.endDate >= :startDate
        ORDER BY l.startDate
      """)

    List<MovementRegisterDTO> findMovementRegister(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
    */
}
