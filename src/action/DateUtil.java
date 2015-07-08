package action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static Date stringToDate(String s) throws ParseException{
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");; 
		Date date = formatter.parse(s);
		return date;
	}
	
	public static String dateToString(Date date){
		DateFormat df= new SimpleDateFormat("MM/dd/yyyy");
		String res = df.format(date);
		return res;
	}
}
