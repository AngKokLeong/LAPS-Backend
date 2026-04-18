package iss.nus.edu.sg.leave_application_processing_system.service.DTO;

import lombok.Data;

@Data
public class EmployeeServiceDTO implements ServiceDTO {
	
	private Long id;
	private String name;
	private String email;
    private String department;
    private String role;
    private String status;

    

	public EmployeeServiceDTO() {}

	public EmployeeServiceDTO(Long id, String name, String email, String department, String role, String status) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.department = department;
		this.role = role;
		this.status = status;
	}



	@Override
	public ServiceDTO getAllAttribute() {
		
		return this;
	}
	
	public String getInitials() {
	    if (this.name == null || this.name.trim().isEmpty()) {
	        return "??";
	    }

	    // Split by whitespace and remove empty strings
	    String[] parts = this.name.trim().split("\\s+");

	    if (parts.length == 1) {
	        String singleName = parts[0];
	        // If it's a single name, take the first two letters if possible
	        if (singleName.length() >= 2) {
	            return singleName.substring(0, 2).toUpperCase();
	        }
	        // If it's just one letter, return that letter twice or with a placeholder
	        return (singleName + singleName).toUpperCase();
	    }

	    // For multiple names, take the first letter of the first name 
	    // and the first letter of the LAST name
	    String firstInitial = parts[0].substring(0, 1);
	    String lastInitial = parts[parts.length - 1].substring(0, 1);

	    return (firstInitial + lastInitial).toUpperCase();
	}
	
	public String getAvatarColorClass() {
	    // List of Tailwind color pairs (Background + Text)
	    String[] colors = {
	        "bg-blue-100 text-blue-600",
	        "bg-green-100 text-green-600",
	        "bg-purple-100 text-purple-600",
	        "bg-orange-100 text-orange-600",
	        "bg-pink-100 text-pink-600",
	        "bg-indigo-100 text-indigo-600",
	        "bg-teal-100 text-teal-600"
	    };
	    
	    // Use the name's hash to pick a consistent color index
	    int index = Math.abs(this.name.hashCode()) % colors.length;
	    return colors[index];
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
