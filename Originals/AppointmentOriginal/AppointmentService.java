// Christian Claudio
// CS-320
// Module 5 Milestone
// April 4, 2025

package Appointments;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class AppointmentService {
	public static Map<String, Appointment> apptMap = new HashMap<String, Appointment>();
	
	// Ensuring that Appointment ID is < 10 characters and does not already exist
	public boolean checkApptID(String apptID) {
		return apptMap.containsKey(apptID);
	}
	
	public boolean checkIDLength(String apptID) {
		return apptID.length() <= 10;
	}
	
	// Method that adds an appointment
	public boolean addAppt(Appointment appointment) {
		if(appointment == null) {
			return false;
		}
		
		String apptID = appointment.getApptID();
		
		if(!this.checkIDLength(apptID)) {
			return false;
		}
		if(!this.checkApptID(apptID)) {
			apptMap.put(apptID, appointment);
			
			return true;
		}
		
		return false;
	}
	
	// Method that removes an appointment
	public void removeAppt(String apptID) {
		if(apptMap.containsKey(apptID)) {
			apptMap.remove(apptID);
		}
	}
}
