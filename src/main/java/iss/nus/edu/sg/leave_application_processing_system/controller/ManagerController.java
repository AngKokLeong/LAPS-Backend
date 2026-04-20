package iss.nus.edu.sg.leave_application_processing_system.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ApiErrorResponse;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.CompensationReportResponseDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveApprovalControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveReportResponseDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.OTClaimControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.SubordinateLeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.TeamLeaveHistoryControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.persistent.enumeration.OTClaimStatus;
import iss.nus.edu.sg.leave_application_processing_system.persistent.enumeration.Role;
import iss.nus.edu.sg.leave_application_processing_system.security.ApplicationUserDetails;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.LeaveApprovalServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ManagerQueryServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.TeamLeaveHistoryServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.features.compensation_leave_management.OverTimeClaimService;
import iss.nus.edu.sg.leave_application_processing_system.service.features.reporting.CSVExportService;
import iss.nus.edu.sg.leave_application_processing_system.service.features.reporting.ReportingService;
import iss.nus.edu.sg.leave_application_processing_system.service.features.view_leave_application_for_approval.TeamManagementService;
import iss.nus.edu.sg.leave_application_processing_system.service.features.view_subordinate_leave_history.ViewTeamLeaveHistoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager")
public class ManagerController {

	private final TeamManagementService teamMngService;
	private final OverTimeClaimService overTimeClaimService;
	private final ViewTeamLeaveHistoryService viewTeamLeaveHistoryService;
	private final ReportingService reportingService;
	private final CSVExportService csvExportService;

	public ManagerController(TeamManagementService teamMngService, OverTimeClaimService overTimeClaimService, 
			ViewTeamLeaveHistoryService viewTeamLeaveHistoryService, ReportingService reportingService,
			CSVExportService csvExportService) {
		this.teamMngService = teamMngService;
		this.overTimeClaimService = overTimeClaimService;
		this.viewTeamLeaveHistoryService = viewTeamLeaveHistoryService;
		this.reportingService = reportingService;
		this.csvExportService = csvExportService;
	}

	@GetMapping("/team-leave-history")
	public String teamLeaveHistory(Model model, HttpSession session) {

		String extractedRoleData = (String) session.getAttribute("userRole");

		if (extractedRoleData == null || extractedRoleData.toString().isEmpty())
			return "redirect:/";

		Role role = Role.valueOf(extractedRoleData);

		if (!role.equals(Role.MANAGER)) {
			return "redirect:/staff"; // Send them home if they aren't a manager
		}

		// use the current employeeId to find all subordinates' leave records
		Long managerId = (Long) session.getAttribute("id");
		TeamLeaveHistoryServiceDTO teamLeaveHistoryService = new TeamLeaveHistoryServiceDTO();
		teamLeaveHistoryService.setEmployeeId(managerId);

		List<ControllerDTO> subordinateLeaveRecords = viewTeamLeaveHistoryService.retrieveCurrentSubordinateLeaveRecords(teamLeaveHistoryService);

		List<TeamLeaveHistoryControllerDTO> subordinateLeaveHistory = subordinateLeaveRecords.stream()
			.map(dto -> (TeamLeaveHistoryControllerDTO) dto.getAllAttribute())
			.collect(Collectors.toList());

		model.addAttribute("subordinateLeaveHistory", subordinateLeaveHistory);

		return "team-leave-history";
	}

	@GetMapping("/view-team-members-leave")
	@PreAuthorize("hasRole('MANAGER')")
	public String teamMembersLeave(@AuthenticationPrincipal ApplicationUserDetails userDetails, Model model) {

	    Long currentManagerId = userDetails.getEmployee().getId();
	    ManagerQueryServiceDTO sDTO = new ManagerQueryServiceDTO(currentManagerId);

	    List<ControllerDTO> teamBalances = teamMngService.viewTeamLeaveBalances(sDTO);

	    model.addAttribute("teamBalances", teamBalances);

		return "view-team-members-leave";
	}

	@GetMapping("/manage-leave-requests")
	@PreAuthorize("hasRole('MANAGER')")
	public String manageLeaveRequests(Authentication authentication, Model model) {

		ApplicationUserDetails userDetails = (ApplicationUserDetails) authentication.getPrincipal();
	    Long managerId = userDetails.getEmployee().getId();

		ManagerQueryServiceDTO query = new ManagerQueryServiceDTO(managerId);

		// Fetch the list from the service
		List<ControllerDTO> teamRequests = teamMngService.getSubordinateLeaveRequests(query);

		// Count those with "PENDING" status
		long pendingCount = teamRequests.stream()
				.map(req -> (SubordinateLeaveRequestControllerDTO) req)
	            .filter(req -> "APPLIED".equals(req.getStatus()) || "UPDATED".equals(req.getStatus()))
	            .count();

		// Add to the Model for Thymeleaf
		model.addAttribute("teamRequests", teamRequests);
		model.addAttribute("pendingCount", pendingCount);

		return "manage-leave-requests";
	}

