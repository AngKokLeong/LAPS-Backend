package iss.nus.edu.sg.leave_application_processing_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
@RequestMapping("/landing")
public class LandingController {
    

    @GetMapping("/")
    public String landingPage() {
        return "landing";
    }
    

}
