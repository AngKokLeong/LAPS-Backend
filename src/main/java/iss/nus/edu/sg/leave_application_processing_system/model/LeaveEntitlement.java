package iss.nus.edu.sg.leave_application_processing_system.model;

import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "leave_entitlements")
public class LeaveEntitlement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	private LeaveType leaveType;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employeeId;

	private int yearApplied;
	private int totalDays;
	private int usedDays;

	// Getters & Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LeaveType getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}
	public Employee getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Employee employeeId) {
		this.employeeId = employeeId;
	}
	public int getYearApplied() {
		return yearApplied;
	}
	public void setYearApplied(int yearApplied) {
		this.yearApplied = yearApplied;
	}
	public int getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}
	public int getUsedDays() {
		return usedDays;
	}
	public void setUsedDays(int usedDays) {
		this.usedDays = usedDays;
	}	
}
