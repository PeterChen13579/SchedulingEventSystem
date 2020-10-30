package Entities;
import java.util.List;

import java.time.LocalDateTime;

public class Speaker extends User {
    // list of talks the speaker gives
    private List<Event> EventAttending;

    /**
     * Getter for the list of EventAttending.
     * @return the EventAttending of this Speaker.
     */
    public List<Event> getEventAttending(){return EventAttending;}

    /**
     * Setter for the list of EventAttending.
     * @param EventAttending the new EventAttending that you wan to set.
     */
    public void setEventAttending(List<Event> EventAttending){this.EventAttending = EventAttending; }

    /**
     * Check whether this speaker is available for this time.
     * @param time the time that you want to check.
     * @return true iff the speaker is available. Otherwise, return false.
     */
    public boolean isSpeakerAvailable(LocalDateTime time){
        for(Event event: EventAttending) {
            return !event.getTime().isEqual(time);
        }
        return true;
    }
}
