package iss.nus.edu.sg.leave_application_processing_system.model;

import java.time.LocalDate;

import iss.nus.edu.sg.leave_application_processing_system.helper.Role;
import jakarta.persistence.*;

@Entity
@Table(name="employees")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  private String name;

  @Enumerated(EnumType.STRING)
  private Role role; // STAFF, MANAGER, ADMIN (enum)
  private String department;

  @ManyToOne
  @JoinColumn(name = "manager_id")
  private Employee manager;

  private LocalDate joindate;
  
  private String status;

  // Constructors for testing
  public Employee() {}
  


  public Employee(String name, String email) {
    this.name = name;
    this.email = email;
  }

  // Getters & Setters
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public LocalDate getJoindate() {
    return joindate;
  }

  public void setJoindate(LocalDate joindate) {
    this.joindate = joindate;
  }
}
