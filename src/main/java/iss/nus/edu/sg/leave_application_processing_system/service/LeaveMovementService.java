package iss.nus.edu.sg.leave_application_processing_system.service;

import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveMovementDTO;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveApplicationRepository;

@Service
public class LeaveMovementService {
  
  private final LeaveApplicationRepository leaveApplicationRepository;
  
  public LeaveMovementService(LeaveApplicationRepository leaveApplicationRepository) {
    this.leaveApplicationRepository = leaveApplicationRepository;
  }

  public Page<LeaveMovementDTO> getApprovedLeaveForMonth(
          YearMonth yearMonth,
          Pageable pageable) {

  LocalDate startOfMonth = yearMonth.atDay(1);
  LocalDate endOfMonth = yearMonth.atEndOfMonth();

  return leaveApplicationRepository.findApprovedLeaveForMonth(
    LeaveStatus.APPROVED,
    startOfMonth,
    endOfMonth,
    pageable
  );
}

}
