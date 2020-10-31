package Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private String title;
    private LocalDateTime time;
    private String roomNum;
    private String speakerUserName;
    private List<String> attendeeList;

    public Event(String eventTitle, LocalDateTime eventTime, String roomNum, String speakerUserName){
    this.title = eventTitle;
    this.time = eventTime;
    this.roomNum = roomNum;
    this.speakerUserName = speakerUserName;
    this.attendeeList = new ArrayList<>(new Room(roomNum).getCapacity());
    }

    /**
     * Setter for title of the Event
     * @param title  The title of this event
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter for time of the Event
     * @param time  The time of this event
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * Setter for the room number of the Event
     * @param roomNum  The room that this event take place
     */
    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    /**
     * Setter for speaker username of the Event
     * @param speakerUserName  The speaker of this event
     */
    public void setSpeakerUserName(String speakerUserName) {
        this.speakerUserName = speakerUserName;
    }

    /**
     * Setter for the List of Attendee usernames of the Event
     * @param attendeeList the List of Attendee of the Event
     */
    public void setAttendeeList(List<String> attendeeList) {
        this.attendeeList = attendeeList;
    }

    /**
     * Getter for title of the Event
     * @return the title of the Event
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the room number of the Event
     * @return the room number of the Event
     */
    public String getRoomNum() {
        return roomNum;
    }

    /**
     * Getter for speaker username of the Event
     * @return the speaker username for the Event
     */
    public String getSpeakerUserName() {
        return speakerUserName;
    }

    /**
     * Getter for time of the Event
     * @return the time of the Event
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Getter for the List of attendee who attend the Event
     * @return the List of Strings of attendee usernames
     */
    public List<String> getAttendeeList() {
        return attendeeList;
    }
}
