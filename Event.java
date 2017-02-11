/**
 * Event.java - Event object that holds an event with that begins and ends at certain times (i.e. a party)
 *
 * @author Ryan Ly and Stephanie Wang
 * @version 1.00 2016/12/16
 */

import java.time.*;

//Inherits from Schedule (subclass class)
public class Event extends Schedule{
	
	private sDate from;
	private sDate to;

	//Constructor that takes date and name
    public Event(sDate f, sDate t, String n) {
		super(n);
    	this.from = f;
    	this.to = t;
    }

    //Constructor taking an Event from Google API
	//Written by Ryan Ly
    public Event(com.google.api.services.calendar.model.Event event){
    	super(event.getId());
    	this.from = sDate.convertTosDate(event.getStart().getDateTime());
    	this.to = sDate.convertTosDate(event.getEnd().getDateTime());
	}

	///Default constructor
    public Event(){
		super("no name");
    	this.from = new sDate();
    	this.to = new sDate();
    }
    
    //Method to retrieve the time
    public sDate getFrom(){
		return this.from;
	}

	//Compares to current time
	//Written by Ryan Ly
	public boolean isTime(){
		if(sDate.localDatesEqual(LocalDateTime.now(),sDate.convertToLocalDateTime(this.from))){
			System.out.println(LocalDateTime.now() + " and " + sDate.convertToLocalDateTime(this.from));
			return true;
		} else {
			return false;
		}
	}

	//ToString in format: "{name}  {from} to {to}
	public String toString(){
		return (super.toString()+" "+this.from +" to "+this.to);
	}
    
    
}