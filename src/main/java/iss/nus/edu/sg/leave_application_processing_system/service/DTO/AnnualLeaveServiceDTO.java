package iss.nus.edu.sg.leave_application_processing_system.service.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnualLeaveServiceDTO implements ServiceDTO { 
    
    private LocalDateTime leavePeriodStart;
    private LocalDateTime leavePeriodEnd;
    
    


    @Override
    public ServiceDTO getAllAttribute() {
        return this;
    }

    
}
