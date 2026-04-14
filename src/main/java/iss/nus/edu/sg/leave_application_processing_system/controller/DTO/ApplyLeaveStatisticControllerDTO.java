package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

public class ApplyLeaveStatisticControllerDTO implements ControllerDTO{
    
    private int numberOfAnnualLeave;
    private int numberOfMedicalLeave;
    private int numberOfCompensationLeave;
    
    public ApplyLeaveStatisticControllerDTO() {}

    public ApplyLeaveStatisticControllerDTO(int numberOfAnnualLeave, int numberOfMedicalLeave, int numberOfCompensationLeave) {
        this.numberOfAnnualLeave = numberOfAnnualLeave;
        this.numberOfMedicalLeave = numberOfMedicalLeave;
        this.numberOfCompensationLeave = numberOfCompensationLeave;
    }

    public int getNumberOfAnnualLeave() {
        return numberOfAnnualLeave;
    }

    public void setNumberOfAnnualLeave(int numberOfAnnualLeave) {
        this.numberOfAnnualLeave = numberOfAnnualLeave;
    }

    public int getNumberOfMedicalLeave() {
        return numberOfMedicalLeave;
    }

    public void setNumberOfMedicalLeave(int numberOfMedicalLeave) {
        this.numberOfMedicalLeave = numberOfMedicalLeave;
    }

    public int getNumberOfCompensationLeave() {
        return numberOfCompensationLeave;
    }

    public void setNumberOfCompensationLeave(int numberOfCompensationLeave) {
        this.numberOfCompensationLeave = numberOfCompensationLeave;
    }

    @Override
    public ControllerDTO getAllAttribute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllAttribute'");
    }

    

    

}
