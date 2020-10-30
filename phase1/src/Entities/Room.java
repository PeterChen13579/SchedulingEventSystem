package Entities;
import java.util.List;
import java.time.LocalDateTime;

/**
 * A room is the location that an event will be held.
 */

public class Room {
    private String roomNum;
    private Integer capacity = 2;
    private List<Event> events;

    public Room(String roomNum){
        this.roomNum = roomNum;
    }

    /**
     * Getter for room number of the room.
     * @return Room number of this room.
     */
    public String getRoomNum(){return roomNum;}

    /**
     * Setter for room number of the room.
     * @param roomNum Room number of this room.
     */
    public void setRoomNum(String roomNum){this.roomNum = roomNum;}

    /**
     * Getter for capacity of the room.
     * @return capacity of this room.
     */
    public Integer getCapacity(){return capacity;}
    /**
     * Setter for capacity of the room.
     * @param capacity Capacity of this room.
     */
    public void setCapacity(Integer capacity){this.capacity = capacity;}

    /**
     * Getter for a list of events held in this room.
     * @return a list of events held in this room.
     */
    public List<Event> getEvents(){return events;}

    /**
     * Setter for a list of events held in this room.
     * @param events the new list of events that you want to set.
     */
    public void setEvents(List<Event> events){this.events = events;}

    /**
     * Check whether this room is available for this time.
     * @param time the time that you want to check.
     * @return true iff the room is available. Otherwise, return false.
     */
    public boolean isRoomAvailable(LocalDateTime time){
        for(Event event: events){
            return !event.getTime().isEqual(time);
        }
        return true;
    }
}
