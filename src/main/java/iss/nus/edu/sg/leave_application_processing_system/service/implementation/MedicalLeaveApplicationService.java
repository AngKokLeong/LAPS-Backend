package iss.nus.edu.sg.leave_application_processing_system.service.implementation;

import java.util.List;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.LeaveApplicationService;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.LeaveApplicationServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;

import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.medical_leave_specification.MedicalLeavePeriodAreInChronologicalIncreasingOrder;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.medical_leave_specification.MedicalLeavePeriodWIthinCalendarYear;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.medical_leave_specification.MedicalLeavePeriodWithinSixtyDays;

public class MedicalLeaveApplicationService implements LeaveApplicationService{

    @Override
    public List<ControllerDTO> viewApplicationStatus(ServiceDTO serviceDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewApplicationStatus'");
    }

    @Override
    public Boolean submitApplication(ServiceDTO serviceDTO) {
        
        LeaveApplicationServiceDTO dto = (LeaveApplicationServiceDTO) serviceDTO.getAllAttribute();

        boolean status = new MedicalLeavePeriodAreInChronologicalIncreasingOrder()
                    .and(new MedicalLeavePeriodWIthinCalendarYear())
                    .and(new MedicalLeavePeriodWithinSixtyDays())
                    .toPredicate()
                    .test(dto, dto);
        return status;
    }
    
}
