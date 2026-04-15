package iss.nus.edu.sg.leave_application_processing_system.controller;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveApplicationControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.constant.LeaveRequestStatusConstant;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.constant.LeaveRequestTypeConstant;
import iss.nus.edu.sg.leave_application_processing_system.controller.helper.LeaveRequestUtilities;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.AnnualLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.implementation.AnnualLeaveApplicationService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping ("/staff")
public class StaffController {
	
	private final AnnualLeaveApplicationService annualLeaveApplicationService;

	public StaffController(AnnualLeaveApplicationService annualLeaveApplicationService){
		this.annualLeaveApplicationService = annualLeaveApplicationService;
	}



	@GetMapping ({"", "/"})
	public String staffDashboard(HttpSession session) {
		String role = (String) session.getAttribute("userRole");
		
		if (role == null || role.toString().isEmpty()) return "redirect:/";
		
		return "dashboard";
	}
	
	@GetMapping ("/apply-leave") 
	public String applyLeave(Model model, HttpSession session) {
		String role = (String) session.getAttribute("userRole");
		
		if (role == null || role.toString().isEmpty()) return "redirect:/";
		
		model.addAttribute("leaveApplication", new LeaveApplicationControllerDTO());

		return "apply-leave";
	}

	@PostMapping("/apply-leave")
	public String applyLeave(@ModelAttribute LeaveApplicationControllerDTO leaveApplication,Model model, HttpSession session){

		//call the service class
		AnnualLeaveServiceDTO annualLeaveServiceDTO = new AnnualLeaveServiceDTO();
		annualLeaveServiceDTO.setStaffId(leaveApplication.getStaffId());
		annualLeaveServiceDTO.setHalfDay(leaveApplication.isHalfDay());
		annualLeaveServiceDTO.setLeavePeriodEnd(leaveApplication.getEndDate());
		annualLeaveServiceDTO.setLeavePeriodStart(leaveApplication.getStartDate());
		
		annualLeaveServiceDTO.setReason(leaveApplication.getReason());
		annualLeaveServiceDTO.setType(leaveApplication.getType());


		ControllerDTO controllerDTO = annualLeaveApplicationService.submitApplication(annualLeaveServiceDTO);
		LeaveApplicationControllerDTO leaveApplicationControllerDTO = (LeaveApplicationControllerDTO) controllerDTO.getAllAttribute();
		//retrieve the result from the service class
		if (leaveApplicationControllerDTO.getApplicationResult()){
			//show success message
			model.addAttribute("leaveApplicationInformation", leaveApplicationControllerDTO);
		}else{
			model.addAttribute("leaveApplicationInformation", leaveApplicationControllerDTO);
		}



		return "apply-leave";


	}


	
	@GetMapping ("/my-leave-requests")
	public String myLeaveRequests(Model model, HttpSession session) {
		String role = (String) session.getAttribute("userRole");
		
		if (role == null || role.toString().isEmpty()) return "redirect:/";
		
		List<LeaveRequestControllerDTO> leaveRequestControllerDTO = new ArrayList<LeaveRequestControllerDTO>();


		/*
		LeaveRequestTypeConstant.ANNUAL_LEAVE,
					DateUtilities.GenerateLeavePeriod(LocalDateTime.of(2026, 4, 15, 11, 30), LocalDateTime.of(2026, 4, 17, 11,30)),
					DateUtilities.RetrieveDateDifferenceText(LocalDateTime.of(2026, 4, 15, 11, 30), LocalDateTime.of(2026, 4, 17, 11,30)),
					DateUtilities.GenerateStandardDateFormat(LocalDateTime.of(2026, 4, 01, 11, 30)),
					"Sarah",
					"Family Vacation Trip",
					LeaveRequestStatusConstant.PENDING
		*/


		leaveRequestControllerDTO.add(
			new LeaveRequestControllerDTO(
				LeaveRequestTypeConstant.ANNUAL_LEAVE,
				LeaveRequestUtilities.GenerateLeavePeriod(LocalDateTime.of(2026, 4, 15, 11, 30), LocalDateTime.of(2026, 4, 17, 11,30)),
				LeaveRequestUtilities.RetrieveDateDifferenceText(LocalDateTime.of(2026, 4, 15, 11, 30), LocalDateTime.of(2026, 4, 17, 11,30)),
				LeaveRequestUtilities.GenerateStandardDateFormat(LocalDateTime.of(2026, 4, 01, 11, 30)),
				"Family Vacation Trip",
				LeaveRequestStatusConstant.PENDING,
				"",
				null,
				""
			)
			
		);

		leaveRequestControllerDTO.add(
			new LeaveRequestControllerDTO(
				LeaveRequestTypeConstant.MEDICAL_LEAVE, 
				LeaveRequestUtilities.GenerateLeavePeriod(LocalDateTime.of(2026, 3, 20, 11, 30), LocalDateTime.of(2026, 3, 22, 11,30)),
				LeaveRequestUtilities.RetrieveDateDifferenceText(LocalDateTime.of(2026, 3, 20, 11, 30), LocalDateTime.of(2026, 3, 22, 11,30)),
				LeaveRequestUtilities.GenerateStandardDateFormat(LocalDateTime.of(2026, 3, 22, 11, 30)),
				"Flu and Fever, doctor advised rest",
				LeaveRequestStatusConstant.APPROVED,
				"Sarah",
				LeaveRequestUtilities.GenerateLeaveApprovalDate(LocalDateTime.of(2026, 3, 19, 11, 30)),
				LeaveRequestUtilities.GenerateLeaveApprovalStatement("Sarah")
			)
		);

		leaveRequestControllerDTO.add(new LeaveRequestControllerDTO(
				LeaveRequestTypeConstant.COMPENSATION_LEAVE, 
				LeaveRequestUtilities.GenerateLeavePeriod(LocalDateTime.of(2026, 2, 10, 11, 30), LocalDateTime.of(2026, 2, 11, 11,30)),
				LeaveRequestUtilities.RetrieveDateDifferenceText(LocalDateTime.of(2026, 2, 10, 11, 30), LocalDateTime.of(2026, 2, 11, 11,30)),
				LeaveRequestUtilities.GenerateStandardDateFormat(LocalDateTime.of(2026, 2, 5, 11, 30)),
				"Personal Matters",
				LeaveRequestStatusConstant.REJECTED,
				"Sarah",
				LeaveRequestUtilities.GenerateLeaveRejectionDate(LocalDateTime.of(2026, 2, 6, 11, 30)),
				LeaveRequestUtilities.GenerateLeaveRejectionReason("Critical project deadline during requested period")
			)
		);

			leaveRequestControllerDTO.add(new LeaveRequestControllerDTO(
				LeaveRequestTypeConstant.COMPENSATION_LEAVE, 
				LeaveRequestUtilities.GenerateLeavePeriod(LocalDateTime.of(2026, 5, 10, 11, 30), LocalDateTime.of(2026, 2, 11, 11,30)),
				LeaveRequestUtilities.RetrieveDateDifferenceText(LocalDateTime.of(2026, 5, 10, 11, 30), LocalDateTime.of(2026, 2, 11, 11,30)),
				LeaveRequestUtilities.GenerateStandardDateFormat(LocalDateTime.of(2026, 5, 5, 11, 30)),
				"Personal Matters",
				LeaveRequestStatusConstant.CANCELLED,
				"Sarah",
				LeaveRequestUtilities.GenerateLeaveCancellationDate(LocalDateTime.of(2026, 5, 6, 11, 30)),
				LeaveRequestUtilities.GenerateLeaveCancellationStatement()
			)
		);


		model.addAttribute("leave_request_list", leaveRequestControllerDTO);

		return "my-leave-requests";
	}
	
	@GetMapping ("/movement-register") 
	public String movementRegister(HttpSession session) {
		String role = (String) session.getAttribute("userRole");
		
		if (role == null || role.toString().isEmpty()) return "redirect:/";
		
		return "movement-register";
	}
	
	@GetMapping ("/submit-ot-claim") 
	public String submitOvertimeClaim(HttpSession session) {
		String role = (String) session.getAttribute("userRole");
		
		if (role == null || role.toString().isEmpty()) return "redirect:/";
		
		return "submit-ot-claim";
	}

}
