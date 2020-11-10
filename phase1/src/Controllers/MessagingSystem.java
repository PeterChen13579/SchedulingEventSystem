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

/**
 * Messaging system for the program. It can message users and view chats/messages.
 * @author William Wang and Kailas Moon
 */
public class MessagingSystem {
    ChatManager userChatManager;
    MessagePresenter MessagingPresenter;
    UserManager userManager;
    EventManager eventManager;

    /**
     * Creates the Messaging System
     */
    public MessagingSystem(UserManager userManager, EventManager eventManager) {
        this.userChatManager = new ChatManager();
        this.MessagingPresenter = new MessagePresenter(userChatManager);
        this.userManager = userManager;
        this.eventManager = eventManager;
    }

    public MessagingSystem(ChatManager chatManager, UserManager userManager, EventManager eventManager) {
        this.userChatManager = chatManager;
        this.MessagingPresenter = new MessagePresenter();
        this.userManager = userManager;
        this.eventManager = eventManager;
    }

    /**
     * Method that runs the Messaging feature
     * @param userType The type of user (Ex. Attendee) that the current user is
     * @param userName The username of the current user
     */
    public void run(String userType, String userName){

        List<String> options = new ArrayList<>(Arrays.asList("1.View Chats", "2.Send Message", "3.View all new Messages ", "4.Exit"));
        MessagingPresenter.displayOptions(options);

        Scanner input = new Scanner(System.in); // used for getting input from keyboard
        String choice = ""; //get input from keyboard
        while (!choice.equals("4")){
            choice = input.nextLine();
            if (choice.equals("1")){
                String newChoice;
                do {
                    this.MessagingPresenter.displayConsoleMessage("Press q to go go back.");
                    viewChatNames(userName);
                    chatInteraction(userName);  //this will lead to a new menu where the user can interact with the chats
                    newChoice = input.nextLine();
                } while (!newChoice.equals("0"));
            } else if (choice.equals("q")) {
                String choice2 = "";
                boolean completed = false;
                List<String> options2 = new ArrayList<String>(Arrays.asList("1.Message one user", "2.Message all attendees", "3.Message all speakers", "4.Messge all attendees of your events", "5.Cancel"));
                MessagingPresenter.displayOptions(options2);
                while (!completed && !choice2.equals("5")) {
                    choice2 = input.nextLine();
                    if (choice2.equals("1")) {
                        MessagingPresenter.displayConsoleMessage("Please enter the username of the user you'd like to message.");
                        String recipient = input.nextLine();
                        MessagingPresenter.displayConsoleMessage("Please enter the message you'd like to send.");
                        String content = input.nextLine();
                        this.messageOneUser(userName, recipient, LocalDateTime.now(),content);
                        completed = true;
                    } else if (choice2.equals("2")) {
                        MessagingPresenter.displayConsoleMessage("Please enter the message you'd like to send.");
                        String content = input.nextLine();
                        this.organizerMessageAllAttendees(userName, LocalDateTime.now(), content);
                        completed = true;
                    } else if (choice2.equals("3")) {
                        MessagingPresenter.displayConsoleMessage("Please enter the message you'd like to send.");
                        String content = input.nextLine();
                        this.organizerMessageAllSpeakers(userName, LocalDateTime.now(), content);
                        completed = true;
                    } else if (choice2.equals("4")) {
                        MessagingPresenter.displayConsoleMessage("Please enter a list of titles of the events, each title separated by one space.");
                        List<String> titles = Arrays.asList(input.nextLine().split(" "));
                        MessagingPresenter.displayConsoleMessage("Please enter the message you'd like to send.");
                        String content = input.nextLine();
                        this.speakerMessageEventAttendees(userName, titles, LocalDateTime.now(), content);
                        completed = true;
                    } else {
                        MessagingPresenter.error("Please enter a number from 1 to 5.");
                    }
                }
            } else if (choice.equals("3")){
                viewAllNewMessages(userName);
                String goBack = input.nextLine();  //Press any key to go back (have to click enter after keypress), probably will need to call presenter
            } else{
                MessagingPresenter.error("Please enter a number from 1 to 4.");
            }
            MessagingPresenter.displayOptions(options);
            choice = input.nextLine();
        }
        input.close();
    }


