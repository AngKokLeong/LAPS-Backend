package iss.nus.edu.sg.leave_application_processing_system.model;

import jakarta.persistence.*;

@Entity
@Table(name="Employee")
public class Employee {

  @Id
  private Long id;
  private String fname;
  private String lname;
}
