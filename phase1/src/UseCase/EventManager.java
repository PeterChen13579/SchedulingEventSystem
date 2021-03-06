package UseCase;

import Entities.Event;
import Entities.Room;


import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Manages all the events for the program. Calls methods in Event or Room or RoomManager.
 * @author Xinyi Chen and Xinpeng Shan
 */
public class EventManager implements Serializable {
    private final List<Event> allEvents;

    public EventManager() {
        allEvents = new ArrayList<>();
    }

    public EventManager(List<Event> event) {
        allEvents = event;
    }

    /**
     * Create an Event object based on the parameters and add it into the list of allEvents variable.
     * @param title the title for the event
     * @param date the date for the event (YYYYMMDD)
     * @param startTime the start time for the event (HH:mm:ss)
     * @param rmNum the room number for the event
     * @param speakerUserName the name of the speaker for the event
     */
    public void createEvent(String title, String date, String startTime, String rmNum, String speakerUserName){
        List<LocalDateTime> time = parseStringToLocalDateTime(date, startTime);
        //create event & add to list
        Event event = new Event(title, time.get(0), time.get(1), rmNum, speakerUserName);
        allEvents.add(event);
    }

    /**
     * Returns a list of 2 LocalDateTime object re presenting the start time and end time of a potential event
     * (endTime will automatically be 1 hour after startTime)
     * @param date the date for the potential event (YYYYMMDD)
     * @param startTime the start time for the event (HH:mm:ss)
     * @return a list of LocalDateTime objects parsed from the date and startTime parameters
     */
    public List<LocalDateTime> parseStringToLocalDateTime (String date, String startTime){
        //parse date to LocalDate format
        DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
        LocalDate localDate = LocalDate.parse(date, formatter);

        //parse startTime and endTime to LocalTime format (end time will be 1 hour later)
        LocalTime localStartTime = LocalTime.parse(startTime);
        LocalTime localEndTime = localStartTime.plusHours(1);

        //create LocalDateTime objects for start time and end time
        LocalDateTime sTime = LocalDateTime.of(localDate, localStartTime);
        LocalDateTime eTime = LocalDateTime.of(localDate, localEndTime);

        return Arrays.asList(sTime, eTime);
    }

