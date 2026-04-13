package iss.nus.edu.sg.leave_application_processing_system.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;


import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.LeaveApplicationService;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.AnnualLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;


import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.annual_leave_specification.LeavePeriodFromIsWorkingDaySpecification;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.annual_leave_specification.LeavePeriodLessThanOrEqualFourteenDaysSpecification;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.annual_leave_specification.LeavePeriodToIsWorkingDaySpecification;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.annual_leave_specification.LeavePeriodWithinTheMonthSpecification;

@Service
public class AnnualLeaveApplicationService implements LeaveApplicationService{
    
    

    @Override
    public List<ControllerDTO> viewApplicationStatus(ServiceDTO serviceDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewApplicationStatus'");
    }

    @Override
    public Boolean submitApplication(ServiceDTO serviceDTO) {
        AnnualLeaveServiceDTO dto = (AnnualLeaveServiceDTO) serviceDTO.getAllAttribute();

        boolean status = new LeavePeriodWithinTheMonthSpecification()
                    .and(new LeavePeriodLessThanOrEqualFourteenDaysSpecification())
                    .and(new LeavePeriodFromIsWorkingDaySpecification())
                    .and(new LeavePeriodToIsWorkingDaySpecification())
                    .toPredicate()
                    .test(dto, dto);

        return status;
    }

}
