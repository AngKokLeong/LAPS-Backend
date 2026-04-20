package iss.nus.edu.sg.leave_application_processing_system.service.features.compensation_leave_management;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.persistent.entity.CompensationLedger;
import iss.nus.edu.sg.leave_application_processing_system.persistent.entity.Employee;
import iss.nus.edu.sg.leave_application_processing_system.persistent.repository.CompensationLedgerRepository;
import iss.nus.edu.sg.leave_application_processing_system.persistent.repository.EmployeeRepository;
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


  // ADD to compensation ledger (called ONLY after OT claim is APPROVED)
  @Transactional
  public void addOvertimeHours(Long employeeId, int year, double otHours) {

    // find ledger OR create ledger if new
    CompensationLedger ledger = ledgerRepo.findByEmployeeIdAndYearApplied(employeeId, year)
    .orElseGet(() -> createNewLedger(employeeId, year));

    // 1. Combine with exisiting unconverted hours
    double totalHours = ledger.getUnconvertedHours() + otHours;

    // 2. Convert full 4-hour blocks
    int fullBlocks = (int) (totalHours / 4);
    double earnedDays = fullBlocks * 0.5;

    // 3. Update ledger
    ledger.setEarnedDays(ledger.getEarnedDays() + earnedDays);
    ledger.setUnconvertedHours(
      Math.round((totalHours % 4)*100.0)/100.0 // keep values like 1.999 from appearing
    );

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
      public double getAvailableCompensationDays(CompensationLedger ledger) {
        return ledger.getEarnedDays() - ledger.getUsedDays();
      }

      public double getUnconvertedHours(Long employeeId, int year) {
        return ledgerRepo.findByEmployeeIdAndYearApplied(employeeId, year)
        .map(CompensationLedger::getUnconvertedHours)
        .orElse(0.0);
      }
}
