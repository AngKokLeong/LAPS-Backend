package iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.medical_leave_specification;


import java.time.LocalDate;

import java.time.temporal.ChronoField;
import java.util.function.BiPredicate;

import iss.nus.edu.sg.leave_application_processing_system.service.DTO.MedicalLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification.composite.CompositeSpecification;

public class MedicalLeavePeriodWithinSixtyDays extends CompositeSpecification<MedicalLeaveServiceDTO>{

    @Override
    public BiPredicate<MedicalLeaveServiceDTO, MedicalLeaveServiceDTO> toPredicate() {
        return (currentNumberOfLeavePeriodDays, maxLeavePeriodDays) -> {
            
            LocalDate leavePeriodEnds = currentNumberOfLeavePeriodDays.getLeavePeriodEnd();
            LocalDate leavePeriodStarts = currentNumberOfLeavePeriodDays.getLeavePeriodStart();

            final int MAX_NUMBER_MEDICAL_LEAVE = 60;


            int dayOfYearForLeavePeriodEnd = leavePeriodEnds.get(ChronoField.DAY_OF_YEAR);
            int dayOfYearForLeavePeriodStart = leavePeriodStarts.get(ChronoField.DAY_OF_YEAR);

            return (dayOfYearForLeavePeriodEnd - dayOfYearForLeavePeriodStart) <= MAX_NUMBER_MEDICAL_LEAVE;
        };   
    }
    
}
