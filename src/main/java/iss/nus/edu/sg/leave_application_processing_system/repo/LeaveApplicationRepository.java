package iss.nus.edu.sg.leave_application_processing_system.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveMovementDTO;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;
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
    
    // For movement register workflow (movement-register.html)
    @Query("""
    SELECT new iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveMovementDTO(
            e.name,
            e.id,
            e.department,
            l.leaveType,
            l.startDate,
            l.endDate
        )
        FROM LeaveApplication l
        JOIN l.employee e
        WHERE l.leaveStatus = :status
          AND l.startDate <= :endOfMonth
          AND l.endDate >= :startOfMonth
    """)
    
    Page<LeaveMovementDTO> findApprovedLeaveForMonth(
      @Param("status") LeaveStatus status,
      @Param("startOfMonth") LocalDate startOfMonth,
      @Param("endOfMonth") LocalDate endOfMonth,
      Pageable pageable
    );
    

    @Query("SELECT l FROM LeaveApplication l WHERE l.employee.id = :staffId " +
            "AND l.leaveStatus IN :statusList " +
            "AND (:start <= l.endDate AND :end >= l.startDate)")
     List<LeaveApplication> findOverlappingLeaves(
         @Param("staffId") Long staffId, 
         @Param("start") LocalDate start, 
         @Param("end") LocalDate end, 
         @Param("statusList") List<LeaveStatus> statusList
     );

     // Fixed field names to leaveType and leaveStatus
     @Query("SELECT l FROM LeaveApplication l WHERE l.employee.id = :staffId " +
            "AND l.leaveType = :leaveType " +
            "AND l.leaveStatus = :leaveStatus " +
            "AND FUNCTION('YEAR', l.startDate) = :year")
     List<LeaveApplication> findByEmployeeIdAndLeaveTypeAndLeaveStatusAndYear(
         @Param("staffId") Long staffId, 
         @Param("leaveType") LeaveType leaveType, 
         @Param("leaveStatus") LeaveStatus leaveStatus, 
         @Param("year") int year
     );

    // For reporting: get leaves by manager, date range, and optional leave type
    @Query("""
    SELECT l FROM LeaveApplication l
    JOIN l.employee e
    WHERE e.manager.id = :managerId
      AND l.leaveStatus = :status
      AND (:leaveType IS NULL OR l.leaveType = :leaveType)
      AND l.startDate <= :endDate
      AND l.endDate >= :startDate
    """)
    List<LeaveApplication> findManagerLeavesByDateRange(
        @Param("managerId") Long managerId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("status") LeaveStatus status,
        @Param("leaveType") LeaveType leaveType
    );


    // For dashboard: recent leaves for employee
    @Query("SELECT l FROM LeaveApplication l WHERE l.employee.id = :employeeId")
    org.springframework.data.domain.Page<LeaveApplication> findTop5RecentByEmployeeId(@Param("employeeId") Long employeeId, org.springframework.data.domain.Pageable pageable);


}
