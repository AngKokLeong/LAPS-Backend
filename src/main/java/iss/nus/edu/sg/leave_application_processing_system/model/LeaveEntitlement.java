package iss.nus.edu.sg.leave_application_processing_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "Leave Entitlement")
public class LeaveEntitlement {

	@Id
	private Long entitlementId;
	private Employee employeeId;
	@Enumerated (EnumType.STRING)
	private LeaveType leavetype;
	private int year;
	private int totalDays;
	private int usedDays;
	
}