	@PostMapping("/leave/{id}/approve")
	@PreAuthorize("hasRole('MANAGER')")
	public String approveLeave(@PathVariable Long id, @RequestParam(required = false) String remarks,
			@AuthenticationPrincipal ApplicationUserDetails userDetails, RedirectAttributes ra) {
		
		try {
			Long currentManagerId = userDetails.getEmployee().getId();
			LeaveApprovalServiceDTO requestDto = new LeaveApprovalServiceDTO(id, "APPROVE", currentManagerId);
			requestDto.setManagerRemarks(remarks);

			LeaveApprovalControllerDTO result = (LeaveApprovalControllerDTO) teamMngService.processApproval(requestDto);

			ra.addFlashAttribute("successMessage", result.getMessage());
		} catch (IllegalArgumentException | EntityNotFoundException e) {
			// The Service has already rolled back! Now we just inform the user.
			ra.addFlashAttribute("errorMessage", e.getMessage());
		} catch (Exception e) {
	        e.printStackTrace(); 
	        ra.addFlashAttribute("errorMessage", "DEBUG ERROR: " + e.toString());
		}

		return "redirect:/manager/manage-leave-requests";
	}

	@PostMapping("/leave/{id}/reject")
	@PreAuthorize("hasRole('MANAGER')")
	public String rejectLeave(@PathVariable Long id, @RequestParam(required = false) String remarks,
			@AuthenticationPrincipal ApplicationUserDetails userDetails, RedirectAttributes ra) {

		try {
	        // Mandatory Validation for Rejection
	        if (remarks == null || remarks.isBlank()) {
	            ra.addFlashAttribute("errorMessage", "A reason is required to reject a leave request.");
	            return "redirect:/manager/manage-leave-requests";
	        }

	        Long currentManagerId = userDetails.getEmployee().getId();
	        LeaveApprovalServiceDTO requestDto = new LeaveApprovalServiceDTO(id, "REJECT", currentManagerId);
	        requestDto.setManagerRemarks(remarks);

	        LeaveApprovalControllerDTO result = (LeaveApprovalControllerDTO) teamMngService.processApproval(requestDto);

	        ra.addFlashAttribute("successMessage", result.getMessage());

	    } catch (RuntimeException e) {
	        ra.addFlashAttribute("errorMessage", "Could not reject request: " + e.getMessage());
	    } catch (Exception e) {
	    	e.printStackTrace(); 
	        ra.addFlashAttribute("errorMessage", "DEBUG ERROR: " + e.toString());
	    }

		return "redirect:/manager/manage-leave-requests";
	}

	// OVERTIME WORKFLOW
	@GetMapping("/approve-ot-claim")
	public String approveOTClaim(HttpSession session, Model model) {
		// check session role
		String extractedRoleData = (String) session.getAttribute("userRole");
		if (extractedRoleData == null || extractedRoleData.toString().isEmpty())
			return "redirect:/";
		Role role = Role.valueOf(extractedRoleData);
		if (!role.equals(Role.MANAGER)) {
			return "redirect:/staff"; // Send them home if they aren't a manager
		}
		
		// 1. Get the current manager's ID 
	    // (For now hardcode this, later get it from Session/Security context)
		Long managerId = (Long) session.getAttribute("id");
		ManagerQueryServiceDTO query = new ManagerQueryServiceDTO(managerId);

	    // 2. Call the service to get the list of DTOs
	    List<ControllerDTO> otClaims = teamMngService.getSubordinateOTClaims(query);
	    long pendingCount = otClaims.stream()
				.filter(claim -> ((OTClaimControllerDTO) claim).getStatus() == OTClaimStatus.PENDING)
				.count();

	    // 3. Add the list to the Model
	    model.addAttribute("otClaims", otClaims);
	    model.addAttribute("pendingCount", pendingCount);

		return "approve-ot-claim";
	}

	// To View OT /manager/approve-ot-claim/list?status=PENDING
	@GetMapping("/approve-ot-claim/list")
	public String viewOTClaims(
			@RequestParam(defaultValue = "PENDING") OTClaimStatus status,
			HttpSession session,
			Model model) {

		String extractedRoleData = (String) session.getAttribute("userRole");

		if (extractedRoleData == null || extractedRoleData.toString().isEmpty())
			return "redirect:/";

		Role role = Role.valueOf(extractedRoleData);

		if (!role.equals(Role.MANAGER)) {
			return "redirect:/staff"; // Send them home if they aren't a manager
		}

		model.addAttribute(
				"otClaims",
				overTimeClaimService.findByStatus(status));
		model.addAttribute("selectedStatus", status);

		return "approve-ot-claim";
	}

