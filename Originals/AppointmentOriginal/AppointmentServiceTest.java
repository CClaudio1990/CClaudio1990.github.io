package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;

import Appointments.Appointment;
import Appointments.AppointmentService;

class AppointmentServiceTest {
	
	@AfterEach
	void tearDown() throws Exception {
		AppointmentService.apptMap.clear();
	}

	@DisplayName("Testing adding an Appointment")
	@Test
	void testAddAppt() {
		String testID = "1000";
		Calendar testDate = Calendar.getInstance();
		String testSummary = "Good Appointment Description";
		
		testDate.set(Calendar.MONTH, Calendar.APRIL);
		testDate.set(Calendar.DATE, 10);
		testDate.set(Calendar.YEAR, 2028);

		Date validDate = testDate.getTime();

		AppointmentService testAppt = new AppointmentService();
		Appointment testAppointment = new Appointment(testID, validDate, testSummary);

		assertEquals(0, AppointmentService.apptMap.size());

		testAppt.addAppt(testAppointment);

		assertTrue(AppointmentService.apptMap.containsKey(testID));
		assertEquals(validDate, AppointmentService.apptMap.get(testID).getApptDate());
		assertEquals(testSummary, AppointmentService.apptMap.get(testID).getApptSummary());
	}

	@DisplayName("Testing adding an Appointment with long summary")
	@Test
	void testAddLongSummary() {
		String testID = "1000";
		Calendar testDate = Calendar.getInstance();
		String testSummary = "This summary is no good because I am typing too many characters and blabbing";
		
		testDate.set(Calendar.MONTH, Calendar.APRIL);
		testDate.set(Calendar.DATE, 10);
		testDate.set(Calendar.YEAR, 2028);

		Date validDate = testDate.getTime();

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {new Appointment(testID, validDate, testSummary);});
      	
      	assertEquals("Error: Invalid Appointment Summary", exception.getMessage());
	}
	
	@DisplayName("Testing adding an Appointment with Null Summary")
	@Test
	void testAddNullSummary() {
		String testID = "1000";
		Calendar testDate = Calendar.getInstance();
		String testSummary = null;
		
		testDate.set(Calendar.MONTH, Calendar.APRIL);
		testDate.set(Calendar.DATE, 10);
		testDate.set(Calendar.YEAR, 2028);

		Date validDate = testDate.getTime();

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {new Appointment(testID, validDate, testSummary);});
      	
      	assertEquals("Error: Invalid Appointment Summary", exception.getMessage());
	}
	
	@DisplayName("Test Removing an Appointment")
	@Test
	void testRemoveAppt() {
		String testID1 = "1000";
		String testID2 = "1001";
		String testID3 = "1002";
		Calendar testDate = Calendar.getInstance();
		String testSummary = "Good Appointment Summary";

		testDate.set(Calendar.MONTH, Calendar.APRIL);
		testDate.set(Calendar.DATE, 10);
		testDate.set(Calendar.YEAR, 2028);

		Date validDate = testDate.getTime();

		AppointmentService testAppt = new AppointmentService();

		assertEquals(0, AppointmentService.apptMap.size());

		// Adding 3 Appointments
		testAppt.addAppt(new Appointment(testID1, validDate, testSummary));
		testAppt.addAppt(new Appointment(testID2, validDate, testSummary));
		testAppt.addAppt(new Appointment(testID3, validDate, testSummary));

		// Make sure 3 were added
		assertEquals(3, AppointmentService.apptMap.size());

		// Remove Appointment
		testAppt.removeAppt("1000");

		// Ensure Appointment was removed
		assertEquals(2, AppointmentService.apptMap.size());
		assertFalse(AppointmentService.apptMap.containsKey("1000"));
		
		testAppt.removeAppt("1000");
		assertEquals(2, AppointmentService.apptMap.size());
	}
}
