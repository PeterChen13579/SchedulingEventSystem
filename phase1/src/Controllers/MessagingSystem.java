package Controllers;
import Entities.*;
import UseCase.ChatManager;
import UseCase.EventManager;
import UseCase.UserManager;
import Presenters.MessagePresenter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Dictionary;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Class for the Messaging system. It can message users and view chats/messages.
 * @author William Wang and Kailas Moon
 */
public class MessagingSystem {
    ChatManager userChatManager;
    MessagePresenter MessagingPresenter;

    /**
     * Creates the Messaging System
     */
    public MessagingSystem(){
        this.userChatManager = new ChatManager();
        this.MessagingPresenter = new MessagePresenter();
    }

    /**
     * Method that runs the Messaging feature
     * @param userType The type of user (Ex. Attendee) that the current user is
     * @param userName The username of the current user
     */
    public void run(String userType, String userName){
        if (userType.equals("Attendee")){
            runAttendee(userName);
        } else if (userType.equals("Organizer")){
            runOrganizer(userName);
        } else if (userType.equals("Speaker")){
            runSpeaker(userName);
        } else {
            MessagingPresenter.error("User type doesn't exist. User treated as Attendee.");
            runAttendee(userName);
        }
    }

    /**
     * Helper method for the run method. Runs the messaging features availible for only attendees
     * @param userName
     */
    private void runAttendee(String userName){
        List<String> options = new ArrayList<String>(Arrays.asList("1.View Chats", "2.Send Message", "3.View all new Messages ", "4.Exit"));
        MessagingPresenter.displayOptions(options);

        Scanner input = new Scanner(System.in); // used for getting input from keyboard
        String choice = input.nextLine(); //get input from keyboard
        while (!choice.equals("4")){
            if (choice.equals("1")){
                String newChoice;
                do {
                    viewChatNames(userName);
                    chatInteraction(userName);  //this will lead to a new menu where the user can interact with the chats
                    newChoice = input.nextLine();
                } while (!newChoice.equals("back")); //will probably change this
            } else if (choice.equals("2")) {
                // do some shit
                // think this should prompt the user for a username and it will find the corresponding chat and show it to the user before sending
                // this might be unnecessary since view chats already shows an option to send
            } else if (choice.equals("3")){
                viewAllNewMessages(userName);
                String goBack = input.nextLine();  //Press any key to go back (have to click enter after keypress), probably will need to call presenter
            } else{
                MessagingPresenter.error("Please enter a number from 1 to 4");
            }
            MessagingPresenter.displayOptions(options);
            choice = input.nextLine();
        }
        input.close();

    }

    private void runOrganizer(String userName){

    }

    private void runSpeaker(String userName){

    }

    /**
     * Helper method for viewing chats in RunAttendee
     * @param userName The username of the current user
     */
    private void chatInteraction(String userName) {
        Scanner input = new Scanner(System.in);
        List<Chat> userChats = userChatManager.getUserChats(userName);
        int numChats = userChats.size();

        //Will probably have to ask the user which chat they want to go to first
        String chatChoice = input.nextLine();  //choose a number for which chat to go to
        Chat chosenChat = userChats.get(Integer.parseInt(chatChoice) - 1);
        viewMessagesInChat(userName, chosenChat.getMemberUsernames()); // will probably change so it calls use case method

//        List<String> options = new ArrayList<String>(Arrays.asList("1.Send Message", "2.Go back"));
//        MessagingPresenter.displayOptions(options);
//        String choice = input.nextLine(); //get input from keyboard
//        while (!choice.equals("2")) {
//            if (choice.equals("1")) {
//                // send message blah blah blah
//                // make sure sending messages in chat refreshes the chat view
//            } else {
//                MessagingPresenter.error("Please enter a number from 1 to 2");
//            }
//            MessagingPresenter.displayOptions(options);
//            choice = input.nextLine();
//        }

    }

    /**
     * View all chat names
     * @param userName The username of the current user
     */
    public void viewChatNames(String userName){
        List<Chat> userChats = userChatManager.getUserChats(userName); //might change the method since it might be redundant in the use case
        MessagingPresenter.format(userChats);
    }

    /**
     * View Messages in the chat
     * @param username The username of the current user
     * @param allUsers All the users in the chat
     */
    public void viewMessagesInChat(String username, List<String> allUsers) {      //allUsers is a list of usernames
        Chat userChats = userChatManager.getChatContainingUsers(allUsers);
        MessagingPresenter.format(userChatManager.getChatMessages(username, userChats));
    }

