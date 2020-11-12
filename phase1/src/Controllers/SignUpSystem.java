package Controllers;

import Presenters.EventPresenter;
import UseCase.EventManager;
import UseCase.UserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SignUpSystem {
    private EventManager em;
    private UserManager um;
    private EventPresenter ep;

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
    public void run(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp = "";

        while (!temp.equals("exit")) {
            System.out.println("Type 'view' to browse the events\n 'sign up' to sign up for an event\n 'cancel' to cancel spot for an event\n 'exit' to get back to the main menu:");
            try {
                temp = br.readLine();
                System.out.println("Type your username:");
                String userName = br.readLine();
                while(!um.isUserExists(userName)){
                    System.out.println("Plz enter a valid username:");
                    userName = br.readLine();
                }
                if (temp.equals("sign up")) {
                    System.out.println("Type the event title for the event you want to sign up:");
                    String eventTitle = br.readLine();
                    signUpEvent(userName, eventTitle);
                } else if (temp.equals("cancel")) {
                    System.out.println("Type the event title for the event you want to cancel spot:");
                    String eventTitle = br.readLine();
                    cancelSpotEvent(userName, eventTitle);
                } else if (temp.equals("view")){
                    ep.displayEvents();
                }
            } catch (IOException e) {
                System.out.println("Something went wrong :(");
            }
        }
    }

    /**
     * Method that calls methods in EventManager and UserManager to sign up for an event
     * @param userName the username of this Attendee
     * @param eventTitle the event title of the event that this attendee want to sign up for
     */
    public void signUpEvent(String userName, String eventTitle){
        if(em.canAddAttendee(userName, eventTitle)){
            em.addAttendee(userName, eventTitle);
            um.signUpEventAttendee(userName, eventTitle);
            System.out.println("You have successfully signed up for this event.");
        }
        if (!em.isEventExist(eventTitle)){
            System.out.println("The event title you have entered is invalid.");
        }
        if (em.isAttendeeAdded(userName, eventTitle)){
            System.out.println("You have signed up for this event before.");
        }
        if (!em.roomNotFull(eventTitle)){
            System.out.println("The event you have entered is already full.");
        }

    }

    /**
     * Method that calls methods in EventManager and UserManager to cancel spot for an event
     * @param userName the username of this Attendee
     * @param eventTitle the event title of the event that this attendee want to cancel spot
     */
    public void cancelSpotEvent(String userName, String eventTitle){
        if(em.canDeleteAttendee(userName, eventTitle)) {
            em.deleteAttendee(userName, eventTitle);
            um.cancelSpotAttendee(userName, eventTitle);
            System.out.println("You have cancelled the spot for this event.");
        }
        if (!em.isEventExist(eventTitle)){
            System.out.println("The event title you have entered is invalid.");
        }
        if (!em.isAttendeeAdded(userName, eventTitle)){
            System.out.println("You haven't signed up for this event before.");
        }
    }
}
