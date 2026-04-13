package iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification.composite;

import iss.nus.edu.sg.leave_application_processing_system.service.business_logic.specification.Specification;

public abstract class CompositeSpecification<T> implements Specification<T> {



    public CompositeSpecification<T> and(Specification<T> compositeSpecification){
        return new AndSpecification<T>(this, compositeSpecification);
    }

    public CompositeSpecification<T> or(Specification<T> compositeSpecification){
        return new OrSpecification<T>(this, compositeSpecification);
    }

    public CompositeSpecification<T> not(){
        return new NotSpecification<>(this);
       
    }

    
}
