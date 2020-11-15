package Controllers;
import Entities.*;
import UseCase.ChatManager;
import UseCase.EventManager;
import UseCase.UserManager;
import Presenters.MessagePresenter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import java.util.UUID;

/**
 * Messaging system for the program. It can message users and view chats/messages.
 * @author William Wang and Kailas Moon
 */
public class MessagingSystem {
    private ChatManager userChatManager;
    private MessagePresenter messagingPresenter;
    private UserManager userManager;
    private EventManager eventManager;

    /**
     * Creates the Messaging System
     */
    public MessagingSystem(ChatManager chatManager, UserManager userManager, EventManager eventManager) {
        this.userChatManager = chatManager;
        this.messagingPresenter = new MessagePresenter(userChatManager);
        this.userManager = userManager;
        this.eventManager = eventManager;
    }

    /**
     * Method that runs the Messaging feature
     * @param userName The username of the current user
     */
    public void run(String userName){

        List<String> options = new ArrayList<>(Arrays.asList("1.View Chats", "2.Send Message", "3.View all new Messages ", "4.Add friend", "5.Exit"));

        Scanner input = new Scanner(System.in); // used for getting input from keyboard
        String choice = ""; //get input from keyboard
        while (!choice.equals("5")){
            messagingPresenter.displayOptions(options);
            choice = input.nextLine();
            if (choice.equals("1")){
                viewChatNames(userName);
                chatInteraction(userName);
                messagingPresenter.displayConsoleMessage("Press any key to go back.");
                input.nextLine();
            } else if (choice.equals("2")) {
                sendMessage(userName);
            } else if (choice.equals("3")){
                viewAllNewMessages(userName);
                messagingPresenter.displayConsoleMessage("Press any key to go back.");
                input.nextLine();
            } else if (choice.equals("4")) {
                messagingPresenter.displayConsoleMessage("Which friend would you like to add?");
                String friendUsername = input.nextLine();
                addPeopleToMessage(userName, friendUsername);
            } else {
                messagingPresenter.error("Please enter a number from 1 to 5.");
            }
        }
        //input.close();
    }

    private void sendMessage(String userName) {
        Scanner input = new Scanner(System.in);
        String choice2 = "";
        boolean completed = false;
        List<String> options2 = new ArrayList<String>(Arrays.asList("1.Message one user", "2.Message all attendees", "3.Message all speakers", "4.Message all attendees of your events", "5.Cancel"));
        messagingPresenter.displayOptions(options2);
        while (!completed && !choice2.equals("5")) {
            choice2 = input.nextLine();
            if (choice2.equals("1")) {
                messagingPresenter.displayConsoleMessage("Please enter the username of the user you'd like to message.");
                String recipient = input.nextLine();
                messagingPresenter.displayConsoleMessage("Please enter the message you'd like to send.");
                String content = input.nextLine();
                this.messageOneUser(userName, recipient, LocalDateTime.now(), content);
                completed = true;
            } else if (choice2.equals("2")) {
                messagingPresenter.displayConsoleMessage("Please enter the message you'd like to send.");
                String content = input.nextLine();
                this.organizerMessageAllAttendees(userName, LocalDateTime.now(), content);
                completed = true;
            } else if (choice2.equals("3")) {
                messagingPresenter.displayConsoleMessage("Please enter the message you'd like to send.");
                String content = input.nextLine();
                this.organizerMessageAllSpeakers(userName, LocalDateTime.now(), content);
                completed = true;
            } else if (choice2.equals("4")) {
                messagingPresenter.displayConsoleMessage("Please enter a list of titles of the events, each title separated by one \"+\" sign.");
                List<String> titles = Arrays.asList(input.nextLine().split("\\+"));
                messagingPresenter.displayConsoleMessage("Please enter the message you'd like to send.");
                String content = input.nextLine();
                this.speakerMessageEventAttendees(userName, titles, LocalDateTime.now(), content);
                completed = true;
            } else {
                messagingPresenter.error("Please enter a number from 1 to 5.");
            }
        }
    }


    /**
     * Helper method for viewing chats in run
     * @param userName The username of the current user
     */
    private void chatInteraction(String userName) {
        Scanner input = new Scanner(System.in);
        List<UUID> userChats = userChatManager.getUserChats(userName);
        //int numChats = userChats.size();

        this.messagingPresenter.displayConsoleMessage("Please enter the number of the chat that you would like to view. Press 0 to cancel.");
        String chatChoice = input.nextLine();  //choose a number for which chat to go to
        int index = Integer.parseInt(chatChoice) - 1;
        while (index >= userChats.size()){
            messagingPresenter.error("Please enter a number that corresponds to a chat or 0 to cancel.");
            chatChoice = input.nextLine();
            index = Integer.parseInt(chatChoice) - 1;
        }

        if (index != -1) {
            UUID chosenChat = userChats.get(index);
            viewChat(userName, userChatManager.getChatMemberUsernames(chosenChat));
        }

    }

    /**
     * View all chat names
     * @param userName The username of the current user
     */
    private void viewChatNames(String userName){
        List<UUID> userChats = userChatManager.getUserChats(userName); //might change the method since it might be redundant in the use case
        messagingPresenter.displayChatNames(userChats);
    }

    /**
     * View Messages in the chat
     * @param username The username of the current user
     * @param allUsers All the users in the chat
     */
    private void viewChat(String username, List<String> allUsers) {      //allUsers is a list of usernames
        UUID userChat = userChatManager.getChatContainingUsers(allUsers);
        List<UUID> messages = userChatManager.getChatMessages(username, userChat);
        messagingPresenter.displayChat(username, userChat, messages);
    }

