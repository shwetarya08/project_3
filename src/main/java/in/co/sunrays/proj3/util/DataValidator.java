package in.co.sunrays.proj3.util;

import java.util.Date;

/**
 * This class validates input data
 *
 * @author SunilOS
 * @version 1.0

 */
public class DataValidator {

	 /**
     * Checks if value is Null
     *
     * @param val
     * @return
     */
	 public static boolean isNull(String val) {
	        if (val == null || val.trim().length() == 0) {
	            return true;
	        } else {
	            return false;
	        }
	    }
	  /**
	     * Checks if value is NOT Null
	     *
	     * @param val
	     * @return
	     */
	 
	 public static boolean isNotNull(String val) {
	        return !isNull(val);
	    }
	  /**
	     * Checks if value is an Integer
	     *
	     * @param val
	     * @return
	     */


	 public static boolean isInteger(String val) {

	        if (isNotNull(val)) {
	            try {
	                int i = Integer.parseInt(val);
	                return true;
	            } catch (NumberFormatException e) {
	                return false;
	            }

	        } else {
	            return false;
	        }
	    }
	    /**
	     * Checks if value is Long
	     *
	     * @param val
	     * @return
	     */
	 
	 public static boolean isLong(String val) {
	        if (isNotNull(val)) {
	            try {
	                long i = Long.parseLong(val);
	                return true;
	            } catch (NumberFormatException e) {
	                return false;
	            }

	        } else {
	            return false;
	        }
	    }
	 /**
	     * Checks if value is valid Email ID
	     *
	     * @param val
	     * @return
	     */
	 
	 public static boolean isEmail(String val) {

	        String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	        if (isNotNull(val)) {
	            try {
	                return val.matches(emailreg);
	            } catch (NumberFormatException e) {
	                return false;
	            }

	        } else {
	            return false;
	        }
	    }

	 /**
	     * Checks if value is valid Name
	     *
	     * @param val
	     * @return
	     */
	 
	 public static boolean isName(String val) {

	        String namereg ="^[a-zA-Z]*$";

	        if (isNotNull(val)) {
	            try {
	                return val.matches(namereg);
	            } catch (NumberFormatException e) {
	                return false;
	            }

	        } else {
	            return false;
	        }
	    }

	 /**
	     * Checks if value is valid Mobile No
	     *
	     * @param val
	     * @return
	     */
	 
	 public static boolean isMobile(String val) {

	        String rollNoreg ="^[7-9][0-9]{9}$";

	        if (isNotNull(val))
	        {
	            try {
	                return val.matches(rollNoreg);
	            } catch (NumberFormatException e) {
	                return false;
	            }

	        } else {
	            return false;
	        }
	    }


	 
	 
	 public static boolean isRollNo(String val) {

	        String mobilereg ="^[0-9]{2}[a-zA-Z]{3}[0-99]{2}$";

	        if (isNotNull(val))
	        {
	            try {
	                return val.matches(mobilereg);
	            } catch (NumberFormatException e) {
	                return false;
	            }

	        } else {
	            return false;
	        }
	    }


	 
	 
	 public static boolean isPassword(String val) {

	        String passreg = "^.*(?=.{6,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";

	        if (isNotNull(val)) {
	            try {
	                return val.matches(passreg);
	            } catch (NumberFormatException e) {
	                return false;
	            }

	        } else {
	            return false;
	        }
	    }

	 
	 
	 /**
	     * Checks if value is Date
	     *
	     * @param val
	     * @return
	     */
	 
	 
	 public static boolean isDate(String val) {

	        Date d = null;
	        if (isNotNull(val)) {
	            d = DataUtility.getDate(val);
	        }
	        return d != null;
	    }
	 
	 
	 /**
	     * Test above methods
	     *
	     * @param args
	     */
	 
	 public static void main(String[] args) {
/*
	        System.out.println("Not Null 2" + isNotNull("ABC"));
	        System.out.println("Not Null 3" + isNotNull(null));
	        System.out.println("Not Null 4" + isNull("123"));

	        System.out.println("Is Int " + isInteger(null));
	        System.out.println("Is Int " + isInteger("ABC1"));
	        System.out.println("Is Int " + isInteger("123"));
	        System.out.println("Is Int " + isNotNull("123"));
	  
*/		 
	// System.out.println(isName("harshu6757"));
		 
		// System.out.println(isMobile("6895678032"));
		// System.out.println(isRollNo("10ca12"));
		 
		 System.out.println(isPassword("Sa12$$123"));
	 }

	}


	 

