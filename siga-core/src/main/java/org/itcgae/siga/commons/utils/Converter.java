package org.itcgae.siga.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Converter {


	/**
	 * 
	 * @param calendar
	 * @return date formatted
	 */
	public static String calendarToString(Calendar calendar){
		
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
		String formatted = format1.format(calendar.getTime());
		
		return formatted;
		
	}
	
	
	/**
	 * 
	 * @param calendar
	 * @return date formatted
	 */
	public static String dateToString(Date date){
		
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
		

		String formatted = format1.format(date);

		
		
		return formatted;
		
	}
	
	
	
}
