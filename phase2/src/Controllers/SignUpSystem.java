package Controllers;

import Presenters.EventPresenter;
import Presenters.StatementPresenter;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that allow Users to sign up/cancel spot for an event
 */
public class SignUpSystem {
    EventManager em;
    UserManager um;
    RoomManager rm;
    EventPresenter ep;
    StatementPresenter sp;

    /**
     * Constructor for SignUpSystem
     * @param em the EventManager for this execution of the program
     * @param um the UserManager for this execution of the program
     */
    public SignUpSystem(EventManager em, UserManager um, RoomManager rm) {
        this.em = em;
        this.um = um;
        this.rm = rm;
        this.ep = new EventPresenter(em, um);
        this.sp = new StatementPresenter();
    }

    public void run(String userName){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp = "";

        while (!temp.equals("4")) {
            sp.printStatement("(1) browse events\n(2) sign up for an event\n(3) cancel spot for an " +
                    "event\n(4) exit\nPlease type the corresponding number of the options:");
            try {
                temp = br.readLine();
                switch (temp) {
                    case "2": {
                        sp.printStatement("Type the event title for the event you want to sign up:");
                        String eventTitle = br.readLine();
                        signUpEvent(userName, eventTitle);
                        break;
                    }
                    case "3": {
                        sp.printStatement("Type the event title for the event you want to cancel spot:");
                        String eventTitle = br.readLine();
                        try{cancelSpotEvent(userName, eventTitle);}
                        catch(IllegalArgumentException e){sp.printStatement("The event title you have entered is invalid.");}
                        break;
                    }
                    case "1":
                        String temp2 = "";
                        while(!temp2.equals("3")){
                            sp.printStatement("(1) browse all the events\n(2) browse the events you have signed up\n(3) exit");
                            temp2 = br.readLine();
                            if(temp2.equals("1")){ep.displayEvents();}
                            else if(temp2.equals("2")){ep.displaySignedUpEvents(userName);}
                        }
                        break;
                }
            } catch (IOException e) {
                sp.printStatement("Something went wrong :(");
            }
        }
    }

    /**
     * Method that calls methods in EventManager and UserManager to sign up for an event
     * @param userName the username of this Attendee
     * @param eventTitle the event title of the event that this attendee want to sign up for
     */
    public int signUpEvent(String userName, String eventTitle){
        if (!em.isEventExist(eventTitle)){
            return 3;
        }
        else if (em.isAttendeeAdded(userName, eventTitle)){
            return 1;
        }
        else if(em.isEventFull(eventTitle)){
            return 2;
        }
        else if (!um.isAttendeeVIP(userName) & em.VIP(eventTitle)){
            return 4;
        }
        else{
            em.addAttendee(userName, eventTitle);
            um.signUpEventAttendee(userName, eventTitle);
            um.setAttendeeVIP(userName);
            return 0;
        }
    }

    /**
     * Method that calls methods in EventManager and UserManager to cancel spot for an event
     * @param userName the username of this Attendee
     * @param eventTitle the event title of the event that this attendee want to cancel spot
     */
    public int cancelSpotEvent(String userName, String eventTitle){
        if(!em.isEventExist(eventTitle)){
            return 2;
        }else if(!em.isAttendeeAdded(userName, eventTitle)){
            return 1;
        }
        else{
            em.deleteAttendee(userName, eventTitle);
            um.cancelSpotAttendee(userName, eventTitle);
            um.setAttendeeVIP(userName);
            return 0;
        }
    }

    /**
     * This method gets the event's a username has signed up for
     * @param username List of events that their signed up for already
     * @return A list of events that this username has signed up for
     */
    public String[] displaySignedUpEvents(String username) {
        List<String> events;
        List<String> eventinfo = new ArrayList<>();
        String[] toReturn;
        if (um.userType(username).equals("Speaker")){
            events = um.getEventsSpeaking(username);
            toReturn = events.toArray(new String[0]);
        }else {
            events = um.getEventAttending(username);
            for(String event : events){
                eventinfo.add(em.getEventInfo(event));
            }
            toReturn = eventinfo.toArray(new String[0]);
        }
        return toReturn;
    }

    /**
     * Gets all events possible to display;
     * @return   A list of event info
     */
    public String[] displayAllEvents() {
        List<String> events = em.getAllEventTitle();
        List<String> eventinfo = new ArrayList<>();
        for(String event : events){
            eventinfo.add(em.getEventInfo(event));
        }
        return eventinfo.toArray(new String[0]);
    }

    /**
     * Checks whether username meets the requirement for VIP account or not (attends at least 2 events)
     * @param username the username want to check
     * @return iff the user is VIP
     */
    public boolean userIsVIP(String username) {
        if (um.userType(username).equals("Speaker")){
            return false;
        }else{
            return um.isAttendeeVIP(username);
        }
    }
}
