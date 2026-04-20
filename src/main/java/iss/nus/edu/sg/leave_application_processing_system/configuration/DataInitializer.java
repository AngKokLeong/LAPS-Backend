package iss.nus.edu.sg.leave_application_processing_system.configuration;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import iss.nus.edu.sg.leave_application_processing_system.helper.Designation;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveStatus;
import iss.nus.edu.sg.leave_application_processing_system.helper.LeaveType;
import iss.nus.edu.sg.leave_application_processing_system.helper.OTClaimStatus;
import iss.nus.edu.sg.leave_application_processing_system.helper.Role;
import iss.nus.edu.sg.leave_application_processing_system.model.Employee;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveApplication;
import iss.nus.edu.sg.leave_application_processing_system.model.CompensationLedger;
import iss.nus.edu.sg.leave_application_processing_system.model.LeaveEntitlement;
import iss.nus.edu.sg.leave_application_processing_system.model.OverTimeClaim;
import iss.nus.edu.sg.leave_application_processing_system.model.PublicHoliday;
import iss.nus.edu.sg.leave_application_processing_system.repo.CompensationLedgerRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.EmployeeRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveApplicationRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.LeaveEntitlementRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.OverTimeClaimRepository;
import iss.nus.edu.sg.leave_application_processing_system.repo.PublicHolidayRepository;
import iss.nus.edu.sg.leave_application_processing_system.service.features.compensation_leave_management.CompensationService;

@Component
public class DataInitializer implements CommandLineRunner {


    private EmployeeRepository empRepo;
    private LeaveApplicationRepository leaveRepo;
    private LeaveEntitlementRepository entitlementRepo;
    private PublicHolidayRepository phRepo;
    private OverTimeClaimRepository otClaimRepo;
    private CompensationLedgerRepository compensationLedgerRepo;
    private CompensationService compensationService;
		private PasswordEncoder passwordEncoder;
    
