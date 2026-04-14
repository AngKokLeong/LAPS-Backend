package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubordinateLeaveBalanceControllerDTO implements ControllerDTO {

	private String employeeName;
	private int annualBalance;
	private int medicalBalance;
	private int compensationBalance;
	
	@Override
	public ControllerDTO getAllAttribute() {
		// TODO Auto-generated method stub
		return this;
	}

}
