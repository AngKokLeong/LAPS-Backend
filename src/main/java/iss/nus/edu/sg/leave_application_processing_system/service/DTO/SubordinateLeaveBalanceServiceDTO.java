package iss.nus.edu.sg.leave_application_processing_system.service.DTO;

public class SubordinateLeaveBalanceServiceDTO implements ServiceDTO {
	
	private String employeeName;
	private String email;
	private String department;
	private int annualBalance;
	private int medicalBalance;
	private int compensationBalance;
	private int totalBalance;

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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
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
	
	public int getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(int totalBalance) {
		this.totalBalance = totalBalance;
	}

}
