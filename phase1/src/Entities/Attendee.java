package Entities;
import java.util.List;

/**
 * Attendee is a class that participates in events and signs up for them
 */
public class Attendee extends User {
    private List <String> eventNames;


    public Attendee(String username, String password){
        super(username, password);
    }

    public void setEventNames(List<String> eventNames) {
        this.eventNames = eventNames;
    }

    public List<String> getEventNames() {
        return eventNames;
    }
}
