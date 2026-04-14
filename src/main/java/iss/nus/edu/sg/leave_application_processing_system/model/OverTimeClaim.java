package iss.nus.edu.sg.leave_application_processing_system.model;

import java.time.LocalDateTime;

import iss.nus.edu.sg.leave_application_processing_system.helper.OTClaimStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "overtime_claims")
public class OverTimeClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private String otDescription;

    @Enumerated(EnumType.STRING)
    private OTClaimStatus status;  // PENDING, APPROVED, REJECTED

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // Getters & Setters
    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public LocalDateTime getStartDateTime() {
      return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
      this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
      return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
      this.endDateTime = endDateTime;
    }

    public String getOtDescription() {
      return otDescription;
    }

    public void setOtDescription(String otDescription) {
      this.otDescription = otDescription;
    }

    public OTClaimStatus getStatus() {
      return status;
    }

    public void setStatus(OTClaimStatus status) {
      this.status = status;
    }

    public Employee getEmployee() {
      return employee;
    }

    public void setEmployee(Employee employee) {
      this.employee = employee;
    }
}
