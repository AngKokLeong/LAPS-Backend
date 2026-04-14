package iss.nus.edu.sg.leave_application_processing_system.service.DTO;

public class SubordinateLeaveBalanceServiceDTO implements ServiceDTO {
	
	private String employeeName;
	private int annualBalance;
	private int medicalBalance;
	private int compensationBalance;

	@Override
	public ServiceDTO getAllAttribute() {
		return this;
	}

	//getters & setters
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getAnnualBalance() {
		return annualBalance;
	}

	public void setAnnualBalance(int annualBalance) {
		this.annualBalance = annualBalance;
	}

	public int getMedicalBalance() {
		return medicalBalance;
	}

	public void setMedicalBalance(int medicalBalance) {
		this.medicalBalance = medicalBalance;
	}

	public int getCompensationBalance() {
		return compensationBalance;
	}

	public void setCompensationBalance(int compensationBalance) {
		this.compensationBalance = compensationBalance;
	}
	
	

}