	// APPROVE OT
	@PostMapping("/approve-ot-claim/{id}/approve")
	public String approveOT(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
		String extractedRoleData = (String) session.getAttribute("userRole");

		if (extractedRoleData == null || extractedRoleData.toString().isEmpty())
			return "redirect:/";

		Role role = Role.valueOf(extractedRoleData);

		if (!role.equals(Role.MANAGER)) {
			return "redirect:/staff"; // Send them home if they aren't a manager
		}
		
		ra.addFlashAttribute("successMessage", "OT Claim Approved");

		overTimeClaimService.approveOTClaim(id);

		return "redirect:/manager/approve-ot-claim";
	}

	// REJECT OT
	@PostMapping("/approve-ot-claim/{id}/reject")
	public String rejectOT(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
		String extractedRoleData = (String) session.getAttribute("userRole");

		if (extractedRoleData == null || extractedRoleData.toString().isEmpty())
			return "redirect:/";

		Role role = Role.valueOf(extractedRoleData);

		if (!role.equals(Role.MANAGER)) {
			return "redirect:/staff"; // Send them home if they aren't a manager
		}
		
		ra.addFlashAttribute("successMessage", "OT Claim Rejected");

		overTimeClaimService.rejectOTClaim(id);
		return "redirect:/manager/approve-ot-claim";
	}

	// ==================== REPORTING ENDPOINTS ====================

	@GetMapping("/reports")
	@PreAuthorize("hasRole('MANAGER')")
	public String reports(@AuthenticationPrincipal ApplicationUserDetails userDetails, Model model) {
		return "reports";
	}

	/**
	 * Fetch leave report data as JSON for AJAX requests.
	 * Filters by date range, leave type, and optional employee ID.
	 */
	@GetMapping("/reports/leaves")
	@PreAuthorize("hasRole('MANAGER')")
	@ResponseBody
	public ResponseEntity<?> getLeaveReport(
			@AuthenticationPrincipal ApplicationUserDetails userDetails,
			@RequestParam LocalDate startDate,
			@RequestParam LocalDate endDate,
			@RequestParam(defaultValue = "all") String leaveType,
			@RequestParam(required = false) Long employeeId) {

		Long managerId = userDetails.getEmployee().getId();

		try {
			List<LeaveReportResponseDTO> report = reportingService.generateLeaveReport(
					managerId, startDate, endDate, leaveType, employeeId);
			return ResponseEntity.ok(report);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiErrorResponse("Failed to load leave report: " + e.getMessage()));
		}
	}

	/**
	 * Fetch compensation/overtime claims report data as JSON for AJAX requests.
	 * Filters by date range and optional employee ID.
	 */
	@GetMapping("/reports/compensation")
	@PreAuthorize("hasRole('MANAGER')")
	@ResponseBody
	public ResponseEntity<?> getCompensationReport(
			@AuthenticationPrincipal ApplicationUserDetails userDetails,
			@RequestParam LocalDate startDate,
			@RequestParam LocalDate endDate,
			@RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) String status) {

		Long managerId = userDetails.getEmployee().getId();

		try {
			List<CompensationReportResponseDTO> report = reportingService.generateCompensationReport(
					managerId, startDate, endDate, employeeId, status);
			return ResponseEntity.ok(report);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiErrorResponse("Failed to load compensation report: " + e.getMessage()));
		}
	}

	/**
	 * Export leave report as CSV file.
	 */
	@PostMapping("/reports/export-leaves-csv")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<String> exportLeaveReportCSV(
			@AuthenticationPrincipal ApplicationUserDetails userDetails,
			@RequestParam LocalDate startDate,
			@RequestParam LocalDate endDate,
			@RequestParam(defaultValue = "all") String leaveType,
			@RequestParam(required = false) Long employeeId) {

		Long managerId = userDetails.getEmployee().getId();

		try {
			List<LeaveReportResponseDTO> report = reportingService.generateLeaveReport(
					managerId, startDate, endDate, leaveType, employeeId);
			String csvContent = csvExportService.generateLeaveReportCSV(report);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("text/csv;charset=UTF-8"));
			headers.setContentDispositionFormData("attachment", "leave_report.csv");

			return new ResponseEntity<>(csvContent, headers, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Export compensation claims report as CSV file.
	 */
	@PostMapping("/reports/export-compensation-csv")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<String> exportCompensationReportCSV(
			@AuthenticationPrincipal ApplicationUserDetails userDetails,
			@RequestParam LocalDate startDate,
			@RequestParam LocalDate endDate,
			@RequestParam(required = false) Long employeeId,
			@RequestParam(required = false) String status) {

		Long managerId = userDetails.getEmployee().getId();

		try {
			List<CompensationReportResponseDTO> report = reportingService.generateCompensationReport(
					managerId, startDate, endDate, employeeId, status);
			String csvContent = csvExportService.generateCompensationReportCSV(report);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("text/csv;charset=UTF-8"));
			headers.setContentDispositionFormData("attachment", "compensation_report.csv");

			return new ResponseEntity<>(csvContent, headers, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
