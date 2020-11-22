package Entities;
import java.util.ArrayList;
import java.util.List;

/**
 * Attendee is a class that participates in events and signs up for them
 */
public class Attendee extends User {

    private List<String> eventAttending;

    /**
     * Class Constructor specifying Attendee's username and password
     */
    public Attendee(String username, String password){
        super(username, password);
        this.eventAttending = new ArrayList<>();
    }

    public List<String> getEventAttending() {
        return eventAttending;
    }

    public void setEventAttending(List<String> eventAttending) {
        this.eventAttending = eventAttending;
    }
}
