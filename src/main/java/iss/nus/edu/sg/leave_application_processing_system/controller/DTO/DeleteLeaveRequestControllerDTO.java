package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;

public class DeleteLeaveRequestControllerDTO implements ControllerDTO {

    private Long leaveRequestId;
    private Long employeeId;
    private LeaveType leaveType;
    private LeaveStatus leaveStatus;
    private String startDate;
    private String endDate;
    private String reason;
    private boolean operationResult;
    private String operationComments;

    public DeleteLeaveRequestControllerDTO() {
    }

    public Long getLeaveRequestId() {
        return leaveRequestId;
    }

    public void setLeaveRequestId(Long leaveRequestId) {
        this.leaveRequestId = leaveRequestId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isOperationResult() {
        return operationResult;
    }

    public void setOperationResult(boolean operationResult) {
        this.operationResult = operationResult;
    }

    public String getOperationComments() {
        return operationComments;
    }

    public void setOperationComments(String operationComments) {
        this.operationComments = operationComments;
    }

    @Override
    public ControllerDTO getAllAttribute() {
        return this;
    }
}