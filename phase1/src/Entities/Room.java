package Entities;
import java.util.List;
import java.time.LocalDateTime;

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

    /**
     * Add an event that will take place at this room to the event list of this room.
     * @param event the event that you want to add.
     * @return true iff the event is added successfully. Otherwise, return false.
     */
    public boolean addEvent(Event event){
        if(isRoomAvailable(event.getTime())){
            events.add(event);
            return true;
        }
        else{return false;}
    }
}
