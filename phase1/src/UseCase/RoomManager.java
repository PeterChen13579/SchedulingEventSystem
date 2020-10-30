package UseCase;

import java.time.LocalDateTime;
import java.util.List;

import Entities.Event;
import Entities.Room;

/**
 * A Use Case class that manages the functionality of room.
 */
public class RoomManager {
    private List<Room> allRoom;

    public boolean createRoom(String roomNum){
        if(!isRoomExist(roomNum)){
            allRoom.add(new Room(roomNum));
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isRoomExist(String roomNum){
        for(Room room: allRoom){
            if(room.getRoomNum().equals(roomNum)) return true;
        }
        return false;
    }

    public boolean isRoomAvailable(String roomNum, LocalDateTime time){
        for(Room room: allRoom){
            if (room.getRoomNum().equals(roomNum)){
                return room.isRoomAvailable(time);
            }
        }
        return false;
    }

    /**
     * Add an event that will be held at this room to the event list of this room.
     * @param event the event that you want to add.
     * @return true iff the event is added successfully. Otherwise, return false.
     */
    public boolean addEvent(Event event){
        if(event.getRoom().isRoomAvailable(event.getTime())){
            List<Event> events = event.getRoom().getEvents();
            events.add(event);
            event.getRoom().setEvents(events);
            return true;
        }
        else{return false;}
    }
}
