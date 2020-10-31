package Controllers;

import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

public class SignUpSystem {
    EventManager em;
    UserManager um;
    RoomManager rm;
    public void signUpEvent(String userName, String eventTitle){
        if(em.canAddAttendee(userName, eventTitle) /* && !rm.isRoomFull()*/){
            em.addAttendee(userName, eventTitle);
        }
        //not finished, need to add event to event List of Attendee
    }
}
