package iss.nus.edu.sg.leave_application_processing_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDateTime;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveType;

import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;
@Controller
@RequestMapping("/leave")
public class ApplyLeaveController {

	@GetMapping("/apply")
	public String showApplyForm(Model model) {
	    model.addAttribute("leaveApp", new LeaveApplication());
	    // Add this line to send the Enum values to the HTML
	    model.addAttribute("leaveTypes", LeaveType.values()); 
	    
	    model.addAttribute("userRole", "MANAGER");
	    model.addAttribute("annualBalance", 15);
	    return "applyleave";
	}

    

    @PostMapping("/apply")
    public String processLeave(@ModelAttribute("leaveApp") LeaveApplication leaveApp) {
        // Business Logic
        leaveApp.setAppliedAt(LocalDateTime.now());
        leaveApp.setStatus(LeaveStatus.PENDING);
        
        System.out.println("Reason submitted: " + leaveApp.getReason());
        
        return "redirect:/dashboard";
    }
}