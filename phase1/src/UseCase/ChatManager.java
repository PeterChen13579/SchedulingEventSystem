package UseCase;

import Entities.Chat;
import Entities.Message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class ChatManager {

    private List<Chat> allChats;

    /**
     * Create an instance of ChatManager
     */
    public ChatManager() {
        this.allChats = new ArrayList<Chat>();
    }

    /**
     * Create a new chat and add it to the list of all chats
     * @param memberUsernames The users who are in the new chat
     */
    public void createChat(List<String> memberUsernames) {
        this.allChats.add(new Chat(memberUsernames));
    }

    /**
     * Send a message
     * @param chat The chat that the message is being sent in
     * @param senderUsername The username of the sender
     * @param time The time the message was sent
     * @param content The content of the message
     */
    public void sendMessage(Chat chat, String senderUsername, LocalDateTime time, String content) {
        Message message = new Message(senderUsername, time, content);
        chat.getAllMessages().add(message);
    }

    /**
     * Get all new messages for a user
     * @param username The username of the user
     * @param chat The chat containing the new messages
     * @return The new messages to that user
     */
    public List<Message> getNewMessages(String username, Chat chat) {
        // Precondition: Username is in the chat

        List<Message> allMessages = chat.getAllMessages();
        Message seenMessage = chat.getLastViewedMessage(username);
        int newMessageIndex = allMessages.indexOf(seenMessage) + 1;

        chat.setLastViewedMessage(username, allMessages.get(allMessages.size() - 1)); // Update the last viewed message
        return allMessages.subList(newMessageIndex, allMessages.size());
    }

    /**
     * Getter for all of a user's chats
     * @param username The username of the user
     * @return All of the user's chats
     */
    public List<Chat> getUserChats(String username) {
        List<Chat> output = new ArrayList<Chat>();
        for (int i=0; i<this.allChats.size(); i++) {
            if (this.allChats.get(i).getMemberUsernames().contains(username)) {
                output.add(this.allChats.get(i));
            }
        }
        return output;
    }

    /**
     * Getter for all chats
     * @return List of all chats
     */
    public List<Chat> getAllChats() {
        return this.allChats;
    }
}
