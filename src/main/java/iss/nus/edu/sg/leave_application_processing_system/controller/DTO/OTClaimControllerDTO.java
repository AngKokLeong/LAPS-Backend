package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OTClaimControllerDTO implements ControllerDTO {

	private Long claimId;
    private String employeeName;
    private String department;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String duration;
    private String status; // APPROVED, PENDING, REJECTED
    private String description;
    private LocalDateTime submittedOn;
	
       
	public OTClaimControllerDTO() {}
	
	public OTClaimControllerDTO(Long claimId, String employeeName, String department, LocalDateTime startDateTime,
			LocalDateTime endDateTime, String duration, String status, String description, LocalDateTime submittedOn) {
		this.claimId = claimId;
		this.employeeName = employeeName;
		this.department = department;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.duration = duration;
		this.status = status;
		this.description = description;
		this.submittedOn = submittedOn;
	}

	@Override
	public ControllerDTO getAllAttribute() {
		return this;
	}


	public Long getClaimId() {
		return claimId;
	}

	public void setClaimId(Long claimId) {
		this.claimId = claimId;
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

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(LocalDateTime submittedOn) {
		this.submittedOn = submittedOn;
	}
	
	

}
