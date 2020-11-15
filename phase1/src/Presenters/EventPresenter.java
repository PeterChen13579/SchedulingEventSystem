package Presenters;

import UseCase.EventManager;
import UseCase.UserManager;

import java.util.List;


public class EventPresenter {
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
            System.out.println("There are no events yet!");
        }
        for(String event:eventList){
            System.out.println(em.getEventInfo(event));
        }

    }

    /**
     * Display the details for all the events that a user has signed up
     * @param username the username that we want to display all the events he/she has signed up
     */
    public void displaySignedUpEvents(String username){
        List<String> eventList = um.getEventAttending(username);
        if(eventList.isEmpty()){
            System.out.println("You Haven't signed up for any event yet!");
        }
        for(String event:eventList){
            System.out.println(em.getEventInfo(event));
        }

    }

}
