package iss.nus.edu.sg.leave_application_processing_system.service.data_transformation;

import java.time.LocalDate;

public class LeavePeriodDataTransformation {
    
    //both can generate leavePeriod
	private LocalDate startDate;
	private LocalDate endDate;

    public LeavePeriodDataTransformation(LocalDate startDate,LocalDate endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }


    @Override
    public String toString(){
        return startDate.getMonth().name() + " " + startDate.getDayOfMonth() + " - " + endDate.getMonth().name() + " " + endDate.getDayOfMonth() + " " + endDate.getYear();
    }

}
