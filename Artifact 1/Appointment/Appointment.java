// Christian Claudio
// CS-320
// Module 5 Milestone
// April 4, 2025

package Appointments;

import java.util.Date;

public class Appointment {
	private String apptID;
	private Date apptDate;
	private String apptSummary;
	
	// Validate the Appointment ID to ensure it meets given standards
	private boolean validateApptID(String apptID) {
		if(apptID == null || apptID.equals("") || apptID.length() > 10) {
			return false;
		}
		
		return true;
	}
	
	// Validate the Appointment Date to ensure it meets given standards
	private boolean validateApptDate(Date apptDate) {
		if(apptDate == null || apptDate.before(new Date())) {
			return false;
		}
		
		return true;
	}

	// Validate the Appointment Summary to ensure it meets given standards
	private boolean validateApptSummary(String apptSummary) {
		if(apptSummary == null || apptSummary.equals("") || apptSummary.length() > 50) {
			return false;
		}
		return true;
	}
	
	// Constructor
	public Appointment(String apptID, Date apptDate, String apptSummary) {
		if(!this.validateApptID(apptID)) {
			throw new IllegalArgumentException("Error: Invalid Appointment ID");
		}
		
		if(!this.validateApptDate(apptDate)) {
			throw new IllegalArgumentException("Error: Invalid Appointment Date");
		}
		
		if(!this.validateApptSummary(apptSummary)) {
			throw new IllegalArgumentException("Error: Invalid Appointment Summary");
		}
		
		setApptID(apptID);
		setApptDate(apptDate);
		setApptSummary(apptSummary);
	}
	
	// Mutators (Getters and Setters)
	public String getApptID() {
		return apptID;
	}
	
	public void setApptID(String apptID) {
		if(!this.validateApptID(apptID)) {
			throw new IllegalArgumentException("Error: Invalid Appointment ID");
		}
		
		this.apptID = apptID;
	}
	
	public Date getApptDate() {
		return apptDate;
	}
	
	public void setApptDate(Date apptDate) {
		if(!this.validateApptDate(apptDate)) {
			throw new IllegalArgumentException("Error: Invalid Appointment Date");
		}
		
		this.apptDate = apptDate;
	}
	
	public String getApptSummary() {
		return apptSummary;
	}
	
	public void setApptSummary(String apptSummary) {
		if(!this.validateApptSummary(apptSummary)) {
			throw new IllegalArgumentException("Error: Invalid Appointment Summary");
		}
		
		this.apptSummary = apptSummary;
	}
}
