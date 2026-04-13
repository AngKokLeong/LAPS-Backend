package iss.nus.edu.sg.leave_application_processing_system.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import iss.nus.edu.sg.leave_application_processing_system.service.implementation.AnnualLeaveApplicationService;
import iss.nus.edu.sg.leave_application_processing_system.service.implementation.MedicalLeaveApplicationService;

@Configuration
public class LeaveApplicationConfiguration {
    
    @Bean
    @Qualifier("AnnualLeaveApplicationService")
    public AnnualLeaveApplicationService annualLeaveApplicationService(){
        return new iss.nus.edu.sg.leave_application_processing_system.service.implementation.AnnualLeaveApplicationService();
    }

    @Bean
    @Qualifier("MedicalLeaveApplicationService")
    public MedicalLeaveApplicationService medicalLeaveApplicationService(){
        return new iss.nus.edu.sg.leave_application_processing_system.service.implementation.MedicalLeaveApplicationService();
    }
    

}
