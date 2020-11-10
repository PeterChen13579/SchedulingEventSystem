package Presenters;

import UseCase.EventManager;

import java.util.List;


public class EventPresenter {
    private EventManager em;

    public EventPresenter(EventManager em){
        this.em = em;
    }

    public void run(){
        List<String> eventList = em.getAllEventTitle();
        for(String event:eventList){
            System.out.println(em.getEventInfo(event));
        }

    }
}
