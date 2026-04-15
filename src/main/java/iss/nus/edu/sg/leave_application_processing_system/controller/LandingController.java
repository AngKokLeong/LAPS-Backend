package iss.nus.edu.sg.leave_application_processing_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.AuthenticationControllerDTO;




@Controller
@RequestMapping("/")
public class LandingController {



    @GetMapping("")
    public String landingPage(Model model) {

        model.addAttribute("authenticationUser", new AuthenticationControllerDTO());

        return "login";
    }
    

}