    public DataInitializer(EmployeeRepository empRepo, LeaveApplicationRepository leaveRepo,
    		LeaveEntitlementRepository entitlementRepo, PublicHolidayRepository phRepo,
    		OverTimeClaimRepository otClaimRepo, CompensationLedgerRepository compensationLedgerRepo, CompensationService compensationService,
    		PasswordEncoder passwordEncoder) {
    	this.empRepo = empRepo;
    	this.leaveRepo = leaveRepo;
    	this.entitlementRepo = entitlementRepo;  
    	this.phRepo = phRepo;
    	this.otClaimRepo = otClaimRepo;
    	this.compensationLedgerRepo = compensationLedgerRepo;
			this.compensationService = compensationService;
    	this.passwordEncoder = passwordEncoder;
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
		boss.setPassword(encodedPassword("any"));
		boss.setRole(Role.MANAGER);
		boss.setDesignation(Designation.PROFESSIONAL);
		boss.setDepartment("CEO");
		boss.setStatus("Active");
		boss.setJoindate(LocalDate.of(2020, 1, 15));

		empRepo.save(boss);
		
		// Create a Manager
		Employee sarah = new Employee();
		sarah.setName("Sarah Goh");
		sarah.setEmail("sarah@company.com");
		sarah.setPassword(encodedPassword("any"));
		sarah.setRole(Role.MANAGER);
		sarah.setDesignation(Designation.PROFESSIONAL);
		sarah.setManager(boss);
		sarah.setDepartment("IT");
		sarah.setStatus("Active");
		sarah.setJoindate(LocalDate.of(2018, 6, 1));

		empRepo.save(sarah);
		
		// Create Subordinates
		Employee john = new Employee();
		john.setName("John John");
		john.setEmail("john@company.com");
		john.setPassword(encodedPassword("any"));
		john.setRole(Role.STAFF);
		john.setDesignation(Designation.ADMINISTRATIVE);
		john.setManager(sarah);
		john.setDepartment("IT");
		john.setStatus("Active");
		john.setJoindate(LocalDate.of(2023, 9, 1));

		empRepo.save(john);
		
		Employee jason = new Employee();
		jason.setName("Jason Tang");
		jason.setEmail("jason@company.com");
		jason.setPassword(encodedPassword("any"));
		jason.setRole(Role.STAFF);
		jason.setDesignation(Designation.PROFESSIONAL);
		jason.setManager(sarah);
		jason.setDepartment("Engineering");
		jason.setStatus("Active");
		jason.setJoindate(LocalDate.of(2015, 1, 1));

		empRepo.save(jason);
		
		Employee maria = new Employee();
		maria.setName("Maria Ong");
		maria.setEmail("maria@company.com");
		maria.setPassword(encodedPassword("any"));
		maria.setRole(Role.STAFF);
		maria.setDesignation(Designation.PROFESSIONAL);
		maria.setManager(sarah);
		maria.setDepartment("Finance");
		maria.setStatus("Active");
		maria.setJoindate(LocalDate.of(2019, 3, 22));

		empRepo.save(maria);

		// Create admin
		Employee admin = new Employee();
		admin.setName("Kelly the Admin");
		admin.setEmail("admin@company.com");
		admin.setPassword(encodedPassword("any"));
		admin.setRole(Role.ADMIN);
		admin.setDesignation(Designation.ADMINISTRATIVE);
		admin.setManager(sarah);
		admin.setDepartment("HR");
		admin.setStatus("Active");
		admin.setJoindate(LocalDate.of(2023, 5, 2));

		empRepo.save(admin);
		
		// Add more employees

		Employee leon = new Employee();
		leon.setName("Leon Biddulph");
		leon.setEmail("leon@company.com");
		leon.setPassword(encodedPassword("any"));
		leon.setRole(Role.MANAGER);
		leon.setDesignation(Designation.ADMINISTRATIVE);
		leon.setManager(boss);
		leon.setDepartment("HR");
		leon.setStatus("Active");
		leon.setJoindate(LocalDate.of(2008, 8, 12));

		empRepo.save(leon);
		
		Employee william = new Employee();
		william.setName("William Attrill");
		william.setEmail("william@company.com");
		william.setPassword(encodedPassword("any"));
		william.setRole(Role.MANAGER);
		william.setDesignation(Designation.PROFESSIONAL);
		william.setManager(boss);
		william.setDepartment("Finance");
		william.setStatus("Active");
		william.setJoindate(LocalDate.of(2004, 10, 1));

		empRepo.save(william);
		
		Employee katerine = new Employee();
		katerine.setName("Katerine Crumpe");
		katerine.setEmail("katerine@company.com");
		katerine.setPassword(encodedPassword("any"));
		katerine.setRole(Role.STAFF);
		katerine.setDesignation(Designation.PROFESSIONAL);
		katerine.setManager(leon);
		katerine.setDepartment("HR");
		katerine.setStatus("Active");
		katerine.setJoindate(LocalDate.of(2021, 3, 3));

		empRepo.save(katerine);
		
		Employee darice = new Employee();
		darice.setName("Darice Easthope");
		darice.setEmail("darice@company.com");
		darice.setPassword(encodedPassword("any"));
		darice.setRole(Role.STAFF);
		darice.setDesignation(Designation.PROFESSIONAL);
		darice.setManager(leon);
		darice.setDepartment("Teaching");
		darice.setStatus("Active");
		darice.setJoindate(LocalDate.of(2017, 1, 2));

		empRepo.save(darice);

		// create compensation ledger
		int currentYear = Year.now().getValue();
		CompensationLedger johnLedger = createCompensationLedger(john, currentYear, 2.0, 0.5, 3.0);
		CompensationLedger sarahLedger = createCompensationLedger(sarah, currentYear, 3.5, 1.0, 1.5);
		CompensationLedger adminLedger = createCompensationLedger(admin, currentYear, 1.5, 0.0, 2.0);

		// Add more compensation ledger
		CompensationLedger jasonLedger = createCompensationLedger(jason, currentYear, 3.0, 1.5, 1.0);
		CompensationLedger mariaLedger = createCompensationLedger(maria, currentYear, 3.5, 2.0, 2.5);
		CompensationLedger katerineLedger = createCompensationLedger(katerine, currentYear, 4.5, 3.5, 1.5);
		CompensationLedger dariceLedger = createCompensationLedger(darice, currentYear, 1.0, 0.5, 3.5);		
		
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
		johnAnnual.setUsedDays(4);
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
		
		// --- Entitlements for LEON ---
		LeaveEntitlement leonAnnual = new LeaveEntitlement();
		leonAnnual.setEmployeeId(leon);
		leonAnnual.setLeaveType(LeaveType.ANNUAL);
		leonAnnual.setYearApplied(2026);
		leonAnnual.setTotalDays(21);
		leonAnnual.setUsedDays(7);
		entitlementRepo.save(leonAnnual);

		LeaveEntitlement leonMedical = new LeaveEntitlement();
		leonMedical.setEmployeeId(leon);
		leonMedical.setLeaveType(LeaveType.MEDICAL);
		leonMedical.setYearApplied(2026);
		leonMedical.setTotalDays(60);
		leonMedical.setUsedDays(30);
		entitlementRepo.save(leonMedical);
		
		// --- Entitlements for WILLIAM ---
		LeaveEntitlement williamAnnual = new LeaveEntitlement();
		williamAnnual.setEmployeeId(william);
		williamAnnual.setLeaveType(LeaveType.ANNUAL);
		williamAnnual.setYearApplied(2026);
		williamAnnual.setTotalDays(21);
		williamAnnual.setUsedDays(9);
		entitlementRepo.save(williamAnnual);

		LeaveEntitlement williamMedical = new LeaveEntitlement();
		williamMedical.setEmployeeId(william);
		williamMedical.setLeaveType(LeaveType.MEDICAL);
		williamMedical.setYearApplied(2026);
		williamMedical.setTotalDays(60);
		williamMedical.setUsedDays(10);
		entitlementRepo.save(williamMedical);
		
		// --- Entitlements for KATERINE ---
		LeaveEntitlement katerineAnnual = new LeaveEntitlement();
		katerineAnnual.setEmployeeId(katerine);
		katerineAnnual.setLeaveType(LeaveType.ANNUAL);
		katerineAnnual.setYearApplied(2026);
		katerineAnnual.setTotalDays(18);
		katerineAnnual.setUsedDays(5);
		entitlementRepo.save(katerineAnnual);

		LeaveEntitlement katerineMedical = new LeaveEntitlement();
		katerineMedical.setEmployeeId(katerine);
		katerineMedical.setLeaveType(LeaveType.MEDICAL);
		katerineMedical.setYearApplied(2026);
		katerineMedical.setTotalDays(60);
		katerineMedical.setUsedDays(15);
		entitlementRepo.save(katerineMedical);
		
		// --- Entitlements for DARICE ---
		LeaveEntitlement dariceAnnual = new LeaveEntitlement();
		dariceAnnual.setEmployeeId(darice);
		dariceAnnual.setLeaveType(LeaveType.ANNUAL);
		dariceAnnual.setYearApplied(2026);
		dariceAnnual.setTotalDays(18);
		dariceAnnual.setUsedDays(8);
		entitlementRepo.save(dariceAnnual);

		LeaveEntitlement dariceMedical = new LeaveEntitlement();
		dariceMedical.setEmployeeId(darice);
		dariceMedical.setLeaveType(LeaveType.MEDICAL);
		dariceMedical.setYearApplied(2026);
		dariceMedical.setTotalDays(60);
		dariceMedical.setUsedDays(59);
		entitlementRepo.save(dariceMedical);		
		
		// Create a Leave Application


		// Current Date: April 18, 2026
		// Signature: (Employee, Entitlement, Ledger, startDays, duration, Status, Reason, Remarks)

		// --- JOHN: The "Demo Star" (Mix of Annual, Medical, and Compensation) ---
		// Past (Annual & Medical)
		createLeaveApplication(john, johnMedical, null, "2026-04-06", 1, false, LeaveStatus.APPROVED, "Morning Migraine", "");
		createLeaveApplication(john, johnAnnual, null, "2026-04-08", 1, false, LeaveStatus.APPROVED, "Bank appointment", "");
		createLeaveApplication(john, johnAnnual, null, "2026-04-20", 1, false, LeaveStatus.REJECTED, "Short Getaway", "Department peak period - all hands on deck for project milestone.");

		// NEW: April Compensation Leave (Using those 2.0 earned days)
		createLeaveApplication(john, null, johnLedger, "2026-04-16", 1, true, LeaveStatus.APPROVED, "Compensatory rest for weekend deployment", "");

		// Future (April/May)
		createLeaveApplication(john, johnAnnual, null, "2026-04-22", 2, false, LeaveStatus.APPROVED, "Family Dinner", ""); 
		createLeaveApplication(john, johnMedical, null, "2026-04-28", 1, false, LeaveStatus.UPDATED, "Dental Surgery", "Rescheduled");
		createLeaveApplication(john, johnAnnual, null, "2026-05-04", 1, false, LeaveStatus.APPROVED, "Secret", "");
		createLeaveApplication(john, johnAnnual, null, "2026-05-18", 1, false, LeaveStatus.CANCELLED, "Just want to rest", "Plan changed");

		// NEW: May Compensation Leave
		createLeaveApplication(john, null, johnLedger, "2026-05-29", 1, true, LeaveStatus.APPLIED, "Friday afternoon off", ""); // Using the 0.5 usage logic


		// --- SARAH (Used: 2 Annual, 3 Medical, 1.0 Comp) ---
		createLeaveApplication(sarah, sarahMedical, null, "2026-02-16", 3, false, LeaveStatus.APPROVED, "Severe Flu", "");
		createLeaveApplication(sarah, null, sarahLedger, "2026-03-18", 1, false, LeaveStatus.APPROVED, "Time off for extra shift", "");
		createLeaveApplication(sarah, sarahAnnual, null, "2026-04-01", 2, false, LeaveStatus.APPROVED, "Birthday Break", "");


		// --- JASON (Used: 5 Annual, 3 Medical, 1.5 Comp) ---
		createLeaveApplication(jason, jasonAnnual, null, "2026-02-02", 5, false, LeaveStatus.APPROVED, "New Year Trip", "");
		createLeaveApplication(jason, null, jasonLedger, "2026-03-10", 2, true, LeaveStatus.APPROVED, "OT Compensation", "");
		createLeaveApplication(jason, jasonMedical, null, "2026-03-30", 3, false, LeaveStatus.APPROVED, "Food Poisoning", "");


		// --- MARIA (Used: 11 Annual, 1 Medical, 2.0 Comp) ---
		createLeaveApplication(maria, mariaAnnual, null, "2026-01-12", 5, false, LeaveStatus.APPROVED, "Winter Vacation", "");
		createLeaveApplication(maria, null, mariaLedger, "2026-02-09", 2, false, LeaveStatus.APPROVED, "Comp leave from Q1 crunch", "");
		createLeaveApplication(maria, mariaAnnual, null, "2026-03-02", 4, false, LeaveStatus.APPROVED, "Renovation", "");
		createLeaveApplication(maria, mariaMedical, null, "2026-04-13", 1, false, LeaveStatus.APPROVED, "Fever", "");


		// --- ADMIN (Used: 4 Annual, 19 Medical, 0 Comp) ---
		createLeaveApplication(admin, adminMedical, null, "2026-01-19", 14, false, LeaveStatus.APPROVED, "Hospital Stay", "");
		createLeaveApplication(admin, adminAnnual, null, "2026-04-06", 4, false, LeaveStatus.APPROVED, "Personal Matters", "");
		createLeaveApplication(admin, null, adminLedger, "2026-05-11", 1, false, LeaveStatus.REJECTED, "Claiming OT", "Ledger balance is 0.0 used, but keep for record");


		// --- LEON (No Ledger - Pure Entitlement) ---
		createLeaveApplication(leon, leonMedical, null, "2026-01-12", 30, false, LeaveStatus.APPROVED, "Prolonged Illness", "");
		createLeaveApplication(leon, leonAnnual, null, "2026-03-30", 7, false, LeaveStatus.APPROVED, "Mental Health Break", "");


		// --- WILLIAM (No Ledger - Pure Entitlement) ---
		createLeaveApplication(william, williamMedical, null, "2026-01-26", 10, false, LeaveStatus.APPROVED, "Surgical Procedure", "");
		createLeaveApplication(william, williamAnnual, null, "2026-03-16", 9, false, LeaveStatus.APPROVED, "Moving House", "");


		// --- KATERINE (Used: 5 Annual, 15 Medical, 3.5 Comp) ---
		createLeaveApplication(katerine, katerineMedical, null, "2026-02-16", 10, false, LeaveStatus.APPROVED, "Therapy", "");
		createLeaveApplication(katerine, null, katerineLedger, "2026-03-16", 2, true, LeaveStatus.APPROVED, "Consolidated OT claim", "");
		createLeaveApplication(katerine, katerineAnnual, null, "2026-04-13", 5, false, LeaveStatus.APPROVED, "Hiking", "");


		// --- DARICE (Used: 8 Annual, 59 Medical, 0.5 Comp) ---
		createLeaveApplication(darice, dariceMedical, null, "2026-01-05", 30, false, LeaveStatus.APPROVED, "Treatment Phase 1", "");
		createLeaveApplication(darice, null, dariceLedger, "2026-02-16", 1, true, LeaveStatus.APPROVED, "Medical follow-up (Comp)", "");
		createLeaveApplication(darice, dariceMedical, null, "2026-03-02", 29, false, LeaveStatus.APPROVED, "Treatment Phase 2", "");
		createLeaveApplication(darice, dariceAnnual, null, "2026-04-01", 8, false, LeaveStatus.APPROVED, "Respite", "");
		
	
		
		
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
		
		
		// --- OT Claims for JASON ---

		// 1. A Pending Claim (Needs Manager Approval)
		OverTimeClaim jasonPendingOT = new OverTimeClaim();
		jasonPendingOT.setEmployee(jason);
		jasonPendingOT.setStartDateTime(LocalDateTime.of(2026, 4, 14, 18, 0));
		jasonPendingOT.setEndDateTime(LocalDateTime.of(2026, 4, 14, 22, 30));   
		jasonPendingOT.setOtDescription("Finalizing GetFreshFood documentation");
		jasonPendingOT.setStatus(OTClaimStatus.PENDING);
		otClaimRepo.save(jasonPendingOT);

		// 2. An already Approved Claim
		seedApprovedOT(
    jason,
    LocalDateTime.of(2026, 4, 10, 18, 30),
    LocalDateTime.of(2026, 4, 10, 20, 0),
    "Emergency server maintenance"
		);


		// --- OT Claim for MARIA ---

		// 3. A Rejected Claim
		OverTimeClaim mariaRejectedOT = new OverTimeClaim();
		mariaRejectedOT.setEmployee(maria);
		mariaRejectedOT.setStartDateTime(LocalDateTime.of(2026, 4, 13, 17, 30)); 
		mariaRejectedOT.setEndDateTime(LocalDateTime.of(2026, 4, 13, 19, 30));
		mariaRejectedOT.setOtDescription("General admin work");
		mariaRejectedOT.setStatus(OTClaimStatus.REJECTED);
		otClaimRepo.save(mariaRejectedOT);
		
		// Add more pending OT claims
		OverTimeClaim jasonPendingOT2 = new OverTimeClaim();
		jasonPendingOT2.setEmployee(jason);
		jasonPendingOT2.setStartDateTime(LocalDateTime.of(2026, 3, 14, 18, 0));
		jasonPendingOT2.setEndDateTime(LocalDateTime.of(2026, 3, 14, 22, 30));   
		jasonPendingOT2.setOtDescription("OT claim");
		jasonPendingOT2.setStatus(OTClaimStatus.PENDING);
		otClaimRepo.save(jasonPendingOT2);	

		OverTimeClaim leonPendingOT = new OverTimeClaim();
		leonPendingOT.setEmployee(leon);
		leonPendingOT.setStartDateTime(LocalDateTime.of(2026, 4, 24, 18, 0));
		leonPendingOT.setEndDateTime(LocalDateTime.of(2026, 4, 24, 22, 30));   
		leonPendingOT.setOtDescription("OT claim");
		leonPendingOT.setStatus(OTClaimStatus.PENDING);
		otClaimRepo.save(leonPendingOT);
		
		OverTimeClaim daricePendingOT = new OverTimeClaim();
		daricePendingOT.setEmployee(darice);
		daricePendingOT.setStartDateTime(LocalDateTime.of(2026, 4, 21, 14, 0));
		daricePendingOT.setEndDateTime(LocalDateTime.of(2026, 4, 21, 16, 30));   
		daricePendingOT.setOtDescription("OT claim");
		daricePendingOT.setStatus(OTClaimStatus.PENDING);
		otClaimRepo.save(daricePendingOT);
		
		// Add more approved OT claims
		seedApprovedOT(
    william,
    LocalDateTime.of(2026, 4, 2, 17, 0),
    LocalDateTime.of(2026, 4, 2, 20, 0),
    "OT claim"
		);
		
		seedApprovedOT(
    admin,
    LocalDateTime.of(2026, 4, 2, 8, 30),
    LocalDateTime.of(2026, 4, 2, 12, 0),
    "OT claim"
		);

		seedApprovedOT(
    leon,
    LocalDateTime.of(2026, 4, 5, 9, 30),
    LocalDateTime.of(2026, 4, 5, 13, 0),
    "OT claim"
		);
		
		// Add more rejected OT claims
		OverTimeClaim katerineRejectedOT = new OverTimeClaim();
		katerineRejectedOT.setEmployee(katerine);
		katerineRejectedOT.setStartDateTime(LocalDateTime.of(2026, 4, 12, 11, 30)); 
		katerineRejectedOT.setEndDateTime(LocalDateTime.of(2026, 4, 12, 16, 30));
		katerineRejectedOT.setOtDescription("OT claim");
		katerineRejectedOT.setStatus(OTClaimStatus.REJECTED);
		otClaimRepo.save(katerineRejectedOT);
		
		OverTimeClaim dariceRejectedOT = new OverTimeClaim();
		dariceRejectedOT.setEmployee(darice);
		dariceRejectedOT.setStartDateTime(LocalDateTime.of(2026, 4, 14, 15, 30)); 
		dariceRejectedOT.setEndDateTime(LocalDateTime.of(2026, 4, 14, 20, 30));
		dariceRejectedOT.setOtDescription("OT claim");
		dariceRejectedOT.setStatus(OTClaimStatus.REJECTED);
		otClaimRepo.save(dariceRejectedOT);
		
		OverTimeClaim leonRejectedOT = new OverTimeClaim();
		leonRejectedOT.setEmployee(leon);
		leonRejectedOT.setStartDateTime(LocalDateTime.of(2026, 1, 15, 12, 30)); 
		leonRejectedOT.setEndDateTime(LocalDateTime.of(2026, 1, 15, 20, 30));
		leonRejectedOT.setOtDescription("OT claim");
		leonRejectedOT.setStatus(OTClaimStatus.REJECTED);
		otClaimRepo.save(leonRejectedOT);
		
    }
	
