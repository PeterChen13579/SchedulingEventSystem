package Controllers;

import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;
import Presenters.StatementPresenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SchedulingSystem {
    EventManager em;
    RoomManager rm;
    UserManager um;
    private final StatementPresenter menu = new StatementPresenter();

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
            menu.printStatement("Type '1' to add room; '2' to add new event; '3' to go back to main menu: ");
            try {
                temp = br.readLine();
                if (temp.equals("1")) {
                    menu.printStatement("Please enter the room number you want to add: ");
                    addRoom(br.readLine());
                } else if (temp.equals("2")) {
                    menu.printStatement("Please enter the date for the new event (in the format 'YYYYMMDD'): ");
                    String inputDate = br.readLine();
                    menu.printStatement("Please enter the start time for the new event (24-hour time, between 9AM - 4PM, in the format 'HH:MM:SS'): ");
                    String inputTime = br.readLine();
                    menu.printStatement("Please enter the room number for the new event: ");
                    String inputRoom = br.readLine();
                    menu.printStatement("Please enter the speaker's username for the new event(case sensitive): ");
                    String inputSpeaker = br.readLine();
                    menu.printStatement("Please enter a unique title for the new event: ");
                    String inputTitle = br.readLine();
                    addEvent(inputDate, inputTime, inputRoom, inputSpeaker, inputTitle);
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

    /**
     * Check if the conditions for adding the given event is satisfied and display error messages accordingly.
     * If satisfied, create new event, update speaker's list of events, and print success message.
     * @param date the date for the event (YYYYMMDD)
     * @param time the start time for the event (HH:mm:ss)
     * @param rmNum the room number for the event
     * @param speakerUsername the name of the speaker for the event
     * @param title the title for the event
     */
    private void addEvent (String date, String time, String rmNum, String speakerUsername, String title){
        //check if date is valid format and value
        if (!em.parseStringToLocalDate(date)){
            menu.printStatement("Uh-oh! The date entered is not a valid date or not written in the correct format (YYYYMMDD)!");
        }
        //check if time is valid format and value
        else if (!em.parseStringToLocalTime(time)){
            menu.printStatement("Uh-oh! The date entered is not a valid date or not written in the correct format (HH:MM:SS)!");
        }
        //check if room exists
        else if(!rm.doesRoomExist(rmNum)){
            menu.printStatement("Uh-oh! Room does not exist! Please add this room first!");
        }
        //check if room is booked already at this time
        else if(!em.isRoomAvailableAtTime(rmNum, date, time)){
            menu.printStatement("Uh-oh! Room is already booked at the given time!");
        }
        //check if speaker exists
        else if(!um.isUserExists(speakerUsername)){
            menu.printStatement("Uh-oh! Speaker does not exist! Please create an account for this speaker first!");
        }
        //check if speaker is already giving another talk at this time
        else if(!em.isSpeakerAvailableAtTime(date,time, speakerUsername)){
            menu.printStatement("Uh-oh! The given speaker is already booked for another event at the given time!");
        }
        //check if event title is unique
        else if(!em.isEventTitleUnique(title)){
            menu.printStatement("Uh-oh! The event title has already been taken!");
        }
        //if everything works out
        else if(canAddEvent(date, time, rmNum, speakerUsername, title)){
            //create event
            em.createEvent(title, date, time, rmNum, speakerUsername);
            //update the speaker's list of events
            um.addEventToSpeaker(title, speakerUsername);
            menu.printStatement("Event successfully created!");
        }
    }

    /**
     * Check whether or not the given event can be created satisfying all the requirements detailed in the description.
     * @param date the date for the event (YYYYMMDD)
     * @param time the start time for the event (HH:mm:ss)
     * @param rmNum the room number for the event
     * @param speakerUserName the name of the speaker for the event
     * @param title the title for the event
     * @return true iff the event can be created
     */
    private boolean canAddEvent(String date, String time, String rmNum, String speakerUserName, String title){
        return em.parseStringToLocalDate(date) && em.parseStringToLocalTime(time) && rm.doesRoomExist(rmNum)
                && em.isRoomAvailableAtTime(rmNum, date, time) && um.isUserExists(speakerUserName)
                && em.isSpeakerAvailableAtTime(date, time, speakerUserName) && em.isEventTitleUnique(title);
    }

}
