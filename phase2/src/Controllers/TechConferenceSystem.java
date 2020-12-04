package Controllers;

import GUI.Dashboard;
import GUI.Viewable;
import Presenters.EventPresenter;
import UseCase.ChatManager;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.lang.Long;

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
    public ArrayList <String> sendChatName(String currentUsername){ //probably change to viewChatnames
        ArrayList<String> output = new ArrayList<>();

        List<UUID> chats = messagingSystem.getCurrentChats(currentUsername);
        int index = 1;
        for (UUID chatId: chats) {
            String outputString = index + ". ";
            index ++;

            String chatName = getChatNameByUser(currentUsername, chatId);
            outputString += chatName;
            output.add(outputString);
        }
        return output;
    }

    private String getChatNameByUser(String username, UUID chatId){
        String chatName = messagingSystem.getChatName(chatId);
        List<String> chatMembers = messagingSystem.getChatMembers(chatId);
        if (chatMembers.size() == 2){   //chat name cannot be custom in 2 person chats
            List<String> membersCopy = new ArrayList<>(chatMembers);
            membersCopy.remove(username);
            chatName = membersCopy.get(0);
        }
        return chatName;
    }


    /**
     *
     * @param chatNumber The chat number that the user wants to view
     * @return           Return String that the User wants to view; IN VIEW CHAT NUMBER: Return null if can not find
     *                   chat number;
     *                   The String Array will be in the form [[senderUsername, content, timestamp],....]
     */
    @Override
    public String[][] viewChat(int chatNumber, String currentUsername){
        List<UUID> userChats = messagingSystem.getCurrentChats(currentUsername);
        List<List<String>> messageInfoList = new ArrayList<>();

        if (chatNumber >= 1 && chatNumber <= userChats.size()){
            UUID chatId = userChats.get(chatNumber-1);
            List<UUID> chatMessages = messagingSystem.getChatMessages(currentUsername, chatId);
            for (UUID messageId: chatMessages){
                List<String> currentMessageInfo = new ArrayList<>(getMessageInfo(chatId, messageId));
                messageInfoList.add(currentMessageInfo); //add a list of message info for this message
            }

            String[][] messageInfoArray = new String[messageInfoList.size()][];
            for (int i = 0; i<messageInfoList.size(); i++){
                List<String> nestedMessageInfo = messageInfoList.get(i);
                messageInfoArray[i] = nestedMessageInfo.toArray(new String[0]); //might have a bug idk
            }
            return messageInfoArray;
        }else{
            return null;
        }

    }

    private List<String> getMessageInfo(UUID chatId, UUID messageId){
        String senderUsername = messagingSystem.getMessageSender(chatId, messageId);
        LocalDateTime timeStamp = messagingSystem.getMessageTimestamp(chatId, messageId);
        String content = messagingSystem.getMessageContent(chatId, messageId);

        // convert datetime to string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss , yyyy-MM-dd");    //format time
        String formattedTimestamp = timeStamp.format(formatter);

        return new ArrayList<>(Arrays.asList(senderUsername, content, formattedTimestamp));
    }

    /**
     * Message all Attendees
     * @param msg message content
     * @param imagePath image path
     */
    @Override
    public void msgAllAttendees(String sender, String msg, String imagePath) {
        messagingSystem.organizerMessageAllAttendees(sender, msg, imagePath);
    }

    /**
     * Message all Speakers
     * @param msg message content
     * @param imagePath image path
     */
    @Override
    public void msgAllSpeakers(String sender, String msg, String imagePath) {
        messagingSystem.organizerMessageAllSpeakers(sender, msg, imagePath);
    }

    /**
     * Message all attendees
     * @param eventTitles the list of event titles
     * @param msg message content
     * @param imagePath image path
     */
    @Override
    public String msgAllAttendeeEvent(String sender, List<String> eventTitles, String msg, String imagePath){
        return messagingSystem.speakerMessageEventAttendees(sender, eventTitles, msg, imagePath);
    }


    /** TO @William Wang and Kailas Moon
     *
     * @param sender The username of the sender
     * @param recipient    The username this user wants to msg
     * @param content     The text this username wants to send
     * @return   A success or error message based on whether sending the message was successful
     */

    @Override
    public String sendOneMsg(String sender, String recipient, String content, String imagePath) {
        return messagingSystem.messageOneUser(sender, recipient, content, imagePath);
    }

    /**
     * Delete a message from the chat
     * PRECONDITION : messageIdString wasn't used to call deleteMsg before
     * @param currentUsername the username of the current user
     * @param chatIndex The index of the chat
     * @param messageIndex the index of the message
     * @return "true" if the action was successful. An error message otherwise.
     */
    public String deleteMsg(String currentUsername, int chatIndex, int messageIndex){
        UUID chatId = messagingSystem.getCurrentChats(currentUsername).get(chatIndex);
        UUID messageId = messagingSystem.getMessageByIndex(chatId, messageIndex);

        return messagingSystem.deleteUserMessage(currentUsername, chatId, messageId);
    }

    /**
     * Mark chat as unread.
     * @param currentUsername The username of the current user
     * @param chatIndex The index of the chat
     * @return "true" if the action was successful. An error message otherwise.
     */
    public String markChatAsUnread(String currentUsername, int chatIndex){
        UUID chatId = messagingSystem.getCurrentChats(currentUsername).get(chatIndex);

        return messagingSystem.markUserChatAsUnread(currentUsername, chatId);
    }

    /**
     * Archive chat.
     * @param currentUsername The username of the current user
     * @param chatIndex The index of the chat
     * @return "true" if the action was successful. An error message otherwise.
     */
    public String archiveChats(String currentUsername, int chatIndex){

        UUID chatId = messagingSystem.getCurrentChats(currentUsername).get(chatIndex);

        return messagingSystem.archiveUserChat(currentUsername, chatId);
    }

    /** TO @William Wang and Kailas Moon
     *
     * @return   All new Messages (View all new messages option)
     */
    /*
    @Override

    public String getNewMessages(String currentUsername){
        String output = "New Messages:\n";
        Map<UUID, List<UUID>> newMessages =  messagingSystem.viewAllNewMessages(currentUsername);

        boolean newMessagesExist = false;
        for (Map.Entry<UUID, List<UUID>> mapItem : newMessages.entrySet()){  //prints out each chat and associated messages

            UUID chatId = mapItem.getKey();
            List<UUID> messageIds = mapItem.getValue();

            // Removed if statement for checking messageIds size cause it's checked in messagingsystem now
            newMessagesExist = true;
            //printing chat name
            String chatName = getChatNameByUser(currentUsername, chatId);
            output += "\n" +chatName + "\n";

            //showing time difference from now and last message
            LocalDateTime lastMessageTime = messagingSystem.getMessageTimestamp(chatId, messageIds.get(messageIds.size() - 1));
            Duration timeDifference = Duration.between(lastMessageTime, LocalDateTime.now());
            output += (timeDifference.toMinutes() + " minutes ago\n");        // might change the format to be more clearer. Also, only prints integers

            //showing last eight messages
            List<UUID> last8Messages = messageIds.subList(messageIds.size()- Math.min(messageIds.size(), 8), messageIds.size());
            for (UUID last8Id : last8Messages){   //only prints last 8 Messages
                output += (messagingSystem.getMessageSender(chatId, last8Id) + "  :  " +
                        messagingSystem.getMessageContent(chatId, last8Id) + "\n"); //might make this call helper instead
            }
        }
        if (!newMessagesExist) {
            output += ("\nNo new messages.");
        }
        return output;
    }
    */

    /**
     * Get the chat names of all chats with new messages. Note: list elements correspond to the values of the same index in the lists returned from the other newMessages methods.
     * @param currentUsername The username of the current user
     * @return An ordered list of all the chat names with new messages
     */
    public List<String> getNewMessagesChatNames(String currentUsername){
        Map<UUID, List<UUID>> newMessages =  messagingSystem.viewAllNewMessages(currentUsername);
        List<String> newMessagesChatNames = new ArrayList<>();

        for (Map.Entry<UUID, List<UUID>> mapItem : newMessages.entrySet()){
            UUID chatId = mapItem.getKey();
            String chatName = getChatNameByUser(currentUsername, chatId);
            newMessagesChatNames.add(chatName);
        }
        return newMessagesChatNames;
    }

    /**
     * Get the timestamps of the chats with new messages. Note: List elements correspond to the values of the same index in the lists returned from the other newMessages methods.
     * @param currentUsername The username of the current user
     * @return An ordered list of
     */
    public List<String> getNewMessagesTimestamp(String currentUsername){
        Map<UUID, List<UUID>> newMessages =  messagingSystem.viewAllNewMessages(currentUsername);
        List<String> newMessagesTimestamps = new ArrayList<>();

        for (Map.Entry<UUID, List<UUID>> mapItem : newMessages.entrySet()){
            UUID chatId = mapItem.getKey();
            List<UUID> messageIds = mapItem.getValue();

            //showing time difference from now and last message
            LocalDateTime lastMessageTime = messagingSystem.getMessageTimestamp(chatId, messageIds.get(messageIds.size() - 1));
            Duration timeDifference = Duration.between(lastMessageTime, LocalDateTime.now());
            newMessagesTimestamps.add(Long.toString(timeDifference.toMinutes()));
        }
        return newMessagesTimestamps;
    }

    /**
     * Get the last 8 new messages for every chat. Note: List elements correspond to the values of the same index in the lists returned from the other newMessages methods.
     * @param currentUsername The username of the current user
     * @return A list of 2d string arrays. Each string array is for a different chat. Each string array will be in the form [[senderUsername, content],....].
     */
    public List<String[][]> getNewMessagesLast8Messages(String currentUsername){
        Map<UUID, List<UUID>> newMessages =  messagingSystem.viewAllNewMessages(currentUsername);
        List<String[][]> newMessagesLast8 = new ArrayList<>();

        for (Map.Entry<UUID, List<UUID>> mapItem : newMessages.entrySet()){
            UUID chatId = mapItem.getKey();
            List<UUID> messageIds = mapItem.getValue();  //list will never be empty because of messagingSystem

            //showing last eight messages
            List<UUID> last8Messages = messageIds.subList(messageIds.size()- Math.min(messageIds.size(), 8), messageIds.size());
            String[][] chatNew8Messages = new String[last8Messages.size()][]; //stored as [[senderUsername, content],......]
            for (int i= 0; i< last8Messages.size(); i++){
                chatNew8Messages[i][0] = messagingSystem.getMessageSender(chatId,last8Messages.get(i)); //set message sender
                chatNew8Messages[i][1] = messagingSystem.getMessageContent(chatId,last8Messages.get(i)); // set message content
            }
            newMessagesLast8.add(chatNew8Messages); // add this chat's new messages to list
        }

        return newMessagesLast8;
    }

    /** TO @William Wang and Kailas Moon
     *
     * @param mainUsername The username of the current user
     * @param newFriendUsername    The username this user wants to add
     * @return            "true" if successfully added.
     *                    An error message if adding the friend was unsuccessful.
     */
    @Override
    public String addFriend(String mainUsername, String newFriendUsername){
        return messagingSystem.addPeopleToMessage(mainUsername, newFriendUsername);
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
        return schedulingSystem.cancelEvent(title, username);
    }


    /**
     * Perform necessary checks & operations for changing the capacity of an event.
     * @param eventName   String event name to change capacity
     * @param capacity    capacity to change to
     * @param username    the username of the organizer that chose to change the capacity of this event
     * @param rmNum       the roomnumber for this event
     * @return            true if successfully changed, false otherwise.
     */
    @Override
    public String changeCapacity(String eventName, int capacity, String username, String rmNum){
        return schedulingSystem.changeCapacity(eventName, capacity, username, rmNum);
    }

    //return true iff added the room, return false otherwise
    public boolean confirmRoom(String roomNumber, int capacity){
        return schedulingSystem.addRoom(roomNumber, capacity);
    }


    /**
     * Check if the conditions for adding the given one speaker/ multi speaker event is satisfied and return error messages accordingly.
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
        System.out.println(speakerUsernames);
        return schedulingSystem.helper_addSpeakerEvent(VIP, date, startTime, endTime, roomNum, speakerUsernames, eventTitle, capacity);
    }

    /**
     * Check if the conditions for adding the given no speaker event is satisfied and return error messages accordingly.
     * If satisfied, create new event, update speaker's list of events, and print success message.
     * @param VIP whether the event is of type VIP
     * @param date the date for the event (YYYYMMDD)
     * @param startTime the start time for the event (HH:mm:ss)
     * @param endTime the end time for the event (HH:mm:ss)
     * @param roomNum the room number for the event
     * @param speakerUsernames the names of the speakers for the event (SHOULD BE JUST EMPTY)
     * @param eventTitle the title for the event
     * @param capacity the maximum/capacity of people that can attend this event
     * @return the error message according to the error or "true" if event successfully created
     */
    public String createParty(boolean VIP, String date, String startTime, String endTime, String roomNum, List<String>
            speakerUsernames, String eventTitle, int capacity){
        System.out.println(speakerUsernames);
        return schedulingSystem.helper_addParty(VIP, date, startTime, endTime, roomNum, speakerUsernames, eventTitle, capacity);

    }

//--------------------------------------------Sign Up Buttons-----------------------------------------


    //@TO JOY/AMY
    public String[] displayAllEvents() {
        return signUpSystem.displayAllEvents();
    }

    //@TO JOY/AMY
    public String[] displaySignedUpEvents(String username) {
        return signUpSystem.displaySignedUpEvents(username);
    }

    /**  //@TO JOY/AMY
     *
     * @param username
     * @param eventTitle
     * @return
     */
    public int signUpForEvent(String username, String eventTitle) {
        return signUpSystem.signUpEvent(username, eventTitle);
    }

    //@TO JOY/AMY
    public int cancelAttendEvent(String username, String eventTitle) {
        return signUpSystem.cancelSpotEvent(username, eventTitle);
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
        return signUpSystem.userIsVIP(username);
    }


}
