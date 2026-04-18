package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import java.time.LocalDate;

import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;

public class LeaveRequestControllerDTO implements ControllerDTO {

    private Long leaveRequestId;
    private LeaveType leaveType;
    private LeaveStatus leaveStatus;
    private String leavePeriod;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate currentDate;
    private String leaveDuration;
    private String leaveAppliedOn;
    private String reason;

    

    private String leaveApprover;
    private String leaveApprovalTransactionDate;
    private String leaveApprovalReason;

    public LeaveRequestControllerDTO() {
    }

    public LeaveRequestControllerDTO(LeaveType type, String leavePeriod, String leaveDuration, String leaveAppliedOn,
            String reason, LeaveStatus leaveStatus, String leaveApprover, String leaveApprovalTransactionDate,
            String leaveApprovalReason) {
        this.leaveType = type;
        this.leavePeriod = leavePeriod;
        this.leaveDuration = leaveDuration;
        this.leaveAppliedOn = leaveAppliedOn;
        this.reason = reason;
        this.leaveStatus = leaveStatus;
        this.leaveApprover = leaveApprover;
        this.leaveApprovalTransactionDate = leaveApprovalTransactionDate;
        this.leaveApprovalReason = leaveApprovalReason;
    }

    public String getLeavePeriod() {
        return leavePeriod;
    }

    public void setLeavePeriod(String leavePeriod) {
        this.leavePeriod = leavePeriod;
    }

    public String getLeaveDuration() {
        return leaveDuration;
    }

    public void setLeaveDuration(String leaveDuration) {
        this.leaveDuration = leaveDuration;
    }

    public String getLeaveAppliedOn() {
        return leaveAppliedOn;
    }

    public void setLeaveAppliedOn(String leaveAppliedOn) {
        this.leaveAppliedOn = leaveAppliedOn;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(LeaveStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public String getLeaveApprover() {
        return leaveApprover;
    }

    public void setLeaveApprover(String leaveApprover) {
        this.leaveApprover = leaveApprover;
    }

    public String getLeaveApprovalTransactionDate() {
        return leaveApprovalTransactionDate;
    }

    public void setLeaveApprovalTransactionDate(String leaveApprovalTransactionDate) {
        this.leaveApprovalTransactionDate = leaveApprovalTransactionDate;
    }

    public String getLeaveApprovalReason() {
        return leaveApprovalReason;
    }

    public void setLeaveApprovalReason(String leaveApprovalReason) {
        this.leaveApprovalReason = leaveApprovalReason;
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

    @Override
    public ControllerDTO getAllAttribute() {
        return this;
    }

    public Long getLeaveRequestId() {
        return leaveRequestId;
    }

    public void setLeaveRequestId(Long leaveRequestId) {
        this.leaveRequestId = leaveRequestId;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

}
