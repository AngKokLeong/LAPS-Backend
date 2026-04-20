package iss.nus.edu.sg.leave_application_processing_system.persistent.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import iss.nus.edu.sg.leave_application_processing_system.persistent.entity.LeaveEntitlement;
import iss.nus.edu.sg.leave_application_processing_system.persistent.enumeration.LeaveType;

public interface LeaveEntitlementRepository extends JpaRepository<LeaveEntitlement, Long>{
    Optional<LeaveEntitlement> findByEmployeeId_IdAndLeaveTypeAndYearApplied(
            Long employeeId,
            LeaveType leaveType,
            int yearApplied
    );

    @Query("SELECT le FROM LeaveEntitlement le WHERE le.employeeId.id = :employeeId")
    List<LeaveEntitlement> findByEmployeeId_Id(@Param("employeeId") Long employeeId);
    
    @Query("SELECT le FROM LeaveEntitlement le " + 
            "WHERE le.employeeId.manager.id = :managerId " + 
            "AND le.yearApplied = :year")
    List<LeaveEntitlement> findAllByManagerId(@Param("managerId") Long managerId, @Param("year") int year);

}
