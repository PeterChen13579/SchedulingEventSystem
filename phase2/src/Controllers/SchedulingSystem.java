package Controllers;

import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;
import Presenters.StatementPresenter;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * A controller class that interacts with use cases and presenters to prompt and
 * allow the user to schedule an event or add a room.
 * @author Xinyi Chen, Xinpeng Shan(phase 2 changes)
 */
public class SchedulingSystem {
    EventManager em;
    RoomManager rm;
    UserManager um;
    StatementPresenter menu = new StatementPresenter();

    /**
     * Constructor for SchedulingSystem
     * @param eventManager the EventManager for this execution of the program
     * @param roomManager the RoomManager for this execution of the program
     * @param userManager the UserManager for this execution of the program
     */
    public SchedulingSystem(EventManager eventManager, RoomManager roomManager, UserManager userManager) {
        em = eventManager;
        rm = roomManager;
        um = userManager;
    }

    /**
     * Execute the schedule event process by displaying options and handling user input.
     * This method ends when user wants to return to main menu.
     */
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//reads from input efficiently
        String temp = "";//input is being added to temp

        while (!temp.equals("3")) {
            menu.printStatement("(1) add room;\n(2) add new event;\n (3) go back to main menu ");
            try {
                temp = br.readLine();
                if (temp.equals("1")) {
                    menu.printStatement("Please enter the room number you want to add: ");
                    addRoom(br.readLine());
                } else if (temp.equals("2")) {
                    String inputEventType = "";
                    while(!inputEventType.equals("4")) {
                        menu.printStatement("Please enter the type of event you want to create:\n" +
                                "(1) a talk (one-speaker)\n(2) a panel (multi-speaker)\n(3) a party (no speaker)\n(4) exit");
                        inputEventType = br.readLine();
                        addEvent(inputEventType);
                    }
                }
            } catch (IOException e) {
                menu.printStatement("Oops! Something unexpected happened!");
            }
        }
    }

    /**
     * Check if the conditions for adding the given room is satisfied and display error messages accordingly.
     * If satisfied, create new room and print success message.
     * @param rmNum the room number for the event
     */
    private void addRoom(String rmNum){
        //check if room already exists
        if (rm.doesRoomExist(rmNum)){
            menu.printStatement("Room already exists!");
        }else{
            //create the new room
            rm.createRoom(rmNum);
            menu.printStatement("Room successfully created!");
        }
    }

    private void addEvent(String EventType){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            menu.printStatement("Please enter 'VIP' if you want to create a vip event, otherwise, leave it blank");
            boolean inputVIP = br.readLine().equals("VIP");
            menu.printStatement("Please enter the date for the new event (in the format 'YYYYMMDD'): ");
            String inputDate = br.readLine();
            menu.printStatement("Please enter the start time for the new event (24-hour time, in the format 'HH:MM:SS'): ");
            String inputStartTime = br.readLine();
            menu.printStatement("Please enter the end time for the new event (24-hour time, in the format 'HH:MM:SS'): ");
            String inputEndTime = br.readLine();
            menu.printStatement("Please enter the room number for the new event: ");
            String inputRoom = br.readLine();
            menu.printStatement("Please enter a unique title for the new event: ");
            String inputTitle = br.readLine();
            List<String> inputSpeakerList= new ArrayList<>();
            if(EventType.equals("1")){
                menu.printStatement("Please enter the speaker's username for the new talk: ");
                inputSpeakerList.add(br.readLine());
            }
            else if(EventType.equals("2")){
                menu.printStatement("Please enter the speaker's usernames for the new talk: " +
                        "\n(Please type different speaker usernames in separate lines, click enter twice to end)");
                while (!br.readLine().isEmpty() ){ inputSpeakerList.add(br.readLine());}
            }
            addEvent(inputVIP, inputDate, inputStartTime,inputEndTime, inputRoom, inputSpeakerList, inputTitle);
        }catch (IOException e){
            menu.printStatement("Oops! Something unexpected happened!");
        }
    }



    /**
     * Check if the conditions for adding the given event is satisfied and display error messages accordingly.
     * If satisfied, create new event, update speaker's list of events, and print success message.
     * @param date the date for the event (YYYYMMDD)
     * @param startTime the start time for the event (HH:mm:ss)
     * @param endTime the end time for the event (HH:mm:ss)
     * @param rmNum the room number for the event
     * @param speakerUsernames the names of the speaker for the event
     * @param title the title for the event
     */
    private void addEvent (Boolean VIP, String date, String startTime, String endTime, String rmNum, List<String> speakerUsernames, String title){

        //check if date is valid format and value
        if (!em.parseStringToLocalDate(date)){
            menu.printStatement("Uh-oh! The date entered is not a valid date or not written in the correct format (YYYYMMDD)!");
        }
        //check if time is valid format and value
        else if (!em.parseStringToLocalTime(startTime)){
            menu.printStatement("Uh-oh! The start time entered is not a valid date or not written in the correct format (24-hour time, HH:MM:SS)!");
        }
        //check if room exists
        else if(!rm.doesRoomExist(rmNum)){
            menu.printStatement("Uh-oh! Room does not exist! Please add this room first!");
        }
        //check if room is booked already at this time
        else if(!em.isRoomAvailableAtTime(rmNum, date, startTime, endTime)){
            menu.printStatement("Uh-oh! Room is already booked at the given time!");
        }
        //check if speaker exists
        else if(!helperAreSpeakersExist(speakerUsernames)){
            menu.printStatement("Uh-oh! One or More Speakers you entered does not exist! Please create an account for these speakers first!");
        }
        //check if speaker is already giving another talk at this time
        else if(!helperAreSpeakersAvailable(date,startTime, endTime, speakerUsernames)){
            menu.printStatement("Uh-oh! One or More Speakers you entered is already booked for another event at the given time!");
        }
        //check if event title is unique
        else if(!em.isEventTitleUnique(title)){
            menu.printStatement("Uh-oh! The event title has already been taken!");
        }
        //if everything works out
        else if(canAddEvent(date, startTime, endTime, rmNum, speakerUsernames, title)){
            em.createEvent(VIP, title, date, startTime, endTime, rmNum, speakerUsernames);
            //update the speaker's list of events
            for (String speakerUsername : speakerUsernames){ um.addEventToSpeaker(title, speakerUsername);}
            menu.printStatement("Event successfully created!");}
        }

    /**
     * Helper method to check if all speakers in the list exists
     * @param speakerUsernames the list of names of the speakers for the event
     * @return true iff all speakers in the list exists
     */
    private Boolean helperAreSpeakersExist(List<String> speakerUsernames){
        for(String speakerUsername : speakerUsernames){
            if(!um.isUserExists(speakerUsername)){
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method to check if all speakers in the list are available
     * @param speakerUsernames the list of names of the speakers for the event
     * @return true iff all speakers in the list are available
     */
    private Boolean helperAreSpeakersAvailable(String date, String startTime, String endTime, List<String> speakerUsernames){
        for(String speakerUsername : speakerUsernames){
            if(!em.isSpeakerAvailableAtTime(date,startTime,endTime, speakerUsername)){
                return false;
            }
        }
        return true;
    }


    /**
     * Check whether or not the given event can be created satisfying all the requirements detailed in the description.
     * @param date the date for the event (YYYYMMDD)
     * @param startTime the start time for the event (HH:mm:ss)
     * @param endTime the end time for the event (HH:mm:ss)
     * @param rmNum the room number for the event
     * @param speakerUsernames the list of names of the speakers for the event
     * @param title the title for the event
     * @return true iff the event can be created
     */
    private boolean canAddEvent(String date, String startTime, String endTime, String rmNum, List<String> speakerUsernames, String title){
        return em.parseStringToLocalDate(date) && em.parseStringToLocalTime(startTime) && em.parseStringToLocalTime(endTime)
                && rm.doesRoomExist(rmNum) && em.isRoomAvailableAtTime(rmNum, date, startTime, endTime)
                && helperAreSpeakersExist(speakerUsernames) && helperAreSpeakersAvailable(date, startTime, endTime, speakerUsernames)
                && em.isEventTitleUnique(title);
    }

}