    /**
     * View all the chat messages
     * @param userName The username of the current user
     */
    private void viewAllNewMessages(String userName){   //Will probably be formatted to be separate the messages by chat
        List<UUID> userChats = userChatManager.getUserChats(userName);
        HashMap<UUID, List<UUID>> newMessages = new HashMap<>();
        for (UUID id: userChats){
            List<UUID> chatNewMessages = userChatManager.getNewMessages(userName, id);
            newMessages.put(id, chatNewMessages);
        }
        messagingPresenter.displayNewMessages(newMessages);
    }
    
    /**
     * Helper method to send a message to a list of users
     * @param usernames The usernames that the message is being sent to
     * @param senderUsername The username of the sender
     * @param time The time the message was sent
     * @param content The content of the message
     */
    private void sendMessageToUsers(List<String> usernames, String senderUsername, LocalDateTime time, String content) {

        for (String username : usernames) {

            List<String> thisChatUsernames = new ArrayList<String>();
            thisChatUsernames.add(senderUsername);
            thisChatUsernames.add(username);

            UUID chat = userChatManager.getChatContainingUsers(thisChatUsernames); // Get the chat between the sender and the recipient

            if (chat == null) {
                if (!userManager.isAddFriend(senderUsername, username)) {
                    if (!addPeopleToMessage(senderUsername, username)) {
                        return;
                    }
                }
                chat = userChatManager.createChat(thisChatUsernames); // If no such chat exists, create it
            }

            this.userChatManager.sendMessageToChat(chat, senderUsername, time, content);
        }
        messagingPresenter.displayConsoleMessage("Message sent!");
    }

    private void messageOneUser(String senderUsername, String recipientUsername, LocalDateTime time, String content) {
        List<String> recipients = new ArrayList<String>();
        recipients.add(recipientUsername);

        if (this.userManager.userType(recipientUsername).equals("Invalid Username")) {
            messagingPresenter.error("Invalid recipient.");
            return;
        }

        if (this.userManager.userType(senderUsername).equals("Speaker")) {
            List<String> chatUsers = new ArrayList<String>();
            chatUsers.add(senderUsername);
            chatUsers.add(recipientUsername);
            UUID chat = userChatManager.getChatContainingUsers(chatUsers);
            if (chat != null && !userChatManager.isChatEmpty(chat)) {
                this.sendMessageToUsers(recipients, senderUsername, time, content);
            } else {
                messagingPresenter.error("Speakers may only reply to a user that has already started the chat.");
            }
        } else if (userManager.userType(senderUsername).equals("Attendee") || userManager.userType(senderUsername).equals("Organizer")) {
            if (!(userManager.userType(recipientUsername).equals("Organizer"))) {
                this.sendMessageToUsers(recipients, senderUsername, time, content);
            } else {
                messagingPresenter.error("You may not message an organizer.");
            }
        } else {
            messagingPresenter.error("Invalid sender username.");
        }
    }

    private void organizerMessageAllAttendees(String senderUsername, LocalDateTime time, String content) {
        if (! (userManager.userType(senderUsername).equals("Organizer"))) {
            messagingPresenter.error("Only organizers may perform this action.");
            return;
        }

        List<String> allAttendees = userManager.getAllAttendee();
        this.sendMessageToUsers(allAttendees, senderUsername, time, content);
    }

    private void organizerMessageAllSpeakers(String senderUsername, LocalDateTime time, String content) {
        if (! (userManager.userType(senderUsername).equals("Organizer"))) {
            messagingPresenter.error("Only organizers may perform this action.");
            return;
        }

        List<String> allSpeakers = userManager.getAllSpeaker();
        this.sendMessageToUsers(allSpeakers, senderUsername, time, content);
    }

    private void speakerMessageEventAttendees(String senderUsername, List<String> eventTitles, LocalDateTime time, String content) {
        if (! (userManager.userType(senderUsername).equals("Speaker"))) {
            messagingPresenter.error("Only speakers may perform this action.");
            return;
        }
        List<String> allEvents = eventManager.getAllEventTitle();
        List<String> recipients = new ArrayList<String>();

        for (String title: eventTitles) {
            boolean found = false;
            for (String event : allEvents) {
                if (event.equals(title)) {
                    if (eventManager.getSpeakerUsernameByTitle(event).equals(senderUsername)) {
                        found = true;
                        for (String recipient: eventManager.getAllAttendeesByTitle(event)) {
                            if (!recipients.contains(recipient)) {
                                recipients.add(recipient);
                            }
                        }
                    } else {
                        messagingPresenter.error("Sender is not the speaker of " + title);
                    }
                }
            }
            if (!found) {
                messagingPresenter.error("No event with title " + title + " found.");
            }
        }

        this.sendMessageToUsers(recipients, senderUsername, time, content);
    }

    private boolean addPeopleToMessage(String mainUserUsername, String newFriend){
        //checks if phase 1 rules allow to message
        if (userManager.userType(newFriend).equals("Organizer") || userManager.userType(mainUserUsername).equals("Speaker")) {
            messagingPresenter.error(newFriend + " cannot be added.");
            return false;
        }

        // tries to add friend
        if (userManager.addFriend(mainUserUsername, newFriend)){
            messagingPresenter.displayConsoleMessage(newFriend + " added as a friend.");
            return true;
        }else if (!userManager.isUserExists(newFriend)){
            messagingPresenter.error(newFriend + " this user doesn't exist.");
            return false;
        } else {
            messagingPresenter.displayConsoleMessage(newFriend + " is already added as a friend.");
            return true;
        }
    }

}
