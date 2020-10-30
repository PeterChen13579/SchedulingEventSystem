package Entities;
import java.util.List;

import java.time.LocalDateTime;

public class Speaker extends User {
    // list of talks the speaker gives
    private List<Event> EventAttending;

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

    /**
     * Add a talk that this speaker will give to the eventAttending list of this speaker.
     * @param event the event that you want to add.
     * @return true iff the event is added successfully. Otherwise, return false.
     */
    public boolean addTalk(Event event){
        if(isSpeakerAvailable(event.getTime())){
            EventAttending.add(event);
            return true;
        }
        else{return false;}
    }
}
