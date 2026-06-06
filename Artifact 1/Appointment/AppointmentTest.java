package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.DisplayName;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

import Appointments.Appointment; 

class AppointmentTest {

	// Null provider method
	static Stream<Arguments> nullProvider() {
		Date validDate = new Date(System.currentTimeMillis() + 86400000); // Tomorrow's Date
		return Stream.of(
			Arguments.of(null, validDate, "Valid Summary", "Error: Invalid Appointment ID"),
			Arguments.of("1000", null, "Valid Summary", "Error: Invalid Appointment Date"),
			Arguments.of("1000", validDate, null, "Error: Invalid Appointment Summary")
		);
	}

	@DisplayName("Testing null ID, Date and Summary")
	@ParameterizedTest(name = "{index} => {3}")
	@MethodSource("nullProvider")
	void testNullInput(String id, Date date, String summary, String expectedMessage) {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(id, date, summary);
		});
		assertEquals(expectedMessage, exception.getMessage());
	}

	@DisplayName("Testing good Appointment")
	@Test
	void testGoodAppt() {
		String testID = "1000";
		Calendar testDate = Calendar.getInstance();
		String testSummary = "Good Appointment Description";
		
		testDate.set(Calendar.MONTH, Calendar.APRIL);
		testDate.set(Calendar.DATE, 10);
		testDate.set(Calendar.YEAR, 2028);
		
		Date validDate = testDate.getTime();
		
		Appointment testAppt = new Appointment(testID, validDate, testSummary);
		
        assertEquals(testID, testAppt.getApptID());
        assertEquals(validDate, testAppt.getApptDate());
        assertEquals(testSummary, testAppt.getApptSummary()); 
	}
	
	@DisplayName("Testing Long Summary")
	@Test
	void testBadSummary() {
		String testID = "1000";
		Calendar testDate = Calendar.getInstance();
		String testSummary = "This is the Appointment description and it is too long to be valid";
		
		testDate.set(Calendar.MONTH, Calendar.APRIL);
		testDate.set(Calendar.DATE, 10);
		testDate.set(Calendar.YEAR, 2028);
		
		Date validDate = testDate.getTime();
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {new Appointment(testID, validDate, testSummary);});
		
		assertEquals("Error: Invalid Appointment Summary", exception.getMessage());
	}

	@DisplayName("Testing Long ID")
	@Test
	void testLongID() {
		String testID = "12345678901234567890";
		Date testDate = new Date();
		String testSummary = "This is the Appointment description";
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {new Appointment(testID, testDate, testSummary);});
		
		assertEquals("Error: Invalid Appointment ID", exception.getMessage());
	}
	
	@DisplayName("Testing Date prior to current")
	@Test
	void testInvalidDate() {
		String testID = "1000";
		Calendar testDate = Calendar.getInstance();
		String testSummary = "Good Appointment Description";
		
		testDate.set(Calendar.MONTH, Calendar.APRIL);
		testDate.set(Calendar.DATE, 10);
		testDate.set(Calendar.YEAR, 2000);
		
		Date invalidDate = testDate.getTime();
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {new Appointment(testID, invalidDate, testSummary);});
		
		assertEquals("Error: Invalid Appointment Date", exception.getMessage());
	}
	
	@DisplayName("Testing Empty Summary")
	@Test
	void testEmptySummary() {
		String testID = "1000";
		Calendar testDate = Calendar.getInstance();
		String testSummary = "";
		
		testDate.set(Calendar.MONTH, Calendar.APRIL);
		testDate.set(Calendar.DATE, 10);
		testDate.set(Calendar.YEAR, 2028);
		
		Date validDate = testDate.getTime();
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {new Appointment(testID, validDate, testSummary);});
		
		assertEquals("Error: Invalid Appointment Summary", exception.getMessage());
	}
}
