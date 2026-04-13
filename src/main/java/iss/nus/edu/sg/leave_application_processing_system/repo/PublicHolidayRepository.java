package iss.nus.edu.sg.leave_application_processing_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iss.nus.edu.sg.leave_application_processing_system.model.PublicHoliday;
import java.util.Optional;
import java.time.LocalDate;


@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Long> {
  Optional<PublicHoliday> findByPhDate(LocalDate phDate);

}
