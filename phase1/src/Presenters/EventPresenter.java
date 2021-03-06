package Presenters;

import UseCase.EventManager;
import UseCase.UserManager;

import java.util.List;

/**
 * Presents information by presenting event info to the screen
 */
public class EventPresenter extends StatementPresenter{
    private EventManager em;
    private UserManager um;

    /**
     * Constructor for EventPresenter
     * @param em the EventManager for this execution of the program
     */
    public EventPresenter(EventManager em, UserManager um){
        this.em = em;
        this.um = um;
    }

    /**
     * Display the details for all the events that are scheduled
     */
    public void displayEvents(){
        List<String> eventList = em.getAllEventTitle();
        if(em.getAllEventTitle().isEmpty()){
            printStatement("There are no events yet!");
        }
        for(String event:eventList){
            printStatement(em.getEventInfo(event));
        }

    }

    /**
     * Display the details for all the events that a user has signed up
     * @param username the username that we want to display all the events he/she has signed up
     */
    public void displaySignedUpEvents(String username){
        List<String> eventList = um.getEventAttending(username);
        if(eventList.isEmpty()){
            printStatement("You haven't signed up for any event yet!");
        }
        for(String event:eventList){
            printStatement(em.getEventInfo(event));
        }

    }
}
