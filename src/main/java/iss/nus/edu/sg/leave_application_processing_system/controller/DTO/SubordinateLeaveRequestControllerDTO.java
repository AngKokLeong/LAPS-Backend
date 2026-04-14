package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import java.time.LocalDate;

public class SubordinateLeaveRequestControllerDTO implements ControllerDTO {

	private Long applicationId;
    private String employeeName;
    private String department;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalDays;
    private String reason;
    private LocalDate submittedDate;
    private String status;

	public SubordinateLeaveRequestControllerDTO() {}

	public SubordinateLeaveRequestControllerDTO(Long applicationId, String employeeName, String department,
			String leaveType, LocalDate startDate, LocalDate endDate, double totalDays, String reason,
			LocalDate submittedDate, String status) {
		this.applicationId = applicationId;
		this.employeeName = employeeName;
		this.department = department;
		this.leaveType = leaveType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.totalDays = totalDays;
		this.reason = reason;
		this.submittedDate = submittedDate;
		this.status = status;
	}

	@Override
	public ControllerDTO getAllAttribute() {
		return this;
	}

	// getters & setters
	public Long getApplicationId() {
		return applicationId;
	}


	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}


	public String getEmployeeName() {
		return employeeName;
	}


	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}


	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	public String getLeaveType() {
		return leaveType;
	}


	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
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


	public double getTotalDays() {
		return totalDays;
	}


	public void setTotalDays(double totalDays) {
		this.totalDays = totalDays;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public LocalDate getSubmittedDate() {
		return submittedDate;
	}


	public void setSubmittedDate(LocalDate submittedDate) {
		this.submittedDate = submittedDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
