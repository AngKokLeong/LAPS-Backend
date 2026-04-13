package iss.nus.edu.sg.leave_application_processing_system.model;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name="employees")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long employeeId;
  private String email;
  private String password;
  private String name;
  private String role; // STAFF, MANAGER, ADMIN
  private String department;

  // Self-referencing manager relationship
  @ManyToOne
  @JoinColumn(name = "manager_id")
  private Employee manager;

  @OneToMany(mappedBy = "manager")
  private List<Employee> subordinates;

  @OneToMany(mappedBy = "employee")
  private List<LeaveApplication> leaveApplications;

  @OneToMany(mappedBy = "employee")
  private List<LeaveEntitlement> leaveEntitlements;

  @OneToMany(mappedBy = "employee")
  private List<OverTimeClaim> overTimeClaims;

  @OneToMany(mappedBy = "employee")
  private List<CompensationLedger> compensationLedgers;

  public Long getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Long employeeId) {
    this.employeeId = employeeId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public Employee getManager() {
    return manager;
  }

  public void setManager(Employee manager) {
    this.manager = manager;
  }

  public List<Employee> getSubordinates() {
    return subordinates;
  }

  public void setSubordinates(List<Employee> subordinates) {
    this.subordinates = subordinates;
  }

  public List<LeaveApplication> getLeaveApplications() {
    return leaveApplications;
  }

  public void setLeaveApplications(List<LeaveApplication> leaveApplications) {
    this.leaveApplications = leaveApplications;
  }

  public List<LeaveEntitlement> getLeaveEntitlements() {
    return leaveEntitlements;
  }

  public void setLeaveEntitlements(List<LeaveEntitlement> leaveEntitlements) {
    this.leaveEntitlements = leaveEntitlements;
  }

  public List<OverTimeClaim> getOverTimeClaims() {
    return overTimeClaims;
  }

  public void setOverTimeClaims(List<OverTimeClaim> overTimeClaims) {
    this.overTimeClaims = overTimeClaims;
  }

  public List<CompensationLedger> getCompensationLedgers() {
    return compensationLedgers;
  }

  public void setCompensationLedgers(List<CompensationLedger> compensationLedgers) {
    this.compensationLedgers = compensationLedgers;
  }

  
}
