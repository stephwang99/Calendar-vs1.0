/**
 * Reminder.java - Reminder object that holds a reminder for a certain date
 *
 * @author Stephanie Wang and Ryan Ly
 * @version 1.00 2016/12/16
 */

import java.time.LocalDateTime;

public class Reminder extends Schedule{
	
	public sDate date;

	//Default constructor
    public Reminder() {
    	super("no name");
    	this.date = new sDate();
    	
    }

    public Reminder(sDate d, String n){
    	super(n);
    	this.date = d;
    	
    }

    //Compares the time of a reminder with the current time
    //Returns true if it is the time of the reminder, else false
    public boolean isTime(){
        if(sDate.localDatesEqual(LocalDateTime.now(),sDate.convertToLocalDateTime(this.date))){
            return true;
        } else {
            return false;
        }
    }

    //ToString in format: "
    public String toString(){
    	return(super.toString()+" "+this.date);
    }
    
    
}