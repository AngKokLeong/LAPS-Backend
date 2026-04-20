package iss.nus.edu.sg.leave_application_processing_system.api;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.DashboardDTO;
import iss.nus.edu.sg.leave_application_processing_system.persistent.entity.Employee;
import iss.nus.edu.sg.leave_application_processing_system.persistent.entity.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.persistent.entity.LeaveEntitlement;
import iss.nus.edu.sg.leave_application_processing_system.persistent.enumeration.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.persistent.repository.EmployeeRepository;
import iss.nus.edu.sg.leave_application_processing_system.persistent.repository.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.persistent.repository.LeaveEntitlementRepository;

@RestController
@RequestMapping("/api/staff")
public class StaffRestController {

    private final EmployeeRepository employeeRepo;
    private final LeaveApplicationRepository leaveAppRepo;
    private final LeaveEntitlementRepository entitlementRepo;

    public StaffRestController(EmployeeRepository employeeRepo,
                              LeaveApplicationRepository leaveAppRepo,
                              LeaveEntitlementRepository entitlementRepo) {
        this.employeeRepo = employeeRepo;
        this.leaveAppRepo = leaveAppRepo;
        this.entitlementRepo = entitlementRepo;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard(Authentication auth) {
        try {
            String email = auth.getName();
            Employee employee = employeeRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("Employee not found"));

            int currentYear = Year.now().getValue();

            // Fetch entitlements for current year
            List<LeaveEntitlement> entitlements = entitlementRepo.findByEmployeeId_Id(employee.getId())
                .stream()
                .filter(e -> e.getYearApplied() == currentYear)
                .toList();
            
            LeaveEntitlement annuLeaveEntitlement = entitlements.stream()
                .filter(e -> e.getLeaveType().toString().equals("ANNUAL"))
                .findFirst()
                .orElse(null);

            int annualTotal = annuLeaveEntitlement != null ? annuLeaveEntitlement.getTotalDays() : 0;
            int annualUsed = annuLeaveEntitlement != null ? annuLeaveEntitlement.getUsedDays() : 0;
            int annualRemaining = annualTotal - annualUsed;

            // Pending requests
            int pendingRequests = (int) leaveAppRepo.countByEmployeeIdAndLeaveStatusIn(employee.getId(), List.of(LeaveStatus.APPLIED, LeaveStatus.UPDATED));

            // Recent leaves (last 5)
            List<LeaveApplication> recentLeaves = leaveAppRepo.findTop5RecentByEmployeeId(employee.getId(), PageRequest.of(0, 5))
                .getContent()
                .stream()
                .sorted((a, b) -> b.getAppliedDate().compareTo(a.getAppliedDate()))
                .collect(Collectors.toList());
            List<DashboardDTO.LeaveSummaryDTO> recentLeaveDTOs = recentLeaves.stream()
                .map(app -> new DashboardDTO.LeaveSummaryDTO(
                    app.getLeaveType().toString(),
                    app.getStartDate().toString(),
                    app.getEndDate().toString(),
                    (int) (app.getEndDate().toEpochDay() - app.getStartDate().toEpochDay() + 1),
                    app.getLeaveStatus().toString()
                ))
                .collect(Collectors.toList());

            DashboardDTO dto = new DashboardDTO(
                employee.getName(),
                annualTotal,
                annualUsed,
                pendingRequests,
                annualRemaining,
                recentLeaveDTOs
            );

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}