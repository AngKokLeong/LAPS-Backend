package iss.nus.edu.sg.leave_application_processing_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity

public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String fname;
  
  
  private String lname;

  public Employee(){}

  public Employee(String fname, String lname){
    this.fname = fname;
    this.lname = lname;
  }


  public Long getId(){
    return this.id;
  }


  public void setFName(String fname){
    this.fname = fname;
  }

  public String getFName(){
    return this.fname;
  }

  public void setLName(String lname){
    this.lname = lname;
  }

  public String getLName(){
    return this.lname;
  }


}
