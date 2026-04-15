package iss.nus.edu.sg.leave_application_processing_system.configuration;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import iss.nus.edu.sg.leave_application_processing_system.helper.Designation;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;
import iss.nus.edu.sg.leave_application_processing_system.helper.Role;
import iss.nus.edu.sg.leave_application_processing_system.model.Employee;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveEntitlement;
import iss.nus.edu.sg.leave_application_processing_system.model.PublicHoliday;
import iss.nus.edu.sg.leave_application_processing_system.repo.EmployeeRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveEntitlementRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.PublicHolidayRepository;

@Component
public class DataInitializer implements CommandLineRunner {


    private EmployeeRepository empRepo;
    private LeaveApplicationRepository leaveRepo;
    private LeaveEntitlementRepository entitlementRepo;
    private PublicHolidayRepository phRepo;
    
    public DataInitializer(EmployeeRepository empRepo, LeaveApplicationRepository leaveRepo,
    		LeaveEntitlementRepository entitlementRepo, PublicHolidayRepository phRepo) {
    	this.empRepo = empRepo;
    	this.leaveRepo = leaveRepo;
    	this.entitlementRepo = entitlementRepo;  
    	this.phRepo = phRepo;
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
		admin.setManager(sarah);
		admin.setDepartment("HR");
		admin.setStatus("Active");

		empRepo.save(admin);
		
		// Leave Entitlement for Sarah
		LeaveEntitlement sarahAnnual = new LeaveEntitlement();
		sarahAnnual.setEmployeeId(sarah);
		sarahAnnual.setLeaveType(LeaveType.ANNUAL);
		sarahAnnual.setYearApplied(2026);
		sarahAnnual.setTotalDays(18);
		sarahAnnual.setUsedDays(2);
		entitlementRepo.save(sarahAnnual);
		
		LeaveEntitlement sarahMedical = new LeaveEntitlement();
		sarahMedical.setEmployeeId(sarah);
		sarahMedical.setLeaveType(LeaveType.MEDICAL);
		sarahMedical.setYearApplied(2026);
		sarahMedical.setTotalDays(60);
		sarahMedical.setUsedDays(3);
		entitlementRepo.save(sarahMedical);
		
		// --- Entitlements for JOHN ---
		LeaveEntitlement johnAnnual = new LeaveEntitlement();
		johnAnnual.setEmployeeId(john);
		johnAnnual.setLeaveType(LeaveType.ANNUAL);
		johnAnnual.setYearApplied(2026);
		johnAnnual.setTotalDays(14);
		johnAnnual.setUsedDays(1);
		entitlementRepo.save(johnAnnual);

		LeaveEntitlement johnMedical = new LeaveEntitlement();
		johnMedical.setEmployeeId(john);
		johnMedical.setLeaveType(LeaveType.MEDICAL);
		johnMedical.setYearApplied(2026);
		johnMedical.setTotalDays(60);
		johnMedical.setUsedDays(12);
		entitlementRepo.save(johnMedical);

		// --- Entitlements for JASON ---
		LeaveEntitlement jasonAnnual = new LeaveEntitlement();
		jasonAnnual.setEmployeeId(jason);
		jasonAnnual.setLeaveType(LeaveType.ANNUAL);
		jasonAnnual.setYearApplied(2026);
		jasonAnnual.setTotalDays(18);
		jasonAnnual.setUsedDays(5);
		entitlementRepo.save(jasonAnnual);

		LeaveEntitlement jasonMedical = new LeaveEntitlement();
		jasonMedical.setEmployeeId(jason);
		jasonMedical.setLeaveType(LeaveType.MEDICAL);
		jasonMedical.setYearApplied(2026);
		jasonMedical.setTotalDays(60);
		jasonMedical.setUsedDays(3);
		entitlementRepo.save(jasonMedical);

		// --- Entitlements for MARIA ---
		LeaveEntitlement mariaAnnual = new LeaveEntitlement();
		mariaAnnual.setEmployeeId(maria);
		mariaAnnual.setLeaveType(LeaveType.ANNUAL);
		mariaAnnual.setYearApplied(2026);
		mariaAnnual.setTotalDays(18);
		mariaAnnual.setUsedDays(11);
		entitlementRepo.save(mariaAnnual);

		LeaveEntitlement mariaMedical = new LeaveEntitlement();
		mariaMedical.setEmployeeId(maria);
		mariaMedical.setLeaveType(LeaveType.MEDICAL);
		mariaMedical.setYearApplied(2026);
		mariaMedical.setTotalDays(60);
		mariaMedical.setUsedDays(1);
		entitlementRepo.save(mariaMedical);

		// --- Entitlements for ADMIN ---
		LeaveEntitlement adminAnnual = new LeaveEntitlement();
		adminAnnual.setEmployeeId(admin);
		adminAnnual.setLeaveType(LeaveType.ANNUAL);
		adminAnnual.setYearApplied(2026);
		adminAnnual.setTotalDays(14);
		adminAnnual.setUsedDays(4);
		entitlementRepo.save(adminAnnual);

		LeaveEntitlement adminMedical = new LeaveEntitlement();
		adminMedical.setEmployeeId(admin);
		adminMedical.setLeaveType(LeaveType.MEDICAL);
		adminMedical.setYearApplied(2026);
		adminMedical.setTotalDays(60);
		adminMedical.setUsedDays(19);
		entitlementRepo.save(adminMedical);
		

		// Create a Leave Application

		createLeaveApplication(jason, LeaveType.ANNUAL, 5, 3, LeaveStatus.APPLIED, "Family vacation");

		createLeaveApplication(john, LeaveType.MEDICAL, 1, 2, LeaveStatus.APPROVED, "Doctor's appointment");

		createLeaveApplication(maria, LeaveType.ANNUAL, 10, 5, LeaveStatus.REJECTED, "Short handed at work");

		createLeaveApplication(admin, LeaveType.ANNUAL, 15, 2, LeaveStatus.APPLIED, "Personal matters");
		
		createLeaveApplication(john, LeaveType.ANNUAL, 20, 9, LeaveStatus.UPDATED, "Overseas vacation");
		
		// create public holidays
		PublicHoliday ny2026 = new PublicHoliday();
		ny2026.setPhName("New Year's Day");
		ny2026.setPhDate(LocalDate.of(2026, 1, 1));
		phRepo.save(ny2026);

		PublicHoliday cny1 = new PublicHoliday();
		cny1.setPhName("Chinese New Year");
		cny1.setPhDate(LocalDate.of(2026, 2, 17));
		phRepo.save(cny1);

		PublicHoliday cny2 = new PublicHoliday();
		cny2.setPhName("Chinese New Year (Day 2)");
		cny2.setPhDate(LocalDate.of(2026, 2, 18));
		phRepo.save(cny2);

		PublicHoliday hrp = new PublicHoliday();
		hrp.setPhName("Hari Raya Puasa");
		hrp.setPhDate(LocalDate.of(2026, 3, 21));
		phRepo.save(hrp);

		PublicHoliday gf = new PublicHoliday();
		gf.setPhName("Good Friday");
		gf.setPhDate(LocalDate.of(2026, 4, 3));
		phRepo.save(gf);

		PublicHoliday ld = new PublicHoliday();
		ld.setPhName("Labour Day");
		ld.setPhDate(LocalDate.of(2026, 5, 1));
		phRepo.save(ld);

		PublicHoliday hrh = new PublicHoliday();
		hrh.setPhName("Hari Raya Haji");
		hrh.setPhDate(LocalDate.of(2026, 5, 27));
		phRepo.save(hrh);

		// Vesak Day falls on Sunday, May 31
		PublicHoliday vd = new PublicHoliday();
		vd.setPhName("Vesak Day");
		vd.setPhDate(LocalDate.of(2026, 5, 31));
		phRepo.save(vd);

		PublicHoliday vdInLieu = new PublicHoliday();
		vdInLieu.setPhName("Vesak Day (Observed)");
		vdInLieu.setPhDate(LocalDate.of(2026, 6, 1));
		phRepo.save(vdInLieu);

		// National Day falls on Sunday, Aug 9
		PublicHoliday nd = new PublicHoliday();
		nd.setPhName("National Day");
		nd.setPhDate(LocalDate.of(2026, 8, 9));
		phRepo.save(nd);

		PublicHoliday ndInLieu = new PublicHoliday();
		ndInLieu.setPhName("National Day (Observed)");
		ndInLieu.setPhDate(LocalDate.of(2026, 8, 10));
		phRepo.save(ndInLieu);

		// Deepavali falls on Sunday, Nov 8
		PublicHoliday dp = new PublicHoliday();
		dp.setPhName("Deepavali");
		dp.setPhDate(LocalDate.of(2026, 11, 8));
		phRepo.save(dp);

		PublicHoliday dpInLieu = new PublicHoliday();
		dpInLieu.setPhName("Deepavali (Observed)");
		dpInLieu.setPhDate(LocalDate.of(2026, 11, 9));
		phRepo.save(dpInLieu);

		PublicHoliday xm = new PublicHoliday();
		xm.setPhName("Christmas Day");
		xm.setPhDate(LocalDate.of(2026, 12, 25));
		phRepo.save(xm);
    }
	
	private void createLeaveApplication(Employee emp, LeaveType type, int startDaysFromNow, int durationDays, LeaveStatus status, String reason) {
	    LeaveApplication app = new LeaveApplication();
	    app.setEmployee(emp);
	    app.setLeaveType(type);

	    LocalDate start = LocalDate.now().plusDays(startDaysFromNow);
	    LocalDate end = start.plusDays(durationDays - 1); 
	    
	    app.setStartDate(start);
	    app.setEndDate(end);
	    app.setLeaveStatus(status);
	    app.setReason(reason);
	    
	    app.setAppliedDate(LocalDate.now());
	    
	    leaveRepo.save(app);
	}


}
