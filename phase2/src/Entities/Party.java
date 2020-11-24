package Entities;

import java.time.LocalDateTime;

/**
 * A Party in our program representing a no-speaker event.
 */
public class Party extends Event {

    /**
     * Initialize a new Party
     * @param partyTitle the title of the party
     * @param startTime the starting time of the party
     * @param endTime the ending time of the party
     * @param roomNum the room number that the party that will be held
     * @param VIP true iff the party is a VIP-only party, otherwise, false
     * @param maxNum the maximum number of people who can attend to this party
     */
    public Party(String partyTitle, LocalDateTime startTime, LocalDateTime endTime,
                 String roomNum, boolean VIP, int maxNum){
        super(partyTitle, startTime, endTime, roomNum, VIP, maxNum);
    }
}
