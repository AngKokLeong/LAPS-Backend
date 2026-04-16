package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import java.time.LocalDate;

import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;

public class LeaveMovementDTO {

    private String employeeName;
    private Long employeeId;
    private String department;
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;

    public LeaveMovementDTO(
            String employeeName,
            Long employeeId,
            String department,
            LeaveType leaveType,
            LocalDate startDate,
            LocalDate endDate) {

        this.employeeName = employeeName;
        this.employeeId = employeeId;
        this.department = department;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Long getEmployeeId() {
        return employeeId;
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
}

