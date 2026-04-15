package iss.nus.edu.sg.leave_application_processing_system.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;


import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.LeaveApplicationService;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.LeaveApplicationServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;


import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.annual_leave_specification.AnnualLeavePeriodFromIsWorkingDaySpecification;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.annual_leave_specification.AnnualLeavePeriodLessThanOrEqualFourteenDaysSpecification;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.annual_leave_specification.AnnualLeavePeriodToIsWorkingDaySpecification;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.annual_leave_specification.AnnualLeavePeriodWithinTheMonthSpecification;

@Service
public class AnnualLeaveApplicationService implements LeaveApplicationService{
    
    

    @Override
    public List<ControllerDTO> viewApplicationStatus(ServiceDTO serviceDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewApplicationStatus'");
    }

    @Override
    public Boolean submitApplication(ServiceDTO serviceDTO) {
        LeaveApplicationServiceDTO dto = (LeaveApplicationServiceDTO) serviceDTO.getAllAttribute();

        boolean status = new AnnualLeavePeriodWithinTheMonthSpecification()
                    .and(new AnnualLeavePeriodLessThanOrEqualFourteenDaysSpecification())
                    .and(new AnnualLeavePeriodFromIsWorkingDaySpecification())
                    .and(new AnnualLeavePeriodToIsWorkingDaySpecification())
                    .toPredicate()
                    .test(dto, dto);

        return status;
    }

}
