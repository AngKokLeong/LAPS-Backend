package iss.nus.edu.sg.leave_application_processing_system.service.DTO;

public class AuthenticationServiceDTO implements ServiceDTO{

    private String email;
    private String password;

    public AuthenticationServiceDTO() {}
    
    
    public AuthenticationServiceDTO(String email, String password) {
        this.email = email;
        this.password = password;
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


    @Override
    public ServiceDTO getAllAttribute() {
        return this;
    }
    
}
