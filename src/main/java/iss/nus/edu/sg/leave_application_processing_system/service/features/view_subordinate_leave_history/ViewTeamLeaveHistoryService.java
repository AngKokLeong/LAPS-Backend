package iss.nus.edu.sg.leave_application_processing_system.service.features.view_subordinate_leave_history;

import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.TeamLeaveHistoryControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.persistent.entity.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.persistent.repository.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.TeamLeaveHistoryServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.features.shared_service.LeaveValidationService;

@Service
public class ViewTeamLeaveHistoryService {
    
    private final LeaveApplicationRepository leaveApplicationRepository;
    private final LeaveValidationService lvService;

    public ViewTeamLeaveHistoryService(LeaveApplicationRepository leaveApplicationRepository, LeaveValidationService lvService){
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.lvService = lvService;
    }


    public List<ControllerDTO> retrieveCurrentSubordinateLeaveRecords(ServiceDTO serviceDTO){
        
        TeamLeaveHistoryServiceDTO teamLeaveHistoryServiceDTO = (TeamLeaveHistoryServiceDTO) serviceDTO.getAllAttribute();

        
        List<LeaveApplication> subordinateLeaveApplications = leaveApplicationRepository.findByEmployee_ManagerIdOrderByStartDateDesc(teamLeaveHistoryServiceDTO.getEmployeeId());

        List<ControllerDTO> controllerDTOList = subordinateLeaveApplications.stream()
                .map(leaveApplication -> {
                    TeamLeaveHistoryControllerDTO dto = new TeamLeaveHistoryControllerDTO();
                    dto.setStaffName(leaveApplication.getEmployee().getName());
                    dto.setStaffDesignation(leaveApplication.getEmployee().getDesignation() != null
                            ? leaveApplication.getEmployee().getDesignation().name()
                            : null);
                    dto.setLeaveType(leaveApplication.getLeaveType());
                    dto.setLeaveStartDate(leaveApplication.getStartDate());
                    dto.setLeaveEndDate(leaveApplication.getEndDate());
                    
                    double actualDuration = lvService.calculateDays(
                    	    leaveApplication.getStartDate(), 
                    	    leaveApplication.getEndDate(), 
                    	    leaveApplication.isHalfDay()
                    	);
                    
                    dto.setNumberOfLeaveDay(actualDuration);
                    dto.setLeaveStatus(leaveApplication.getLeaveStatus());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d");
                    String leaveDuration = leaveApplication.getStartDate().format(formatter)
                            + " - "
                            + leaveApplication.getEndDate().format(formatter)
                            + " "
                            + leaveApplication.getEndDate().getYear();
                    dto.setLeaveDuration(leaveDuration);
                    dto.setReviewBy(leaveApplication.getEmployee().getManager() != null
                            ? leaveApplication.getEmployee().getManager().getName()
                            : null);
                    dto.setLeaveRequestUpdatedDate(leaveApplication.getUpdatedDate());
                    return (ControllerDTO) dto;
                })
                .collect(Collectors.toList());

        return controllerDTOList;
    }

}
