package iss.nus.edu.sg.leave_application_processing_system.service.implementation;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.ControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.TeamLeaveHistoryControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.model.Employee;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.repo.EmployeeRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.ServiceDTO;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.TeamLeaveHistoryServiceDTO;

@Service
public class ViewTeamLeaveHistoryService {
    
    private final LeaveApplicationRepository leaveApplicationRepository;
    private final EmployeeRepository employeeRepository;

    public ViewTeamLeaveHistoryService(LeaveApplicationRepository leaveApplicationRepository, EmployeeRepository employeeRepository){
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.employeeRepository = employeeRepository;
    }


    public List<ControllerDTO> retrieveCurrentSubordinateLeaveRecords(ServiceDTO serviceDTO){
        
        TeamLeaveHistoryServiceDTO teamLeaveHistoryServiceDTO = (TeamLeaveHistoryServiceDTO) serviceDTO.getAllAttribute();

        List<Employee> subordinateList = employeeRepository.findByManagerId(teamLeaveHistoryServiceDTO.getEmployeeId());

        List<LeaveApplication> subordinateLeaveApplications = subordinateList.stream()
                .map(Employee::getId)
                .filter(employeeId -> employeeId != null)
                .flatMap(employeeId -> leaveApplicationRepository.findByEmployeeId(employeeId).stream())
                .collect(Collectors.toList());

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
                    dto.setNumberOfLeaveDay((int) ChronoUnit.DAYS.between(leaveApplication.getStartDate(), leaveApplication.getEndDate()) + 1);
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
