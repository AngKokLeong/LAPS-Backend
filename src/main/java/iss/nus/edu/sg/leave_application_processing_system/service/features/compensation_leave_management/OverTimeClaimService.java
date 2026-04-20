package iss.nus.edu.sg.leave_application_processing_system.service.features.compensation_leave_management;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.persistent.entity.OverTimeClaim;
import iss.nus.edu.sg.leave_application_processing_system.persistent.enumeration.OTClaimStatus;
import iss.nus.edu.sg.leave_application_processing_system.persistent.repository.OverTimeClaimRepository;
import jakarta.transaction.Transactional;

@Service
public class OverTimeClaimService {

    private final OverTimeClaimRepository overTimeClaimRepository;
    private final CompensationService compensationService;

    public OverTimeClaimService(
            OverTimeClaimRepository overTimeClaimRepository,
            CompensationService compensationService) {
        this.overTimeClaimRepository = overTimeClaimRepository;
        this.compensationService = compensationService;
    }

    
    /**
     * Employee submits OT claim (TO BE MAPPED TO SUBMIT BUTTON)
     * @param claim 
     * @return void
     */
    public void submitOTClaim(OverTimeClaim claim) {
        claim.setStatus(OTClaimStatus.PENDING);
        overTimeClaimRepository.save(claim);
    }

    /**
     * Purpose: Manager approves OT claim
     * Transactional
     * @param claimId 
     * @return void
     */
    @Transactional
    public void approveOTClaim(Long claimId) {

        OverTimeClaim claim = overTimeClaimRepository.findById(claimId).orElseThrow();

        if (claim.getStatus() != OTClaimStatus.PENDING) {
            throw new IllegalStateException("OT Claim already processed");
        }
        claim.setStatus(OTClaimStatus.APPROVED);

        // 1. Calculate OT duration
        double hoursWorked = Math.round(calculateHours(
                claim.getStartDateTime(),
                claim.getEndDateTime()) * 100.0) / 100.0;

        if (hoursWorked <= 0) {
            throw new IllegalArgumentException("Invalid OT Duration");
        }

        int year = claim.getStartDateTime().getYear();

        compensationService.addOvertimeHours(
            claim.getEmployee().getId(), year, hoursWorked
        );

        overTimeClaimRepository.save(claim);
    }

    /**
     * Purpose: Manager rejects OT claim
     * Transactional
     * @param claimId 
     * @return void
     */
    public void rejectOTClaim(Long claimId) {
        OverTimeClaim claim = overTimeClaimRepository.findById(claimId).orElseThrow();
        claim.setStatus(OTClaimStatus.REJECTED);
        overTimeClaimRepository.save(claim);
    }
    
    // Find by OT Status (Pending, Approve, Reject)
    public List<OverTimeClaim> findByStatus(OTClaimStatus status) {
            return overTimeClaimRepository.findByStatus(status);
        }


    // Utility
    private double calculateHours(
            LocalDateTime start,
            LocalDateTime end) {

        long minutes =
                Duration.between(start, end).toMinutes();

        return minutes / 60.0;
    }
}