package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	
	public static String getSpecifiedDayBefore(String specifiedDay){   
        Calendar c = Calendar.getInstance();  
        Date date=null;  
        try {  
            date = new SimpleDateFormat("MM/dd/yyyy").parse(specifiedDay);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        c.setTime(date);  
        int day=c.get(Calendar.DATE);  
        c.set(Calendar.DATE,day-1);  
  
        String dayBefore=new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());  
        return dayBefore;  
    }
	
	public static String getSpecifiedDayAfter(String specifiedDay){  
        Calendar c = Calendar.getInstance();  
        Date date=null;  
        try {  
            date = new SimpleDateFormat("MM/dd/yyyy").parse(specifiedDay);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        c.setTime(date);  
        int day=c.get(Calendar.DATE);  
        c.set(Calendar.DATE,day+1);  
  
        String dayAfter=new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());  
        return dayAfter;  
    }
}
