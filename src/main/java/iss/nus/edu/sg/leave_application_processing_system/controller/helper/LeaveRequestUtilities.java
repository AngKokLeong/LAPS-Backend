package iss.nus.edu.sg.leave_application_processing_system.controller.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LeaveRequestUtilities {
    
    public static int CalculateDateDifference(LocalDateTime startDate, LocalDateTime endDate){
        return endDate.getDayOfYear() - startDate.getDayOfYear();
    }

    public static String RetrieveDateDifferenceText(LocalDateTime startDate, LocalDateTime endDate){
        return CalculateDateDifference(startDate, endDate) > 1 ? CalculateDateDifference(startDate, endDate) + " days" : CalculateDateDifference(startDate, endDate) + " day";
    }

    public static String GenerateLeavePeriod(LocalDateTime startDate, LocalDateTime endDate){
        return startDate.getMonth().name() + " " + startDate.getDayOfMonth() + " - " + endDate.getMonth().name() + " " + endDate.getDayOfMonth() + ", " + endDate.getYear();
    }



    public static String GenerateStandardDateFormat(LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern("d MMM uuuu"));
    }

    public static String GenerateLeaveApprovalDate(LocalDateTime leaveApprovedDate){
        return "Approved on " + leaveApprovedDate.format(DateTimeFormatter.ofPattern("d MMM uuuu"));
    }

    public static String GenerateLeaveApprovalStatement(String leaveApprover){
        return "Approved by: " + leaveApprover;
    }

    public static String GenerateLeaveRejectionDate(LocalDateTime leaveRejectionDate){
        return "Rejected on " + leaveRejectionDate.format(DateTimeFormatter.ofPattern("d MMM uuuu"));
    }

    public static String GenerateLeaveRejectionReason(String rejectionReason){
        return "Reason: " + rejectionReason;
    }

    public static String GenerateLeaveCancellationDate(LocalDateTime leaveCancellationDate){
        return "Cancelled on " + leaveCancellationDate.format(DateTimeFormatter.ofPattern("d MMM uuuu"));
    }

    public static String GenerateLeaveCancellationStatement(){
        return "Reason: User cancelled Leave Application";
    }



}
