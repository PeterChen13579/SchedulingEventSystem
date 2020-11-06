package Controllers;
import Entities.Organizer;
import Entities.Attendee;
import UseCase.ChatManager;
import UseCase.UserManager;

import java.time.LocalDateTime;
import java.util.List;

public class AttendeeMessagingSystem extends MessagingSystem {

    public AttendeeMessagingSystem() {
        super();
    }


    public void messageUsers(String senderUsername, List<String> recipientUsernames, LocalDateTime time, String content, ChatManager manager, UserManager userManager) {
        if (! (userManager.stringtoUser(senderUsername) instanceof Attendee)) {
            return;
        }

        if (recipientUsernames.size() == 1 && !(userManager.stringtoUser(recipientUsernames.get(0)) instanceof Organizer)) {
            manager.sendMessageToUsers(recipientUsernames, senderUsername, time, content);
        }
    }

}
