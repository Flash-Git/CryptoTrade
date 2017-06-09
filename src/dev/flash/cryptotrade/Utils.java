package dev.flash.cryptotrade;

/**
 * Created by Flash on 15/02/2017.
 */

public class Utils {
	public static float parseFloat(String number){
		try{
			return Float.parseFloat(number);
		}
		catch(NumberFormatException e){
			e.printStackTrace();
			return 0;
		}
	}
	public static boolean parseBool(String number){
		try{
			if(Integer.parseInt(number)==0)
				return false;
			else return true;
		}
		catch(NumberFormatException e){
			e.printStackTrace();
			return false;
		}
	}
	public static boolean parseBuySell(String buySell) {
		if(buySell.equals("Buy")){
			return true;
		}
		if(buySell.equals("Sell")){
			return false;
		}
		System.out.println(buySell + " FUCKED ");
		return false;
	}
}
