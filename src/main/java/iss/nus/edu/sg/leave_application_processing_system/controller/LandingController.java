package iss.nus.edu.sg.leave_application_processing_system.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import iss.nus.edu.sg.leave_application_processing_system.service.LeaveApplicationService;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.AnnualLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.MedicalLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.implementation.MedicalLeaveApplicationService;





@Controller
@RequestMapping("/")
public class LandingController {

    private LeaveApplicationService annualLeaveApplicationService;
    private LeaveApplicationService medicalLeaveApplicationService;

    public LandingController(@Qualifier("AnnualLeaveApplicationService") LeaveApplicationService annualLeaveApplicationService, @Qualifier("MedicalLeaveApplicationService") LeaveApplicationService medicalLeaveApplicationService){
        this.annualLeaveApplicationService = annualLeaveApplicationService;
        this.medicalLeaveApplicationService = medicalLeaveApplicationService;

    }


    @GetMapping("")
    public String landingPage(Model model) {

        ServiceDTO amedicalLeaveApplicationDTO = new MedicalLeaveServiceDTO(
                                            LocalDateTime.of(2025, 03, 04, 11, 0),
                                            LocalDateTime.of(2026, 05, 03, 11, 0)
                                        );
        
        boolean status = medicalLeaveApplicationService.submitApplication(amedicalLeaveApplicationDTO);

        model.addAttribute("test", status);


        return "login";
    }
    

}
