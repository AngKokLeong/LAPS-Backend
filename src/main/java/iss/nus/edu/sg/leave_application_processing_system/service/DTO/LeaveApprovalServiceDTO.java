package iss.nus.edu.sg.leave_application_processing_system.service.DTO;

public class LeaveApprovalServiceDTO implements ServiceDTO{

	private Long applicationId;
    private String status;
    private String action;
    private String managerRemarks;
    private Long managerId;
    
	public LeaveApprovalServiceDTO(Long applicationId, String action, Long managerId) {
		this.applicationId = applicationId;
		this.action = action;
		this.managerId = managerId;
	}
	
	@Override
	public ServiceDTO getAllAttribute() {
		return this;
	}

	// getters & setters
	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}	

	public String getManagerRemarks() {
		return managerRemarks;
	}

	public void setManagerRemarks(String managerRemarks) {
		this.managerRemarks = managerRemarks;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	
	
}
