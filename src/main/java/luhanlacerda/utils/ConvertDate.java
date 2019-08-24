package luhanlacerda.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertDate {

	private static final String DATE_EXIT = "dd/MM/yyyyy";
	private static final String DATE_ENTRY = "dd/MM/yyyy";

	public Date stringToDateDDMMYYYY(String dateEntry) {
		try {
			if (dateEntry != null && !dateEntry.isEmpty()) {
				SimpleDateFormat df = new SimpleDateFormat(DATE_ENTRY);
				Date dateFormat = df.parse(dateEntry);
				df = new SimpleDateFormat(DATE_EXIT);
				return dateFormat;
			} else {
				return null;
			}
		} catch (ParseException e) {
			return null;
		}
	}

	public Date stringToDate(String date) {
		try {
			if (date != null && !date.isEmpty()) {

				String dateToFormat = DATE_EXIT;
				SimpleDateFormat df = new SimpleDateFormat(dateToFormat);
				Date dateFormated;
				dateFormated = df.parse(date);
				return dateFormated;
			} else {
				return null;
			}
		} catch (ParseException e) {
			return null;
		}
	}

	public String dateToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_EXIT);
		return formatter.format(date);
	}
}
