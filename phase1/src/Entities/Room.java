package Entities;
import java.io.Serializable;
import java.util.List;

/**
 * A room is the location that an event will be held.
 */

public class Room implements Serializable {
    private String roomNum;
    private Integer capacity = 2;
    private List<String> eventTitles;

    public Room(String roomNum){
        this.roomNum = roomNum;
    }

    /**
     * Getter for room number of the room.
     * @return Room number of this room.
     */
    public String getRoomNum(){return roomNum;}

    /**
     * Getter for capacity of the room.
     * @return capacity of this room.
     */
    public Integer getCapacity(){return capacity;}

    /**
     * Getter for a list of events held in this room.
     * @return a list of events held in this room.
     */
    public List<String> getEvents(){return eventTitles;}

    /**
     * Setter for a list of events held in this room.
     * @param eventTitles the new list of events that you want to set.
     */
    public void setEvents(List<String> eventTitles){this.eventTitles = eventTitles;}
}
