package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	public static boolean checkPasswordSpelling(String password1, String password2){
		if(!password1.isEmpty() && password1.equals(password2)){
			return true;
		}
	return false;	
	}
	
	public static boolean validateEmail(String emailStr) {
	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
	        return matcher.find();
	}
	
	public static boolean isNumber(String str){
		try{
			Integer.parseInt(str);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
	
	public static boolean isDouble(String str){
		try{
			Double.parseDouble(str);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
	
	public static boolean isValidDay(String str){
		if(isNumber(str) && Integer.parseInt(str)>0 && Integer.parseInt(str)<=31){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isValidMonth(String str){
		if(isNumber(str) && Integer.parseInt(str)>0 && Integer.parseInt(str)<=12){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isValidYear(String str){
		if(isNumber(str) && Integer.parseInt(str)>=2012 && Integer.parseInt(str)<9999){
			return true;
		}else{
			return false;
		}
	}
	
}
