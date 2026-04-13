package iss.nus.edu.sg.leave_application_processing_system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "compensation_ledgers")
public class CompensationLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ledgerId;

    private int yearApplied;

    private double earnedDays;

    private double usedDays;

    /* 
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public Long getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(Long ledgerId) {
        this.ledgerId = ledgerId;
    }

    public int getYearApplied() {
        return yearApplied;
    }

    public void setYearApplied(int yearApplied) {
        this.yearApplied = yearApplied;
    }

    public double getEarnedDays() {
        return earnedDays;
    }

    public void setEarnedDays(double earnedDays) {
        this.earnedDays = earnedDays;
    }

    public double getUsedDays() {
        return usedDays;
    }

    public void setUsedDays(double usedDays) {
        this.usedDays = usedDays;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    */
}