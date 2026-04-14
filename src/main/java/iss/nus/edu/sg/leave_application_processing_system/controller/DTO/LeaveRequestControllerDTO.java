package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;



import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;



public class LeaveRequestControllerDTO implements ControllerDTO{

    private String type;
    private String leavePeriod;
    private String leaveDuration;
    private String leaveAppliedOn;
    private String reason;
    private String leaveRequestStatus;
    private String leaveApprover;
    private String leaveApprovalTransactionDate;
    private String leaveApprovalReason;

    

    public LeaveRequestControllerDTO(String type, String leavePeriod, String leaveDuration, String leaveAppliedOn, String reason, String leaveRequestStatus, String leaveApprover, String leaveApprovalTransactionDate, String leaveApprovalReason){
        this.type = type;
        this.leavePeriod = leavePeriod;
        this.leaveDuration = leaveDuration;
        this.leaveAppliedOn = leaveAppliedOn;
        this.reason = reason;
        this.leaveRequestStatus = leaveRequestStatus;
        this.leaveApprover = leaveApprover;
        this.leaveApprovalTransactionDate = leaveApprovalTransactionDate;
        this.leaveApprovalReason = leaveApprovalReason;
    }

    
    


    public String getType() {
        return type;
    }





    public void setType(String type) {
        this.type = type;
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





    public String getLeaveRequestStatus() {
        return leaveRequestStatus;
    }





    public void setLeaveRequestStatus(String leaveRequestStatus) {
        this.leaveRequestStatus = leaveRequestStatus;
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





    @Override
    public ControllerDTO getAllAttribute() {
        return this;
    }
    
    
}
