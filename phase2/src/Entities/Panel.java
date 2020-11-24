package Entities;

import java.time.LocalDateTime;
import java.util.List;

/**
 * A Panel in our program representing multi-speaker event.
 */
public class Panel extends Event{

    private List<String> speakerUserNames;

    /**
     * Initialize a new talk with one speaker.
     * @param panelTitle the title of the new panel
     * @param startTime the starting time of the panel
     * @param endTime the ending time of the panel
     * @param roomNum the room number that the panel will be held
     * @param VIP true iff the talk is a VIP-only panel, otherwise, false
     * @param maxNum the maximum number of people who can attend the panel
     * @param speakerUserNames the speaker username of the panel
     */
    public Panel(String panelTitle, LocalDateTime startTime, LocalDateTime endTime, String roomNum,
                 boolean VIP, int maxNum, List<String> speakerUserNames) {
        super(panelTitle, startTime, endTime, roomNum, VIP, maxNum);
        this.speakerUserNames = speakerUserNames;
    }

    /**
     * Getter for the list of speakers' usernames of this panel
     * @return the list of speakers' usernames of this panel
     */
    public List<String> getSpeakerUsernames() {
        return speakerUserNames;
    }

    /**
     * Setter for the list of speakers' usernames of this panel
     * @param speakerUserNames the list of new speakers' usernames of this panel.
     */
    public void setSpeakerUsernames(List<String> speakerUserNames) {
        this.speakerUserNames = speakerUserNames;
    }
}
