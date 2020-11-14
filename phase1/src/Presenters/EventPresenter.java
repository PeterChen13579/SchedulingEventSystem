package Presenters;

import UseCase.EventManager;

import java.util.List;


public class EventPresenter {
    private EventManager em;

    /**
     * Constructor for EventPresenter
     * @param em the EventManager for this execution of the program
     */
    public EventPresenter(EventManager em){
        this.em = em;
    }

    /**
     * Display the details for all the events that are scheduled
     */
    public void displayEvents(){
        List<String> eventList = em.getAllEventTitle();
        if(em.getAllEventTitle().isEmpty()){
            System.out.println("There are no event yet!");
        }
        for(String event:eventList){
            System.out.println(em.getEventInfo(event));
        }

    }
}
