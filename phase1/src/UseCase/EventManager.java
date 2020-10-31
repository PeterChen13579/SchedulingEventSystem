package UseCase;

import Entities.Event;
import Entities.Room;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class EventManager {
    private List<Event> allEvents;

    /**---ask lisa if have concerns
     * Create an Event object based on the parameters and add it into the list of allEvents variable.
     * @param title the title for the event
     * @param date the date for the event (YYYYMMDD)
     * @param startTime the start time for the event (HH:mm:ss)
     * @param rmNum the room number for the event
     * @param speakerUserName the name of the speaker for the event
     */
    public void createEvent(String title, String date, String startTime, String rmNum, String speakerUserName){
        //parse date to LocalDate format
        DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
        LocalDate localDate = LocalDate.parse(date, formatter);
        //parse startTime and endTime to LocalTime format
        LocalTime localStartTime = LocalTime.parse(startTime);
        //create LocalDateTime object
        LocalDateTime time = LocalDateTime.of(localDate, localStartTime);

        //create event & add to list
        Event event = new Event(title, time, rmNum, speakerUserName);
        allEvents.add(event);
    }

    public boolean isTimeAvaliable();

    public boolean isSpeakerAvaliable();

    public boolean isRoomAvaliable();

    //returns true if can add event, calls the above 3 helper methods
    public boolean canAddEvent();

    /**
     * Getter for List of all Events
     * @return List of all Events
     */
    public List<Event> getAllEvents() {
        return allEvents;
    }

    /**
     * Add attendee to the attendeelist stored in Event
     * @param attendeeUserName the username of attendee that is added to the attendeeList
     * @param eventTitle the event that this attendee sign up to
     * @return true iff attendee added successfully, false otherwise.
     */
    public boolean addAttendee(String attendeeUserName, String eventTitle){
        Event event = helperEventTitle(eventTitle);
        List<String> currAttendee = event.getAttendeeList();
        currAttendee.add(attendeeUserName);
        event.setAttendeeList(currAttendee);
        return true;
    }

    /**
     * private helper method for finding corresponding Event base on eventTitle
     * @param eventTitle the eventTitle of the Event
     * @return the event that has this eventTitle
     */
    private Event helperEventTitle(String eventTitle){
        for(Event event:allEvents){
                if(event.getTitle().equals(eventTitle)){
                return event;
            }
        }
    }

    /**
     * Delete attendee from the attendeelist stored in Event
     * @param attendeeUserName the username of attendee that is is deleted from the attendeeList
     * @param eventTitle the event that this attendee want to cancel spot from
     */
    public void deleteAttendee(String attendeeUserName, String eventTitle){
        Event event = helperEventTitle(eventTitle);
        List<String> currAttendee = event.getAttendeeList();
        currAttendee.remove(attendeeUserName);
        event.setAttendeeList(currAttendee);
    }
}
