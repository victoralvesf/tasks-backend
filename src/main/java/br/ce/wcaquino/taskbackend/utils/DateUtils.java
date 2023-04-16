package br.ce.wcaquino.taskbackend.utils;

import java.time.LocalDate;

public class DateUtils {

	private DateUtils() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static boolean isEqualOrFutureDate(LocalDate date) {
		return date.isEqual(LocalDate.now()) || date.isAfter(LocalDate.now());
	}
}
