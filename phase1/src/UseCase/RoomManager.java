package UseCase;

import java.util.ArrayList;
import java.util.List;

import Entities.Room;

/**
 * A Use Case class that manages the functionality of room.
 */
public class RoomManager {
    private List<Room> allRoom = new ArrayList<>();

    public void createRoom(String roomNum){ allRoom.add(new Room(roomNum));
    }

    public boolean doesRoomExist(String roomNum){
        for(Room room: allRoom){
            if(room.getRoomNum().equals(roomNum)) return true;
        }
        return false;
    }

//    already done so in EventManager(Lisa: I THINK IT'S EASIER TO DO SO IN EVENTMANAGER)
//    public boolean isRoomAvailable(String roomNum, LocalDateTime time){
//        for(Room room: allRoom){
//            if (room.getRoomNum().equals(roomNum)){
//                return room.isRoomAvailable(time);
//            }
//        }
//        return false;
//    }

//    /** already done so in EventManager
//     * Add an event that will be held at this room to the event list of this room.
//     * @param event the event that you want to add.
//     * @return true iff the event is added successfully. Otherwise, return false.
//     */
//    public boolean addEvent(Event event){
//        if(event.getRoom().isRoomAvailable(event.getTime())){
//            List<Event> events = event.getRoom().getEvents();
//            events.add(event);
//            event.getRoom().setEvents(events);
//            return true;
//        }
//        else{return false;}
//    }
}