	private void createLeaveApplication(Employee emp, LeaveEntitlement LE,
			CompensationLedger cl, String startDateStr, int durationDays,
			boolean isHalfDay, LeaveStatus status, String reason, String mgrRemarks) {
	    LeaveApplication app = new LeaveApplication();
	    app.setEmployee(emp);
	    app.setEntitlement(LE);
	    app.setLedger(cl);
	    
	    // SAFE TYPE CHECK: If LE is null, default to COMPENSATION
	    LeaveType type = (LE != null) ? LE.getLeaveType() : LeaveType.COMPENSATION;
	    app.setLeaveType(type);

	    LocalDate start = LocalDate.parse(startDateStr);

	    LocalDate end = start.plusDays(durationDays - 1);
	    
	    app.setStartDate(start);
	    app.setEndDate(end);
	    app.setLeaveStatus(status);
	    app.setReason(reason);
	    app.setMgrRemarks(mgrRemarks);
	    
	    if (type == LeaveType.COMPENSATION) {
	    	app.setHalfDay(isHalfDay);
	    } else {
	    	app.setHalfDay(false);
	    }

		if (status == LeaveStatus.APPROVED){
			app.setUpdatedDate(LocalDate.now());
		}
	    
	    
	    // Logic: Medical leave is applied 0-1 days before/on start. 
	    // Annual leave is applied 7-30 days before start.
	    int leadTime;
	    if (type == LeaveType.MEDICAL) {
	        leadTime = 0; 
	    } else if (durationDays > 5) {
	        leadTime = 60; 
	    } else if (durationDays > 2) {
	        leadTime = 20;
	    } else {
	        leadTime = 7; 
	    }
	    
	    app.setAppliedDate(start.minusDays(leadTime));
	    
	    leaveRepo.save(app);
	}

	private String encodedPassword(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	private CompensationLedger createCompensationLedger(Employee employee, int yearApplied, double earnedDays, double usedDays,
			double unconvertedHours) {
		CompensationLedger ledger = new CompensationLedger();
		ledger.setEmployee(employee);
		ledger.setYearApplied(yearApplied);
		ledger.setEarnedDays(earnedDays);
		ledger.setUsedDays(usedDays);
		ledger.setUnconvertedHours(unconvertedHours);
		return compensationLedgerRepo.save(ledger);
		
	}

	// Helper method to seed approved OT claims and update compensation ledger accordingly
	private void seedApprovedOT(
    Employee emp,
    LocalDateTime start,
    LocalDateTime end,
    String description
		) {
    OverTimeClaim claim = new OverTimeClaim();
    claim.setEmployee(emp);
    claim.setStartDateTime(start);
    claim.setEndDateTime(end);
    claim.setOtDescription(description);
    claim.setStatus(OTClaimStatus.APPROVED);

    otClaimRepo.save(claim);

    double hours =
        Duration.between(start, end).toMinutes() / 60.0;

    compensationService.addOvertimeHours(
        emp.getId(),
        Year.now().getValue(),
        hours
    	);
		}
}
