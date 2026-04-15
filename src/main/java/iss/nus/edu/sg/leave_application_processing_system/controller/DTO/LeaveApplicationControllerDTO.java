package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;



import java.time.LocalDate;

import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveEntitlement;




public class LeaveApplicationControllerDTO implements ControllerDTO{

    private LeaveType type;
    private Long staffId;
    private LeaveEntitlement leaveEntitlement;
    private LocalDate startDate;
    private LocalDate endDate;

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

    private boolean applicationResult;


    public LeaveApplicationControllerDTO() {
    }

    


    


    public LeaveApplicationControllerDTO(boolean applicationResult) {
        this.applicationResult = applicationResult;
    }







    public LeaveApplicationControllerDTO(LeaveType type, Long staffId, LocalDate startDate, LocalDate endDate,
            LeaveStatus leaveRequestStatus, String leaveDuration, LocalDate leaveAppliedOn, boolean applicationResult) {
        this.type = type;
        this.staffId = staffId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveRequestStatus = leaveRequestStatus;
        this.leaveDuration = leaveDuration;
        this.leaveAppliedOn = leaveAppliedOn;
        this.applicationResult = applicationResult;
    }











    public LeaveApplicationControllerDTO(LeaveType type, Long staffId, LeaveEntitlement leaveEntitlement,
            LocalDate startDate, LocalDate endDate, String reason, LeaveStatus leaveRequestStatus, String leaveDuration,
            LocalDate leaveAppliedOn, LocalDate leaveUpdateOn, String leaveApprover, String mgrRemarks,
            LocalDate leaveApprovalTransactionDate, String leaveApprovalReason, boolean halfDay) {
        this.type = type;
        this.staffId = staffId;
        this.leaveEntitlement = leaveEntitlement;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.leaveRequestStatus = leaveRequestStatus;
        this.leaveDuration = leaveDuration;
        this.leaveAppliedOn = leaveAppliedOn;
        this.leaveUpdateOn = leaveUpdateOn;
        this.leaveApprover = leaveApprover;
        this.mgrRemarks = mgrRemarks;
        this.leaveApprovalTransactionDate = leaveApprovalTransactionDate;
        this.leaveApprovalReason = leaveApprovalReason;
        this.halfDay = halfDay;
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




    @Override
    public ControllerDTO getAllAttribute() {
        return this;
    }






    public boolean getApplicationResult() {
        return applicationResult;
    }






    public void setApplicationResult(boolean applicationResult) {
        this.applicationResult = applicationResult;
    }
    
    
}
