package iss.nus.edu.sg.leave_application_processing_system.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.CalendarEventControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.security.ApplicationUserDetails;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ViewLeaveRequestsServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.implementation.ViewLeaveRequestsService;

@RequestMapping("/staff")
public class LeaveCalendarController {
	
	private final ViewLeaveRequestsService viewLeaveRequestsService;

    public LeaveCalendarController(ViewLeaveRequestsService viewLeaveRequestsService) {
        this.viewLeaveRequestsService = viewLeaveRequestsService;
    }
    
    @GetMapping("/calendar")
    public List<CalendarEventControllerDTO> getMyLeaves(@AuthenticationPrincipal ApplicationUserDetails userDetails) {
        
    	Long staffId = userDetails.getEmployee().getId();
        
    	// 2. Reuse the Team's Service DTO structure
        ViewLeaveRequestsServiceDTO serviceDTO = new ViewLeaveRequestsServiceDTO();
        serviceDTO.setStaffId(staffId);

        // 3. Fetch data using the existing service method
        // This returns the List of ControllerDTOs your teammate used
        List<ControllerDTO> leaveRequestControllerDTOs = viewLeaveRequestsService
                .retrieveAllLeaveRequestByEmployeeId(serviceDTO);

        // 4. Map the teammate's DTO to your CalendarEventDTO
        return leaveRequestControllerDTOs.stream()
                .map(dto -> (LeaveRequestControllerDTO) dto.getAllAttribute())
                .map(this::convertToCalendarEvent)
                .collect(Collectors.toList());
    }

    private CalendarEventDTO convertToCalendarEvent(LeaveRequestControllerDTO leave) {
        // FullCalendar 'end' is exclusive: Add 1 day to the end date
        String endDateStr = leave.getEndDate().plusDays(1).toString();

        return CalendarEventDTO.builder()
                .id(String.valueOf(leave.getId()))
                .title(leave.getLeaveType() + " (" + leave.getLeaveStatus() + ")")
                .start(leave.getStartDate().toString())
                .end(endDateStr)
                .color(determineColor(leave.getLeaveStatus().toString()))
                .textColor("#ffffff")
                .description(leave.getReason())
                .build();
    }

    private String determineColor(String status) {
        return switch (status.toUpperCase()) {
            case "APPROVED" -> "#28a745"; // Green
            case "PENDING"  -> "#ffc107"; // Amber
            case "REJECTED" -> "#dc3545"; // Red
            default -> "#6c757d";         // Gray
        };
    }
}
