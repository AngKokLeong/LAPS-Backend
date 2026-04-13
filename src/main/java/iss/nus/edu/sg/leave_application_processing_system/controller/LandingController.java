package iss.nus.edu.sg.leave_application_processing_system.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import iss.nus.edu.sg.leave_application_processing_system.service.LeaveApplicationService;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.AnnualLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;





@Controller
@RequestMapping("/")
public class LandingController {

    //private LeaveApplicationService leaveApplicationService;

    //public LandingController(@Qualifier("AnnualLeaveApplicationService") LeaveApplicationService leaveApplicationService){
    //    this.leaveApplicationService = leaveApplicationService;
    //}


    @GetMapping("")
    public String landingPage() {

        //ServiceDTO annualLeaveApplicationDTO = new AnnualLeaveServiceDTO(
        //                                    LocalDateTime.of(2026, 05, 04, 11, 0),
        //                                    LocalDateTime.of(2026, 05, 11, 11, 0)
        //                                );
        
        //boolean status = leaveApplicationService.submitApplication(annualLeaveApplicationDTO);

        //model.addAttribute("test", status);


        return "login";
    }
    

}
