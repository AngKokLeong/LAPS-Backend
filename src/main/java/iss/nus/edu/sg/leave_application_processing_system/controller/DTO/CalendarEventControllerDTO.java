package iss.nus.edu.sg.leave_application_processing_system.controller.DTO;

import lombok.Data;

@Data
public class CalendarEventControllerDTO implements ControllerDTO {

	private String id;          // The Leave Application ID
    private String title;       // e.g., "Annual Leave - Approved"
    private String start;       // ISO8601 string (e.g., "2023-10-01")
    private String end;         // ISO8601 string (Exclusive end date)
    private String color;       // Hex code or CSS color name
    private String textColor;   // To ensure readability (e.g., "white")
    
    // Optional: Add extra info for tooltips
    private String description;
    
    
	public CalendarEventControllerDTO() {
	}
	
	public CalendarEventControllerDTO(String id, String title, String start, String end, String color, String textColor,
			String description) {
		this.id = id;
		this.title = title;
		this.start = start;
		this.end = end;
		this.color = color;
		this.textColor = textColor;
		this.description = description;
	}


	@Override
	public ControllerDTO getAllAttribute() {
		// TODO Auto-generated method stub
		return this;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
