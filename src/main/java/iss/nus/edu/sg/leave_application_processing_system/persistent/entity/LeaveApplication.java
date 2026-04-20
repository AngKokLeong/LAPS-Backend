package iss.nus.edu.sg.leave_application_processing_system.persistent.entity;

import java.time.LocalDate;

import iss.nus.edu.sg.leave_application_processing_system.persistent.enumeration.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.persistent.enumeration.LeaveType;
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
@Table(name = "leave_applications")
public class LeaveApplication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private LeaveType leaveType;

	@ManyToOne(optional = false)
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "ledger_id")
	private CompensationLedger ledger; // nullable

	@ManyToOne
	@JoinColumn(name = "entitlement_id")
	private LeaveEntitlement entitlement; // nullable
	
	
	private LocalDate startDate;
	private LocalDate endDate;
	private String reason;

	@Enumerated(EnumType.STRING)
	private LeaveStatus leaveStatus;

	private LocalDate appliedDate;
	private LocalDate updatedDate;
	private String mgrRemarks;
	private boolean halfDay;
	
	// Constructors for testing
	public LeaveApplication() {}

	public LeaveApplication(Long id, LeaveType leaveType, Employee employee, CompensationLedger ledger,
			LeaveEntitlement entitlement, LocalDate startDate, LocalDate endDate, String reason, LeaveStatus leaveStatus,
			LocalDate appliedDate, LocalDate updatedDate, String mgrRemarks, boolean halfDay) {
		this.id = id;
		this.leaveType = leaveType;
		this.employee = employee;
		this.ledger = ledger;
		this.entitlement = entitlement;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reason = reason;
		this.leaveStatus = leaveStatus;
		this.appliedDate = LocalDate.now();
		this.updatedDate = updatedDate;
		this.mgrRemarks = mgrRemarks;
		this.halfDay = halfDay;
	}

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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public LeaveStatus getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(LeaveStatus leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public LocalDate getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(LocalDate appliedDate) {
		this.appliedDate = appliedDate;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getMgrRemarks() {
		return mgrRemarks;
	}

	public void setMgrRemarks(String mgrRemarks) {
		this.mgrRemarks = mgrRemarks;
	}

	public boolean isHalfDay() {
		return halfDay;
	}

	public void setHalfDay(boolean halfDay) {
		this.halfDay = halfDay;
	}
	
	

		
}
