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

    public ChatManager(List<Chat> chat) {
        allChats = chat;
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
     * Get the chat containing only the specified users. Returns null if no such chat exists
     * @param usernames A list of the usernames of the users in the chat
     * @return The chat containing all of the given users if it exists, or null otherwise
     */
    public Chat getChatContainingUsers(List<String> usernames) {
        // Precondition: There is only one chat between all the users in usernames

        for (int i=0; i<this.allChats.size(); i++) {
            Chat chat = this.allChats.get(i);
            boolean rightChat = true;
            for (int j=0; j<usernames.size(); j++) {
                if (!chat.getMemberUsernames().contains(usernames.get(j))) {
                    rightChat = false;
                }
            }
            if (rightChat && chat.getMemberUsernames().size() == usernames.size()) {
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

    /**
     * Checks if a chat has no messages
     * @param chat The chat being checked
     * @return A boolean to represent whether or not the chat is empty
     */
    public boolean isChatEmpty(Chat chat) {
        return chat.getAllMessages().size() == 0;
    }

    /**
     * Get all messages of a chat
     * @param chat The chat being looked at
     * @return A list of all the messages in the chat
     */
    public List<Message> getChatMessages(String username, Chat chat) {
        List<Message> allMessages = chat.getAllMessages();
        chat.setLastViewedMessage(username, allMessages.get(allMessages.size()-1));
        return chat.getAllMessages();  // can this be changed to just returning allMessages?
    }

    public String getChatName(Chat chat) {
        return chat.getChatName();
    }

    public List<String> getChatMemberUsernames(Chat chat) {
        return chat.getMemberUsernames();
    }

    public void setChatName(Chat chat, String newName) {
        chat.setChatName(newName);
    }


    public void addUserToChat(Chat chat, String user) {
        chat.addUser(user);
    }

    public String getMessageSenderUsername(Message message) {
        return message.getSenderUsername();
    }

    public LocalDateTime getMessageTimeStamp(Message message) {
        return message.getTimeStamp();
    }

    public String getMessageContent(Message message) {
        return message.getContent();
    }
}
