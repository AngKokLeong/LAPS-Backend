package iss.nus.edu.sg.leave_application_processing_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "Leave Balance")
public class LeaveBalance {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int balanceId;
	@OneToOne
	@JoinColumn (name = "employee_id", unique = true)
	private Employee employee;
	@Enumerated (EnumType.STRING)
	private LeaveType leaveType;
	@ManyToOne
	@JoinColumn (name = "entitlement_id", nullable = false)
	private LeaveEntitlement leaveEntitlement;
	private int leaveBalance;
	private int year;

}
