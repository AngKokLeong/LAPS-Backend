package iss.nus.edu.sg.leave_application_processing_system.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.CalendarEventControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.security.ApplicationUserDetails;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ViewLeaveRequestsServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.implementation.ViewLeaveRequestsService;

@RestController
public class LeaveCalendarController {
	
	private final ViewLeaveRequestsService viewLeaveRequestsService;

    public LeaveCalendarController(ViewLeaveRequestsService viewLeaveRequestsService) {
        this.viewLeaveRequestsService = viewLeaveRequestsService;
    }
    
    @GetMapping("/api/calendar")
    public List<CalendarEventControllerDTO> getMyLeaves(@AuthenticationPrincipal ApplicationUserDetails userDetails) {
        
    	Long staffId = userDetails.getEmployee().getId();
        
        ViewLeaveRequestsServiceDTO serviceDTO = new ViewLeaveRequestsServiceDTO();
        serviceDTO.setStaffId(staffId);

        List<ControllerDTO> leaveRequestControllerDTOs = viewLeaveRequestsService
                .retrieveAllLeaveRequestByEmployeeId(serviceDTO);

        // 4. Map DTO to CalendarEventDTO
        return leaveRequestControllerDTOs.stream()
                .map(dto -> (LeaveRequestControllerDTO) dto.getAllAttribute())
                .map(this::convertToCalendarEvent)
                .collect(Collectors.toList());
    }

    private CalendarEventControllerDTO convertToCalendarEvent(LeaveRequestControllerDTO leave) {
    	// FullCalendar 'end' is exclusive: Add 1 day
        String endDateStr = leave.getEndDate().plusDays(1).toString();
        CalendarEventControllerDTO event = new CalendarEventControllerDTO();
        
        event.setId(String.valueOf(leave.getLeaveRequestId()));
        event.setStart(leave.getStartDate().toString());
        event.setEnd(endDateStr);

        String type = leave.getLeaveType().toString().toUpperCase();
        String status = leave.getLeaveStatus().toString().toUpperCase();
        boolean isPending = status.equals("APPLIED") || status.equals("UPDATED");

        // ICON LOGIC: Matches new Status Symbols Legend exactly
        String statusIcon = switch (status) {
            case "APPROVED" -> "✅ ";
            case "REJECTED" -> "❌ ";
            case "APPLIED", "UPDATED" -> "⏳ ";
            case "CANCELLED" -> "🚫 ";
            default -> "● ";
        };
        
        // Set the title with the icon and capitalize only the first letter of type
        String typeLabel = leave.getLeaveType().toString().toLowerCase();
        typeLabel = typeLabel.substring(0, 1).toUpperCase() + typeLabel.substring(1);
        event.setTitle(statusIcon + typeLabel);

        // CLASS LOGIC: Maps to our CSS for the colorful left bars
        StringBuilder cssClasses = new StringBuilder("custom-event");
        
        // Append type class (type-annual, type-medical, etc.)
        cssClasses.append(" type-").append(type.toLowerCase());
        
        if (isPending) {
            cssClasses.append(" is-pending");
        }
        
        if (status.equals("CANCELLED") || status.equals("REJECTED")) {
            cssClasses.append(" is-cancelled");
        }

        event.setClassName(cssClasses.toString());

        // TOOLTIP / DESCRIPTION
        event.setDescription(leave.getReason());

        return event;
    }

    
}
