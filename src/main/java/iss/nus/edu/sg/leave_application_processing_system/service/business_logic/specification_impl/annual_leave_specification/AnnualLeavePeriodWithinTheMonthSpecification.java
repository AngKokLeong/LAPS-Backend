package iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification_impl.annual_leave_specification;

import java.util.function.BiPredicate;

import iss.nus.edu.sg.leave_application_processing_system.service.DTO.AnnualLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification.composite.CompositeSpecification;

public class AnnualLeavePeriodWithinTheMonthSpecification extends CompositeSpecification<AnnualLeaveServiceDTO>{
    
    
    @Override
    public BiPredicate<AnnualLeaveServiceDTO, AnnualLeaveServiceDTO> toPredicate() {
        
        
        return (currentAnnualLeaveServiceDTO, maxLeavePeriodDays) -> {
            
            int monthOfYearForLeavePeriodEnd = currentAnnualLeaveServiceDTO.getLeavePeriodEnd().getMonthValue();
            int monthOfYearForLeavePeriodStart = currentAnnualLeaveServiceDTO.getLeavePeriodStart().getMonthValue();
            

            return (monthOfYearForLeavePeriodEnd - monthOfYearForLeavePeriodStart) == 0;
        };   
    }
}


