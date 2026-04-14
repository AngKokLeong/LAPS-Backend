package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubordinateLeaveBalanceControllerDTO implements ControllerDTO {

	private String employeeName;
	private String department;
	private int annualBalance;
	private int medicalBalance;
	private int compensationBalance;
	private int totalBalance;
	
	@Override
	public ControllerDTO getAllAttribute() {
		// TODO Auto-generated method stub
		return this;
	}
	
	//getters & setters
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
