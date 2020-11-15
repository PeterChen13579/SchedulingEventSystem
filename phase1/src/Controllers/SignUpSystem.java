package Controllers;

import Presenters.EventPresenter;
import Presenters.StatementPresenter;
import UseCase.EventManager;
import UseCase.UserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SignUpSystem {
    EventManager em;
    UserManager um;
    EventPresenter ep;
    private final StatementPresenter sp = new StatementPresenter();

    /**
     * Constructor for SignUpSystem
     * @param em the EventManager for this execution of the program
     * @param um the UserManager for this execution of the program
     */
    public SignUpSystem(EventManager em, UserManager um) {
        this.em = em;
        this.um = um;
        this.ep = new EventPresenter(em);
    }

    /**
     * Sign up or cancel spot for an event by displaying options and handling user input.
     * This method ends when user want to return to the main menu.
     */
    public void run(String userName){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp = "";

        while (!temp.equals("4")) {
            sp.printStatement("(1) browse the events\n(2) sign up for an event\n(3) cancel spot for an " +
                    "event\n(4) exit\nPlease type the corresponding number of the options:");
            try {
                temp = br.readLine();
                switch (temp) {
                    case "2": {
                        sp.printStatement("Type the event title for the event you want to sign up:");
                        String eventTitle = br.readLine();
                        try{signUpEvent(userName, eventTitle);}
                        catch(IllegalArgumentException e){sp.printStatement("The event title you have entered is invalid.");}
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
                        ep.displayEvents();
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
        if (em.isAttendeeAdded(userName, eventTitle)){
            sp.printStatement("You have signed up for this event before.");
        }
        if(em.canAddAttendee(userName, eventTitle)){
            em.addAttendee(userName, eventTitle);
            um.signUpEventAttendee(userName, eventTitle);
            sp.printStatement("You have successfully signed up for this event.");
        }
        if (!em.roomNotFull(eventTitle)){
            sp.printStatement("The event you have entered is already full.");
        }

    }

    /**
     * Method that calls methods in EventManager and UserManager to cancel spot for an event
     * @param userName the username of this Attendee
     * @param eventTitle the event title of the event that this attendee want to cancel spot
     */
    public void cancelSpotEvent(String userName, String eventTitle){
        if (!em.isAttendeeAdded(userName, eventTitle)){
            sp.printStatement("You haven't signed up for this event yet.");
        }
        if(em.canDeleteAttendee(userName, eventTitle)) {
            em.deleteAttendee(userName, eventTitle);
            um.cancelSpotAttendee(userName, eventTitle);
            sp.printStatement("You have cancelled the spot for this event.");
        }
    }
}
