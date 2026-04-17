package iss.nus.edu.sg.leave_application_processing_system.service.DTO;

import java.time.LocalDate;

import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;

public class CompensationLeaveServiceDTO implements ServiceDTO {

	private Long staffId;
    private LeaveType type;
    private LocalDate leavePeriodStart;
    private LocalDate leavePeriodEnd;
    private String reason;
    private boolean halfDay;
    

	public CompensationLeaveServiceDTO() {}

	public CompensationLeaveServiceDTO(Long staffId, LeaveType type, LocalDate leavePeriodStart,
			LocalDate leavePeriodEnd, String reason, boolean halfDay) {
		this.staffId = staffId;
		this.type = type;
		this.leavePeriodStart = leavePeriodStart;
		this.leavePeriodEnd = leavePeriodEnd;
		this.reason = reason;
		this.halfDay = halfDay;
	}



	@Override
	public ServiceDTO getAllAttribute() {
		return this;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public LeaveType getType() {
		return type;
	}

	public void setType(LeaveType type) {
		this.type = type;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean isHalfDay() {
		return halfDay;
	}

	public void setHalfDay(boolean halfDay) {
		this.halfDay = halfDay;
	}
	
	

}
