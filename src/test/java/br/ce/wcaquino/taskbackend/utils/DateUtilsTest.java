package br.ce.wcaquino.taskbackend.utils;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTest {
	
	private static final LocalDate CURRENT_DATE = LocalDate.now();
	private static final LocalDate TOMORROW_DATE = CURRENT_DATE.plusDays(1);
	private static final LocalDate YESTERDAY_DATE = CURRENT_DATE.minusDays(1);

	@Test
	public void shouldReturnTrueForFutureDates() {	
		Boolean result = DateUtils.isEqualOrFutureDate(TOMORROW_DATE);
		
		Assert.assertTrue(result);
	}
	
	@Test
	public void shouldReturnFalseForPastDates() {		
		Boolean result = DateUtils.isEqualOrFutureDate(YESTERDAY_DATE);
		
		Assert.assertFalse(result);
	}
	
	@Test
	public void shouldReturnTrueIfDateIsToday() {		
		Boolean result = DateUtils.isEqualOrFutureDate(CURRENT_DATE);
		
		Assert.assertTrue(result);
	}
	
}
