package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import lombok.Data;

@Data
public class LeaveEntitlementCreditDTO implements ControllerDTO {

	private String employeeName;
	private int annualEntitlement;
	private int medicalEntitlement;
	
	public LeaveEntitlementCreditDTO() {}

	public LeaveEntitlementCreditDTO(String employeeName, int annualEntitlement, int medicalEntitlement) {
		this.employeeName = employeeName;
		this.annualEntitlement = annualEntitlement;
		this.medicalEntitlement = medicalEntitlement;
	}

	@Override
	public ControllerDTO getAllAttribute() {
		// TODO Auto-generated method stub
		return this;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getAnnualEntitlement() {
		return annualEntitlement;
	}

	public void setAnnualEntitlement(int annualEntitlement) {
		this.annualEntitlement = annualEntitlement;
	}

	public int getMedicalEntitlement() {
		return medicalEntitlement;
	}

	public void setMedicalEntitlement(int medicalEntitlement) {
		this.medicalEntitlement = medicalEntitlement;
	}
	
	

}
