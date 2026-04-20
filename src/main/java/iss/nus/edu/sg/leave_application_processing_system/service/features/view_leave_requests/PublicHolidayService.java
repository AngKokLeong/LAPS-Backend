package iss.nus.edu.sg.leave_application_processing_system.service.features.view_leave_requests;

import java.util.List;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.model.PublicHoliday;
import iss.nus.edu.sg.leave_application_processing_system.repo.PublicHolidayRepository;

@Service
public class PublicHolidayService {

	private final PublicHolidayRepository phRepo;

	public PublicHolidayService(PublicHolidayRepository phRepo) {
		this.phRepo = phRepo;
	}
	

	 /**
     * Find all public holiday records
     * @return List of Public Holiday Entity class object
     */
	public List<PublicHoliday> findAll() {
		return phRepo.findAll();
	}
}
