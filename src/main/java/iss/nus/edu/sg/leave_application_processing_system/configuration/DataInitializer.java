package iss.nus.edu.sg.leave_application_processing_system.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import iss.nus.edu.sg.leave_application_processing_system.helper.Designation;
import iss.nus.edu.sg.leave_application_processing_system.helper.Role;
import iss.nus.edu.sg.leave_application_processing_system.model.Employee;
import iss.nus.edu.sg.leave_application_processing_system.repo.EmployeeRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveEntitlementRepository;

@Component
public class DataInitializer implements CommandLineRunner {


    private EmployeeRepository empRepo;

    private LeaveApplicationRepository leaveRepo;

    private LeaveEntitlementRepository entitlementRepo;
    
    public DataInitializer(EmployeeRepository empRepo, LeaveApplicationRepository leaveRepo,
    		LeaveEntitlementRepository entitlementRepo) {
    	this.empRepo = empRepo;
    	this.leaveRepo = leaveRepo;
    	this.entitlementRepo = entitlementRepo;    	
    }
    
	@Override
	public void run(String... args) throws Exception {
		initializeData();
	}
	
	private void initializeData() {
		
		// Create Sarah's Boss
		Employee boss = new Employee();
		boss.setName("The Boss");
		boss.setEmail("boss@company.com");
		boss.setPassword("any");
		boss.setRole(Role.MANAGER);
		boss.setDesignation(Designation.PROFESSIONAL);
		boss.setDepartment("CEO");
		boss.setStatus("Active");

		empRepo.save(boss);
		
		// Create a Manager
		Employee sarah = new Employee();
		sarah.setName("Sarah Goh");
		sarah.setEmail("sarah@company.com");
		sarah.setPassword("any");
		sarah.setRole(Role.MANAGER);
		sarah.setDesignation(Designation.PROFESSIONAL);
		sarah.setManager(boss);
		sarah.setDepartment("IT");
		sarah.setStatus("Active");

		empRepo.save(sarah);

		// Create Subordinates
		Employee john = new Employee();
		john.setName("John John");
		john.setEmail("john@company.com");
		john.setPassword("any");
		john.setRole(Role.STAFF);
		john.setDesignation(Designation.ADMINISTRATIVE);
		john.setManager(sarah);
		john.setDepartment("IT");
		john.setStatus("Active");

		empRepo.save(john);
		
		Employee jason = new Employee();
		jason.setName("Jason Tang");
		jason.setEmail("jason@company.com");
		jason.setPassword("any");
		jason.setRole(Role.STAFF);
		jason.setDesignation(Designation.PROFESSIONAL);
		jason.setManager(sarah);
		jason.setDepartment("Engineering");
		jason.setStatus("Active");

		empRepo.save(jason);
		
		Employee maria = new Employee();
		maria.setName("Maria Ong");
		maria.setEmail("maria@company.com");
		maria.setPassword("any");
		maria.setRole(Role.STAFF);
		maria.setDesignation(Designation.PROFESSIONAL);
		maria.setManager(sarah);
		maria.setDepartment("Finance");
		maria.setStatus("Active");

		empRepo.save(maria);
		
		// Create admin
		Employee admin = new Employee();
		admin.setName("Kelly the Admin");
		admin.setEmail("admin@company.com");
		admin.setPassword("any");
		admin.setRole(Role.ADMIN);
		admin.setDesignation(Designation.ADMINISTRATIVE);
		admin.setManager(boss);
		admin.setDepartment("HR");
		admin.setStatus("Active");

		empRepo.save(admin);
    }

}
