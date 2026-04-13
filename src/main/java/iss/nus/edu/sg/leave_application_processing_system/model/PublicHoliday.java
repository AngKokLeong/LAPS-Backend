package iss.nus.edu.sg.leave_application_processing_system.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "public_holidays")
public class PublicHoliday {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate phDate;
  private String phName;

  // Getters & Setters
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public LocalDate getPhDate() {
    return phDate;
  }
  public void setPhDate(LocalDate phDate) {
    this.phDate = phDate;
  }
  public String getPhName() {
    return phName;
  }
  public void setPhName(String phName) {
    this.phName = phName;
  }
}
