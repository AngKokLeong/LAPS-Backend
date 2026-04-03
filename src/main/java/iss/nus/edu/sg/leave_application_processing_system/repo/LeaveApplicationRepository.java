package iss.nus.edu.sg.leave_application_processing_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {

}
