package iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.annual_leave_specification;

import java.util.function.BiPredicate;


import iss.nus.edu.sg.leave_application_processing_system.service.DTO.LeaveApplicationServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification.composite.CompositeSpecification;

public class AnnualLeavePeriodToIsWorkingDaySpecification extends CompositeSpecification<LeaveApplicationServiceDTO>{


    @Override
    public BiPredicate<LeaveApplicationServiceDTO, LeaveApplicationServiceDTO> toPredicate() {
        return (localDateTimeOne, localDateTimeTwo) ->  localDateTimeOne.getLeavePeriodEnd().getDayOfWeek().getValue() <= 5;
    }

 
    
}