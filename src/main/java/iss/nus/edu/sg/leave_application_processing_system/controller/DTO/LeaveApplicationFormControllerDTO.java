package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

public class LeaveApplicationFormControllerDTO implements ControllerDTO{

    

    private String leaveType;
    private String leaveStartDate;
    private String leaveEndDate;
    private String leaveReason;
    private boolean IsHalfDayLeave;


    @Override
    public ControllerDTO getAllAttribute() {
        return this;
    }


    public LeaveApplicationFormControllerDTO() {
    }


    public LeaveApplicationFormControllerDTO(String leaveType, String leaveStartDate, String leaveEndDate, String leaveReason, boolean isHalfDayLeave) {
        this.leaveType = leaveType;
        this.leaveStartDate = leaveStartDate;
        this.leaveEndDate = leaveEndDate;
        this.leaveReason = leaveReason;
        this.IsHalfDayLeave = isHalfDayLeave;
    }


    public String getLeaveType() {
        return leaveType;
    }


    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }


    public String getLeaveStartDate() {
        return leaveStartDate;
    }


    public void setLeaveStartDate(String leaveStartDate) {
        this.leaveStartDate = leaveStartDate;
    }


    public String getLeaveEndDate() {
        return leaveEndDate;
    }


    public void setLeaveEndDate(String leaveEndDate) {
        this.leaveEndDate = leaveEndDate;
    }


    public String getLeaveReason() {
        return leaveReason;
    }


    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }


    public boolean isIsHalfDayLeave() {
        return IsHalfDayLeave;
    }


    public void setIsHalfDayLeave(boolean isHalfDayLeave) {
        this.IsHalfDayLeave = isHalfDayLeave;
    }
    
    
    

}
