package iss.nus.edu.sg.leave_application_processing_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iss.nus.edu.sg.leave_application_processing_system.model.CompensationLedger;

import java.util.Optional;

@Repository
public interface CompensationLedgerRepository extends JpaRepository<CompensationLedger, Long> {

    Optional<CompensationLedger> findByEmployeeIdAndYearApplied(
            String employeeId,
            int yearApplied
    );
}