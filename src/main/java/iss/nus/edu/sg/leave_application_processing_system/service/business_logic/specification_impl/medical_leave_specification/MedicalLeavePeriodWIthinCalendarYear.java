package iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.medical_leave_specification;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.function.BiPredicate;


import iss.nus.edu.sg.leave_application_processing_system.service.DTO.MedicalLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification.composite.CompositeSpecification;

public class MedicalLeavePeriodWIthinCalendarYear extends CompositeSpecification<MedicalLeaveServiceDTO>{

    @Override
    public BiPredicate<MedicalLeaveServiceDTO, MedicalLeaveServiceDTO> toPredicate() {
        return (currentNumberOfLeavePeriodDays, maxLeavePeriodDays) -> {
            
            LocalDate leavePeriodEnds = currentNumberOfLeavePeriodDays.getLeavePeriodEnd();
            LocalDate leavePeriodStarts = currentNumberOfLeavePeriodDays.getLeavePeriodStart();

            int yearForLeavePeriodEnd = leavePeriodEnds.get(ChronoField.YEAR);
            int yearForLeavePeriodStart = leavePeriodStarts.get(ChronoField.YEAR);

            return yearForLeavePeriodStart == yearForLeavePeriodEnd;
        };
    }
    
}
