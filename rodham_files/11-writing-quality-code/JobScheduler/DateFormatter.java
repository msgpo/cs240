

import java.text.*;
import java.util.Date;


/**
 * DateFormatter provides opeations for formatting and parsing date and date/time values
 */
public final class DateFormatter {

	/**
	 * Formatter that defines how we format date values
	 */
	private static SimpleDateFormat dateFormatter;
	
	/**
	 * Formatter that defines how we format date/time values
	 */
	private static SimpleDateFormat dateTimeFormatter;
	
	static {
		dateFormatter = new SimpleDateFormat("MM/dd/yy");
		dateTimeFormatter = new SimpleDateFormat("MM/dd/yy hh:mm a");
	}
	
	/**
	 * Converts a Date object to a string of the form "MM/dd/yy"
	 * 
	 * @param d The date to be formatted
	 * 
	 * @pre d != null
	 * 
	 * @post returns a string representation of the specified date
	 * 	of the form "MM/dd/yy"
	 */
	public static String formatDate(Date d) {
		return dateFormatter.format(d);
	}
	
	/**
	 * Converts a string of the form "MM/dd/yy" to a Date object
	 * 
	 * @param text The date string to be parsed
	 * 
	 * @throws ParseException if text is not of the form "MM/dd/yy"
	 * 
	 * @pre text != null
	 * @pre text is of the form "MM/dd/yy"
	 * 
	 * @post returns a Date object initialized from the specified string
	 */
	public static Date parseDate(String text) throws ParseException {
		return dateFormatter.parse(text);
	}
	
	/**
	 * Converts a Date object to a string of the form "MM/dd/yy hh:mm a"
	 * 
	 * @param d The date/time to be formatted
	 * 
	 * @pre d != null
	 * 
	 * @post returns a string representation of the specified date/time
	 * 	of the form "MM/dd/yy hh:mm a"
	 */
	public static String formatDateTime(Date d) {
		return dateTimeFormatter.format(d);
	}
	
	/**
	 * Converts a string of the form "MM/dd/yy hh:mm a" to a Date object
	 * 
	 * @param text The date/time string to be parsed
	 * 
	 * @throws ParseException if text is not of the form "MM/dd/yy hh:mm a"
	 * 
	 * @pre text != null
	 * @pre text is of the form "MM/dd/yy hh:mm a"
	 * 
	 * @post returns a Date object initialized from the specified string
	 */
	public static Date parseDateTime(String text) throws ParseException {
		return dateTimeFormatter.parse(text);
	}
	
	private DateFormatter() {
		assert false;
	}

}
