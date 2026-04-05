package iss.nus.edu.sg.leave_application_processing_system.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "LeaveApplications")
public class LeaveApplication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Employee employee;
	private LeaveType leaveType;
	
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDateTime appliedAt;
	private LeaveStatus status;
	
	public LeaveApplication() {}

	public LeaveApplication(Employee employee, LeaveType leaveType, LocalDate startDate, LocalDate endDate) {
		this.employee = employee;
		this.leaveType = leaveType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.appliedAt = LocalDateTime.now();
		this.status = LeaveStatus.PENDING;
	}
	
	//getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Enum<LeaveType> getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(Enum<LeaveType> leaveType) {
		this.leaveType = leaveType;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalDateTime getAppliedAt() {
		return appliedAt;
	}

	public void setAppliedAt(LocalDateTime appliedAt) {
		this.appliedAt = appliedAt;
	}

	public Enum<LeaveStatus> getStatus() {
		return status;
	}

	public void setStatus(Enum<LeaveStatus> status) {
		this.status = status;
	}
		
	
}
