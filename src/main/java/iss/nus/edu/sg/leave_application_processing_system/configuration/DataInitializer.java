package iss.nus.edu.sg.leave_application_processing_system.configuration;

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

@Component
@Profile("local")
public class DataInitializer implements CommandLineRunner {


    private EmployeeRepository empRepo;
    private LeaveApplicationRepository leaveRepo;
    private LeaveEntitlementRepository entitlementRepo;
    private PublicHolidayRepository phRepo;
    private OverTimeClaimRepository otClaimRepo;
    private CompensationLedgerRepository compensationLedgerRepo;
    private PasswordEncoder passwordEncoder;
    
    public DataInitializer(EmployeeRepository empRepo, LeaveApplicationRepository leaveRepo,
    		LeaveEntitlementRepository entitlementRepo, PublicHolidayRepository phRepo,
    		OverTimeClaimRepository otClaimRepo, CompensationLedgerRepository compensationLedgerRepo,
    		PasswordEncoder passwordEncoder) {
    	this.empRepo = empRepo;
    	this.leaveRepo = leaveRepo;
    	this.entitlementRepo = entitlementRepo;  
    	this.phRepo = phRepo;
    	this.otClaimRepo = otClaimRepo;
    	this.compensationLedgerRepo = compensationLedgerRepo;
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

		empRepo.save(darice);

		// create compensation ledger
		int currentYear = Year.now().getValue();
		createCompensationLedger(john, currentYear, 2.0, 0.5, 3.0);
		createCompensationLedger(sarah, currentYear, 3.5, 1.0, 1.5);
		createCompensationLedger(admin, currentYear, 1.5, 0.0, 2.0);

		// Add more compensation ledger
		createCompensationLedger(jason, currentYear, 3.0, 1.5, 1.0);
		createCompensationLedger(maria, currentYear, 3.5, 2.0, 2.5);
		createCompensationLedger(katerine, currentYear, 4.5, 3.5, 1.5);
		createCompensationLedger(darice, currentYear, 1.0, 0.5, 3.5);		
		
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


		// --- JOHN: The "Demo Star" (Heavy April/May Activity) ---
		// Past (April)
		createLeaveApplication(john, johnMedical, -14, 1, LeaveStatus.APPROVED, "Morning Migraine", "");
		createLeaveApplication(john, johnAnnual, -10, 1, LeaveStatus.APPROVED, "Bank appointment", "");
		createLeaveApplication(john, johnAnnual, -5, 1, LeaveStatus.DELETED, "Drafting-Mistake", ""); // Deleted before approval

		// Current/Near Future (April/May)
		createLeaveApplication(john, johnAnnual, 2, 2, LeaveStatus.APPLIED, "Family Dinner", ""); 
		createLeaveApplication(john, johnMedical, 5, 1, LeaveStatus.UPDATED, "Dental Surgery", "Rescheduled from morning to afternoon");
		createLeaveApplication(john, johnAnnual, 12, 1, LeaveStatus.REJECTED, "Friday Off", "Too many people away this day");
		createLeaveApplication(john, johnAnnual, 20, 3, LeaveStatus.CANCELLED, "Short Trip", "Trip postponed by travel agency"); // Cancelled after approval

		// Further Future (May)
		createLeaveApplication(john, johnAnnual, 35, 2, LeaveStatus.APPLIED, "Course Workshop", "");
		createLeaveApplication(john, johnMedical, 40, 1, LeaveStatus.APPLIED, "Regular Checkup", "");


		// --- SARAH (Used: 2 Annual, 3 Medical) ---
		createLeaveApplication(sarah, sarahMedical, -60, 3, LeaveStatus.APPROVED, "Severe Flu", "");
		createLeaveApplication(sarah, sarahAnnual, -15, 2, LeaveStatus.APPROVED, "Birthday Break", "");
		createLeaveApplication(sarah, sarahAnnual, 45, 5, LeaveStatus.APPLIED, "Summer Holiday", "");


		// --- JASON (Used: 5 Annual, 3 Medical) ---
		createLeaveApplication(jason, jasonAnnual, -70, 5, LeaveStatus.APPROVED, "New Year Trip", "");
		createLeaveApplication(jason, jasonMedical, -20, 3, LeaveStatus.APPROVED, "Food Poisoning", "");
		createLeaveApplication(jason, jasonAnnual, 15, 2, LeaveStatus.UPDATED, "Wedding Ceremony", "Changing dates per invite");


		// --- MARIA (Used: 11 Annual, 1 Medical) ---
		createLeaveApplication(maria, mariaAnnual, -100, 5, LeaveStatus.APPROVED, "Winter Vacation", "");
		createLeaveApplication(maria, mariaAnnual, -50, 4, LeaveStatus.APPROVED, "Renovation", "");
		createLeaveApplication(maria, mariaAnnual, -15, 2, LeaveStatus.APPROVED, "Spring Festival", "");
		createLeaveApplication(maria, mariaMedical, -5, 1, LeaveStatus.APPROVED, "Fever", "");
		createLeaveApplication(maria, mariaAnnual, 10, 1, LeaveStatus.REJECTED, "Monday Blues", "Not a valid reason for leave");


		// --- ADMIN (Used: 4 Annual, 19 Medical) ---
		createLeaveApplication(admin, adminMedical, -80, 14, LeaveStatus.APPROVED, "Hospital Stay", "MC Provided");
		createLeaveApplication(admin, adminMedical, -40, 5, LeaveStatus.APPROVED, "Recovery", "");
		createLeaveApplication(admin, adminAnnual, -10, 4, LeaveStatus.APPROVED, "Personal Matters", "");
		createLeaveApplication(admin, adminAnnual, 5, 2, LeaveStatus.CANCELLED, "Urgent Trip", "Meeting rescheduled, no longer need leave");


		// --- LEON (Used: 7 Annual, 30 Medical) ---
		createLeaveApplication(leon, leonMedical, -90, 30, LeaveStatus.APPROVED, "Prolonged Illness", "HR Notified");
		createLeaveApplication(leon, leonAnnual, -20, 7, LeaveStatus.APPROVED, "Mental Health Break", "");
		createLeaveApplication(leon, leonAnnual, 100, 5, LeaveStatus.APPLIED, "Year-end Holiday", "");


		// --- WILLIAM (Used: 9 Annual, 10 Medical) ---
		createLeaveApplication(william, williamMedical, -85, 10, LeaveStatus.APPROVED, "Surgical Procedure", "");
		createLeaveApplication(william, williamAnnual, -30, 9, LeaveStatus.APPROVED, "Moving House", "");
		createLeaveApplication(william, williamAnnual, 60, 3, LeaveStatus.DELETED, "Mistyped dates", "");


		// --- KATERINE (Used: 5 Annual, 15 Medical) ---
		createLeaveApplication(katerine, katerineMedical, -50, 10, LeaveStatus.APPROVED, "Therapy", "");
		createLeaveApplication(katerine, katerineMedical, -15, 5, LeaveStatus.APPROVED, "Chronic Back Pain", "");
		createLeaveApplication(katerine, katerineAnnual, -5, 5, LeaveStatus.APPROVED, "Hiking", "");


		// --- DARICE (Used: 8 Annual, 59 Medical) ---
		createLeaveApplication(darice, dariceMedical, -120, 30, LeaveStatus.APPROVED, "Treatment Phase 1", "");
		createLeaveApplication(darice, dariceMedical, -60, 29, LeaveStatus.APPROVED, "Treatment Phase 2", "");
		createLeaveApplication(darice, dariceAnnual, -25, 8, LeaveStatus.APPROVED, "Respite", "");
		
	
		
		
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
		OverTimeClaim jasonApprovedOT = new OverTimeClaim();
		jasonApprovedOT.setEmployee(jason);
		jasonApprovedOT.setStartDateTime(LocalDateTime.of(2026, 4, 10, 18, 30)); // Last Friday
		jasonApprovedOT.setEndDateTime(LocalDateTime.of(2026, 4, 10, 20, 0));
		jasonApprovedOT.setOtDescription("Emergency server maintenance");
		jasonApprovedOT.setStatus(OTClaimStatus.APPROVED);
		otClaimRepo.save(jasonApprovedOT);

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
		OverTimeClaim williamApprovedOT = new OverTimeClaim();
		williamApprovedOT.setEmployee(william);
		williamApprovedOT.setStartDateTime(LocalDateTime.of(2026, 4, 2, 17, 00)); 
		williamApprovedOT.setEndDateTime(LocalDateTime.of(2026, 4, 2, 20, 0));
		williamApprovedOT.setOtDescription("OT claim");
		williamApprovedOT.setStatus(OTClaimStatus.APPROVED);
		otClaimRepo.save(williamApprovedOT);
		
		OverTimeClaim adminApprovedOT = new OverTimeClaim();
		adminApprovedOT.setEmployee(admin);
		adminApprovedOT.setStartDateTime(LocalDateTime.of(2026, 4, 4, 8, 30)); 
		adminApprovedOT.setEndDateTime(LocalDateTime.of(2026, 4, 4, 12, 0));
		adminApprovedOT.setOtDescription("OT claim");
		adminApprovedOT.setStatus(OTClaimStatus.APPROVED);
		otClaimRepo.save(adminApprovedOT);
		
		OverTimeClaim leonApprovedOT = new OverTimeClaim();
		leonApprovedOT.setEmployee(leon);
		leonApprovedOT.setStartDateTime(LocalDateTime.of(2026, 4, 5, 9, 30)); 
		leonApprovedOT.setEndDateTime(LocalDateTime.of(2026, 4, 5, 13, 0));
		leonApprovedOT.setOtDescription("OT claim");
		leonApprovedOT.setStatus(OTClaimStatus.APPROVED);
		otClaimRepo.save(leonApprovedOT);
		
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
	
	private void createLeaveApplication(Employee emp, LeaveEntitlement LE, int startDaysFromNow, int durationDays, LeaveStatus status, String reason, String mgrRemarks) {
	    LeaveApplication app = new LeaveApplication();
	    app.setEmployee(emp);
	    app.setEntitlement(LE);
	    app.setLeaveType(LE.getLeaveType());

	    LocalDate start = LocalDate.now().plusDays(startDaysFromNow);
	    LocalDate end = start.plusDays(durationDays - 1); 
	    
	    app.setStartDate(start);
	    app.setEndDate(end);
	    app.setLeaveStatus(status);
	    app.setReason(reason);
	    app.setMgrRemarks(mgrRemarks);
	    
	    // Logic: Medical leave is applied 0-1 days before/on start. 
	    // Annual leave is applied 7-30 days before start.
	    int leadTime;

	    if (LE.getLeaveType() == LeaveType.MEDICAL) {
	        leadTime = 0; // Medical leave is usually reported the day of
	    } else if (durationDays > 5) {
	        leadTime = 60; // Big vacations are planned a month in advance
	    } else if (durationDays > 2) {
	        leadTime = 20;
	    } else {
	        leadTime = 7; // Short leaves are planned a week in advance
	    }
	    
	    app.setAppliedDate(start.minusDays(leadTime));
	    
	    leaveRepo.save(app);
	}

	private String encodedPassword(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	private void createCompensationLedger(Employee employee, int yearApplied, double earnedDays, double usedDays,
			double unconvertedHours) {
		CompensationLedger ledger = new CompensationLedger();
		ledger.setEmployee(employee);
		ledger.setYearApplied(yearApplied);
		ledger.setEarnedDays(earnedDays);
		ledger.setUsedDays(usedDays);
		ledger.setUnconvertedHours(unconvertedHours);
		compensationLedgerRepo.save(ledger);
	}


}
