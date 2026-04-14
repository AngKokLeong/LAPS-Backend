package iss.nus.edu.sg.leave_application_processing_system.service.DTO;

public class ManagerQueryServiceDTO implements ServiceDTO {

	private Long managerId;
	
	public ManagerQueryServiceDTO(Long managerId) {
        this.managerId = managerId;
    }
	
	@Override
	public ServiceDTO getAllAttribute() {
		// TODO Auto-generated method stub
		return this;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	
	

}
