package iss.nus.edu.sg.leave_application_processing_system.service.DTO;

import java.time.LocalDate;

import iss.nus.edu.sg.leave_application_processing_system.persistent.enumeration.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.persistent.enumeration.LeaveType;

public class ViewLeaveRequestsServiceDTO implements ServiceDTO{

    //private Long id;
    private Long leaveRequestID;
    private Long staffId;
    private LeaveType leaveType;
    private LeaveStatus leaveStatus;

    //both can generate leavePeriod
	private LocalDate startDate;
	private LocalDate endDate;

    private String leaveDuration;
    private String leaveAppliedOn;
    private String reason;
    private String leaveApprover;

    // updatedDate
    private String leaveApprovalTransactionDate;
    //mgrRemarks
    private String leaveApprovalReason;

    
	private LocalDate appliedDate;
	private LocalDate updatedDate;

	private boolean halfDay;

        //Retrieve the data from the database
		//need to pass the staffId into the method
        
		// Leave Request Card Structure
			// Leave Application Id
			// Leave Type
			// Date of the Leave Request Submitted
			// Duration
				// Leave Date From - Leave Date To
			
			// Total Number of Leave Days
				// N days
			
			// Leave Status (Leave Request Status)
			
			// Applied , Updated
				// Show Edit Request button and Delete Request button
			
			// Approved
				// Show Cancel Request button when the Leave Period have started
			
			// Rejected

			// Cancelled

			// Deleted

    public ViewLeaveRequestsServiceDTO(){}




    

    public Long getLeaveRequestID() {
        return leaveRequestID;
    }






    public void setLeaveRequestID(Long leaveRequestID) {
        this.leaveRequestID = leaveRequestID;
    }






    public Long getStaffId() {
        return staffId;
    }






    public void setStaffId(Long staffId) {
        this.staffId = staffId;
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






    public LocalDate getAppliedDate() {
        return appliedDate;
    }






    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }






    public LocalDate getUpdatedDate() {
        return updatedDate;
    }






    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }






    public boolean isHalfDay() {
        return halfDay;
    }






    public void setHalfDay(boolean halfDay) {
        this.halfDay = halfDay;
    }






    @Override
    public ServiceDTO getAllAttribute() {
        return this;
    }
    
}
