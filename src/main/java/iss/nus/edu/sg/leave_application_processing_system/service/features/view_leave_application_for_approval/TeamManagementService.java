package iss.nus.edu.sg.leave_application_processing_system.service.features.view_leave_application_for_approval;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveApprovalControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.OTClaimControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.SubordinateLeaveBalanceControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.SubordinateLeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;
import iss.nus.edu.sg.leave_application_processing_system.model.CompensationLedger;
import iss.nus.edu.sg.leave_application_processing_system.model.Employee;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveEntitlement;
import iss.nus.edu.sg.leave_application_processing_system.model.OverTimeClaim;
import iss.nus.edu.sg.leave_application_processing_system.repo.CompensationLedgerRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveEntitlementRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.OverTimeClaimRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.LeaveApprovalServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ManagerQueryServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TeamManagementService {

    private final OverTimeClaimRepository otClaimRepo;
    private final LeaveApplicationRepository laRepo;
    private final LeaveEntitlementRepository lEntitlementRepo;
    private final CompensationLedgerRepository clRepo;

    public TeamManagementService(
            OverTimeClaimRepository otClaimRepo,
            LeaveApplicationRepository laRepo,
            LeaveEntitlementRepository lEntitlementRepo,
            CompensationLedgerRepository clRepo) {
        this.otClaimRepo = otClaimRepo;
        this.laRepo = laRepo;
        this.lEntitlementRepo = lEntitlementRepo;
        this.clRepo = clRepo;
    }

    // ===============================
    // MANAGER APPROVAL
    // ===============================

    @Transactional
    public ControllerDTO processApproval(ServiceDTO serviceDTO) {

        LeaveApprovalServiceDTO request = (LeaveApprovalServiceDTO) serviceDTO.getAllAttribute();

        LeaveApprovalControllerDTO response = new LeaveApprovalControllerDTO();

        LeaveApplication application = laRepo.findById(request.getApplicationId())
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));

        LeaveStatus oldStatus = application.getLeaveStatus();
        String action = request.getAction().toUpperCase();

        if ("APPROVE".equals(action)) {

            if (oldStatus != LeaveStatus.APPLIED) {
                throw new IllegalStateException(
                        "Only APPLIED requests can be approved.");
            }

            if (application.getLeaveType() == LeaveType.COMPENSATION) {
                deductCompensationLeave(application);
            }

            if (application.getLeaveType() == LeaveType.ANNUAL ||
                    application.getLeaveType() == LeaveType.MEDICAL) {
                deductEntitlementLeave(application);
            }

            application.setLeaveStatus(LeaveStatus.APPROVED);
            application.setMgrRemarks(request.getManagerRemarks());

            response.setNewStatus("APPROVED");
            response.setMessage("Application approved and balance updated.");
            response.setSuccess(true);
        }

        else if ("REJECT".equals(action)) {

            if (oldStatus == LeaveStatus.APPROVED) {
                revertEntitlementOrCompensation(application);
            }

            application.setLeaveStatus(LeaveStatus.REJECTED);
            application.setMgrRemarks(request.getManagerRemarks());

            response.setNewStatus("REJECTED");
            response.setMessage("Application rejected.");
            response.setSuccess(true);
        }

        else {
            throw new IllegalArgumentException("Unknown action: " + action);
        }

        laRepo.save(application);
        return response;
    }

    // ===============================
    // HELPER METHODS
    // ===============================

    private void deductEntitlementLeave(LeaveApplication app) {

        int year = app.getStartDate().getYear();

        LeaveEntitlement entitlement = lEntitlementRepo
                .findByEmployeeId_IdAndLeaveTypeAndYearApplied(
                        app.getEmployee().getId(),
                        app.getLeaveType(),
                        year)
                .orElseThrow(() -> new IllegalStateException("Entitlement not found"));

        double days = calculateDuration(app.getStartDate(), app.getEndDate(), app.isHalfDay());

        double newUsed = entitlement.getUsedDays() + days;

        if (newUsed > entitlement.getTotalDays()) {
            throw new IllegalArgumentException(
                    "Insufficient leave balance.");
        }

        entitlement.setUsedDays((int) (entitlement.getUsedDays() + days));
        lEntitlementRepo.save(entitlement);
    }

    private void deductCompensationLeave(LeaveApplication app) {

        int year = app.getStartDate().getYear();

        CompensationLedger ledger = clRepo.findByEmployeeIdAndYearApplied(
                app.getEmployee().getId(), year)
                .orElseThrow(() -> new EntityNotFoundException("Compensation ledger not found"));

        double days = calculateDuration(app.getStartDate(), app.getEndDate(), app.isHalfDay());

        double newUsed = ledger.getUsedDays() + days;

        if (newUsed > ledger.getEarnedDays()) {
            throw new IllegalArgumentException(
                    "Insufficient compensation balance.");
        }

        ledger.setUsedDays(newUsed);
        clRepo.save(ledger);
    }

    private void revertEntitlementOrCompensation(LeaveApplication app) {

        double days = calculateDuration(app.getStartDate(), app.getEndDate(), app.isHalfDay());

        int year = app.getStartDate().getYear();

        if (app.getLeaveType() == LeaveType.COMPENSATION) {

            CompensationLedger ledger = clRepo.findByEmployeeIdAndYearApplied(
                    app.getEmployee().getId(), year)
                    .orElseThrow(() -> new EntityNotFoundException("Compensation ledger not found"));

            ledger.setUsedDays(ledger.getUsedDays() - days);
            clRepo.save(ledger);

        } else {

            LeaveEntitlement entitlement = lEntitlementRepo
                    .findByEmployeeId_IdAndLeaveTypeAndYearApplied(
                            app.getEmployee().getId(),
                            app.getLeaveType(),
                            year)
                    .orElseThrow(() -> new IllegalStateException("Entitlement not found"));

            entitlement.setUsedDays((int) (entitlement.getUsedDays() - days));
            lEntitlementRepo.save(entitlement);
        }
    }

    private double calculateDuration(LocalDate start, LocalDate end, boolean isHalfDay) {

        double workingDays = 0;
        LocalDate current = start;

        while (!current.isAfter(end)) {
            DayOfWeek dow = current.getDayOfWeek();
            if (dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY) {
                workingDays++;
            }
            current = current.plusDays(1);
        }

        return isHalfDay ? workingDays * 0.5 : workingDays;
    }

    public List<ControllerDTO> viewTeamLeaveBalances(ServiceDTO serviceDTO) {

        ManagerQueryServiceDTO input = (ManagerQueryServiceDTO) serviceDTO.getAllAttribute();

        Long managerId = input.getManagerId();
        int currentYear = LocalDate.now().getYear();

        List<LeaveEntitlement> entitlements = lEntitlementRepo.findAllByManagerId(managerId, currentYear);

        List<CompensationLedger> compLedgers = clRepo.findAllByManagerId(managerId);

        Map<Long, SubordinateLeaveBalanceControllerDTO> balanceMap = new HashMap<>();

        // Annual / Medical
        for (LeaveEntitlement ent : entitlements) {
            Employee emp = ent.getEmployeeId();

            SubordinateLeaveBalanceControllerDTO dto = balanceMap.computeIfAbsent(emp.getId(), id -> {
                SubordinateLeaveBalanceControllerDTO d = new SubordinateLeaveBalanceControllerDTO();
                d.setEmployeeName(emp.getName());
                d.setEmail(emp.getEmail());
                d.setDepartment(emp.getDepartment());
                return d;
            });

            double remaining = ent.getTotalDays() - ent.getUsedDays();
            if (ent.getLeaveType() == LeaveType.ANNUAL) {
                dto.setAnnualBalance((int) remaining);
            } else if (ent.getLeaveType() == LeaveType.MEDICAL) {
                dto.setMedicalBalance((int) remaining);
            }
        }

        // Compensation
        for (CompensationLedger ledger : compLedgers) {
            Employee emp = ledger.getEmployee();

            SubordinateLeaveBalanceControllerDTO dto = balanceMap.computeIfAbsent(emp.getId(), id -> {
                SubordinateLeaveBalanceControllerDTO d = new SubordinateLeaveBalanceControllerDTO();
                d.setEmployeeName(emp.getName());
                d.setEmail(emp.getEmail());
                d.setDepartment(emp.getDepartment());
                return d;
            });

            dto.setCompensationBalance(
                    ledger.getEarnedDays() - ledger.getUsedDays());
        }

        // Total
        for (SubordinateLeaveBalanceControllerDTO dto : balanceMap.values()) {
            dto.setTotalBalance(
                    dto.getAnnualBalance()
                            + dto.getMedicalBalance()
                            + dto.getCompensationBalance());
        }

        return new ArrayList<>(balanceMap.values());
    }

 
    public List<ControllerDTO> getSubordinateLeaveRequests(ServiceDTO serviceDTO) {

        ManagerQueryServiceDTO query = (ManagerQueryServiceDTO) serviceDTO.getAllAttribute();

        List<LeaveApplication> leaveApplications = laRepo.findSubordinateLeaves(query.getManagerId());

        return leaveApplications.stream()
                .map(leave -> new SubordinateLeaveRequestControllerDTO(
                        leave.getId(),
                        leave.getEmployee().getName(),
                        leave.getEmployee().getDepartment(),
                        leave.getLeaveType().toString(),
                        leave.getStartDate(),
                        leave.getEndDate(),
                        calculateDuration(
                                leave.getStartDate(),
                                leave.getEndDate(),
                                leave.isHalfDay()),
                        leave.getReason(),
                        leave.getAppliedDate(),
                        leave.getLeaveStatus().toString()))
                .collect(Collectors.toList());
    }

    
    public List<ControllerDTO> getSubordinateOTClaims(ServiceDTO serviceDTO) {

        ManagerQueryServiceDTO query = (ManagerQueryServiceDTO) serviceDTO.getAllAttribute();

        List<OverTimeClaim> claims = otClaimRepo.findSubordinateClaimsCustomSort(query.getManagerId());

        return claims.stream().map(claim -> {

            Duration d = Duration.between(
                    claim.getStartDateTime(),
                    claim.getEndDateTime());

            long hours = d.toHours();
            long mins = d.toMinutes() % 60;

            return new OTClaimControllerDTO(
                    claim.getId(),
                    claim.getEmployee().getName(),
                    claim.getEmployee().getDepartment(),
                    claim.getStartDateTime(),
                    claim.getEndDateTime(),
                    hours + "h " + mins + "m",
                    claim.getStatus(),
                    claim.getOtDescription());
        }).collect(Collectors.toList());
    }
}