package iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification.composite;

import java.util.function.BiPredicate;


public class NotSpecification<T> extends CompositeSpecification<T>{
    
    private final CompositeSpecification<T> firstSpecification;


    public NotSpecification(CompositeSpecification<T> firstSpecification){
        this.firstSpecification = firstSpecification;
    }



    @Override
    public BiPredicate<T, T> toPredicate() {
        return this.firstSpecification.toPredicate().negate();
    }
}


