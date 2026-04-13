package iss.nus.edu.sg.leave_application_processing_system.service.DTO;

import java.time.LocalDateTime;

public class MedicalLeaveServiceDTO implements ServiceDTO{
    
    private LocalDateTime leavePeriodStart;
    private LocalDateTime leavePeriodEnd;
    
    public MedicalLeaveServiceDTO(LocalDateTime leavePeriodStart, LocalDateTime leavePeriodEnd){
        this.leavePeriodStart = leavePeriodStart;
        this.leavePeriodEnd = leavePeriodEnd;
    }

    public void setLeavePeriodStart(LocalDateTime leavePeriodStart){
        this.leavePeriodStart = leavePeriodStart;
    }

    public LocalDateTime getLeavePeriodStart(){
        return this.leavePeriodStart;
    }

    public void setLeavePeriodEnd(LocalDateTime leavePeriodEnd){
        this.leavePeriodEnd = leavePeriodEnd;
    }

    public LocalDateTime getLeavePeriodEnd(){
        return this.leavePeriodEnd;
    }

    @Override
    public ServiceDTO getAllAttribute() {
        return this;
    }


}
