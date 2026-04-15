package iss.nus.edu.sg.leave_application_processing_system.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import iss.nus.edu.sg.leave_application_processing_system.helper.OTClaimStatus;
import iss.nus.edu.sg.leave_application_processing_system.model.OverTimeClaim;

@Repository
public interface OverTimeClaimRepository extends JpaRepository<OverTimeClaim, Long> {
    
    // Employee OT History
    List<OverTimeClaim> findByEmployeeId(Long employeeId);

    // Manager approval views (used for Pending / Approved / Rejected) tabs
    List<OverTimeClaim> findByStatus(OTClaimStatus status);

    // Optional: filter OT claims by date range (might be used for reports / validation)
    List<OverTimeClaim> findByStartDateTimeBetween(
            LocalDateTime start,
            LocalDateTime end
    );
    
    @Query("SELECT o FROM OverTimeClaim o " +
            "WHERE o.employee.manager.id = :managerId " +
            "ORDER BY CASE WHEN o.status = 'PENDING' THEN 0 ELSE 1 END ASC, " +
            "o.submittedOn DESC")
    List<OverTimeClaim> findSubordinateClaimsCustomSort(@Param("managerId") Long managerId);


}