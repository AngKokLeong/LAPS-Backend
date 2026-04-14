package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LeaveRequestControllerDTO implements ControllerDTO{

    private String type;
    private String leavePeriod;
    private String leaveDuration;
    private String leaveAppliedOn;
    private String reason;
    private String leaveRequestStatus;
    private String leaveApprover;
    private String leaveApprovalTransactionDate;
    private String leaveApprovalReason;

    /*

    public LeaveRequestControllerDTO(String type, LocalDateTime leavePeriodFrom, LocalDateTime leavePeriodTo, LocalDateTime leaveAppliedOn, String leaveApprover, String reason, String leaveRequestStatus){
        this.type = type;
        this.leavePeriodFrom = leavePeriodFrom;
        this.leavePeriodTo = leavePeriodTo;
        this.leaveAppliedOn = leaveAppliedOn;
        this.leaveApprover = leaveApprover;
        this.reason = reason;
        this.leaveRequestStatus = leaveRequestStatus;
    }
    */


    @Override
    public ControllerDTO getAllAttribute() {
        // TODO Auto-generated method stub
        return this;
    }
    
    
}
