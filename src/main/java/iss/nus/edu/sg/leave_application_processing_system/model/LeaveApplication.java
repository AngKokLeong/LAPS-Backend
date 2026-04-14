package iss.nus.edu.sg.leave_application_processing_system.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "LeaveApplications")
public class LeaveApplication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name = "ledger_id")
	private CompensationLedger ledger;
	
	@ManyToOne
	@JoinColumn(name = "leaveEntitlement_id")
	private LeaveEntitlement entitlement;
	
	@Enumerated(EnumType.STRING)
	private LeaveType leaveType;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDateTime appliedAt;
	
	@Enumerated(EnumType.STRING)
	private LeaveStatus status;
	private String reason;
	private String mgrRemarks;
	private LocalDate updatedDate;
	private boolean halfDay;
	
	public LeaveApplication() {}
	
	
	public LeaveApplication(Employee employee, CompensationLedger ledger, LeaveEntitlement entitlement,
			LeaveType leaveType, LocalDate startDate, LocalDate endDate, LocalDateTime appliedAt, LeaveStatus status,
			String reason, String mgrRemarks, LocalDate updatedDate, boolean halfDay) {
	
		this.employee = employee;
		this.ledger = ledger;
		this.entitlement = entitlement;
		this.leaveType = leaveType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.appliedAt = appliedAt;
		this.status = status;
		this.reason = reason;
		this.mgrRemarks = mgrRemarks;
		this.updatedDate = updatedDate;
		this.halfDay = halfDay;
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

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	
	public CompensationLedger getLedger() {
		return ledger;
	}

	public void setLedger(CompensationLedger ledger) {
		this.ledger = ledger;
	}

	public LeaveEntitlement getEntitlement() {
		return entitlement;
	}

	public void setEntitlement(LeaveEntitlement entitlement) {
		this.entitlement = entitlement;
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

	public LeaveStatus getStatus() {
		return status;
	}

	public void setStatus(LeaveStatus status) {
		this.status = status;
	}
		
	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getMgrRemarks() {
		return mgrRemarks;
	}


	public void setMgrRemarks(String mgrRemarks) {
		this.mgrRemarks = mgrRemarks;
	}


	public LocalDate getUpdatedDate() {
		return updatedDate;
	}


	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}


	public boolean isHalfDay() {
		return halfDay;
	}


	public void setHalfday(boolean halfDay) {
		this.halfDay = halfDay;
	}

	public enum LeaveType {
		
		ANNUAL,
		MEDICAL,
		COMPENSATION
		
	}
	
	public enum LeaveStatus {
		
		APPLIED,
		UPDATED,
		APPROVED,
		REJECTED,
		DELETED,
		CANCELLED,
		ARCHIVED,
		
	}
	
	// Good to have for your business logic later
	public long getDurationInDays() {
	    if (startDate == null || endDate == null) return 0;
	    return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
	}
}
