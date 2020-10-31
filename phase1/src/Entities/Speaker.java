package Entities;
import java.util.List;

public class Speaker extends User {
    // list of talks the speaker gives
    private List<String> EventAttending;


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
}
