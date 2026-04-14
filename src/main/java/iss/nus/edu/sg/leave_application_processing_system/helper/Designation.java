package iss.nus.edu.sg.leave_application_processing_system.helper;

public enum Designation {
    ADMINISTRATIVE(14),
    PROFESSIONAL(18);

    private final int annualLeaveDays;

    Designation(int annualLeaveDays) {
        this.annualLeaveDays = annualLeaveDays;
    }

    public int getAnnualLeaveDays() {
        return annualLeaveDays;
    }
}
