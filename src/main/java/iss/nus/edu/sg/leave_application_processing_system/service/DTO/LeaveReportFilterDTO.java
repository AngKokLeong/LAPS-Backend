package iss.nus.edu.sg.leave_application_processing_system.service.DTO;

import java.time.LocalDate;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;

public class LeaveReportFilterDTO implements ServiceDTO {

    private Long managerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveType leaveType; // Can be null for "All"
    private Long employeeId; // Can be null for all subordinates

    public LeaveReportFilterDTO(Long managerId, LocalDate startDate, LocalDate endDate, 
            LeaveType leaveType, Long employeeId) {
        this.managerId = managerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leaveType;
        this.employeeId = employeeId;
    }

    @Override
    public ServiceDTO getAllAttribute() {
        return this;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

}
