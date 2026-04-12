package iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification.composite;

import java.util.function.BiPredicate;

import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification.Specification;

public class AndSpecification<T> extends CompositeSpecification<T>{

    private final Specification<T> firstSpecification;
    private final Specification<T> secondSpecification;

    public AndSpecification(Specification<T> firstSpecification, Specification<T> secondSpecifiction){
        this.firstSpecification = firstSpecification;
        this.secondSpecification = secondSpecifiction;
    }


    @Override
    public BiPredicate<T, T> toPredicate() {
        return firstSpecification.toPredicate().and(secondSpecification.toPredicate());
    }
    
}