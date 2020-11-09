package Entities;
import java.util.List;

/**
 * Attendee is a class that participates in events and signs up for them
 */
public class Attendee extends User {
    private List <String> eventNames;


    public List<String> getEventNames(){
        return eventNames;
    }


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
     * Precondition: User has not signed up for this event.
     * Adds the event title to a list of eventNames this attendee wants to attend
     *
     * @param event the event an attendee wants to attend
     */
    public void addEvent(Event event){
        eventNames.add(event.getTitle());
    }

    /**
     * Precondition: User has already signed up for this event.
     * Cancels the event a user wants to attend.
     *
     * @param  event the event an atttendee wants to cancel
     */
    public void cancelEvent(Event event){
        for (String e: eventNames) {
            if (e.equals(event.getTitle())) {
                eventNames.remove(e);
                System.out.println("Successfully cancelled event.");
            }
        }
    }



}
