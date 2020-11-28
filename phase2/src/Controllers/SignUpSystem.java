package Controllers;

import Presenters.EventPresenter;
import Presenters.StatementPresenter;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    /**
     * Sign up or cancel spot for an event by displaying options and handling user input.
     * This method ends when user want to return to the main menu.
     */
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
    public void signUpEvent(String userName, String eventTitle){
        if (!em.isEventExist(eventTitle)){
            sp.printStatement("The event title you have entered is invalid.");
        }
        else if (em.isAttendeeAdded(userName, eventTitle)){
            sp.printStatement("You have signed up for this event before.");
        }
        else if(em.isEventFull(userName)){
            sp.printStatement("Sorry, this event has reached its maximum capacity.");
        }
        else if (!um.isAttendeeVIP(userName) & em.VIP(eventTitle)){
            sp.printStatement("Sorry, you are not a VIP user, so you cannot sign up for a VIP event.");
        }
        else{
            em.addAttendee(userName, eventTitle);
            um.signUpEventAttendee(userName, eventTitle);
            um.setAttendeeVIP(userName);
            sp.printStatement("You have successfully signed up for this event!");
        }
    }

    /**
     * Method that calls methods in EventManager and UserManager to cancel spot for an event
     * @param userName the username of this Attendee
     * @param eventTitle the event title of the event that this attendee want to cancel spot
     */
    public void cancelSpotEvent(String userName, String eventTitle){
        if(!em.isEventExist(eventTitle)){
            sp.printStatement("The event title you have entered is invalid.");
        }else if(!em.isAttendeeAdded(userName, eventTitle)){
            sp.printStatement("You haven't signed up for this event yet.");
        }
        else{
            em.deleteAttendee(userName, eventTitle);
            um.cancelSpotAttendee(userName, eventTitle);
            um.setAttendeeVIP(userName);
            sp.printStatement("You have cancelled the spot for this event!");
        }
    }
}
