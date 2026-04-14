package iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.medical_leave_specification;


import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.function.BiPredicate;

import iss.nus.edu.sg.leave_application_processing_system.service.DTO.LeaveApplicationServiceDTO;

import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification.composite.CompositeSpecification;

public class MedicalLeavePeriodWithinSixtyDays extends CompositeSpecification<LeaveApplicationServiceDTO>{

    @Override
    public BiPredicate<LeaveApplicationServiceDTO, LeaveApplicationServiceDTO> toPredicate() {
        return (currentNumberOfLeavePeriodDays, maxLeavePeriodDays) -> {
            
            LocalDateTime leavePeriodEnds = currentNumberOfLeavePeriodDays.getLeavePeriodEnd();
            LocalDateTime leavePeriodStarts = currentNumberOfLeavePeriodDays.getLeavePeriodStart();

            final int MAX_NUMBER_MEDICAL_LEAVE = 60;
            

            int dayOfYearForLeavePeriodEnd = leavePeriodEnds.get(ChronoField.DAY_OF_YEAR);
            int dayOfYearForLeavePeriodStart = leavePeriodStarts.get(ChronoField.DAY_OF_YEAR);

            return (dayOfYearForLeavePeriodEnd - dayOfYearForLeavePeriodStart) <= MAX_NUMBER_MEDICAL_LEAVE;
        };   
    }
    
}
