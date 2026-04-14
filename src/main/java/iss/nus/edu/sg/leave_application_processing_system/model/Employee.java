package iss.nus.edu.sg.leave_application_processing_system.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.*;

@Entity
@Table(name="employees")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private String password;
  private String name;

  private String department;
  
  @Enumerated(EnumType.STRING)
	private Role role; // STAFF, MANAGER, ADMIN
  
  @ManyToOne
  @JoinColumn(name = "manager_id")
  private Employee manager;
  
  @OneToMany(mappedBy = "employee")
  @JsonIgnore // Prevents infinite loops in JSON serialization
  private List<LeaveApplication> leaveApplications;
  
  @OneToMany(mappedBy = "manager") // currentManager.getSubordinates() to instantly get a list of their team members for leave approval.
  @JsonIgnore
  private List<Employee> subordinates;
  
  public Employee() {}
  
  public Employee(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Employee getManager() {
	return manager;
}

  public void setManager(Employee manager) {
	this.manager = manager;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }
  public enum Role {
		
		STAFF,
		MANAGER,
		ADMIN
		
	}
  

  
}
