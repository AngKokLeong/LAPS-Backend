package iss.nus.edu.sg.leave_application_processing_system.service.features.reporting;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.CompensationReportResponseDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveReportResponseDTO;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.helper.OTClaimStatus;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.model.OverTimeClaim;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.OverTimeClaimRepository;

@Service
public class ReportingService {

    private final LeaveApplicationRepository laRepo;
    private final OverTimeClaimRepository otClaimRepo;

    public ReportingService(LeaveApplicationRepository laRepo, OverTimeClaimRepository otClaimRepo) {
        this.laRepo = laRepo;
        this.otClaimRepo = otClaimRepo;
    }

    /**
     * Generate leave report for a manager's subordinates within a date range.
     * @param managerId The manager's employee ID
     * @param startDate Report start date
     * @param endDate Report end date
     * @param leaveType Filter by leave type (null = all types)
     * @param employeeId Filter by specific employee (null = all subordinates)
     * @return List of leave report DTOs
     */
    public List<LeaveReportResponseDTO> generateLeaveReport(Long managerId, LocalDate startDate,
            LocalDate endDate, String leaveType, Long employeeId) {

        // Convert leaveType string to enum (null if "all")
        iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType leaveTypeEnum = null;
        if (leaveType != null && !leaveType.equalsIgnoreCase("all")) {
            try {
                leaveTypeEnum = iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType
                        .valueOf(leaveType.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Invalid leave type, use null (all types)
                leaveTypeEnum = null;
            }
        }

        // Get approved leaves from repository
        List<LeaveApplication> leaves = laRepo.findManagerLeavesByDateRange(managerId, startDate,
                endDate, LeaveStatus.APPROVED, leaveTypeEnum);

        // If specific employee filter is applied, filter further
        if (employeeId != null) {
            leaves = leaves.stream()
                    .filter(l -> l.getEmployee().getId().equals(employeeId))
                    .collect(Collectors.toList());
        }

        // Map to response DTOs with calculated days taken
        return leaves.stream()
                .map(leave -> {
                    long daysTaken = calculateBusinessDays(leave.getStartDate(), leave.getEndDate(),
                            leave.isHalfDay());
                    return new LeaveReportResponseDTO(
                            leave.getEmployee().getId(),
                            leave.getEmployee().getName(),
                            leave.getEmployee().getDepartment(),
                            leave.getLeaveType(),
                            leave.getStartDate(),
                            leave.getEndDate(),
                            daysTaken,
                            leave.getReason());
                })
                .collect(Collectors.toList());
    }

    /**
     * Generate compensation claims report for a manager's subordinates within a date range.
     * @param managerId The manager's employee ID
     * @param startDate Report start date
     * @param endDate Report end date
     * @param employeeId Filter by specific employee (null = all subordinates)
     * @param statusFilter Filter by status (null = all statuses)
     * @return List of compensation report DTOs
     */
    public List<CompensationReportResponseDTO> generateCompensationReport(Long managerId, LocalDate startDate,
            LocalDate endDate, Long employeeId, String statusFilter) {

        // Convert status string to enum if provided
        OTClaimStatus status = null;
        if (statusFilter != null && !statusFilter.isEmpty()) {
            try {
                status = OTClaimStatus.valueOf(statusFilter.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Invalid status, use null (all statuses)
                status = null;
            }
        }

        // Convert LocalDate to LocalDateTime for repository query
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        // Get OT claims from repository
        List<OverTimeClaim> claims = otClaimRepo.findManagerOTClaimsByDateRange(managerId, startDateTime,
                endDateTime, status);

        // If specific employee filter is applied, filter further
        if (employeeId != null) {
            claims = claims.stream()
                    .filter(c -> c.getEmployee().getId().equals(employeeId))
                    .collect(Collectors.toList());
        }

        // Map to response DTOs with calculated hours
        return claims.stream()
                .map(claim -> {
                    double hoursWorked = calculateHoursBetween(claim.getStartDateTime(),
                            claim.getEndDateTime());
                    return new CompensationReportResponseDTO(
                            claim.getId(),
                            claim.getEmployee().getId(),
                            claim.getEmployee().getName(),
                            claim.getEmployee().getDepartment(),
                            claim.getStartDateTime(),
                            claim.getEndDateTime(),
                            hoursWorked,
                            claim.getOtDescription(),
                            claim.getStatus());
                })
                .collect(Collectors.toList());
    }

    /**
     * Calculate business days between two dates, accounting for half days.
     */
    private long calculateBusinessDays(LocalDate startDate, LocalDate endDate, boolean isHalfDay) {
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1; // +1 to include both dates
        if (isHalfDay) {
            return (totalDays + 1) / 2; // Round up for half days
        }
        return totalDays;
    }

    /**
     * Calculate hours between two LocalDateTimes.
     */
    private double calculateHoursBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0.0;
        }
        long minutes = ChronoUnit.MINUTES.between(start, end);
        return minutes / 60.0;
    }

}
