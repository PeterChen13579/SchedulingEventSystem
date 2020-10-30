package Entities;
import java.util.List;

/**
 * Attendee is a class that participates in events and signs up for them
 */
public class Attendee extends User {
    private List <String> eventNames;
    private List <String> friends;

    public Attendee(String username, String password){
        super(username, password);
    }

    /**
     * Checks to see if Attendee has already booked this event
     *
     * @param eventName   Checking to see if Attendee has already booked this event
     * @return        returns true if already booked, false otherwise.
     */
    public boolean isAttendingEvent(String eventName){
        for (String event: eventNames){
            if (event.equals(eventName)){
                System.out.println("You already signed up for this event.");
                return true;
            }
        }
        System.out.println("You are not signed up for this event.");
        return false;
    }

    /**
     * Adds the event title to a list of eventNames this attendee wants to attend
     *
     * @param event the event an attendee wants to attend
     */
    public void addEvent(Event event){
        boolean flag = true;
        for (String e: eventNames){
            if (e.equals(event.getTitle())) {
                flag = false;

                break;
            }
        }
        if (flag){
            eventNames.add(event.getTitle());
        }
    }

    /**
     * Cancels the event a user wants to attend.
     *
     * @param  event the event an atttendee wants to cancel
     * @return true if successfully cancelled event. False otherwise.
     */
    public boolean cancelEvent(Event event){
        for (String e: eventNames) {
            if (e.equals(event.getTitle())) {
                eventNames.remove(e);
                System.out.println("Successfully cancelled event.");
                return true;
            }
        }
        System.out.println("This user is not attending this event.");
        return false;
    }
}
