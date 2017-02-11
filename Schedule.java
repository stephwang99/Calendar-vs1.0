/**
 * Schedule.java - parent class for events on the calendar
 *
 *
 * @author Stephanie Wang and Ryan Ly
 * @version 1.00 2016/12/16
 */

//Super class
public abstract class Schedule {
	
	private String name;
	
	//Constructor that takes a string (name)
    public Schedule(String n) {
    	this.name = n;
    }

    //Default constructor
    public Schedule(){
    	this.name = "unknown";
    }

    //ToString in format: "Name"
    public String toString(){
    	return this.name + "\n";
    }
    
    //Method that retrieves the name
    public String getName(){
        return this.name;
    }
}