package iss.nus.edu.sg.leave_application_processing_system.service.features.shared_service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveApplicationControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.persistent.entity.CompensationLedger;
import iss.nus.edu.sg.leave_application_processing_system.persistent.entity.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.persistent.entity.LeaveEntitlement;
import iss.nus.edu.sg.leave_application_processing_system.persistent.entity.PublicHoliday;
import iss.nus.edu.sg.leave_application_processing_system.persistent.enumeration.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.persistent.enumeration.LeaveType;
import iss.nus.edu.sg.leave_application_processing_system.persistent.repository.CompensationLedgerRepository;
import iss.nus.edu.sg.leave_application_processing_system.persistent.repository.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.persistent.repository.LeaveEntitlementRepository;
import iss.nus.edu.sg.leave_application_processing_system.persistent.repository.PublicHolidayRepository;

@Service
public class LeaveValidationService {
	
	private final LeaveApplicationRepository laRepo;
    private final LeaveEntitlementRepository entitlementRepo;
    private final CompensationLedgerRepository compRepo;
    private final PublicHolidayRepository phRepo;
    
    

    public LeaveValidationService(LeaveApplicationRepository laRepo, LeaveEntitlementRepository entitlementRepo,
			CompensationLedgerRepository compRepo, PublicHolidayRepository phRepo) {
		this.laRepo = laRepo;
		this.entitlementRepo = entitlementRepo;
		this.compRepo = compRepo;
		this.phRepo = phRepo;
	}
    
    public class DayCountResult {
        public double workingDays = 0;
        public int weekendCount = 0;
        public int holidayCount = 0;
    }

    public String validate(LeaveApplicationControllerDTO dto) {
        
    	DayCountResult countDetail = calculateDaysDetail(dto.getStartDate(), dto.getEndDate(), dto.isHalfDay());
        double workingDaysRequested = countDetail.workingDays;
        
        // Basic Date Logic
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            return "End date cannot be earlier than start date.";
        }

        // Non-Working Day Checks
        if (workingDaysRequested <= 0) {
        	if (countDetail.weekendCount > 0 && countDetail.holidayCount > 0) {
                return "The selected period consists only of weekends and public holidays.";
            } else if (countDetail.holidayCount > 0) {
                return "The selected period consists only of public holidays.";
            } else {
                return "The selected period consists only of weekends.";
            }
        }

        // Company Rule: 14 Calendar Day Limit
        long calendarDays = java.time.temporal.ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) + 1;
        if (calendarDays > 14) {
            return "Leave applications cannot exceed 14 calendar days (including weekends).";
        }

        // Overlap Check
        if (hasOverlap(dto)) {
            return "You have an existing pending or approved leave application that overlaps with these dates.";
        }

        // Balance Check (Including Pending Approval)
        double currentAvailableBalance = calculateAvailableBalance(dto.getStaffId(), dto.getType(), dto.getStartDate().getYear());
        if (workingDaysRequested > currentAvailableBalance) {
            return String.format("Insufficient balance. Requested: %.1f, Available (including pending): %.1f", 
                                 workingDaysRequested, currentAvailableBalance);
        }

        return null; // All validations passed
    }

	// helper method to check overlapping application
    private boolean hasOverlap(LeaveApplicationControllerDTO dto) {
        List<LeaveStatus> activeStatuses = Arrays.asList(LeaveStatus.APPLIED, LeaveStatus.UPDATED, LeaveStatus.APPROVED);
        return !laRepo.findOverlappingLeaves(
            dto.getStaffId(), 
            dto.getStartDate(), 
            dto.getEndDate(), 
            activeStatuses
        ).isEmpty();
    }

    private double calculateAvailableBalance(Long staffId, LeaveType type, int year) {
        double totalEntitled = 0;
        double usedDays = 0;

        // Fetching the "Hard" data from Entitlement or Compensation tables
        if (type == LeaveType.COMPENSATION) {
            Optional<CompensationLedger> ledger = compRepo.findByEmployeeIdAndYearApplied(staffId, year);
            if (ledger.isPresent()) {
                totalEntitled = ledger.get().getEarnedDays();
                usedDays = ledger.get().getUsedDays();
            }
        } else {
            Optional<LeaveEntitlement> ent = entitlementRepo.findByEmployeeId_IdAndLeaveTypeAndYearApplied(staffId, type, year);
            if (ent.isPresent()) {
                totalEntitled = ent.get().getTotalDays();
                usedDays = ent.get().getUsedDays();
            }
        }

        // Calculate "Pending" days: Applications submitted but not yet Approved or Rejected
        double pendingDays = calculatePendingDays(staffId, type, year);

        // Actual Available = Total - Already Used (Approved) - Still Pending
        return totalEntitled - usedDays - pendingDays;
    }

    private double calculatePendingDays(Long staffId, LeaveType type, int year) {
        // Fetch all APPLIED (Pending) applications for this user/type/year
        List<LeaveApplication> pendingApps = laRepo.findByEmployeeIdAndLeaveTypeAndLeaveStatusAndYear(staffId, type, LeaveStatus.APPLIED, year);
        
        return pendingApps.stream()
                .mapToDouble(app -> calculateDays(app.getStartDate(), app.getEndDate(), app.isHalfDay()))
                .sum();
    }

    // Reuse weekend-skipping logic here
    public double calculateDays(LocalDate start, LocalDate end, boolean isHalfDay) {
        // Fetch all holiday dates within this range
        List<LocalDate> holidays = phRepo.findAll().stream()
                .map(PublicHoliday::getPhDate)
                .collect(Collectors.toList());

        double workingDaysCount = 0;
        LocalDate current = start;

        while (!current.isAfter(end)) {
            DayOfWeek dow = current.getDayOfWeek();
            boolean isWeekend = (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY);
            boolean isHoliday = holidays.contains(current);

            if (!isWeekend && !isHoliday) {
                workingDaysCount++;
            }
            current = current.plusDays(1);
        }

        return isHalfDay ? workingDaysCount * 0.5 : workingDaysCount;
    }
    
    public DayCountResult calculateDaysDetail(LocalDate start, LocalDate end, boolean isHalfDay) {
        DayCountResult result = new DayCountResult();
        List<LocalDate> holidays = phRepo.findAll().stream()
                .map(PublicHoliday::getPhDate)
                .collect(Collectors.toList());

        LocalDate current = start;
        while (!current.isAfter(end)) {
            DayOfWeek dow = current.getDayOfWeek();
            boolean isWeekend = (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY);
            boolean isHoliday = holidays.contains(current);

            if (isWeekend) {
                result.weekendCount++;
            } else if (isHoliday) {
                result.holidayCount++;
            } else {
                result.workingDays++;
            }
            current = current.plusDays(1);
        }
        
        if (isHalfDay) result.workingDays *= 0.5;
        return result;
    }

}
