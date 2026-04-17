package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import java.util.List;

public class DashboardDTO {
    private String userName;
    private int totalLeaveDays;
    private int daysUsed;
    private int pendingRequests;
    private int daysRemaining;
    private List<LeaveSummaryDTO> recentLeaves;

    public DashboardDTO(String userName, int totalLeaveDays, int daysUsed, int pendingRequests, int daysRemaining, List<LeaveSummaryDTO> recentLeaves) {
        this.userName = userName;
        this.totalLeaveDays = totalLeaveDays;
        this.daysUsed = daysUsed;
        this.pendingRequests = pendingRequests;
        this.daysRemaining = daysRemaining;
        this.recentLeaves = recentLeaves;
    }

    // Getters
    public String getUserName() { return userName; }
    public int getTotalLeaveDays() { return totalLeaveDays; }
    public int getDaysUsed() { return daysUsed; }
    public int getPendingRequests() { return pendingRequests; }
    public int getDaysRemaining() { return daysRemaining; }
    public List<LeaveSummaryDTO> getRecentLeaves() { return recentLeaves; }

    public static class LeaveSummaryDTO {
        private String type;
        private String startDate;
        private String endDate;
        private int days;
        private String status;

        public LeaveSummaryDTO(String type, String startDate, String endDate, int days, String status) {
            this.type = type;
            this.startDate = startDate;
            this.endDate = endDate;
            this.days = days;
            this.status = status;
        }

        // Getters
        public String getType() { return type; }
        public String getStartDate() { return startDate; }
        public String getEndDate() { return endDate; }
        public int getDays() { return days; }
        public String getStatus() { return status; }
    }
}