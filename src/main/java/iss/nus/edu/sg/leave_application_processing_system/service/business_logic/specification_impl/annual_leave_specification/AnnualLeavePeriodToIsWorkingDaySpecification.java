package iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.annual_leave_specification;

import java.util.function.BiPredicate;

import iss.nus.edu.sg.leave_application_processing_system.service.DTO.AnnualLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification.composite.CompositeSpecification;

public class AnnualLeavePeriodToIsWorkingDaySpecification extends CompositeSpecification<AnnualLeaveServiceDTO>{


    @Override
    public BiPredicate<AnnualLeaveServiceDTO, AnnualLeaveServiceDTO> toPredicate() {
        return (localDateTimeOne, localDateTimeTwo) ->  localDateTimeOne.getLeavePeriodEnd().getDayOfWeek().getValue() <= 5;
    }

 
    
}