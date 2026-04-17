package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import java.time.LocalDateTime;
import iss.nus.edu.sg.leave_application_processing_system.helper.OTClaimStatus;

public class CompensationReportResponseDTO implements ControllerDTO {

    private Long claimId;
    private Long employeeId;
    private String employeeName;
    private String department;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private double hoursWorked;
    private String description;
    private OTClaimStatus status;

    public CompensationReportResponseDTO(Long claimId, Long employeeId, String employeeName,
            String department, LocalDateTime startDateTime, LocalDateTime endDateTime, double hoursWorked,
            String description, OTClaimStatus status) {
        this.claimId = claimId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.hoursWorked = hoursWorked;
        this.description = description;
        this.status = status;
    }

    @Override
    public ControllerDTO getAllAttribute() {
        return this;
    }

    public Long getClaimId() {
        return claimId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getDepartment() {
        return department;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public String getDescription() {
        return description;
    }

    public OTClaimStatus getStatus() {
        return status;
    }

}
