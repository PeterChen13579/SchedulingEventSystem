package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A Event in our program
 */
public abstract class Event implements Serializable {
    private String title;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String roomNum;
    private List<String> attendeeList;
    private final boolean VIP;
    private final int maxNum;

    public Event(String eventTitle, LocalDateTime startTime, LocalDateTime endTime,
                 String roomNum, boolean VIP, int maxNum){
    this.title = eventTitle;
    this.startTime = startTime;
    this.endTime = endTime;
    this.roomNum = roomNum;
    this.attendeeList = new ArrayList<>();
    this.VIP = VIP;
    this.maxNum = maxNum;
    }

    /**
     * Setter for title of the Event
     * @param title The title of this event
     */
    public void setTitle(String title) {
        this.title = title;
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
     * Getter for the start time of the Event
     * @return the start time of the Event
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Getter for the List of attendee who attend the Event
     * @return the List of Strings of attendee usernames
     */
    public List<String> getAttendeeList() {
        return attendeeList;
    }

    /**
     * Getter for the end time of the Event
     * @return the end time of the Event
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Getter for the type of the event.
     * @return return true iff this event is a VIP event.
     */
    public boolean getVIP() { return VIP; }

    /**
     * Getter for the maximum number of people who can attend this event.
     * @return the maximum number of people who can attend this event.
     */
    public int getMaxNum() { return maxNum; }
}
