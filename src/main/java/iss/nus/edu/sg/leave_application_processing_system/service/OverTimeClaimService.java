package iss.nus.edu.sg.leave_application_processing_system.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.helper.OTClaimStatus;
import iss.nus.edu.sg.leave_application_processing_system.model.OverTimeClaim;
import iss.nus.edu.sg.leave_application_processing_system.repo.OverTimeClaimRepository;
import jakarta.transaction.Transactional;

@Service
public class OverTimeClaimService {

    private final OverTimeClaimRepository otRepo;
    private final CompensationService compensationService;

    public OverTimeClaimService(
            OverTimeClaimRepository otRepo,
            CompensationService compensationService) {
        this.otRepo = otRepo;
        this.compensationService = compensationService;
    }

    // Employee submits OT claim (TO BE MAPPED TO SUBMIT BUTTON)
    public void submitOTClaim(OverTimeClaim claim) {
        claim.setStatus(OTClaimStatus.PENDING);
        otRepo.save(claim);
    }

    // Manager approves OT claim (TO BE MAPPED TO APPROVE BUTTON)
    @Transactional
    public void approveOTClaim(Long claimId) {

        OverTimeClaim claim = otRepo.findById(claimId).orElseThrow();

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

        otRepo.save(claim);
    }

    // Manager rejects OT claim (TO BE MAPPED TO REJECT BUTTON)
    public void rejectOTClaim(Long claimId) {
        OverTimeClaim claim = otRepo.findById(claimId).orElseThrow();
        claim.setStatus(OTClaimStatus.REJECTED);
        otRepo.save(claim);
    }
    
    // Find by OT Status (Pending, Approve, Reject)
    public List<OverTimeClaim> findByStatus(OTClaimStatus
        status) {
            return otRepo.findByStatus(status);
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