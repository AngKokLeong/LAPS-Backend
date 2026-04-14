package iss.nus.edu.sg.leave_application_processing_system.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveEntitlement;

public interface LeaveEntitlementRepository extends JpaRepository<LeaveEntitlement, Long>{
    Optional<LeaveEntitlement> findByEmployeeIdAndLeaveTypeAndYearApplied(
            Long employeeId,
            LeaveType leaveType,
            int yearApplied
    );

    List<LeaveEntitlement> findByEmployeeId(Long employeeId);

}
