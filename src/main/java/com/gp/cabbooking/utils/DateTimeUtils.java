package com.gp.cabbooking.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtils {

	private static final DateTimeFormatter ISO_DATE_TIME = DateTimeFormatter.ISO_DATE_TIME;

	private DateTimeUtils() {

	}

	public static String formatCurrentDateTimeTOISO() {
		return formatDateTimeToISO(LocalDateTime.now());
	}

	public static String formatDateTimeToISO(LocalDateTime dateTime) {
		return ISO_DATE_TIME.format(dateTime);
	}
	
	
	
	public static LocalDateTime parseDateTime(String dateInStringFormat,String pattern){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(dateInStringFormat, formatter);
	}
	
	//-----date
	
	public static LocalDate parseISODate(String dateInStringFormat){
		return LocalDate.parse(dateInStringFormat, DateTimeFormatter.ISO_DATE);
	}
	
	public static String formatISODate(LocalDate date){
		return DateTimeFormatter.ISO_DATE.format(date);
	}
	
//	public static void main(String[] args) {
//		System.out.println(" "+parseISODate("2016-07-27"));
//	}
	
	
//	public static LocalDateTime parseDateToISO(String dateInStringFormat){
//		return LocalDateTime.parse(dateInStringFormat, DateTimeFormatter.ISO_DATE);
//	}
	
//	public static void main(String[] args) {
//		LocalDateTime dateTime=LocalDateTime.now();
//		dateTime.toLocalDate();
//		String dateTimeString=formatCurrentDateTimeTOISO();
//		System.out.println(" "+dateTimeString+"    "+dateTime.toLocalDate());
//	}
	
//	public static void main(String[] args) {
//		System.out.println(" "+formatISODate(LocalDate.now() ));
//	}

}
