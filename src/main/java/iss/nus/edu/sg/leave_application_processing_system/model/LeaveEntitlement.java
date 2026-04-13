package iss.nus.edu.sg.leave_application_processing_system.model;

import jakarta.persistence.Column;
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
public class LeaveEntitlement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long entitlementId;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employeeId;

	@NotNull
	@Enumerated(EnumType.STRING)
	private LeaveType leaveType = LeaveType.ANNUAL;
	
	private int yearApplied;
	private int totalDays;
	private int usedDays;
	
}
