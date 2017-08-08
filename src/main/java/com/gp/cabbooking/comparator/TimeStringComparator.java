package com.gp.cabbooking.comparator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class TimeStringComparator implements Comparator<String> {

	private DateFormat primaryFormat = new SimpleDateFormat("HH:mm");

	@Override
	public int compare(String time1, String time2) {
		return timeInMillis(time1) - timeInMillis(time2);
	}

	public int timeInMillis(String time) {
		return timeInMillis(time, primaryFormat);
	}

	private int timeInMillis(String time, DateFormat format) {
		// you may need more advanced logic here when parsing the time if some
		// times have am/pm and others don't.
		try {
			Date date = format.parse(time);
			return (int) date.getTime();
		} catch (ParseException e) {
			// need to throw run time exception throw e;
		}
		return -1;
	}

	public TimeStringComparator() {
		// TODO Auto-generated constructor stub
	}

}
