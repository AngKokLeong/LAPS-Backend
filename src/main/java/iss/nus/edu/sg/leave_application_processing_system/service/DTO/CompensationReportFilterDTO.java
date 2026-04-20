package iss.nus.edu.sg.leave_application_processing_system.service.DTO;

import java.time.LocalDate;

import iss.nus.edu.sg.leave_application_processing_system.persistent.enumeration.OTClaimStatus;

public class CompensationReportFilterDTO implements ServiceDTO {

    private Long managerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long employeeId; // Can be null for all subordinates
    private OTClaimStatus status; // Can be null for all statuses

    public CompensationReportFilterDTO(Long managerId, LocalDate startDate, LocalDate endDate, 
            Long employeeId, OTClaimStatus status) {
        this.managerId = managerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employeeId = employeeId;
        this.status = status;
    }

    @Override
    public ServiceDTO getAllAttribute() {
        return this;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public OTClaimStatus getStatus() {
        return status;
    }

    public void setStatus(OTClaimStatus status) {
        this.status = status;
    }

}
