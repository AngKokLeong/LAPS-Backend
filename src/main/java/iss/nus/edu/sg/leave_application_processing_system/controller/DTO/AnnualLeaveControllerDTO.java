package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveControllerDTO implements ControllerDTO{
    
    private LocalDateTime leavePeriodStart;
    private LocalDateTime leavePeriodEnd;
    
    @Override
    public ControllerDTO getAllAttribute() {
        return this;
    }
    
    
}
