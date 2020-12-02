package Controllers;

import GUI.Dashboard;
import GUI.Viewable;
import Presenters.EventPresenter;
import UseCase.ChatManager;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Determines all the behaviour for the text-based UI
 * @author Joyce Huang, Peter Chen, and Amy Miao
 */
public class TechConferenceSystem implements Viewable{

    private LoginSystem loginSystem;
    private MessagingSystem messagingSystem;
    private SchedulingSystem schedulingSystem;
    private SignUpSystem signUpSystem;
    private UserManager userManager;
    private ChatManager chatManager;
    private EventManager eventManager;
    private RoomManager roomManager;
    private final Dashboard dashboard;


    /**
     * Constructor for the entire controller, begins the program
     */
    public TechConferenceSystem(final Dashboard dashboard){
        this.dashboard = dashboard;
        dashboard.setView(this);
        createProgram();
    }

    /**
     * A method that determines if an attendee account can be successfully created or not.
     *
     * @param username  The username the user wants to create
     * @param password  The Password the user wants for their account
     * @return          True if Attendee account successfully created. False Otherwise
     */
    public boolean createAttendeeButton(String username, String password){
        return userManager.createAttendeeAccount(username, password);
    }

    /**
     * A method that determines if an organizer account can be successfully created or not.
     *
     * @param username  The username the user wants to create
     * @param password  The Password the user wants for their account
     * @return          True if Organizer account successfully created. False Otherwise
     */
    public boolean createOrganizerButton(String username, String password){
        return userManager.createOrganizerAccount(username, password);
    }

    /**
     * A method that determines if a Speaker account can be successfully created or not.
     *
     * @param username  The username the user wants to create
     * @param password  The Password the user wants for their account
     * @return          True if Speaker account successfully created. False Otherwise
     */
    public boolean createSpeakerButton(String username, String password){
        return userManager.createSpeakerAccount(username, password);
    }


    /**
     * A method that loads an existing conference; Method runs when the user clicks the "load conference" button
     */
    public boolean loadConferenceButton(String filename){
        Reader reader = new Reader();
        if (reader.verifySaves(filename)) {
            Object loadedObjects[] = reader.loadData(filename);
            chatManager = (ChatManager) loadedObjects[0];
            eventManager = (EventManager) loadedObjects[1];
            roomManager = (RoomManager) loadedObjects[2];
            userManager = (UserManager) loadedObjects[3];
        } else {
            return false;
        }
        initializeManagers();
        return true;
    }


    /**
     *
     * @param username   Username that the user wants to login with
     * @param password   Password that the user wants to login with
     * @return          False if username is already in database or invalid. True otherwise.
     */
    public String LogInButton(String username, String password){
        String userType = userManager.userType(username);
        if (userType.equals("Invalid Username")){
            return "false";
        }else{
            if (loginSystem.verifyLogin(username, password)){
                return loginSystem.verifyUserType(username);
            }else{
                return "false";
            }
        }
    }


//--------------------------------------------Messaging Buttons-----------------------------------------

    /**
     * TO @William Wang and Kailas Moon; This method is for sending the info of ALL chat usernames to
     * display on GUI; Add in the number AND username for this; ie. [1.kailas, 2.william]
     */
    @Override
    public ArrayList <String> sendChatName(){
        return null;
    }


    /**
     *
     * @param chatNumber The chat number that the user wants to view
     * @return           Return String that the User wants to view; IN VIEW CHAT NUMBER: Return "false" if can not find
     *                   chat number;
     */
    @Override
    public String viewChatMsg(int chatNumber){
        return null;
    }

    @Override
    public void msgAllAttendees(String msg){

    }

    @Override
    public void msgAllSpeakers(String msg){

    }

    @Override
    public void msgAllAttendeeEvent(String msg){

    }


    /** TO @William Wang and Kailas Moon
     *
     * @param username    The username this user wants to msg
     * @param content     The text this username wants to send
     * @return   True if successfully sent. False otherwise(username does not exist);
     */

    @Override
    public boolean sendOneMsg(String username, String content) {
        return false;
    }

    /** TO @William Wang and Kailas Moon
     *
     * @return   All new Messages (View all new messages option)
     */
    @Override
    public String getNewMessages(){
        return null;
    }

    /** TO @William Wang and Kailas Moon
     *
     * @param username    The username this user wants to add
     * @return            "true" if successfully added.
     *                    "You have already added this username" if username is already on this user's friend list.
     *                    "Username entered is not in database." if username is not in database.
     */
    @Override
    public String addFriend(String username){
        return null;
    }


//-----------------------------------------Scheduling Buttons-------------------------------------------

