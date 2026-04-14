package iss.nus.edu.sg.leave_application_processing_system.service.DTO;

import java.time.LocalDateTime;

import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;
import lombok.Data;

@Data
public class LeaveApplicationServiceDTO implements ServiceDTO { 
    
    private LocalDateTime leavePeriodStart;
    private LocalDateTime leavePeriodEnd;
    private LeaveType leaveType;
    private String leaveReason;
    private boolean IsHalfDayLeave;
    private String staffId;
    private String email;


    public LeaveApplicationServiceDTO() {

    }

    


    public LeaveApplicationServiceDTO(LocalDateTime leavePeriodStart, LocalDateTime leavePeriodEnd, LeaveType leaveType,
            String leaveReason, boolean isHalfDayLeave, String staffId, String email) {
        this.leavePeriodStart = leavePeriodStart;
        this.leavePeriodEnd = leavePeriodEnd;
        this.leaveType = leaveType;
        this.leaveReason = leaveReason;
        IsHalfDayLeave = isHalfDayLeave;
        this.staffId = staffId;
        this.email = email;
    }




    @Override
    public ServiceDTO getAllAttribute() {
        return this;
    }

    
}
