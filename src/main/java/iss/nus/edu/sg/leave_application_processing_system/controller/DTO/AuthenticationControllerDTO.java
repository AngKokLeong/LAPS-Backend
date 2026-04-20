package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import iss.nus.edu.sg.leave_application_processing_system.persistent.enumeration.Role;

public class AuthenticationControllerDTO implements ControllerDTO{

    private Long staffId;
    private String email;
    private String password;
    private Role role;


    public AuthenticationControllerDTO() {}


    public AuthenticationControllerDTO(String email, String password) {
        this.email = email;
        this.password = password;
        
    }

    
    public AuthenticationControllerDTO(Long staffId, String email, Role role) {
        this.staffId = staffId;
        this.email = email;
        this.role = role;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public Long getStaffId() {
        return staffId;
    }


    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }



    public Role getRole() {
        return role;
    }


    public void setRole(Role role) {
        this.role = role;
    }
    
    @Override
    public ControllerDTO getAllAttribute() {
        return this;
    }

}
