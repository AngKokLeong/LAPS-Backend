package iss.nus.edu.sg.leave_application_processing_system.service;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.model.CompensationLedger;
import iss.nus.edu.sg.leave_application_processing_system.model.Employee;
import iss.nus.edu.sg.leave_application_processing_system.repo.CompensationLedgerRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.EmployeeRepository;
import jakarta.transaction.Transactional;

@Service
public class CompensationService {
  
  private final CompensationLedgerRepository ledgerRepo;
  private final EmployeeRepository employeeRepo;
  
  public CompensationService(
    CompensationLedgerRepository ledgerRepo,
    EmployeeRepository employeeRepo) {
    this.ledgerRepo = ledgerRepo;
    this.employeeRepo = employeeRepo;
  }


  // ADD earned compensation days (called ONLY after OT claim is APPROVED)
  @Transactional
  public void addEarnedDays(
    Long employeeId,
    int year,
    double earnedDays) {
      CompensationLedger ledger =
      ledgerRepo.findByEmployeeIdAndYearApplied(employeeId, year)
      .orElseGet(() -> createNewLedger(employeeId, year));

      ledger.setEarnedDays(ledger.getEarnedDays() + earnedDays);
      ledgerRepo.save(ledger);
    }


  // DEDUCT compensation days (called ONLY after compensation leave is APPROVED)
  @Transactional
  public void deductUsedDays(
    CompensationLedger ledger,
    double usedDays) {
      double available = ledger.getEarnedDays() - ledger.getUsedDays();

        if (usedDays > available) {
            throw new IllegalArgumentException(
                    "Insufficient compensation leave balance");
        }

        ledger.setUsedDays(ledger.getUsedDays() + usedDays);
        ledgerRepo.save(ledger);
    }

    // ✅ Helper: Create yearly ledger when first OT is approved    
    private CompensationLedger createNewLedger(
      Long employeeId,
      int year) {
        Employee employee = employeeRepo.findById(employeeId)
        .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        
        CompensationLedger ledger = new CompensationLedger();
        ledger.setEmployee(employee);
        ledger.setYearApplied(year);
        ledger.setEarnedDays(0);
        ledger.setUsedDays(0);
        
        return ledgerRepo.save(ledger);
      }
      
      // ✅ Check available compensation balance
      public double getAvailableDays(CompensationLedger ledger) {
        return ledger.getEarnedDays() - ledger.getUsedDays();
      }


}
