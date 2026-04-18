package iss.nus.edu.sg.leave_application_processing_system.service.DTO;

import java.time.LocalDate;

import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;

public class TeamLeaveHistoryServiceDTO implements ServiceDTO {

    private Long employeeId;
    private String staffName;
    private String staffDesignation;
    private LeaveType leaveType;
    private LocalDate leaveStartDate;
    private LocalDate leaveEndDate;
    private String leaveDuration;
    private double numberOfLeaveDay;
    private LeaveStatus leaveStatus;
    private String reviewBy;
    private LocalDate leaveRequestUpdatedDate;

    public TeamLeaveHistoryServiceDTO() {
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getLeaveDuration() {
        return leaveDuration;
    }

    public void setLeaveDuration(String leaveDuration) {
        this.leaveDuration = leaveDuration;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffDesignation() {
        return staffDesignation;
    }

    public void setStaffDesignation(String staffDesignation) {
        this.staffDesignation = staffDesignation;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public LocalDate getLeaveStartDate() {
        return leaveStartDate;
    }

    public void setLeaveStartDate(LocalDate leaveStartDate) {
        this.leaveStartDate = leaveStartDate;
    }

    public LocalDate getLeaveEndDate() {
        return leaveEndDate;
    }

    public void setLeaveEndDate(LocalDate leaveEndDate) {
        this.leaveEndDate = leaveEndDate;
    }

    public double getNumberOfLeaveDay() {
        return numberOfLeaveDay;
    }

    public void setNumberOfLeaveDay(double numberOfLeaveDay) {
        this.numberOfLeaveDay = numberOfLeaveDay;
    }

    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(LeaveStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public String getReviewBy() {
        return reviewBy;
    }

    public void setReviewBy(String reviewBy) {
        this.reviewBy = reviewBy;
    }

    public LocalDate getLeaveRequestUpdatedDate() {
        return leaveRequestUpdatedDate;
    }

    public void setLeaveRequestUpdatedDate(LocalDate leaveRequestUpdatedDate) {
        this.leaveRequestUpdatedDate = leaveRequestUpdatedDate;
    }

    @Override
    public ServiceDTO getAllAttribute() {
        return this;
    }

}
