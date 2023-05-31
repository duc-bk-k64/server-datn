package hust.project3.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	public static Date String2Date(String string) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = simpleDateFormat.parse(string);
		return date;
	}

	public static String Date2String(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String string = simpleDateFormat.format(date);
		return string;

	}

	public static String Instant2String(Instant instant) {
		ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(zoneId);
		String string = dateTimeFormatter.format(instant);
		return string;
	}

}
