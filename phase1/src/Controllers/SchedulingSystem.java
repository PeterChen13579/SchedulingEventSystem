package Controllers;

import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

public class SchedulingSystem {
    public void run(){
        //instanciates scanner
        //display options: 1. add room 2. add event

        /////////NOTE: ALL THE PROMPTS/MSG GIVE OPTION TO QUIT
        ////if 1:
        ////get input for room number
        //// call RoomManager.doesRoomExist; if exist, say "Room already exists"; if not, call RoomManager.createRoom and display "Room successfully created"

        ////if 2:
        ////get input for date
        ////check if date has valid format, valid value; if not, display error& prompt again, if yes:

        ////get input for time
        ////check if time has valid format, valid value (call isTimeValid), display error&prompt again, if yes:

        ////get input for room number
            ////check if room exists (RoomManager.doesRoomExist); if not, prompt to create the room first, if yes:
            ////check if room is booked at given time(call isRoomAvailableAtTime); if not, display error and prompt again, if yes:

        ////get input for speakerUserName
            ////check if speaker exists(UserManager.doesSpeakerExist); if not, prompt to create the room first, if yes:
             ////check if room is booked at given time(call isSpeakerAvailableAtTime); if not, display error and prompt again, if yes:

        ////get input for eventTitle
            ////check if unique event title(isTitleUnique), if not, prompt again, if yes create the event (call createEvent) & update the speaker's list of events?



    }
}