    /**
     * View all the chat messages
     * @param userName The username of the current user
     */
    public void viewAllNewMessages(String userName){   //Will probably be formatted to be separate the messages by chat
        List<Chat> userChats = userChatManager.getUserChats(userName);
        Dictionary<Chat, List<Message>> newMessages = new Hashtable<>();
        for (Chat i: userChats){
            List<Message> chatNewMessages = userChatManager.getNewMessages(userName, i);
            newMessages.put(i, chatNewMessages);
        }
        MessagingPresenter.format(newMessages);
    }
    
    /**
     * Helper method to send a message to a list of users
     * @param usernames The usernames that the message is being sent to
     * @param senderUsername The username of the sender
     * @param time The time the message was sent
     * @param content The content of the message
     */
    private void sendMessageToUsers(List<String> usernames, String senderUsername, LocalDateTime time, String content) {

        for (int i=0; i<usernames.size(); i++) {

            List<String> thisChatUsernames = new ArrayList<String>();
            thisChatUsernames.add(senderUsername);
            thisChatUsernames.add(usernames.get(i));

            Chat chat = this.userChatManager.getChatContainingUsers(thisChatUsernames); // Get the chat between the sender and the recipient
            if (chat == null) {
                chat = this.userChatManager.createChat(thisChatUsernames); // If no such chat exists, create it
            }
            this.userChatManager.sendMessageToChat(chat, senderUsername, time, content);
        }
    }

    public void messageOneUser(String senderUsername, String recipientUsername, LocalDateTime time, String content, UserManager userManager) {
        List<String> recipients = new ArrayList<String>();
        recipients.add(recipientUsername);

        if (userManager.stringtoUser(senderUsername) instanceof Speaker) {
            List<String> chatUsers = new ArrayList<String>();
            chatUsers.add(senderUsername);
            chatUsers.add(recipientUsername);
            Chat chat = this.userChatManager.getChatContainingUsers(chatUsers);
            if (chat != null && !this.userChatManager.isChatEmpty(chat)) {
                this.sendMessageToUsers(recipients, senderUsername, time, content);
            } else {
                MessagingPresenter.error("Speakers may only reply to a user that has already started the chat.");
            }
        } else {
            if (!(userManager.stringtoUser(recipientUsername) instanceof Organizer)) {
                this.sendMessageToUsers(recipients, senderUsername, time, content);
            }
        }
    }

    public void organizerMessageAllAttendees(String senderUsername, LocalDateTime time, String content, UserManager userManager) {
        if (! (userManager.stringtoUser(senderUsername) instanceof Organizer)) {
            MessagingPresenter.error("Only organizers may perform this action.");
        }

        List<Attendee> allAttendees = userManager.getAllAttendee();
        List<String> recipients = new ArrayList<String>();

        for (Attendee attendee: allAttendees) {
            recipients.add(attendee.getUsername());
        }

        this.sendMessageToUsers(recipients, senderUsername, time, content);
    }

    public void organizerMessageAllSpeakers(String senderUsername, LocalDateTime time, String content, UserManager userManager) {
        if (! (userManager.stringtoUser(senderUsername) instanceof Organizer)) {
            MessagingPresenter.error("Only organizers may perform this action.");
        }

        List<Speaker> allSpeakers = userManager.getAllSpeaker();
        List<String> recipients = new ArrayList<String>();

        for (Speaker speaker: allSpeakers) {
            recipients.add(speaker.getUsername());
        }

        this.sendMessageToUsers(recipients, senderUsername, time, content);
    }

    public void speakerMessageEventAttendees(String senderUsername, List<String> eventTitles, LocalDateTime time, String content, UserManager userManager, EventManager eventManager) {
        if (! (userManager.stringtoUser(senderUsername) instanceof Speaker)) {
            MessagingPresenter.error("Only speakers may perform this action.");
        }

        List<Event> allEvents = eventManager.getAllEvents();
        List<String> recipients = new ArrayList<String>();

        for (String title: eventTitles) {
            for (Event event : allEvents) {
                if (event.getTitle().equals(title)) {
                    for (String recipient: event.getAttendeeList()) {
                        if (!recipients.contains(recipient)) {
                            recipients.add(recipient);
                        }
                    }
                }
            }
        }

        this.sendMessageToUsers(recipients, senderUsername, time, content);
    }


}
