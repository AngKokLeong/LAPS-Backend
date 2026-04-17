package iss.nus.edu.sg.leave_application_processing_system.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.CalendarEventControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveRequestControllerDTO;
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
        // FullCalendar 'end' is exclusive: Add 1 day to the end date
        String endDateStr = leave.getEndDate().plusDays(1).toString();

        // Create the object using the default constructor
        CalendarEventControllerDTO event = new CalendarEventControllerDTO();
        
        event.setId(String.valueOf(leave.getLeaveRequestId()));
        
        String displayStatus = leave.getLeaveStatus().toString();
        if (displayStatus.equals("APPLIED") || displayStatus.equals("UPDATED")) {
            displayStatus = "PENDING";
        }
        
        event.setTitle(leave.getLeaveType() + " (" + displayStatus + ")");
        event.setStart(leave.getStartDate().toString());
        event.setEnd(endDateStr);
        event.setColor(determineColor(leave.getLeaveStatus().toString()));
        event.setTextColor("#ffffff");
        event.setDescription(leave.getReason());

        return event;
    }

    private String determineColor(String status) {
        return switch (status.toUpperCase()) {
            case "APPROVED" -> "#28a745"; // Success Green
            case "REJECTED" -> "#dc3545"; // Danger Red
            case "APPLIED", "UPDATED" -> "#ffc107"; // Warning Amber (Pending)
            case "CANCELLED" -> "#6c757d"; // Subtle Gray
            default -> "#e9ecef"; // Very light gray for others
        };
    }
    
}
