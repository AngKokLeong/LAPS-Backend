package iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification.composite;

import java.util.function.BiPredicate;

import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification.Specification;

public class OrSpecification<T> extends CompositeSpecification<T> {

    private final Specification<T> firstSpecification;
    private final Specification<T> secondSpecification;

    public OrSpecification(Specification<T> firstSpecification, Specification<T> secondSpecification){
        this.firstSpecification = firstSpecification;
        this.secondSpecification = secondSpecification;
    }

    @Override
    public BiPredicate<T, T> toPredicate() {
        // TODO Auto-generated method stub
        return firstSpecification.toPredicate().or(secondSpecification.toPredicate());
    }
    
}
