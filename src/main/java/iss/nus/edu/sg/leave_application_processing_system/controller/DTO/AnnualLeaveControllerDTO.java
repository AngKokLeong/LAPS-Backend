package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import java.time.LocalDateTime;


public class AnnualLeaveControllerDTO implements ControllerDTO{
    
    private LocalDateTime leavePeriodStart;
    private LocalDateTime leavePeriodEnd;
    
    @Override
    public ControllerDTO getAllAttribute() {
        return this;
    }

    public AnnualLeaveControllerDTO(LocalDateTime leavePeriodStart, LocalDateTime leavePeriodEnd) {
        this.leavePeriodStart = leavePeriodStart;
        this.leavePeriodEnd = leavePeriodEnd;
    }

    public AnnualLeaveControllerDTO() {
    }

    public LocalDateTime getLeavePeriodStart() {
        return leavePeriodStart;
    }

    public void setLeavePeriodStart(LocalDateTime leavePeriodStart) {
        this.leavePeriodStart = leavePeriodStart;
    }

    public LocalDateTime getLeavePeriodEnd() {
        return leavePeriodEnd;
    }

    public void setLeavePeriodEnd(LocalDateTime leavePeriodEnd) {
        this.leavePeriodEnd = leavePeriodEnd;
    }
    
    
}
