package jstock.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtils
{
	private static String[] week = { null, "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };

	/**
	 * @param dateStr
	 *            格式为"YYYYMMDD"的日期字符串
	 * @return 该日期对应的Data类对象
	 */
	public static Date getDate(String dateStr)
	{
		int year = parse(dateStr, 0, 4);
		int month = parse(dateStr, 4, 6) - 1;
		int day = parse(dateStr, 6, 8);
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		Date date = calendar.getTime();
		return date;
	}

	public static String getWeekD(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return week[dayOfWeek];
	}

	public static int getDuration(Date startDate, Date endDate)
	{
		LocalDate start = getLocalDate(startDate);
		LocalDate end = getLocalDate(endDate);
		Period period = Period.between(start, end);
		int duration = period.getDays();
		return duration;
	}

	public static LocalDate getLocalDate(Date date)
	{
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDate localDate = instant.atZone(zoneId).toLocalDate();
		return localDate;
	}

	private static int parse(String dateStr, int beginIndex, int endIndex)
	{
		int parsed = Integer.parseInt(dateStr.substring(beginIndex, endIndex));
		return parsed;
	}
}
