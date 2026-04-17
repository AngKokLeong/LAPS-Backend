package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

public class ApiErrorResponse {
    private final String message;

    public ApiErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
