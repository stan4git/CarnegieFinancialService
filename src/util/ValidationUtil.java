package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
	
	/*judgment of validation*/
	
	
	//1#Date
	public static boolean isValidDate(String s){
		String eL= "((([13578]|0[13578]|1[02])/([1-9]|0[1-9]|[12][0-9]|3[01]))"+
                "|(([469]|0[469]|11)/([1-9]|0[1-9]|[12][0-9]|30))"+
                "|([2]|[02]/([1-9]|0[1-9]|[1][0-9]|2[0-8])))"+
                "/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})";
        Pattern p = Pattern.compile(eL);        
        Matcher m = p.matcher(s);
        return(m.matches());
	}
	
	//2#Amount is a number ?
	public static boolean isNumber(String str){
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		} 
	}

}
