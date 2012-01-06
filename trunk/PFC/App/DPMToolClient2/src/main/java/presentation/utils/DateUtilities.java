package presentation.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class DateUtilities {

	private static final int FINAL_MONTH = 11;

	public static String convert(Date date) {
		DateFormat format = new SimpleDateFormat("d/M/yyyy");
		return format.format(date);
	}
	
	// Returns the years between two dates
	public static ArrayList<Integer> getYearsBetweenDates(Date d1, Date d2) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(d1);
		int initYear = cal.get(Calendar.YEAR);
		cal.setTime(d2);
		int finalYear = cal.get(Calendar.YEAR);
		
		for (int i=initYear; i<=finalYear; i++)
			result.add(i);
		return result;
	}
	
	// Returns the months between two dates. Each month corresponds to one year, so each year has a list of months
	public static Hashtable<Integer, List<Integer>> getMonthsBetweenDates(Date d1, Date d2) {
		Hashtable<Integer, List<Integer>> result = new Hashtable<Integer, List<Integer>>();
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(d1);
		int initYear = cal.get(Calendar.YEAR);
		int initMonth = cal.get(Calendar.MONTH);
		cal.setTime(d2);
		int finalYear = cal.get(Calendar.YEAR);
		int finalMonth = cal.get(Calendar.MONTH);
		
		List<Integer> monthsByYear = null;
		for (int i=initYear; i<=finalYear; i++) {
			monthsByYear = getMonths(i, initMonth, finalMonth, initYear, finalYear);
			result.put(i, monthsByYear);
		}		
		return result;
	}

	// Returns the months since the initial month until the end of the year, or to another month if the initial and final year are the same.
	private static List<Integer> getMonths(int actualYear, int initMonth, int finalMonth, int initYear, int finalYear) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int initValue = 0;
		int finalValue = 0;
		if (initYear == finalYear) {
			finalValue = finalMonth;
			initValue = initMonth;
		}
		else if (actualYear == initYear) {
			finalValue = FINAL_MONTH;
			initValue = initMonth;
		}
		else if (actualYear == finalYear) {
			finalValue = finalMonth;
			initValue = 0;
		}
		else {
			finalValue = FINAL_MONTH;
			initValue = 0;
		}
		for (int i=initValue; i<=finalValue; i++)
			result.add(i);
		return result;
	}

	public static boolean yearEquals(Date date, int year) {
		Calendar cal = Calendar.getInstance();		
		cal.setTime(date);
		int initYear = cal.get(Calendar.YEAR);
		return (initYear == year);
	}

	public static boolean monthEquals(Date date, int month, int year) {
		Calendar cal = Calendar.getInstance();		
		cal.setTime(date);
		int initYear = cal.get(Calendar.YEAR);
		int initMonth = cal.get(Calendar.MONTH);
		return (initYear == year && initMonth == month);
	}
}
