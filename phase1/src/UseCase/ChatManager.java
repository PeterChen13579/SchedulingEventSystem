package UseCase;

import Entities.Chat;
import Entities.Message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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
     * @return The new chat
     */
    public Chat createChat(List<String> memberUsernames) {
        Chat newChat = new Chat(memberUsernames);
        this.allChats.add(newChat);
        return newChat;
    }

    /**
     * Send a message to one chat
     * @param chat The chat that the message is being sent in
     * @param senderUsername The username of the sender
     * @param time The time the message was sent
     * @param content The content of the message
     */
    public void sendMessageToChat(Chat chat, String senderUsername, LocalDateTime time, String content) {
        // Precondition: senderUsername is in this chat
        Message message = new Message(senderUsername, time, content);
        chat.addChatMessage((message));
        chat.setLastViewedMessage(senderUsername, message);
    }

    /**
     * Send a message to a list of users
     * @param usernames The usernames that the message is being sent to
     * @param senderUsername The username of the sender
     * @param time The time the message was sent
     * @param content The content of the message
     */
    public void sendMessageToUsers(String[] usernames, String senderUsername, LocalDateTime time, String content) {

        for (int i=0; i<usernames.length; i++) {
            String[] thisChatUsernames = new String[] {senderUsername, usernames[i]};
            Chat chat = this.getChatContainingUsers(thisChatUsernames); // Get the chat between the sender and the recipient
            if (chat == null) {
                chat = this.createChat(Arrays.asList(thisChatUsernames)); // If no such chat exists, create it
            }
            this.sendMessageToChat(chat, senderUsername, time, content);
        }
    }

    /**
     * Get the chat containing only the specified users. Returns null if no such chat exists
     * @param usernames A list of the usernames of the users in the chat
     * @return The chat containing all of the given users if it exists, or null otherwise
     */
    public Chat getChatContainingUsers(String[] usernames) {
        for (int i=0; i<this.allChats.size(); i++) {
            Chat chat = this.allChats.get(i);
            boolean rightChat = true;
            for (int j=0; j<usernames.length; j++) {
                if (!chat.getMemberUsernames().contains(usernames[j])) {
                    rightChat = false;
                }
            }
            if (rightChat && chat.getMemberUsernames().size() == usernames.length) {
                return chat;
            }
        }
        return null;
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
