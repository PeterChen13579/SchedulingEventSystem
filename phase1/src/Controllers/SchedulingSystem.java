package Controllers;

import Entities.User;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SchedulingSystem {
    EventManager em;
    RoomManager rm;
    UserManager um;

    //need these?
    public void setEventManager(EventManager em){
        this.em = em;
    }
    public void setRoomManager(RoomManager rm){
        this.rm = rm;
    }
    public void setUserManager(UserManager um){
        this.um = um;
    }

    /**
     * Execute the schedule event process by displaying options and handling user input.
     * This method ends when user wants to return to main menu.
     */
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//reads from input efficiently
        String temp = "";//input is being added to temp

        while (!temp.equals("3")) {
            System.out.println("Type '1' to add room; '2' to add new event; '3' to go back to main menu: ");
            try {
                temp = br.readLine();
                if (temp.equals("1")) {
                    System.out.println("Please enter the room number you want to add: ");
                    addRoom(br.readLine());
                } else if (temp.equals("2")) {
                    System.out.println("Please enter the date for the new event (in the format 'YYYYMMDD'): ");
                    String inputDate = br.readLine();
                    System.out.println("Please enter the start time for the new event (in the format 'HH:MM:SS'): ");
                    String inputTime = br.readLine();
                    System.out.println("Please enter the room number for the new event: ");
                    String inputRoom = br.readLine();
                    System.out.println("Please enter the speaker's username for the new event(case sensitive): ");
                    String inputSpeaker = br.readLine();
                    System.out.println("Please enter a unique title for the new event: ");
                    String inputTitle = br.readLine();
                    addEvent(inputDate, inputTime, inputRoom, inputSpeaker, inputTitle);
                }
            } catch (IOException e) {
                System.out.println("Oops! Something unexpected happened!");
            }
        }
    }

    /**
     * Check if the conditions for adding the given room is satisfied and display error messages accordingly.
     * If satisfied, create new room and print success message.
     * @param rmNum the room number for the event
     */
    public void addRoom(String rmNum){
        //check if room already exists
        if (rm.doesRoomExist(rmNum)){
            System.out.println("Room already exists!");
        }else{
            //create the new room
            rm.createRoom(rmNum);
            System.out.println("Room successfully created!");
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
    public void addEvent (String date, String time, String rmNum, String speakerUsername, String title){
        //check if date is valid format and value
        if (!em.parseStringToLocalDate(date)){
            System.out.println("Uh-oh! The date entered is not a valid date or not written in the correct format (YYYYMMDD)!");
        }
        //check if time is valid format and value
        else if (!em.parseStringToLocalTime(time)){
            System.out.println("Uh-oh! The date entered is not a valid date or not written in the correct format (HH:MM:SS)!");
        }
        //check if room exists
        else if(!rm.doesRoomExist(rmNum)){
            System.out.println("Uh-oh! Room does not exist! Please add this room first!");
        }
        //check if room is booked already at this time
        else if(!em.isRoomAvailableAtTime(rmNum, date, time)){
            System.out.println("Uh-oh! Room is already booked at the given time!");
        }
        //check if speaker exists
        else if(!um.doesSpeakerExist(speakerUsername)){
            System.out.println("Uh-oh! Speaker does not exist! Please create an account for this speaker first!");
        }
        //check if speaker is already giving another talk at this time
        else if(!em.isSpeakerAvailableAtTime(date,time, speakerUsername)){
            System.out.println("Uh-oh! The given speaker is already booked for another event at the given time!");
        }
        //check if event title is unique
        else if(!em.isEventTitleUnique(title)){
            System.out.println("Uh-oh! The event title has already been taken!");
        }
        //if everything works out
        else if(canAddEvent(date, time, rmNum, speakerUsername, title)){
            //create event
            em.createEvent(title, date, time, rmNum, speakerUsername);
            //update the speaker's list of events
            um.addEventToSpeaker(title, speakerUsername);
            System.out.println("Event successfully created!");
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
    public boolean canAddEvent(String date, String time, String rmNum, String speakerUserName, String title){
        return em.parseStringToLocalDate(date) && em.parseStringToLocalTime(time) && rm.doesRoomExist(rmNum)
                && em.isRoomAvailableAtTime(rmNum, date, time) && um.doesSpeakerExist(speakerUserName)
                && em.isSpeakerAvailableAtTime(date, time, speakerUserName) && em.isEventTitleUnique(title);
    }

}
