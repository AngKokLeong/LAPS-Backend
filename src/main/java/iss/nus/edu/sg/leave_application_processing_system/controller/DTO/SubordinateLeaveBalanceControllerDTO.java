package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class SubordinateLeaveBalanceControllerDTO implements ControllerDTO {

	private String employeeName;
	private String email;
	private String department;
	private int annualBalance;
	private int medicalBalance;
	private double compensationBalance;
	private double totalBalance;
	
	@Override
	public ControllerDTO getAllAttribute() {
		// TODO Auto-generated method stub
		return this;
	}
		
	
	public SubordinateLeaveBalanceControllerDTO() {}

	public SubordinateLeaveBalanceControllerDTO(String employeeName, String email, String department, int annualBalance,
			int medicalBalance, double compensationBalance, double totalBalance) {
		this.employeeName = employeeName;
		this.email = email;
		this.department = department;
		this.annualBalance = annualBalance;
		this.medicalBalance = medicalBalance;
		this.compensationBalance = compensationBalance;
		this.totalBalance = totalBalance;
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

	public double getCompensationBalance() {
		return compensationBalance;
	}

	public void setCompensationBalance(double compensationBalance) {
		this.compensationBalance = compensationBalance;
	}

	public double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(double totalBalance) {
		this.totalBalance = totalBalance;
	}
	
	

}
