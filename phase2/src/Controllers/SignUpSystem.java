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
                        if(signUpEvent(userName, eventTitle) == 0){
                            em.addAttendee(userName, eventTitle);
                            um.signUpEventAttendee(userName, eventTitle);
                            um.setAttendeeVIP(userName);
                        }
                        break;
                    }
                    case "3": {
                        sp.printStatement("Type the event title for the event you want to cancel spot:");
                        String eventTitle = br.readLine();
                        if(cancelSpotEvent(userName, eventTitle)==0) {
                            em.deleteAttendee(userName, eventTitle);
                            um.cancelSpotAttendee(userName, eventTitle);
                            um.setAttendeeVIP(userName);
                        }
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
     * @return the number used for implementing GUI
     */
    public int signUpEvent(String userName, String eventTitle){
        if (!em.isEventExist(eventTitle)){
            //sp.printStatement("The event title you have entered is invalid.");
            return 3;
        }
        else if (em.isAttendeeAdded(userName, eventTitle)){
            //sp.printStatement("You have signed up for this event before.");
            return 1;
        }
        else if(em.isEventFull(eventTitle)){
            //sp.printStatement("Sorry, this event has reached its maximum capacity.");
            return 2;
        }
        else if (!um.isAttendeeVIP(userName) & em.VIP(eventTitle)){
            //sp.printStatement("Sorry, you are not a VIP user, so you cannot sign up for a VIP event.");
            return 4;
        }
        else{
            em.addAttendee(userName, eventTitle);
            um.signUpEventAttendee(userName, eventTitle);
            um.setAttendeeVIP(userName);
            sp.printStatement("You have successfully signed up for this event!");
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
            //sp.printStatement("The event title you have entered is invalid.");
            return 2;
        }else if(!em.isAttendeeAdded(userName, eventTitle)){
            //sp.printStatement("You haven't signed up for this event yet.");
            return 1;
        }
        else{
            em.deleteAttendee(userName, eventTitle);
            um.cancelSpotAttendee(userName, eventTitle);
            um.setAttendeeVIP(userName);
            sp.printStatement("You have cancelled the spot for this event!");
            return 0;
        }
    }
}