    /**
     * Helper method for viewing chats in run
     * @param userName The username of the current user
     */
    private void chatInteraction(String userName) {
        Scanner input = new Scanner(System.in);
        List<Chat> userChats = userChatManager.getUserChats(userName);
        int numChats = userChats.size();

        this.MessagingPresenter.displayConsoleMessage("Please enter the number of the chat that you would like to view.");
        String chatChoice = input.nextLine();  //choose a number for which chat to go to
        Chat chosenChat = userChats.get(Integer.parseInt(chatChoice) - 1);
        viewMessagesInChat(userName, this.userChatManager.getChatMemberUsernames(chosenChat));

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
        HashMap<Chat, List<Message>> newMessages = new HashMap<>();
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
        this.MessagingPresenter.displayConsoleMessage("Message sent!");
    }

    public void messageOneUser(String senderUsername, String recipientUsername, LocalDateTime time, String content) {
        List<String> recipients = new ArrayList<String>();
        recipients.add(recipientUsername);
        User sender = this.userManager.stringtoUser(senderUsername);

        if (this.userManager.stringtoUser(recipientUsername) == null) {
            MessagingPresenter.error("Invalid recipient.");
            return;
        }

        if (sender instanceof Speaker) {
            List<String> chatUsers = new ArrayList<String>();
            chatUsers.add(senderUsername);
            chatUsers.add(recipientUsername);
            Chat chat = this.userChatManager.getChatContainingUsers(chatUsers);
            if (chat != null && !this.userChatManager.isChatEmpty(chat)) {
                this.sendMessageToUsers(recipients, senderUsername, time, content);
            } else {
                MessagingPresenter.error("Speakers may only reply to a user that has already started the chat.");
            }
        } else if (sender instanceof Attendee || sender instanceof Organizer) {
            if (!(this.userManager.stringtoUser(recipientUsername) instanceof Organizer)) {
                this.sendMessageToUsers(recipients, senderUsername, time, content);
            } else {
                MessagingPresenter.error("You may not message an organizer.");
            }
        } else {
            MessagingPresenter.error("Invalid sender username.");
        }
    }

    public void organizerMessageAllAttendees(String senderUsername, LocalDateTime time, String content) {
        if (! (this.userManager.stringtoUser(senderUsername) instanceof Organizer)) {
            MessagingPresenter.error("Only organizers may perform this action.");
        }

        List<Attendee> allAttendees = this.userManager.getAllAttendee();
        List<String> recipients = new ArrayList<String>();

        for (Attendee attendee: allAttendees) {
            recipients.add(attendee.getUsername());
        }

        this.sendMessageToUsers(recipients, senderUsername, time, content);
    }

    public void organizerMessageAllSpeakers(String senderUsername, LocalDateTime time, String content) {
        if (! (this.userManager.stringtoUser(senderUsername) instanceof Organizer)) {
            MessagingPresenter.error("Only organizers may perform this action.");
        }

        List<Speaker> allSpeakers = this.userManager.getAllSpeaker();
        List<String> recipients = new ArrayList<String>();

        for (Speaker speaker: allSpeakers) {
            recipients.add(speaker.getUsername());
        }

        this.sendMessageToUsers(recipients, senderUsername, time, content);
    }

    public void speakerMessageEventAttendees(String senderUsername, List<String> eventTitles, LocalDateTime time, String content) {
        if (! (this.userManager.stringtoUser(senderUsername) instanceof Speaker)) {
            MessagingPresenter.error("Only speakers may perform this action.");
        }

        List<Event> allEvents = this.eventManager.getAllEvents();
        List<String> recipients = new ArrayList<String>();

        for (String title: eventTitles) {
            boolean found = false;
            for (Event event : allEvents) {
                if (event.getTitle().equals(title)) {
                    if (event.getSpeakerUserName().equals(senderUsername)) {
                        found = true;
                        for (String recipient: event.getAttendeeList()) {
                            if (!recipients.contains(recipient)) {
                                recipients.add(recipient);
                            }
                        }
                    } else {
                        this.MessagingPresenter.error("Sender is not the speaker of " + title);
                    }
                }
            }
            if (!found) {
                this.MessagingPresenter.error("No event with title " + title + " found.");
            }
        }

        this.sendMessageToUsers(recipients, senderUsername, time, content);
    }


}
