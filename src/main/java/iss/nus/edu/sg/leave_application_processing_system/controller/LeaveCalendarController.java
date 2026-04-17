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
    	String endDateStr = leave.getEndDate().plusDays(1).toString();
        CalendarEventControllerDTO event = new CalendarEventControllerDTO();
        
        event.setId(String.valueOf(leave.getLeaveRequestId()));
        event.setStart(leave.getStartDate().toString());
        event.setEnd(endDateStr);

        String type = leave.getLeaveType().toString().toUpperCase();
        String status = leave.getLeaveStatus().toString().toUpperCase();
        boolean isPending = status.equals("APPLIED") || status.equals("UPDATED");

        // Icon Logic
        String statusIcon = isPending ? "○ " : (status.equals("CANCELLED") ? "✕ " : "● ");
        event.setTitle(statusIcon + leave.getLeaveType());

        // CLASS LOGIC: We define the "Vibe" purely through classes now
        String cssClasses = "custom-event";
        cssClasses += " type-" + type.toLowerCase(); // e.g., type-annual
        if (isPending) cssClasses += " is-pending";
        if (status.equals("CANCELLED")) cssClasses += " is-cancelled";

        event.setClassName(cssClasses);

        return event;
    }

    
}
