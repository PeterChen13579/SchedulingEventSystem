package Controllers;
import Entities.Attendee;
import UseCase.ChatManager;
import UseCase.UserManager;
import UseCase.EventManager;

import Entities.Event;
import Entities.Chat;
import Entities.Speaker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SpeakerMessagingSystem extends MessagingSystem {

    public SpeakerMessagingSystem() {
        super();
    }


    public void messageUsers(String senderUsername, List<String> recipientUsernames, LocalDateTime time, String content, ChatManager manager, UserManager userManager) {
        if (! (userManager.stringtoUser(senderUsername) instanceof Speaker)) {
            return;
        }

        List<String> chatUsers = new ArrayList<String>();
        chatUsers.add(senderUsername);
        chatUsers.add(recipientUsernames.get(0));
        Chat chat = manager.getChatContainingUsers(chatUsers);
        if (recipientUsernames.size() == 1 && chat != null && chat.getAllMessages().size() != 0) {
            manager.sendMessageToUsers(recipientUsernames, senderUsername, time, content);
        }
    }

    public void messageEventAttendees(String senderUsername, List<String> eventTitles, LocalDateTime time, String content, ChatManager manager, UserManager userManager, EventManager eventManager) {
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

        manager.sendMessageToUsers(recipients, senderUsername, time, content);
    }

}
