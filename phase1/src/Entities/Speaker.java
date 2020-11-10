package Entities;
import java.util.ArrayList;
import java.util.List;

public class Speaker extends User {
    // list of talks the speaker gives
    private List<String> EventAttending = new ArrayList<>();


    public Speaker(String username, String password){
        super(username, password);
    }
    /**
     * Getter for the list of EventAttending.
     * @return the EventAttending of this Speaker.
     */
    public List<String> getEventAttending() {return EventAttending;}

    /**
     * Setter for the list of EventAttending.
     * @param EventAttending the new EventAttending that you wan to set.
     */
    public void setEventAttending(List<String> EventAttending){this.EventAttending = EventAttending;}

    /**
     * Add the given event title to list of events the speaker is attending/hosting.
     * @param title the title of the new event
     */
    public void addEventToSpeaker(String title){
        EventAttending.add(title);
    }
}
