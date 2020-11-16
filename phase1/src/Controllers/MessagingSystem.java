package Controllers;
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

        List<String> options = new ArrayList<>(Arrays.asList("(1) View Chats", "(2) Send Message", "(3) View all new Messages ", "(4) Add friend", "(5) Exit"));

        Scanner input = new Scanner(System.in); // used for getting input from keyboard
        String choice = ""; //get input from keyboard
        while (!choice.equals("5")){
            messagingPresenter.displayOptions(options);
            choice = input.nextLine();
            if (choice.equals("1")){
                chatInteraction(userName);
            } else if (choice.equals("2")) {
                sendMessage(userName);
            } else if (choice.equals("3")){
                viewAllNewMessages(userName);
                messagingPresenter.printStatement("Press enter to go back.");
                input.nextLine();
            } else if (choice.equals("4")) {
                messagingPresenter.printStatement("Which friend would you like to add?");
                String friendUsername = input.nextLine();
                addPeopleToMessage(userName, friendUsername);
            } else if (!choice.equals("5")) {
                messagingPresenter.error("Please enter a number from 1 to 5.");
            }
        }
    }

    private void sendMessage(String userName) {
        if (userManager.userType(userName).equals("Attendee")) {
            attendeeSendMessage(userName);
        } else if (userManager.userType(userName).equals("Organizer")) {
            organizerSendMessage(userName);
        } else if (userManager.userType(userName).equals("Speaker")) {
            speakerSendMessage(userName);
        }
    }

    /**
     * Helper method for viewing chats in run
     * @param userName The username of the current user
     */
    private void chatInteraction(String userName) {
        Scanner input = new Scanner(System.in);
        viewChatNames(userName);   // show chats

        List<UUID> userChats = userChatManager.getUserChats(userName);
        String chatChoice;
        int index = 0;
        boolean inputError = true;
        while (inputError){       //shows chat names until valid input is given
            messagingPresenter.printStatement("Please enter the number of the chat that you would like to view. Press 0 to cancel.");
            chatChoice = input.nextLine(); //choose a number for which chat to go to

            try{   //checks to see if input is an integer and it corresponds to a chat
                index = Integer.parseInt(chatChoice) - 1;
                if (index >= userChats.size()){
                    messagingPresenter.error("Please enter a number that corresponds to a chat or 0 to cancel.");
                    inputError = true;
                }else{
                    inputError = false;
                }
            } catch (NumberFormatException e){
                messagingPresenter.error("Please enter an integer.");
            }
        }

        if (index != -1) {   //retrieves the chat
            UUID chosenChat = userChats.get(index);
            viewChat(userName, userChatManager.getChatMemberUsernames(chosenChat));
            messagingPresenter.printStatement("Press enter to go back.");
            input.nextLine();
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

    private void attendeeSendMessage(String userName) {
        Scanner input = new Scanner(System.in);
        String choice = "";
        List<String> options = new ArrayList<>(Arrays.asList("(1) Message one user", "(2) Cancel"));
        while (!choice.equals("2")) {
            messagingPresenter.displayOptions(options);
            choice = input.nextLine();
            if (choice.equals("1")) {
                messageOneUser(userName);
            } else if (!choice.equals("2")) {
                messagingPresenter.error("Please enter a number from 1 to 2.");
            }
        }
    }

    private void organizerSendMessage(String userName) {
        Scanner input = new Scanner(System.in);
        String choice = "";
        List<String> options = new ArrayList<>(Arrays.asList("(1) Message one user", "(2) Message all attendees", "(3) Message all speakers", "(4) Cancel"));
        while (!choice.equals("4")) {
            messagingPresenter.displayOptions(options);
            choice = input.nextLine();
            if (choice.equals("1")) {
                messageOneUser(userName);
            } else if (choice.equals("2")) {
                messagingPresenter.printStatement("Please enter the message you'd like to send.");
                String content = input.nextLine();
                organizerMessageAllAttendees(userName, LocalDateTime.now(), content);
            } else if (choice.equals("3")) {
                messagingPresenter.printStatement("Please enter the message you'd like to send.");
                String content = input.nextLine();
                organizerMessageAllSpeakers(userName, LocalDateTime.now(), content);
            } else if (! choice.equals("4")) {
                messagingPresenter.error("Please enter a number from 1 to 4.");
            }
        }
    }

    private void speakerSendMessage(String userName) {
        Scanner input = new Scanner(System.in);
        String choice = "";
        List<String> options = new ArrayList<>(Arrays.asList("(1) Message one user", "(2) Message attendees of your events", "(3) Cancel"));
        while (!choice.equals("3")) {
            messagingPresenter.displayOptions(options);
            choice = input.nextLine();
            if (choice.equals("1")) {
                messageOneUser(userName);
            } else if (choice.equals("2")) {
                messagingPresenter.printStatement("Please enter a list of titles of the events, each title separated by one \"+\" sign.");
                List<String> titles = Arrays.asList(input.nextLine().split("\\+"));
                messagingPresenter.printStatement("Please enter the message you'd like to send.");
                String content = input.nextLine();
                speakerMessageEventAttendees(userName, titles, LocalDateTime.now(), content);
            } else if (! choice.equals("3")) {
                messagingPresenter.error("Please enter a number from 1 to 3.");
            }
        }
    }

    private void messageOneUser(String senderUsername) {
        Scanner input = new Scanner(System.in);
        messagingPresenter.printStatement("Please enter the username of the user you'd like to message.");
        String recipient = input.nextLine();
        messagingPresenter.printStatement("Please enter the message you'd like to send.");
        String content = input.nextLine();
        List<String> recipients = new ArrayList<>();
        recipients.add(recipient);
        if (userManager.isAddFriend(senderUsername, recipient)) {
            this.sendMessageToUsers(recipients, senderUsername, LocalDateTime.now(), content);
        } else {
            messagingPresenter.error(recipient + " is not your friend.");
        }
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

            List<String> thisChatUsernames = new ArrayList<>();
            thisChatUsernames.add(senderUsername);
            thisChatUsernames.add(username);
            UUID chat = userChatManager.getChatContainingUsers(thisChatUsernames); // Get the chat between the sender and the recipient

            if (chat == null) {
                chat = userChatManager.createChat(thisChatUsernames); // If no such chat exists, create it
            }
            this.userChatManager.sendMessageToChat(chat, senderUsername, time, content);
        }
        messagingPresenter.printStatement("Message sent!");
    }

    private void organizerMessageAllAttendees(String senderUsername, LocalDateTime time, String content) {
        List<String> allAttendees = userManager.getAllAttendee();
        this.sendMessageToUsers(allAttendees, senderUsername, time, content);
    }

    private void organizerMessageAllSpeakers(String senderUsername, LocalDateTime time, String content) {
        List<String> allSpeakers = userManager.getAllSpeaker();
        this.sendMessageToUsers(allSpeakers, senderUsername, time, content);
    }

    private void speakerMessageEventAttendees(String senderUsername, List<String> eventTitles, LocalDateTime time, String content) {
        List<String> allEvents = eventManager.getAllEventTitle();
        List<String> recipients = new ArrayList<>();

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

    /**
     * Add friends to message
     * @param mainUserUsername the current user
     * @param newFriend new user they want to add
     * @return True if the friend is added or already your friend, false otherwise
     */
    private boolean addPeopleToMessage(String mainUserUsername, String newFriend){
        if (userManager.userType(mainUserUsername).equals("Speaker")) {
            if (userManager.userType(newFriend).equals("Attendee")) {
                UUID chat = userChatManager.getChatContainingUsers(Arrays.asList(mainUserUsername, newFriend));
                if (chat == null || !userChatManager.doesChatHaveMessageFrom(chat, newFriend)) {
                    messagingPresenter.error("You may not add attendees as a friend until they have messaged you.");
                    return false;
                }
            } else {
                messagingPresenter.error("You may not add this user as a friend.");
                return false;
            }
        } else if (userManager.userType(newFriend).equals("Organizer")) {
            messagingPresenter.error("You may not add an organizer as a friend.");
            return false;
        }

        if (!userManager.isUserExists(newFriend)) {
            messagingPresenter.error("The user " + newFriend + " does not exist.");
            return false;
        } else if (mainUserUsername.equals(newFriend)){
            messagingPresenter.error("You cannot add yourself as a friend");
            return false;
        } else if (userManager.addFriend(mainUserUsername, newFriend)) {
            messagingPresenter.printStatement("Added " + newFriend + " to your friends list.");
            return true;
        } else {
            messagingPresenter.printStatement(newFriend + " is already a friend.");
            return true;
        }
    }

}
