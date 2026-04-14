package iss.nus.edu.sg.leave_application_processing_system.model;

import java.time.LocalDate;

import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
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
	
	

		
}
