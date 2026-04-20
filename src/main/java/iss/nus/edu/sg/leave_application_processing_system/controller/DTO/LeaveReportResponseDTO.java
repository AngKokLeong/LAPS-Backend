package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import java.time.LocalDate;

import iss.nus.edu.sg.leave_application_processing_system.persistent.enumeration.LeaveType;

public class LeaveReportResponseDTO implements ControllerDTO {

    private Long employeeId;
    private String employeeName;
    private String department;
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private long daysTaken;
    private String reason;

    public LeaveReportResponseDTO(Long employeeId, String employeeName, String department,
            LeaveType leaveType, LocalDate startDate, LocalDate endDate, long daysTaken, String reason) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.daysTaken = daysTaken;
        this.reason = reason;
    }

    @Override
    public ControllerDTO getAllAttribute() {
        return this;
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

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public long getDaysTaken() {
        return daysTaken;
    }

    public String getReason() {
        return reason;
    }

}
