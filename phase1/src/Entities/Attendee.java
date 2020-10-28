package Entities;
import java.util.List;

/**
 * Attendee is
 */
public class Attendee extends User {
    private List <Event> eventsAttending;


    /**
     * Checks to see if Attendee has already booked this event
     *
     * @param event   Checking to see if Attendee has already booked this event
     * @return        returns true if already booked, false otherwise.
     */
    public boolean isAttendingEvent(Event event){
        for (Event events: eventsAttending){
            if (events == event){
                System.out.println("You already signed up for this event.");
                return true;
            }
        }
        System.out.println("You are not signed up for this event.");
        return false;
    }
}
