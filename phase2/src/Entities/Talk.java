package Entities;

import java.time.LocalDateTime;

/**
 * A Talk in our program representing an one-speaker event.
 */
public class Talk extends Event {
    private String speakerUserName;

    /**
     * Initialize a new talk with one speaker.
     * @param talkTitle the title of the new talk
     * @param startTime the starting time of the talk
     * @param endTime the ending time of the talk
     * @param roomNum the room number that the talk will be held
     * @param VIP true iff the talk is a VIP-only talk, otherwise, false
     * @param maxNum the maximum number of people who can attend the talk
     * @param speakerUserName the speaker username of the talk
     */
    public Talk(String talkTitle, LocalDateTime startTime, LocalDateTime endTime,
                 String roomNum, boolean VIP, int maxNum, String speakerUserName){
        super(talkTitle, startTime, endTime, roomNum, VIP, maxNum);
        this.speakerUserName = speakerUserName;
    }

    /**
     * Getter for the speaker username of this talk
     * @return the speaker username of this talk
     */
    public String getSpeakerUserName() {
        return speakerUserName;
    }

    /**
     * Setter for the speaker username of this talk
     * @param speakerUserName the new speaker username of this talk
     */
    public void setSpeakerUserName(String speakerUserName) {
        this.speakerUserName = speakerUserName;
    }
}
