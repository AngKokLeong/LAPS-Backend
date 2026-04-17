package iss.nus.edu.sg.leave_application_processing_system.service.DTO;

import java.time.LocalDate;

public class AddEmployeeServiceDTO implements ServiceDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String designation;
    private String department;
    private LocalDate joinDate;
    private Long managerId;
    private String status;
    private String role;

    public AddEmployeeServiceDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }

    public Long getManagerId() { return managerId; }
    public void setManagerId(Long managerId) { this.managerId = managerId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public ServiceDTO getAllAttribute() {
        return this;
    }
}
