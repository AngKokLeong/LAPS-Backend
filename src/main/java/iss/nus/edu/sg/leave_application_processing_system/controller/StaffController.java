package iss.nus.edu.sg.leave_application_processing_system.controller;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.CancelLeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveApplicationControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveMovementDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.UpdateLeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.helper.LeaveRequestUtilities;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;
import iss.nus.edu.sg.leave_application_processing_system.model.Employee;
import iss.nus.edu.sg.leave_application_processing_system.model.OverTimeClaim;
import iss.nus.edu.sg.leave_application_processing_system.service.LeaveBalanceService;
import iss.nus.edu.sg.leave_application_processing_system.service.LeaveMovementService;
import iss.nus.edu.sg.leave_application_processing_system.service.OverTimeClaimService;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.AnnualLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.CompensationLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.MedicalLeaveServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ViewLeaveRequestsServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.implementation.AnnualLeaveApplicationService;
import iss.nus.edu.sg.leave_application_processing_system.service.implementation.CompensationLeaveApplicationService;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.CancelLeaveRequestServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.UpdateLeaveRequestServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.implementation.CancelLeaveRequestService;
import iss.nus.edu.sg.leave_application_processing_system.service.implementation.MedicalLeaveApplicationService;
import iss.nus.edu.sg.leave_application_processing_system.service.implementation.UpdateLeaveRequestService;
import iss.nus.edu.sg.leave_application_processing_system.service.implementation.ViewLeaveRequestsService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/staff")
public class StaffController {

	private final AnnualLeaveApplicationService annualLeaveApplicationService;
	private final MedicalLeaveApplicationService medicalLeaveApplicationService;
	private final ViewLeaveRequestsService viewLeaveRequestsService;
	private final OverTimeClaimService otClaimService;
	private final CancelLeaveRequestService cancelLeaveRequestService;
	private final UpdateLeaveRequestService updateLeaveRequestService;
	private final LeaveMovementService leaveMovementService;
	private final LeaveBalanceService balanceService;
	private final CompensationLeaveApplicationService compService;

	// Constructor Injections
	public StaffController(AnnualLeaveApplicationService annualLeaveApplicationService,
			MedicalLeaveApplicationService medicalLeaveApplicationService,
			ViewLeaveRequestsService viewLeaveRequestsService,
			OverTimeClaimService otClaimService,
			LeaveMovementService leaveMovementService,
			LeaveBalanceService balanceService,
			CompensationLeaveApplicationService compService,
			CancelLeaveRequestService cancelLeaveRequestService,
			UpdateLeaveRequestService updateLeaveRequestService) {
		this.annualLeaveApplicationService = annualLeaveApplicationService;
		this.medicalLeaveApplicationService = medicalLeaveApplicationService;
		this.viewLeaveRequestsService = viewLeaveRequestsService;
		this.otClaimService = otClaimService;
		this.leaveMovementService = leaveMovementService;
		this.balanceService = balanceService;
		this.compService = compService;
		this.cancelLeaveRequestService = cancelLeaveRequestService;
		this.updateLeaveRequestService = updateLeaveRequestService;
	}

	@GetMapping({ "", "/" })
	public String staffDashboard(HttpSession session) {
		String role = (String) session.getAttribute("userRole");

		if (role == null || role.toString().isEmpty())
			return "redirect:/";

		return "dashboard";
	}

	@GetMapping("/apply-leave")
	public String applyLeave(Model model, HttpSession session) {
		String role = (String) session.getAttribute("userRole");
		Long userId = (Long) session.getAttribute("id");

		if (role == null || role.toString().isEmpty())
			return "redirect:/";

		model.addAttribute("annualBalance", balanceService.getAnnualBalance(userId));
		model.addAttribute("medicalBalance", balanceService.getMedicalBalance(userId));
		model.addAttribute("compBalance", balanceService.getCompBalance(userId));

		model.addAttribute("leaveApplication", new LeaveApplicationControllerDTO());

		return "apply-leave";
	}

