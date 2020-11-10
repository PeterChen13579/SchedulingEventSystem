package Controllers;

import UseCase.EventManager;
import UseCase.UserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SignUpSystem {
    private EventManager em;
    private UserManager um;

    SignUpSystem(EventManager eventManager, UserManager userManager) {
        em = eventManager;
        um = userManager;
    }

    public void run(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type 'sign up' to sign up for an event or 'cancel' to cancel spot for an event.");
        try{
            String input = br.readLine();
            System.out.println("Type your user name (plz include only your user name and nothing else)");
            String userName = br.readLine();
            if(input.equals("sign up")){
                System.out.println("Type the event title for the event you want to sign up.");
                String eventTitle = br.readLine();
                signUpEvent(userName, eventTitle);
            }
            if(input.equals("cancel")){
                System.out.println("Type the event title for the event you want to cancel spot.");
                String eventTitle = br.readLine();
                cancelSpotEvent(userName, eventTitle);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong :(");
        }

    }

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

    public void cancelSpotEvent(String userName, String eventTitle){
        boolean canceled = um.cancelSpotAttendee(userName, eventTitle);
        if(canceled){
            System.out.println("You have cancelled the spot for this event.");
        }
        else{
            System.out.println("You haven't signed up for this event yet.");
        }
    }
    }
