package presentation.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtilities {

	public static String convert(Date date) {
		DateFormat format = new SimpleDateFormat("d/M/yyyy");
		return format.format(new Date());
	}
}
