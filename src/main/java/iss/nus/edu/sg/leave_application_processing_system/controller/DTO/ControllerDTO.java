package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ControllerDTO {
    @JsonIgnore
    ControllerDTO getAllAttribute();
}
