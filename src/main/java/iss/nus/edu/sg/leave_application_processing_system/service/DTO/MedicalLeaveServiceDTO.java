package iss.nus.edu.sg.leave_application_processing_system.service.DTO;

import java.time.LocalDate;


import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveEntitlement;

public class MedicalLeaveServiceDTO implements ServiceDTO{
    
    private LeaveType type;


    private Long staffId;

    
    private LeaveEntitlement leaveEntitlement;


    private String reason;


    private LeaveStatus leaveRequestStatus;
    private String leaveDuration;
    private LocalDate leaveAppliedOn;
    private LocalDate leaveUpdateOn;

    private String leaveApprover;
    private String mgrRemarks;
    private LocalDate leaveApprovalTransactionDate;
    private String leaveApprovalReason;

	private boolean halfDay;


    private LocalDate leavePeriodStart;
    private LocalDate leavePeriodEnd;




    public MedicalLeaveServiceDTO() {
    }




    public LeaveType getType() {
        return type;
    }




    public void setType(LeaveType type) {
        this.type = type;
    }




    public Long getStaffId() {
        return staffId;
    }




    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }




    public LeaveEntitlement getLeaveEntitlement() {
        return leaveEntitlement;
    }




    public void setLeaveEntitlement(LeaveEntitlement leaveEntitlement) {
        this.leaveEntitlement = leaveEntitlement;
    }




    public String getReason() {
        return reason;
    }




    public void setReason(String reason) {
        this.reason = reason;
    }




    public LeaveStatus getLeaveRequestStatus() {
        return leaveRequestStatus;
    }




    public void setLeaveRequestStatus(LeaveStatus leaveRequestStatus) {
        this.leaveRequestStatus = leaveRequestStatus;
    }




    public String getLeaveDuration() {
        return leaveDuration;
    }




    public void setLeaveDuration(String leaveDuration) {
        this.leaveDuration = leaveDuration;
    }




    public LocalDate getLeaveAppliedOn() {
        return leaveAppliedOn;
    }




    public void setLeaveAppliedOn(LocalDate leaveAppliedOn) {
        this.leaveAppliedOn = leaveAppliedOn;
    }




    public LocalDate getLeaveUpdateOn() {
        return leaveUpdateOn;
    }




    public void setLeaveUpdateOn(LocalDate leaveUpdateOn) {
        this.leaveUpdateOn = leaveUpdateOn;
    }




    public String getLeaveApprover() {
        return leaveApprover;
    }




    public void setLeaveApprover(String leaveApprover) {
        this.leaveApprover = leaveApprover;
    }




    public String getMgrRemarks() {
        return mgrRemarks;
    }




    public void setMgrRemarks(String mgrRemarks) {
        this.mgrRemarks = mgrRemarks;
    }




    public LocalDate getLeaveApprovalTransactionDate() {
        return leaveApprovalTransactionDate;
    }




    public void setLeaveApprovalTransactionDate(LocalDate leaveApprovalTransactionDate) {
        this.leaveApprovalTransactionDate = leaveApprovalTransactionDate;
    }




    public String getLeaveApprovalReason() {
        return leaveApprovalReason;
    }




    public void setLeaveApprovalReason(String leaveApprovalReason) {
        this.leaveApprovalReason = leaveApprovalReason;
    }




    public boolean isHalfDay() {
        return halfDay;
    }




    public void setHalfDay(boolean halfDay) {
        this.halfDay = halfDay;
    }




    public LocalDate getLeavePeriodStart() {
        return leavePeriodStart;
    }




    public void setLeavePeriodStart(LocalDate leavePeriodStart) {
        this.leavePeriodStart = leavePeriodStart;
    }




    public LocalDate getLeavePeriodEnd() {
        return leavePeriodEnd;
    }




    public void setLeavePeriodEnd(LocalDate leavePeriodEnd) {
        this.leavePeriodEnd = leavePeriodEnd;
    }




    @Override
    public ServiceDTO getAllAttribute() {
        return this;
    }


}
