/**
 * Notification.java - used to push notifications for events
 *
 * @author Ryan Ly
 * @version 1.00 2016/12/21
 */

import java.time.Duration;
import java.time.LocalDateTime;

public class Notification {

    //note: these are now deprecated due to changes in the GUI system
    //uses java.time.Duration to get the difference between now and when the event/reminder happens
    private double timeDifference(Reminder item){
        return Duration.between(LocalDateTime.now(), sDate.convertToLocalDateTime(item.date)).getSeconds();
    }

    private double timeDifference(Event item){
        return Duration.between(LocalDateTime.now(), sDate.convertToLocalDateTime(item.getFrom())).getSeconds();
    }

    //Returns a message if it is currently time for a reminder
    public static String pushNotification(Reminder item){
        //Checks if current time matches reminder's time
        if(item.isTime()) {
            return item.getName() + " is happening right now!";
        } else {
            return null;
        }
    }

    //Returns a message if it is currently time for an event
    public static String pushNotification(Event item){
        //Checks if current time matches event's start time
        if(item.isTime()) {
            return item.getName() + " is happening right now!";
        } else {
            return null;
        }
    }

}