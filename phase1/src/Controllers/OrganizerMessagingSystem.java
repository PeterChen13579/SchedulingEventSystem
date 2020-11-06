package Controllers;

import Entities.*;
import UseCase.ChatManager;
import UseCase.UserManager;

import java.time.LocalDateTime;
import java.util.List;

public class OrganizerMessagingSystem extends MessagingSystem {
    public OrganizerMessagingSystem() {
        super();
    }


    public void messageUsers(String senderUsername, List<String> recipientUsernames, LocalDateTime time, String content, ChatManager manager, UserManager userManager) {
        if (! (userManager.stringtoUser(senderUsername) instanceof Organizer)) {
            return;
        }

        if (recipientUsernames.size() == 1 && !(userManager.stringtoUser(recipientUsernames.get(0)) instanceof Organizer)) {
            manager.sendMessageToUsers(recipientUsernames, senderUsername, time, content);
        } else if (userManager.stringtoUser(recipientUsernames.get(0)) instanceof Attendee) {
            for (User checking : userManager.getAllAttendee()) {
                boolean valid = true;
                if (!(recipientUsernames.contains(checking.getUsername()))) {
                    valid = false;
                }
                if (valid && recipientUsernames.size() == userManager.getAllAttendee().size()) {
                    manager.sendMessageToUsers(recipientUsernames, senderUsername, time, content);
                }
            }
        } else if (userManager.stringtoUser(recipientUsernames.get(0)) instanceof Speaker) {
            for (User checking : userManager.getAllSpeaker()) {
                boolean valid = true;
                if (!(recipientUsernames.contains(checking.getUsername()))) {
                    valid = false;
                }
                if (valid && recipientUsernames.size() == userManager.getAllSpeaker().size()) {
                    manager.sendMessageToUsers(recipientUsernames, senderUsername, time, content);
                }
            }

        }
    }
}
