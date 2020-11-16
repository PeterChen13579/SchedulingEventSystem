package UseCase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Entities.Room;

/**
 * A Use Case class that manages the functionality of room.
 */
public class RoomManager implements Serializable {
    private List<Room> allRoom;

    /**
     * A constructor for a RoomManager that initializes the list of all room as an empty list.
     */
    public RoomManager() {
        allRoom = new ArrayList<>();
    }

    /**
     * A constructor for a RoomManager that initializes the list of all rooms as the given list.
     * @param room a list of room.
     */
    public RoomManager(List<Room> room) {
        allRoom = room;
    }

    /**
     * Create a new room.
     * @param roomNum the room number of the new room.
     */
    public void createRoom(String roomNum){ allRoom.add(new Room(roomNum));
    }

    /**
     * Check whether the room exists.
     * @param roomNum the room number of the room that you want to check.
     * @return true iff the room has existed. Otherwise, return false.
     */
    public boolean doesRoomExist(String roomNum){
        for(Room room: allRoom){
            if(room.getRoomNum().equals(roomNum)) return true;
        }
        return false;
    }
}
