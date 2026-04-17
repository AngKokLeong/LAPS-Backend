package iss.nus.edu.sg.leave_application_processing_system.api;

import java.time.Duration;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.OTClaimControllerDTO;
import iss.nus.edu.sg.leave_application_processing_system.helper.OTClaimStatus;
import iss.nus.edu.sg.leave_application_processing_system.model.Employee;
import iss.nus.edu.sg.leave_application_processing_system.model.OverTimeClaim;
import iss.nus.edu.sg.leave_application_processing_system.repo.CompensationLedgerRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.EmployeeRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.OverTimeClaimRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.CompensationService;

@RestController
@RequestMapping("/api/compensation")
public class CompensationRestController {

    private final CompensationService compensationService;
    private final EmployeeRepository employeeRepo;
    private final CompensationLedgerRepository ledgerRepo;
    private final OverTimeClaimRepository otClaimRepo;

    public CompensationRestController(
            CompensationService compensationService,
            EmployeeRepository employeeRepo,
            CompensationLedgerRepository ledgerRepo,
            OverTimeClaimRepository otClaimRepo) {
        this.compensationService = compensationService;
        this.employeeRepo = employeeRepo;
        this.ledgerRepo = ledgerRepo;
        this.otClaimRepo = otClaimRepo;
    }

    @GetMapping("/summary")
    public Map<String, Object> getCompensationSummary(Authentication auth) {
        String email = auth.getName();
        Employee employee = employeeRepo.findByEmail(email).orElseThrow();

        int year = Year.now().getValue();

        double availableDays = ledgerRepo.findByEmployeeIdAndYearApplied(employee.getId(), year)
                .map(compensationService::getAvailableCompensationDays)
                .orElse(0.0);
        double unconvertedHours = compensationService.getUnconvertedHours(employee.getId(), year);

        Map<String, Object> response = new HashMap<>();
        response.put("availableDays", availableDays);
        response.put("unconvertedHours", unconvertedHours);

        return response;
    }

    @GetMapping("/claims")
    public List<OTClaimControllerDTO> getCompensationClaims(Authentication auth,
            @RequestParam(defaultValue = "all") String status) {
        String email = auth.getName();
        Employee employee = employeeRepo.findByEmail(email).orElseThrow();

        List<OverTimeClaim> claims = otClaimRepo.findByEmployeeId(employee.getId());
        if (!"all".equalsIgnoreCase(status)) {
            try {
                OTClaimStatus claimStatus = OTClaimStatus.valueOf(status.toUpperCase());
                claims = claims.stream()
                        .filter(claim -> claim.getStatus() == claimStatus)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                // invalid status, return all claims
            }
        }

        return claims.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private OTClaimControllerDTO toDto(OverTimeClaim claim) {
        long minutes = Duration.between(claim.getStartDateTime(), claim.getEndDateTime()).toMinutes();
        double hours = Math.round((minutes / 60.0) * 100.0) / 100.0;
        String duration = String.format("%.2f hrs", hours);

        return new OTClaimControllerDTO(
                claim.getId(),
                claim.getEmployee().getName(),
                claim.getEmployee().getDepartment(),
                claim.getStartDateTime(),
                claim.getEndDateTime(),
                duration,
                claim.getStatus(),
                claim.getOtDescription());
    }

}
