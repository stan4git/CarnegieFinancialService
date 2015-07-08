package action;

public class CoverNumUtil {
	
	public static String priceToString(Double price){
		java.text.DecimalFormat ddd = new java.text.DecimalFormat("###,##0.00");
		String res = ddd.format(price);
		return res;
	}
	
	public static String shareToString(Double share){
		java.text.DecimalFormat ddd = new java.text.DecimalFormat("###,##0.000");
		String res = ddd.format(share);
		return res;
	}
}
