package UseCase;

import Entities.Event;
import Entities.Room;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class EventManager {
    private List<Event> allEvents;

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
     * Returns a list of 2 LocalDateTime object representating the start time and end time of a potential event
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
     * Returns whether or not the given date and time already has an event booked in that interval
     * @param date the date for the potential event (YYYYMMDD)
     * @param startTime the start time for the potential event (HH:mm:ss)
     * @return true iff there is no date or time conflict with the already scheduled events and the new time
     */
    public boolean isTimeAvailable(String date, String startTime){
        //create LocalDateTime object for potential start time
        LocalDateTime time = parseStringToLocalDateTime(date, startTime).get(0);
        for (Event e : allEvents){
            if (e.getStartTime().isEqual(time)){
                return false;
            }
        }
        return true;
    }
    //get a list of all the rooms that were booked
    public List<String> getListOfRooms(){
        List<String> rooms = new ArrayList<>();
        for (Event e : allEvents){
            if(!rooms.contains(e.getRoomNum())){
                rooms.add(e.getRoomNum());
            }
        }
        return rooms;
    }

    //checks if room exists
    public boolean doesRoomExist(String roomNum){
        for(String room: getListOfRooms()){
            if(room.equals(roomNum)) return true;
        }
        return false;
    }
    //checks if room exists and is available at the given time
    public boolean isRoomAvailableAtTime(String roomNum, String date, String startTime){
        LocalDateTime time = parseStringToLocalDateTime(date, startTime).get(0);
        //check if room exists
        if (!doesRoomExist(roomNum)){
            return false;
        }
        //check if room is booked at the time
        for(Event e: allEvents){
            if (e.getStartTime().isEqual(time) && e.getRoomNum().equals(roomNum)){
                return false;
            }
        }
        return true;
    }
    //get a list of all the speakers that were going to perform
    public List<String> getListOfSpeakers(){
        List<String> speakers = new ArrayList<>();
        for (Event e : allEvents){
            if(!speakers.contains(e.getSpeakerUserName())){
                speakers.add(e.getSpeakerUserName());
            }
        }
        return speakers;
    }

    //checks if speaker exists
    public boolean doesSpeakerExist(String speakerUserName){
        for(String speaker: getListOfSpeakers()){
            if(speaker.equals(speakerUserName)) return true;
        }
        return false;
    }

    //checks if speaker is available at the given time
    public boolean isSpeakerAvailableAtTime(String date, String startTime, String speakerUserName){
        LocalDateTime time = parseStringToLocalDateTime(date, startTime).get(0);
        //checks if speaker exists
        if (!doesSpeakerExist(speakerUserName)){
            return false; //throw exception???
        }
        //check if speaker is booked at the time
        for(Event e: allEvents){
            if (e.getStartTime().isEqual(time) && e.getSpeakerUserName().equals(speakerUserName)){
                return false;
            }
        }
        return true;
    }

    //returns true iff can add event, calls the above helper methods
    public boolean canAddEvent(String date, String startTime, String rmNum, String speakerUserName){
        return isTimeAvailable(date, startTime) && isRoomAvailableAtTime(rmNum, date, startTime) &&
                isSpeakerAvailableAtTime(date, startTime, speakerUserName);
    }

    //checks if the event title is unique
    public boolean isEventTitleUnique(String title){
        for (Event e: allEvents){
            if (e.getTitle() == title){
                return false;
            }
        }
        return true;
    }

    /**
     * Getter for List of all Events
     * @return List of all Events
     */
    public List<Event> getAllEvents() {
        return allEvents;
    }


    //return true if the event is in allEvents
    private boolean isEventExist(String eventTitle){
        for(Event event:allEvents){
            if (event.getTitle().equals(eventTitle)){return true;}
        }
        return false;
    }

    //return true if the attendee is already in attendeeList stored in this event
    private boolean isAttendeeAdded(String userName, String eventTitle){
        Event event = helperEventTitle(eventTitle);
        return event.getAttendeeList().contains(userName);
    }

    //return true if can add attendee. calls the above helper methods
    public boolean canAddAttendee(String userName, String eventTitle){
        return isEventExist(eventTitle) && !isAttendeeAdded(userName, eventTitle);
    }


    //return true if can delete attendee. calls the above helper methods
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
