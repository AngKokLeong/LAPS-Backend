package iss.nus.edu.sg.leave_application_processing_system.persistent.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "compensation_ledgers")
public class CompensationLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int yearApplied;

    private double earnedDays;

    private double usedDays;

    private double unconvertedHours = 0.0; // default is 0

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // Constructor
    public CompensationLedger() {}

    public CompensationLedger(Long id, int yearApplied, double earnedDays, double usedDays, double unconvertedHours, Employee employee) {
        this.id = id;
        this.yearApplied = yearApplied;
        this.earnedDays = earnedDays;
        this.usedDays = usedDays;
        this.unconvertedHours = unconvertedHours;
        this.employee = employee;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getUnconvertedHours() {
        return unconvertedHours;
    }

    public void setUnconvertedHours(double unconvertedHours) {
        this.unconvertedHours = unconvertedHours;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}