package iss.nus.edu.sg.leave_application_processing_system.model;

import jakarta.persistence.*;

@Entity
@Table(name="Employee")
public class Employee {

  @Id
  private Long id;
  private String fname;
  private String lname;
  
  
  public Employee() {
	super();
  }

  public Employee(Long id, String fname, String lname) {
	super();
	this.id = id;
	this.fname = fname;
	this.lname = lname;
  }
}
