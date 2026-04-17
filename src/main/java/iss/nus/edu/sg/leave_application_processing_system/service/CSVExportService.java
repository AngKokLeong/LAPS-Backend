package iss.nus.edu.sg.leave_application_processing_system.service;

import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.CompensationReportResponseDTO;
import iss.nus.edu.sg.leave_application_processing_system.controller.DTO.LeaveReportResponseDTO;

/**
 * Service for generating CSV exports of leave and compensation reports.
 */
@Service
public class CSVExportService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Generate CSV content for leave report.
     * @param leaveReports List of leave report DTOs
     * @return CSV formatted string with headers
     */
    public String generateLeaveReportCSV(List<LeaveReportResponseDTO> leaveReports) {
        StringWriter writer = new StringWriter();

        // Write headers
        writer.append("Employee Name,Employee ID,Department,Leave Type,Start Date,End Date,Days Taken,Reason\n");

        // Write data rows
        for (LeaveReportResponseDTO report : leaveReports) {
            writer.append(escapeCsvField(report.getEmployeeName())).append(",");
            writer.append(report.getEmployeeId().toString()).append(",");
            writer.append(escapeCsvField(report.getDepartment())).append(",");
            writer.append(report.getLeaveType().toString()).append(",");
            writer.append(report.getStartDate().format(DATE_FORMATTER)).append(",");
            writer.append(report.getEndDate().format(DATE_FORMATTER)).append(",");
            writer.append(String.valueOf(report.getDaysTaken())).append(",");
            writer.append(escapeCsvField(report.getReason())).append("\n");
        }

        return writer.toString();
    }

    /**
     * Generate CSV content for compensation/overtime claims report.
     * @param compensationReports List of compensation report DTOs
     * @return CSV formatted string with headers
     */
    public String generateCompensationReportCSV(List<CompensationReportResponseDTO> compensationReports) {
        StringWriter writer = new StringWriter();

        // Write headers
        writer.append("Employee Name,Employee ID,Department,Start DateTime,End DateTime,Hours Worked,Status,Description\n");

        // Write data rows
        for (CompensationReportResponseDTO report : compensationReports) {
            writer.append(escapeCsvField(report.getEmployeeName())).append(",");
            writer.append(report.getEmployeeId().toString()).append(",");
            writer.append(escapeCsvField(report.getDepartment())).append(",");
            writer.append(report.getStartDateTime().format(DATETIME_FORMATTER)).append(",");
            writer.append(report.getEndDateTime().format(DATETIME_FORMATTER)).append(",");
            writer.append(String.format("%.2f", report.getHoursWorked())).append(",");
            writer.append(report.getStatus().toString()).append(",");
            writer.append(escapeCsvField(report.getDescription())).append("\n");
        }

        return writer.toString();
    }

    /**
     * Escape CSV field values according to RFC 4180.
     * Fields containing comma, double quote, or line break are enclosed in double quotes.
     * Any double quotes within the field are escaped with an additional double quote.
     * @param field The field value to escape
     * @return The escaped field value
     */
    private String escapeCsvField(String field) {
        if (field == null) {
            return "";
        }

        // If field contains comma, newline, or quote, wrap in quotes and escape inner quotes
        if (field.contains(",") || field.contains("\n") || field.contains("\"")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }

        return field;
    }

}