    /**
     * Perform necessary checks & operations for cancelling an event.
     * @param   title the event name entered
     * @param   username the username of the organizer that chose to cancel this event
     * @return  true if successfully canceled event, False Otherwise.
     */
    @Override
    public boolean cancelEvent(String title, String username){
        schedulingSystem.cancelEvent(title, username);
    }


    /**
     * Perform necessary checks & operations for changing the capacity of an event.
     * @param eventName   String event name to change capacity
     * @param capacity    capacity to change to
     * @param username    the username of the organizer that chose to change the capacity of this event
     * @return            true if successfully changed, false otherwise.
     */
    @Override
    public boolean changeCapacity(String eventName, int capacity, String username){
        schedulingSystem.changeCapacity(eventName, capacity, username);
    }

    //return true iff added the room, return false otherwise
    public boolean confirmRoom(String roomNumber, int capacity){
        schedulingSystem.addRoom(roomNumber, capacity);
    }


    /**
     * Check if the conditions for adding the given event is satisfied and return error messages accordingly.
     * If satisfied, create new event, update speaker's list of events, and print success message.
     * @param VIP whether the event is of type VIP
     * @param date the date for the event (YYYYMMDD)
     * @param startTime the start time for the event (HH:mm:ss)
     * @param endTime the end time for the event (HH:mm:ss)
     * @param roomNum the room number for the event
     * @param speakerUsernames the names of the speakers for the event
     * @param eventTitle the title for the event
     * @param capacity the maximum/capacity of people that can attend this event
     * @return the error message according to the error or "true" if event successfully created
     */
    public String createSpeakerEvent(boolean VIP, String date, String startTime, String endTime, String roomNum, List<String>
            speakerUsernames, String eventTitle, int capacity){
        return schedulingSystem.helper_addEvent(VIP, date, startTime, endTime, roomNum, speakerUsernames, eventTitle, capacity);

    }

//--------------------------------------------Sign Up Buttons-----------------------------------------

    public String[] displayAllEvents() {
        List<String> events = eventManager.getAllEventTitle();
        String[] toReturn = events.toArray(new String[0]);
        return toReturn;
    }

    public String[] displaySignedUpEvents(String username) {
        List<String> events;
        if (userManager.userType(username).equals("Speaker")){
            events = userManager.getEventsSpeaking(username);
        }else {
            events = userManager.getEventAttending(username);
        }
        String[] toReturn = events.toArray(new String[0]);
        return toReturn;
    }

    /**
     *
     * @param username
     * @param eventTitle
     * @return
     */
    public int signUpForEvent(String username, String eventTitle) {
        if (!eventManager.isEventExist(eventTitle)){
            return 3;
        }
        else if (eventManager.isAttendeeAdded(username, eventTitle)){
            return 1;
        }
        else if(eventManager.isEventFull(username)){
            return 2;
        }
        else if (!userManager.isAttendeeVIP(username) & eventManager.VIP(eventTitle)){
            return 4;
        }
        else{
            eventManager.addAttendee(username, eventTitle);
            userManager.signUpEventAttendee(username, eventTitle);
            userManager.setAttendeeVIP(username);
        }
        return 0;
    }

    public int cancelAttendEvent(String username, String eventTitle) {
        try {
            if (!eventManager.isAttendeeAdded(username, eventTitle)) {
//            sp.printStatement("You haven't signed up for this event yet.");
                return 1;
            }
            if (eventManager.canDeleteAttendee(username, eventTitle)) {
                eventManager.deleteAttendee(username, eventTitle);
                userManager.cancelSpotAttendee(username, eventTitle);
//            sp.printStatement("You have cancelled the spot for this event.");
            }
        } catch (IllegalArgumentException e) {
            return 2;
        }
        return 0;
    }

    //--------------------------------------------Creating Controller-----------------------------------------
    public boolean createProgram() {
        chatManager = new ChatManager();
        eventManager = new EventManager();
        roomManager = new RoomManager();
        userManager = new UserManager();
        initializeManagers();
        return true;
    }

    private void initializeManagers() {
        loginSystem = new LoginSystem(userManager);
        messagingSystem = new MessagingSystem(chatManager, userManager, eventManager);
        schedulingSystem = new SchedulingSystem(eventManager, roomManager, userManager, messagingSystem);
        signUpSystem = new SignUpSystem(eventManager, userManager, roomManager);
    }

    public void saveProgram(String filename) {
        Writer writer = new Writer();
        Object saveObjects[] = new Object[4];
        saveObjects[0] = chatManager;
        saveObjects[1] = eventManager;
        saveObjects[2] = roomManager;
        saveObjects[3] = userManager;
        writer.writeToFile(filename, saveObjects);
    }

    public boolean userIsVIP(String username) {
        return userManager.isAttendeeVIP(username);
    }


}
