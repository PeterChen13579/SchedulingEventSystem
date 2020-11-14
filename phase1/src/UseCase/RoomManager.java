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

    public RoomManager() {
        allRoom = new ArrayList<>();
    }

    public RoomManager(List<Room> room) {
        allRoom = room;
    }

    public void createRoom(String roomNum){ allRoom.add(new Room(roomNum));
    }

    public boolean doesRoomExist(String roomNum){
        for(Room room: allRoom){
            if(room.getRoomNum().equals(roomNum)) return true;
        }
        return false;
    }
}
