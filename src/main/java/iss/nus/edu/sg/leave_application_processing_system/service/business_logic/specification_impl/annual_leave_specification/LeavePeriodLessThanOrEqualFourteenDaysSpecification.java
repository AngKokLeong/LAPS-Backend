package iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.annual_leave_specification;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

import java.util.function.BiPredicate;

import iss.nus.edu.sg.leave_application_processing_system.service.DTO.AnnualLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification.composite.CompositeSpecification;


public class LeavePeriodLessThanOrEqualFourteenDaysSpecification extends CompositeSpecification<AnnualLeaveServiceDTO>{

    //private static final int NUMBER_OF_LEAVE_PERIOD_DAYS = 14;

    @Override
    public BiPredicate<AnnualLeaveServiceDTO, AnnualLeaveServiceDTO> toPredicate() {
        return (currentNumberOfLeavePeriodDays, maxLeavePeriodDays) -> {
            
            LocalDateTime leavePeriodEnds = currentNumberOfLeavePeriodDays.getLeavePeriodEnd();
            LocalDateTime leavePeriodStarts = currentNumberOfLeavePeriodDays.getLeavePeriodStart();

            

            int numberOfWeekEnds = 0;
            
            for (int dayOfMonth = leavePeriodStarts.getDayOfMonth(); dayOfMonth <= leavePeriodEnds.getDayOfMonth(); dayOfMonth++){
                LocalDate leavePeriodDay = LocalDate.of(leavePeriodStarts.getYear(), leavePeriodStarts.getMonth(), dayOfMonth);
                if (leavePeriodDay.getDayOfWeek().getValue() > 5){
                    ++numberOfWeekEnds;
                }
            }

            int dayOfYearForLeavePeriodEnd = leavePeriodEnds.get(ChronoField.DAY_OF_YEAR);
            int dayOfYearForLeavePeriodStart = leavePeriodStarts.get(ChronoField.DAY_OF_YEAR);
            

            if ((dayOfYearForLeavePeriodEnd - dayOfYearForLeavePeriodStart) <= 14){
                return (dayOfYearForLeavePeriodEnd - dayOfYearForLeavePeriodStart - numberOfWeekEnds) <= 14;
            }

            return (dayOfYearForLeavePeriodEnd - dayOfYearForLeavePeriodStart) <= 14;
        };   
    }
    
}
