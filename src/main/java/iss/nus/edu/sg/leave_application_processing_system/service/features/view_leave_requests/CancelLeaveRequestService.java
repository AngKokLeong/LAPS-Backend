package iss.nus.edu.sg.leave_application_processing_system.service.features.view_leave_requests;

import java.time.DayOfWeek;
import java.time.LocalDate;

import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.CancelLeaveRequestControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;
import iss.nus.edu.sg.leave_application_processing_system.model.CompensationLedger;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveEntitlement;
import iss.nus.edu.sg.leave_application_processing_system.repo.CompensationLedgerRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveEntitlementRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.DTO.CancelLeaveRequestServiceDTO;
import jakarta.transaction.Transactional;

@Service
public class CancelLeaveRequestService {

    private LeaveApplicationRepository leaveApplicationRepository;
		private LeaveEntitlementRepository leaveEntitlementRepository;
		private CompensationLedgerRepository compensationLedgerRepository;

    public CancelLeaveRequestService(LeaveApplicationRepository leaveApplicationRepository, LeaveEntitlementRepository leaveEntitlementRepository, CompensationLedgerRepository compensationLedgerRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
				this.leaveEntitlementRepository = leaveEntitlementRepository;
				this.compensationLedgerRepository = compensationLedgerRepository;
    }
    
    
		@Transactional
    public CancelLeaveRequestControllerDTO cancelLeaveRequest(
            CancelLeaveRequestServiceDTO dto) {

        CancelLeaveRequestControllerDTO response =
                new CancelLeaveRequestControllerDTO();

        // Load leave (SOURCE OF TRUTH — not client)
        LeaveApplication leave = leaveApplicationRepository
            .findById(dto.getLeaveRequestId())
            .orElseThrow(() ->
                new IllegalArgumentException("Leave not found"));

        // Ownership & status check
        if (!leave.getEmployee().getId().equals(dto.getEmployeeId()) ||
            leave.getLeaveStatus() != LeaveStatus.APPROVED) {

            response.setOperationResult(false);
            response.setOperationComments(
                "You can only cancel your own approved leave");
            return response;
        }

        // Date rule: cannot cancel after leave has started
        if (LocalDate.now().isAfter(leave.getStartDate())) {
            response.setOperationResult(false);
            response.setOperationComments(
                "You cannot cancel a leave that has already started");
            return response;
        }

				// calculate days to restore
				double daysToRestore = calculateDuration(
						leave.getStartDate(),
						leave.getEndDate(),
						leave.isHalfDay()
				);

				int year = leave.getStartDate().getYear();

				// restore balance
				if (leave.getLeaveType() == LeaveType.COMPENSATION) {
					CompensationLedger ledger = compensationLedgerRepository.findByEmployeeIdAndYearApplied(leave.getEmployee().getId(), year)
							.orElseThrow(() -> new IllegalStateException("Compensation ledger not found"));
					ledger.setUsedDays(ledger.getUsedDays() - daysToRestore);
					compensationLedgerRepository.save(ledger);
				} else {
					LeaveEntitlement entitlement = leaveEntitlementRepository
							.findByEmployeeId_IdAndLeaveTypeAndYearApplied(
									leave.getEmployee().getId(),
									leave.getLeaveType(),
									year)
							.orElseThrow(() -> new IllegalStateException("Leave entitlement not found"));
					int currentUsed = entitlement.getUsedDays();
					int restore = (int) daysToRestore;
					int newUsed = Math.max(0, currentUsed - restore); // Ensure we don't go negative
					entitlement.setUsedDays(newUsed);
					leaveEntitlementRepository.save(entitlement);
				}

        // Update leave status upon cancellation
        leave.setLeaveStatus(LeaveStatus.CANCELLED);
        leave.setUpdatedDate(LocalDate.now());
        leave.setReason(dto.getReason());
        leaveApplicationRepository.save(leave);

				response.setOperationResult(true);
				return response;
		}

		// Helper - Duration logic (similar to TeamManagementService)
		private double calculateDuration(LocalDate start, LocalDate end, boolean isHalfDay) {
				double workingDays = 0;
				LocalDate current = start;

				while (!current.isAfter(end)) {
					DayOfWeek dow = current.getDayOfWeek();
						if (dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY) {
								workingDays++;
						}
						current = current.plus(1, ChronoUnit.DAYS);
				}
				return isHalfDay ? workingDays*0.5 : workingDays;
			}
}