    /**
     * Returns whether the given date is a valid date written in the form "YYYYMMDD"
     * @param date the date for the potential event (YYYYMMDD)
     * @return true iff the date is a valid date written in the correct format
     */
    public boolean parseStringToLocalDate(String date){
        //source https://www.baeldung.com/java-string-valid-date
        try {
            LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns whether the given time is a valid time written in the form "HH:MM:SS"
     * @param time the time for the potential event (HH:MM:SS)
     * @return true iff the time is a valid time written in the correct format
     */
    public boolean parseStringToLocalTime(String time){
        //source https://www.baeldung.com/java-string-valid-date
        try {
            LocalTime.parse(time);
        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns whether or not the given start time is between 9am and 4pm
     * @param startTime the start time for the potential event (HH:mm:ss)
     * @return true iff the time is within 9am to 4pm
     */
    public boolean isTimeAvailable(String startTime){
        //create LocalDateTime object for potential start time and valid start times
        LocalTime potentialTime = LocalTime.parse(startTime);
        LocalTime validTime9 = LocalTime.parse("09:00:00");
        LocalTime validTime16 = LocalTime.parse("16:00:00");

        if (potentialTime.compareTo(validTime9) == 0 || potentialTime.compareTo(validTime16) == 0){
            return true;
        }else{
            return potentialTime.compareTo(validTime9) > 0 && potentialTime.compareTo(validTime16) < 0;
        }
    }

    /**
     * Returns whether or not the given room is booked at the given date and time
     * @param roomNum the room number for the potential event
     * @param date the date for the potential event (YYYYMMDD)
     * @param startTime the start time for the potential event (HH:mm:ss)
     * @return true iff the room given is not booked by another event at the same time
     */
    public boolean isRoomAvailableAtTime(String roomNum, String date, String startTime){
        LocalDateTime time = parseStringToLocalDateTime(date, startTime).get(0);
        //check if room is booked at the time
        for(Event e: allEvents){
            if (e.getStartTime().isEqual(time) && e.getRoomNum().equals(roomNum)){
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether or not the given speaker is already hosting an event at the given date and time
     * @param date the date for the potential event (YYYYMMDD)
     * @param startTime the start time for the potential event (HH:mm:ss)
     * @param speakerUserName the username for the speaker of the event
     * @return true iff the speaker is not booked for any other event at give date and time
     */
    public boolean isSpeakerAvailableAtTime(String date, String startTime, String speakerUserName){
        LocalDateTime time = parseStringToLocalDateTime(date, startTime).get(0);
        //check if speaker is booked at the time
        for(Event e: allEvents){
            if (e.getStartTime().isEqual(time) && e.getSpeakerUserName().equals(speakerUserName)){
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether or not the given event title is unique
     * @param title the date for the potential event (YYYYMMDD)
     * @return true iff the given event title has not been created before
     */
    public boolean isEventTitleUnique(String title){
        for (Event e: allEvents){
            if (e.getTitle().equals(title)){
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether or not the event exists
     * @param eventTitle the event title that we want to check
     * @return true iff the event with the corresponding event title is in the allEvents list
     */
    public boolean isEventExist(String eventTitle){
        for(Event event:allEvents){
            if (event.getTitle().equals(eventTitle)){return true;}
        }
        return false;
    }

    /**
     * Returns whether or not the username of this attendee is in attendeeList of this event
     * @param userName the username of the attendee that we want to check
     * @param eventTitle the event title that we want to check if the attendee is in
     * @return true iff the username of this attendee is in attendeeList of this event
     */
    public boolean isAttendeeAdded(String userName, String eventTitle){
        Event event = helperEventTitle(eventTitle);
        return event.getAttendeeList().contains(userName);
    }

    /**
     * Returns whether or not we can add the username of this attendee to the attendeeList of this event,
     * calls the above helper methods (isEventExist isAttendeeAdded roomNotFull)
     * @param userName the username of the attendee that we want to add
     * @param eventTitle the event title that we want to check if we can add this attendee
     * @return true iff we can add the username of this attendee to the attendeeList of this event
     */
    public boolean canAddAttendee(String userName, String eventTitle){
        return isEventExist(eventTitle) && !isAttendeeAdded(userName, eventTitle) && roomNotFull(eventTitle);
    }

    /**
     * Returns whether or not we can delete the username of this attendee from the attendeeList of this event,
     * calls the above helper methods (isEventExist isAttendeeAdded)
     * @param userName the username of the attendee that we want to delete
     * @param eventTitle the event title that we want to check if we can delete this attendee
     * @return true iff we can delete the username of this attendee from the attendeeList of this event
     */
    public boolean canDeleteAttendee(String userName, String eventTitle){
        return isEventExist(eventTitle) && isAttendeeAdded(userName, eventTitle);
    }

    /**
     * private helper method for finding corresponding Event base on eventTitle
     * Precondition: eventTitle correspond to a event in event List
     * @param eventTitle the eventTitle of the Event
     * @throws IllegalArgumentException if eventTitle does not correspond to any event in event List
     * @return the event that has this eventTitle
     */
    private Event helperEventTitle(String eventTitle) {
        assert isEventExist(eventTitle);
        for (Event event : allEvents) {
            if (event.getTitle().equals(eventTitle)) {
                return event;
            }
        }
        throw new IllegalArgumentException("eventTitle does not correspond to any event in event List");

    }

    /**
     * Returns whether or not the room of this event is full
     * @param eventTitle the event title that we want to check
     * @return true iff the room is not full
     */
    public boolean roomNotFull(String eventTitle){
        Event event = helperEventTitle(eventTitle);
        int attendeeNum = event.getAttendeeList().size();
        Room exampleRoom = new Room("exampleRoom");
        return exampleRoom.getCapacity() > attendeeNum;
    }

    /**
     * Add attendee to the attendeelist stored in Event
     * @param attendeeUserName the username of attendee that is added to the attendeeList
     * @param eventTitle the event that this attendee sign up to
     */
    public void addAttendee(String attendeeUserName, String eventTitle){
        Event event = helperEventTitle(eventTitle);
        List<String> currAttendee = event.getAttendeeList();
        currAttendee.add(attendeeUserName);
        event.setAttendeeList(currAttendee);
    }

    /**
     * Delete attendee from the attendeeList stored in Event
     * @param attendeeUserName the username of attendee that is is deleted from the attendeeList
     * @param eventTitle the event that this attendee want to cancel spot from
     */
    public void deleteAttendee(String attendeeUserName, String eventTitle){
        Event event = helperEventTitle(eventTitle);
        List<String> currAttendee = event.getAttendeeList();
        currAttendee.remove(attendeeUserName);
        event.setAttendeeList(currAttendee);
    }

    /**
     * Get a list of all the event titles that are scheduled
     * @return a list of all the event titles that are booked
     */
    public List<String> getAllEventTitle(){
        List<String> eventList = new ArrayList<>();
        for(Event event: allEvents){
            eventList.add(event.getTitle());
        }
        return eventList;
    }

    /**
     * Get a string representation for the given event title
     * @param eventTitle the title for the event
     * @return a string with the details about the event with given event title
     */
    public String getEventInfo(String eventTitle){
        Event event = helperEventTitle(eventTitle);
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
        String startTime = event.getStartTime().format(formatter);
        String endTime = event.getEndTime().format(formatter);
        String speaker = event.getSpeakerUserName();
        String roomNum = event.getRoomNum();
        return eventTitle + ": " + startTime + " - " + endTime + ", in Room " + roomNum +
                ". Speaker: " + speaker;
    }

    /**
     * Get a list of all the usernames of attendees for the given event title
     * @return a list of all the attendee usernames for the given event title
     */
    public List<String> getAllAttendeesByTitle(String title){
        for(Event event: allEvents) {
            if (event.getTitle().equals(title)) {
                return event.getAttendeeList();
            }
        }
        throw new IllegalArgumentException("The given title does not correspond to any event in the event list.");
    }

    /**
     * Get speaker username for the given event title
     * @return the speaker username for the given event title
     */
    public String getSpeakerUsernameByTitle(String eventTitle){
        Event event = helperEventTitle(eventTitle);
        return event.getSpeakerUserName();
    }
}
