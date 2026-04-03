package iss.nus.edu.sg.leave_application_processing_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iss.nus.edu.sg.leave_application_processing_system.entity.LeaveHistory;

@Repository
public interface LeaveHistoryRepository extends JpaRepository<LeaveHistory, Long>{
}
