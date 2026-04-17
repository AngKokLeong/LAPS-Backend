package iss.nus.edu.sg.leave_application_processing_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import iss.nus.edu.sg.leave_application_processing_system.model.CompensationLedger;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompensationLedgerRepository extends JpaRepository<CompensationLedger, Long> {

    Optional<CompensationLedger> findByEmployeeIdAndYearApplied(
            Long employeeId,
            int yearApplied
    );
    
    @Query("SELECT cl FROM CompensationLedger cl WHERE cl.employee.manager.id = :managerId")
    List<CompensationLedger> findAllByManagerId(@Param("managerId") Long managerId);

}