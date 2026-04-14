package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApprovalControllerDTO implements ControllerDTO {

	private boolean success;
    private String message;
    private Long applicationId;
    private String newStatus;


	@Override
	public ControllerDTO getAllAttribute() {
		return this;
	}

	// getters & setters
	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Long getApplicationId() {
		return applicationId;
	}


	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}


	public String getNewStatus() {
		return newStatus;
	}


	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

	
}
