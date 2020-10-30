package Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private String title;
    private LocalDateTime time;
    private Room room;
    private Speaker speaker;
    private List<Attendee> attendeeList;

    public Event(String eventTitle, LocalDateTime eventTime, Room room, Speaker speaker){
    this.title = eventTitle;
    this.time = eventTime;
    this.room = room;
    this.speaker = speaker;
    this.attendeeList = new ArrayList<>(room.getCapacity());
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
     * Setter for room of the Event
     * @param room  The room that this event take place
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Setter speaker of the Event
     * @param speaker  The speaker of this event
     */
    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    /**
     * Setter the List of Attendee of the Event
     * @param attendeeList the List of Attendee of the Event
     */
    public void setAttendeeList(List<Attendee> attendeeList) {
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
     * Getter for room of the Event
     * @return the room of the Event
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Getter for speaker of the Event
     * @return the speaker of the Event
     */
    public Speaker getSpeaker() {
        return speaker;
    }

    /**
     * Getter for time of the Event
     * @return the time of the Event
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Getter for the List of Attendee of the Event
     * @return the List of Attendee of the Event
     */
    public List<Attendee> getAttendeeList() {
        return attendeeList;
    }
}
