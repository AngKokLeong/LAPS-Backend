package iss.nus.edu.sg.leave_application_processing_system.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;
import iss.nus.edu.sg.leave_application_processing_system.repo.CompensationLedgerRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveEntitlementRepository;

@Service
public class LeaveBalanceService {
	private LeaveEntitlementRepository leRepo;

    private CompensationLedgerRepository clRepo;
    
    public LeaveBalanceService(LeaveEntitlementRepository leRepo, CompensationLedgerRepository clRepo) {
		this.leRepo = leRepo;
		this.clRepo = clRepo;
	}

	public int getAnnualBalance(Long userId) {
        int currentYear = LocalDate.now().getYear();
        return leRepo.findByEmployeeId_IdAndLeaveTypeAndYearApplied(userId, LeaveType.ANNUAL, currentYear)
                .map(ent -> ent.getTotalDays() - ent.getUsedDays())
                .orElse(0); // Return 0 if no entitlement record found
    }

    public int getMedicalBalance(Long userId) {
        int currentYear = LocalDate.now().getYear();
        return leRepo.findByEmployeeId_IdAndLeaveTypeAndYearApplied(userId, LeaveType.MEDICAL, currentYear)
                .map(ent -> ent.getTotalDays() - ent.getUsedDays())
                .orElse(0);
    }

    public double getCompBalance(Long userId) {
        int currentYear = LocalDate.now().getYear();
        // Assuming you have a method in CompensationLedgerRepository to find by employee and year
        return clRepo.findByEmployeeIdAndYearApplied(userId, currentYear)
                .map(comp -> comp.getEarnedDays() - comp.getUsedDays())
                .orElse(0.0);
    }
}