	@PostMapping("/apply-leave")
	public String applyLeave(@ModelAttribute LeaveApplicationControllerDTO leaveApplication, Model model,
			HttpSession session, RedirectAttributes ra) {

		try {

			if (leaveApplication.getType() == null ||
					leaveApplication.getStartDate() == null ||
					leaveApplication.getEndDate() == null ||
					leaveApplication.getReason().isBlank()) {

				ra.addFlashAttribute("errorMessage", "Please fill in all fields.");
				return "redirect:/staff/apply-leave";
			}

			if (leaveApplication.getType().equals(LeaveType.ANNUAL)) {
				// call the service class
				AnnualLeaveServiceDTO annualLeaveServiceDTO = new AnnualLeaveServiceDTO();
				annualLeaveServiceDTO.setStaffId(leaveApplication.getStaffId());
				annualLeaveServiceDTO.setHalfDay(leaveApplication.isHalfDay());
				annualLeaveServiceDTO.setLeavePeriodEnd(leaveApplication.getEndDate());
				annualLeaveServiceDTO.setLeavePeriodStart(leaveApplication.getStartDate());

				annualLeaveServiceDTO.setReason(leaveApplication.getReason());
				annualLeaveServiceDTO.setType(leaveApplication.getType());

				ControllerDTO controllerDTO = annualLeaveApplicationService.submitApplication(annualLeaveServiceDTO);
				LeaveApplicationControllerDTO leaveApplicationControllerDTO = (LeaveApplicationControllerDTO) controllerDTO
						.getAllAttribute();
				// retrieve the result from the service class
				if (leaveApplicationControllerDTO.getApplicationResult()) {
					// show success message
					model.addAttribute("leaveApplicationInformation", leaveApplicationControllerDTO);
				} else {
					model.addAttribute("leaveApplicationInformation", leaveApplicationControllerDTO);
				}

				ra.addFlashAttribute("successMessage", "Leave application submitted successfully!");

			} else if (leaveApplication.getType().equals(LeaveType.MEDICAL)) {
				MedicalLeaveServiceDTO medicalLeaveServiceDTO = new MedicalLeaveServiceDTO();

				medicalLeaveServiceDTO.setStaffId(leaveApplication.getStaffId());
				medicalLeaveServiceDTO.setHalfDay(leaveApplication.isHalfDay());
				medicalLeaveServiceDTO.setLeavePeriodEnd(leaveApplication.getEndDate());
				medicalLeaveServiceDTO.setLeavePeriodStart(leaveApplication.getStartDate());

				medicalLeaveServiceDTO.setReason(leaveApplication.getReason());
				medicalLeaveServiceDTO.setType(leaveApplication.getType());

				ControllerDTO controllerDTO = medicalLeaveApplicationService.submitApplication(medicalLeaveServiceDTO);
				LeaveApplicationControllerDTO leaveApplicationControllerDTO = (LeaveApplicationControllerDTO) controllerDTO
						.getAllAttribute();

				if (leaveApplicationControllerDTO.getApplicationResult()) {
					// show success message
					model.addAttribute("leaveApplicationInformation", leaveApplicationControllerDTO);
				} else {
					model.addAttribute("leaveApplicationInformation", leaveApplicationControllerDTO);
				}

				ra.addFlashAttribute("successMessage", "Leave application submitted successfully!");
			} else if (leaveApplication.getType().equals(LeaveType.COMPENSATION)) {
				CompensationLeaveServiceDTO compDTO = new CompensationLeaveServiceDTO();
				compDTO.setStaffId(leaveApplication.getStaffId());
				compDTO.setHalfDay(leaveApplication.isHalfDay());
				compDTO.setLeavePeriodStart(leaveApplication.getStartDate());
				compDTO.setLeavePeriodEnd(leaveApplication.getEndDate());
				compDTO.setReason(leaveApplication.getReason());
				compDTO.setType(leaveApplication.getType());

				ControllerDTO result = compService.submitApplication(compDTO);
				LeaveApplicationControllerDTO response = (LeaveApplicationControllerDTO) result.getAllAttribute();

				if (response.getApplicationResult()) {
					ra.addFlashAttribute("successMessage", "Compensation leave submitted successfully!");
				} else {
					ra.addFlashAttribute("errorMessage", response.getLeaveApprovalReason());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("errorMessage", "DEBUG ERROR: " + e.toString());
		}

		return "redirect:/staff/apply-leave";

	}

	@GetMapping("/my-leave-requests")
	public String myLeaveRequests(Model model, HttpSession session) {
		String role = (String) session.getAttribute("userRole");

		if (role == null || role.toString().isEmpty())
			return "redirect:/";

		// Retrieve the data from the database
		// need to pass the staffId into the method

		// Leave Request Card Structure
		// Leave Application Id
		// Leave Type
		// Date of the Leave Request Submitted
		// Duration
		// Leave Date From - Leave Date To

		// Total Number of Leave Days
		// N days

		// Leave Status (Leave Request Status)

		// Applied , Updated
		// Show Edit Request button and Delete Request button

		// Approved
		// Show Cancel Request button when the Leave Period have started

		// Rejected

		// Cancelled

		// Deleted

		ViewLeaveRequestsServiceDTO viewLeaveRequestsServiceDTO = new ViewLeaveRequestsServiceDTO();
		Long staffId = (Long) session.getAttribute("id");

		viewLeaveRequestsServiceDTO.setStaffId(staffId);

		List<ControllerDTO> leaveRequestControllerDTO = viewLeaveRequestsService
				.retrieveAllLeaveRequestByEmployeeId(viewLeaveRequestsServiceDTO);
		List<LeaveRequestControllerDTO> leaveRequestDTOList = leaveRequestControllerDTO.stream()
				.map(dto -> (LeaveRequestControllerDTO) dto.getAllAttribute())
				.collect(Collectors.toList());

		model.addAttribute("leaveRequestList", leaveRequestDTOList);

		return "my-leave-requests";
	}

	@GetMapping("/movement-register")
	public String movementRegister(
			@RequestParam(required = false) String month,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size, // Records of 3 per page, can be changed
			Model model,
			HttpSession session) {

		String role = (String) session.getAttribute("userRole");
		if (role == null || role.isEmpty()) {
			return "redirect:/";
		}

		YearMonth selectedMonth = (month == null || month.isEmpty())
				? YearMonth.now()
				: YearMonth.parse(month);

		Pageable pageable = PageRequest.of(page, size);

		Page<LeaveMovementDTO> leavePage = leaveMovementService.getApprovedLeaveForMonth(selectedMonth, pageable);

		model.addAttribute("leavePage", leavePage);
		model.addAttribute("selectedMonth", selectedMonth);
		model.addAttribute("pageSize", size);

		return "movement-register";
	}

	@GetMapping("/submit-ot-claim")
	public String submitOvertimeClaim(HttpSession session, Model model) {
		String role = (String) session.getAttribute("userRole");

		if (role == null || role.toString().isEmpty())
			return "redirect:/";

		model.addAttribute("otClaim", new OverTimeClaim());

		return "submit-ot-claim";
	}

	@PostMapping("/submit-ot-claim")
	public String processOTSubmission(@ModelAttribute OverTimeClaim otClaim,
			HttpSession session, RedirectAttributes redirectAttrs) {

		Long employeeId = (Long) session.getAttribute("id");

		// Create a "dummy" employee with just the ID
		Employee employee = new Employee();
		employee.setId(employeeId);

		otClaim.setEmployee(employee);

		otClaimService.submitOTClaim(otClaim);

		redirectAttrs.addFlashAttribute("successMessage", "Your OT claim was submitted successfully.");

		return "redirect:/staff/submit-ot-claim";
	}

	@PostMapping("/cancel-leave-request")
	public String cancelLeaveRequest(@ModelAttribute CancelLeaveRequestControllerDTO cancelLeaveRequestControllerDTO,
			HttpSession session, RedirectAttributes redirectAttrs) {
		String role = (String) session.getAttribute("userRole");

		if (role == null || role.toString().isEmpty())
			return "redirect:/";

		Long employeeId = (Long) session.getAttribute("id");
		if (employeeId == null) {
			return "redirect:/";
		}

		CancelLeaveRequestServiceDTO cancelLeaveRequestServiceDTO = new CancelLeaveRequestServiceDTO();
		cancelLeaveRequestServiceDTO.setEmployeeId(employeeId);
		cancelLeaveRequestServiceDTO.setStartDate(cancelLeaveRequestControllerDTO.getStartDate());
		cancelLeaveRequestServiceDTO.setEndDate(cancelLeaveRequestControllerDTO.getEndDate());
		cancelLeaveRequestServiceDTO.setLeaveRequestId(cancelLeaveRequestControllerDTO.getLeaveRequestId());
		cancelLeaveRequestServiceDTO.setLeaveStatus(cancelLeaveRequestControllerDTO.getLeaveStatus());
		cancelLeaveRequestServiceDTO.setReason(cancelLeaveRequestControllerDTO.getReason());

		ControllerDTO controllerDTO = cancelLeaveRequestService.cancelLeaveRequest(cancelLeaveRequestServiceDTO);

		CancelLeaveRequestControllerDTO result = (CancelLeaveRequestControllerDTO) controllerDTO.getAllAttribute();

		if (result.getOperationResult()) {
			redirectAttrs.addFlashAttribute("message", "The leave request is cancelled.");
			redirectAttrs.addFlashAttribute("cancelLeaveRequestData", result);
		} else {
			redirectAttrs.addFlashAttribute("message", "The leave request is not cancelled.");
			redirectAttrs.addFlashAttribute("cancelLeaveRequestData", result);
		}

		return "redirect:/staff/my-leave-requests";
	}

	@PostMapping("/update-leave-request")
	public String updateLeaveRequest(@ModelAttribute UpdateLeaveRequestControllerDTO updateLeaveRequestControllerDTO,
			HttpSession session, RedirectAttributes redirectAttrs) {
		String role = (String) session.getAttribute("userRole");

		if (role == null || role.toString().isEmpty())
			return "redirect:/";

		Long employeeId = (Long) session.getAttribute("id");
		if (employeeId == null) {
			return "redirect:/";
		}

		UpdateLeaveRequestServiceDTO updateLeaveRequestServiceDTO = new UpdateLeaveRequestServiceDTO();
		updateLeaveRequestServiceDTO.setEmployeeId(employeeId);
		updateLeaveRequestServiceDTO.setLeaveRequestId(updateLeaveRequestControllerDTO.getLeaveRequestId());
		updateLeaveRequestServiceDTO.setLeaveType(updateLeaveRequestControllerDTO.getLeaveType());
		updateLeaveRequestServiceDTO.setLeaveStatus(updateLeaveRequestControllerDTO.getLeaveStatus());
		updateLeaveRequestServiceDTO.setStartDate(updateLeaveRequestControllerDTO.getStartDate());
		updateLeaveRequestServiceDTO.setEndDate(updateLeaveRequestControllerDTO.getEndDate());
		updateLeaveRequestServiceDTO.setReason(updateLeaveRequestControllerDTO.getReason());

		ControllerDTO controllerDTO = updateLeaveRequestService.updateLeaveRequest(updateLeaveRequestServiceDTO);
		UpdateLeaveRequestControllerDTO result = (UpdateLeaveRequestControllerDTO) controllerDTO.getAllAttribute();

		if (result.getOperationResult()) {
			redirectAttrs.addFlashAttribute("message", "The leave request has been updated.");
			redirectAttrs.addFlashAttribute("updateLeaveRequestData", result);
		} else {
			redirectAttrs.addFlashAttribute("message", "The leave request could not be updated.");
			redirectAttrs.addFlashAttribute("updateLeaveRequestData", result);
		}

		return "redirect:/staff/my-leave-requests";
	}

}
