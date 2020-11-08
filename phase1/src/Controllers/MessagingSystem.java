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

/**
 * Abstract class for the Messaging system. It can message users and view chats/messages.
 * @author William Wang
 */
public abstract class MessagingSystem {
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
            if (chat != null && chat.getAllMessages().size() != 0) {
                this.sendMessageToUsers(recipients, senderUsername, time, content);
            }
        } else {
            if (!(userManager.stringtoUser(recipientUsername) instanceof Organizer)) {
                this.sendMessageToUsers(recipients, senderUsername, time, content);
            }
        }
    }

    public void organizerMessageAllAttendees(String senderUsername, LocalDateTime time, String content, UserManager userManager) {
        if (! (userManager.stringtoUser(senderUsername) instanceof Organizer)) {
            return;
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
            return;
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
            return;
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


    public void viewChats(String userName){
        List<Chat> userChats = userChatManager.getUserChats(userName); //might change the method since it might be redundant in the use case
        MessagingPresenter.format(userChats);
    }

    public void viewMessagesInChat(List<String> allUsers){      //allUsers is a list of usernames
        Chat userChats = userChatManager.getChatContainingUsers(allUsers);
        MessagingPresenter.format(userChats.getAllMessages());
    }

    public void viewAllNewMessages(String userName){   //Will probably be formatted to be separate the messages by chat
        List<Chat> userChats = userChatManager.getUserChats(userName);
        Dictionary<Chat, List<Message>> newMessages = new Hashtable<>();
        for (Chat i: userChats){
            List<Message> chatNewMessages = userChatManager.getNewMessages(userName, i);
            newMessages.put(i, chatNewMessages);
        }
        MessagingPresenter.format(newMessages);
    }




}
