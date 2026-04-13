package iss.nus.edu.sg.leave_application_processing_system.model;

import java.time.LocalDate;

import iss.nus.edu.sg.leave_application_processing_system.helper.OTClaimStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "overtime_claims")
public class OverTimeClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate otDate;

    private int otHoursWorked;

    private String otDescription;

    private OTClaimStatus status;  // SUBMITTED, APPROVED, REJECTED

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public LocalDate getOtDate() {
      return otDate;
    }

    public void setOtDate(LocalDate otDate) {
      this.otDate = otDate;
    }

    public int getOtHoursWorked() {
      return otHoursWorked;
    }

    public void setOtHoursWorked(int otHoursWorked) {
      this.otHoursWorked = otHoursWorked;
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
