import com.google.api.client.util.DateTime;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * sDate.java - A custom date object for use with events in calendar
 *
 *
 * @author Stephanie Wang and Ryan Ly
 * @version 1.00 2016/12/13
 */


public class sDate {
	
	int year;
	int month;
	int day;
	int hrs;
	int min;
	
	//Default constructor
    public sDate() {
    	this.year = 2017;
    	this.month = 01;
    	this.day = 01;
    	this.hrs = 00;
    	this.min = 00;
    }
    
    //Constructor that takes yyy/mmm/ddd/ as well as hrs and min
    public sDate(int y, int m, int d, int h, int mi){
    	//Checking if the date is valid.
    	if(m<0||m>12||d<0||d>31){
    		System.out.println ("Error: month and date invalid");
    	}
    	else{
    		this.year = y;
    		this.month = m;
    		this.day = d;
    		this.hrs = h;
    		this.min = mi;
    	}
    }
    
    //Retrieving the name of the month (converting number to name)
    public static String getMName(int m){
    	String [] months = {"Empty", "Jan", "Feb", "March", "Apr", "May", "June", "July", "August", "Sep", "October", "Nov", "Dec"};
    	String temp = "";
    	temp = months[m];
    	return temp;
    }
    
    //Retrieving the number of the month (converting name to number)
    public static int getMNum(String m){
		String [] months = {"Empty", "Jan", "Feb", "March", "Apr", "May", "June", "July", "August", "Sep", "October", "Nov", "Dec"};
		for(int i = 0;i < months.length;i++){
			if(m.equals(months[i])){
				return i;
			}
		}
		return -1;
	}
    
    //ToString in format: "{month} {date}, {year} {hours}:{mins}
	public String toString(){
		//if less than 10, add 0 to day
		String month1 = getMName(this.month);
		if(this.day < 10){
			//Adding 0 to minute if less than 10, to have the format 00:00
			if(this.min<10){
				return (month1 + " 0" + this.day +"," + this.year + " "+this.hrs+":" + "0"+ this.min);
			}
			else{
				return (month1 + " 0" + this.day +"," + this.year + " "+this.hrs+":" + this.min);
			}

		}
		else{
			if(this.min<10){
				return (month1 + " " + this.day +"," + this.year + " "+this.hrs+":" + this.min + "0");
			}
			else{
				return (month1 + " " + this.day +"," + this.year + " "+this.hrs+":" + this.min);
			}
		}

	}

	//Uses LocalDateTime.now() to get current time, then converts it to sDate
    public static sDate now(){
    	return ldtTosDate(LocalDateTime.now());
	}

	//Accessor and mutator methods
    public int getYear(){
    	return this.year;
    }
    
    public int getMonth(){
    	return this.month;
    }
    
    public int getDay(){
    	return this.day;
    }
    
    public int getHours(){
    	return this.hrs;
    }    
    
    public int getMinutes(){
    	return this.min;
    }
    
    public void setDay(int d){
    	this.day = d;
    }
    
    public void setYear(int y){
    	this.year = y;
    }
    
    public void setMonth(int m){
    	this.month = m;
    }
    
    public void setTime(int h, int m){
    	this.hrs = h;
    	this.min = m;
    }

    //Method to retrieve the hrs from a time (format 00:00)
	public static int gethrs (String temp){
		String hr = "";
		for(int i = 0; i < temp.length(); i ++){
			if(temp.charAt(i)!= ':'){
				hr +=temp.charAt(i);
			}
			else{
				i = temp.length()+2;
			}
		}
		return Integer.parseInt(hr);
	}

	//Method to retrieve the min from a time (format 00:00)
	public static int getmin (String temp){
		String min = "";
		boolean what = false;
		for(int i = 0; i < temp.length()-1; i ++){
			if(temp.charAt(i) == ':'){
				what = true;
			}
			if(what){
				min += temp.charAt(i+1);
			}
		}
		return Integer.parseInt(min);
	}

    //Parses a Date string (written in same format as ToString())
    public static sDate parseString(String input){
    	//Removes commas
    	input = input.replaceAll(",", " ");
    	//Splits the text by spaces
    	String[] partsString = input.split(" ");
    	//Holds each part, converted to int
    	int[] parts = new int[partsString.length + 1];
    	//Converts month to int representation
    	parts[0] = sDate.getMNum(partsString[0]);
    	//Parses each every other part, other than time into int
		for(int i = 1;i < partsString.length - 1;i++){
			parts[i] = Integer.parseInt(partsString[i]);
		}
		//Hour is first 2 characters of time
		parts[3] = Integer.parseInt(partsString[3].substring(0,2));
		//Minutes are from index 3 to end of time
		parts[4] = Integer.parseInt(partsString[3].substring(3));
		//Constructs a new sDate and returns it
		return new sDate(parts[2],parts[0],parts[1],parts[3],parts[4]);
	}

	//Converts an sDate into LocalDateTime
    public static LocalDateTime convertToLocalDateTime(sDate date){
    	//Changes every property of the LocalDateTime to that of the sDate
		return LocalDateTime.now().withYear(date.year).withMonth(date.month).withDayOfMonth(date.day).withHour(date.hrs).withMinute(date.min);
	}
	//Converts a LocalDateTime into an sDate
	public static sDate ldtTosDate(LocalDateTime date){
    	//Constructs and returns a sDate with the properties of the LocalDateTime
    	return new sDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(), date.getMinute());
	}

	//Used in place of equals in LocalDateTime (cannot override the original due to LocalDateTime being a final class, and this is simpler than making a wrapper class)
	//Compares everything except for seconds
	public static boolean localDatesEqual(LocalDateTime dateOne, LocalDateTime dateTwo){
		if(dateOne.getYear() == dateTwo.getYear() && dateOne.getMonth() == dateTwo.getMonth() && dateOne.getDayOfMonth() == dateTwo.getDayOfMonth() && dateOne.getHour() == dateTwo.getHour() && dateOne.getMinute() == dateTwo.getMinute()){
			return true;
		} else {
			return false;
		}
	}
	
	//Converts Google API DateTime to sDate
	public static sDate convertTosDate(DateTime time){
		//Gets EpochSeconds from DateTime, then creates a LocalDateTime using that
		LocalDateTime ldtTime = LocalDateTime.ofEpochSecond(time.getValue(), 0, ZoneOffset.ofHours(-5));
		int year = ldtTime.getYear();
		int month = ldtTime.getMonth().getValue();
		int day = ldtTime.getDayOfMonth();
		int hour = ldtTime.getHour();
		int min = ldtTime.getMinute();
		//Constructs a new sDate with the properties of the DateTime
		return new sDate(year, month, day, hour, min);
	}
}