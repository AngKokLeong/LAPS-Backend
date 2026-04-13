package iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.medical_leave_specification;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.function.BiPredicate;

import iss.nus.edu.sg.leave_application_processing_system.service.DTO.MedicalLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification.composite.CompositeSpecification;

public class MedicalLeavePeriodAreInChronologicalIncreasingOrder extends CompositeSpecification<MedicalLeaveServiceDTO>{

    @Override
    public BiPredicate<MedicalLeaveServiceDTO, MedicalLeaveServiceDTO> toPredicate() {
         return (currentNumberOfLeavePeriodDays, maxLeavePeriodDays) -> {
            
            LocalDateTime leavePeriodEnds = currentNumberOfLeavePeriodDays.getLeavePeriodEnd();
            LocalDateTime leavePeriodStarts = currentNumberOfLeavePeriodDays.getLeavePeriodStart();

    

            int dayOfYearForLeavePeriodEnd = leavePeriodEnds.get(ChronoField.DAY_OF_YEAR);
            int dayOfYearForLeavePeriodStart = leavePeriodStarts.get(ChronoField.DAY_OF_YEAR);

            return (dayOfYearForLeavePeriodEnd - dayOfYearForLeavePeriodStart) >= 0;
        };   
    }
    
}
