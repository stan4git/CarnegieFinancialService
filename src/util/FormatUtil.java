package util;

import java.text.DecimalFormat;

public class FormatUtil {
	public String rightAlign(String s){
		String format = "%2s"; 
		return String.format(format,s);
	}
	
	//michelle 19:20 01/26   for prices, show two decimals
	public static String parseValue(double d){
		DecimalFormat df = new DecimalFormat("###,###,##0.00");
		return ("$"+df.format(d));
	}
	
	//michelle 19:20 01/26   for shares, show three decimals
	public static String parseShare(double d){
		DecimalFormat df = new DecimalFormat("###,###,##0.000");
		return (df.format(d));
	}
	
	public static Double getDouble(double a){
	    DecimalFormat df=new DecimalFormat("0.00");
	    return new Double(df.format(a).toString());
	}
	 
	public static double getdouble(double a){
	    DecimalFormat df=new DecimalFormat("0.00");
	    return Double.parseDouble(df.format(a).toString());
	}

}
