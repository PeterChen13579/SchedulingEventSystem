package Controllers;

import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

public class TechConferenceSystem {

    public TechConferenceSystem(){
        UserManager manager = new UserManager();
        EventManager eventManager = new EventManager();
        RoomManager roomManager = new RoomManager();
        LogInSystem logIn = new LogInSystem(manager);
        MessagingSystem msg = new MessagingSystem(manager, eventManager);
        Reader read = new Reader();
        SchedulingSystem schedule = new SchedulingSystem(eventManager, roomManager, manager);
        SignUpSystem signUp = new SignUpSystem(eventManager, manager);
        UserMenuOptions menu = new UserMenuOptions();
        Writer write = new Writer();

        //Basically controller does all the logic, presenter PRINTS to screen.
        //
    }
}
