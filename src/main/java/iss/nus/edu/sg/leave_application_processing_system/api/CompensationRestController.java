package iss.nus.edu.sg.leave_application_processing_system.api;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.nus.edu.sg.leave_application_processing_system.model.Employee;
import iss.nus.edu.sg.leave_application_processing_system.repo.CompensationLedgerRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.EmployeeRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.CompensationService;

@RestController
@RequestMapping("/api/compensation")
public class CompensationRestController {

    private final CompensationService compensationService;
    private final EmployeeRepository employeeRepo;
    private final CompensationLedgerRepository ledgerRepo;

    public CompensationRestController(
            CompensationService compensationService,
            EmployeeRepository employeeRepo,
            CompensationLedgerRepository ledgerRepo) {
        this.compensationService = compensationService;
        this.employeeRepo = employeeRepo;
        this.ledgerRepo = ledgerRepo;
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

}
