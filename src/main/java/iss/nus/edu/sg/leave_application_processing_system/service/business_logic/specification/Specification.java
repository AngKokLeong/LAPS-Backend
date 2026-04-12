package iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification;

import java.util.function.BiPredicate;


public interface Specification<T>{
    BiPredicate<T, T> toPredicate();
}
