package UseCase;

import Entities.Chat;
import Entities.Message;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Manages the chats in our program. It calls methods in the Chats or in the messages in the Chats.
 * @author Kailas Moon and William Wang
 */
public class ChatManager implements Serializable {

    private Map<UUID, Chat> allChats;

    /**
     * Create an instance of ChatManager
     */
    public ChatManager() {
        this.allChats = new HashMap<>();
    }

    public ChatManager(HashMap<UUID, Chat> chat) {  // might need to change this for loading objects
        allChats = chat;
    }

    /**
     * Create a new chat and add it to the list of all chats
     * @param memberUsernames The users who are in the new chat
     * @return The id of the new chat
     */
    public UUID createChat(List<String> memberUsernames) {
        Chat newChat = new Chat(memberUsernames);
        UUID newChatId = UUID.randomUUID();
        allChats.put(newChatId, newChat);
        return newChatId;
    }

    /**
     * Send a message to one chat
     * PRECONDITION : senderUsername is in this chat and the time is the current time
     * @param chatId The id of the chat that the message is being sent in
     * @param senderUsername The username of the sender
     * @param time The time the message was sent
     * @param content The content of the message
     */
    public void sendMessageToChat(UUID chatId, String senderUsername, LocalDateTime time, String content) {
        Message message = new Message(senderUsername, time, content);
        UUID newMessageId = UUID.randomUUID();

        Chat chosenChat = allChats.get(chatId);
        chosenChat.addChatMessage(newMessageId, message);
        chosenChat.setLastViewedMessage(senderUsername, newMessageId);
    }

    /**
     * Get the chat containing only the specified users. Returns null if no such chat exists
     * PRECONDITION : There is only one chat between all the users in usernames
     * @param usernames A list of the usernames of the users in the chat
     * @return The id of the chat containing all of the given users if it exists, or null otherwise
     */
    public UUID getChatContainingUsers(List<String> usernames) {
        for (Map.Entry<UUID, Chat> allChatsItem : allChats.entrySet()){
            UUID chatId = allChatsItem.getKey();
            Chat chat = allChatsItem.getValue();
            boolean rightChat = true;
            for (String username : usernames) {
                if (!chat.getMemberUsernames().contains(username)) {
                    rightChat = false;
                    break;
                }
            }
            if (rightChat && chat.getMemberUsernames().size() == usernames.size()) {
                return chatId;
            }
        }
        return null;
    }

    /**
     * Getter for all of a user's chats
     * @param username The username of the user
     * @return All of the user's chats
     */
    public List<UUID> getUserChats(String username) {
        List<UUID> output = new ArrayList<>();
        for (Map.Entry<UUID, Chat> allChatsItem : allChats.entrySet()){
            UUID chatId = allChatsItem.getKey();
            Chat chat = allChatsItem.getValue();
            if (chat.getMemberUsernames().contains(username)){
                output.add(chatId);
            }
        }
        return output;
    }

    /**
     * Getter for all chats
     * @return List of all chats
     */
    public List<UUID> getAllChats() {
        return new ArrayList<>(allChats.keySet());
    }

    /**
     * Checks if a chat has no messages
     * @param chatId The id of the chat being checked
     * @return A boolean to represent whether or not the chat is empty
     */
    public boolean isChatEmpty(UUID chatId) {
        Chat chat = allChats.get(chatId);
        return chat.getAllMessages().size() == 0;
    }

    /**
     * Get all messages of a chat and updates the last viewed message.
     * PRECONDITION : user exists in chat
     * @param chatId The id of the chat being looked at
     * @return A list of all the messages in the chat
     */
    public List<UUID> getChatMessages(String username, UUID chatId) {
        if (isChatEmpty(chatId)){
            return new ArrayList<>(); // returns empty list if chat is empty
        }

        Chat chat = allChats.get(chatId);
        LinkedHashMap<UUID, Message> chatMessages = chat.getAllMessages();
        List<UUID> chatMessagesList = new ArrayList<>(chatMessages.keySet());
        chat.setLastViewedMessage(username, chatMessagesList.get(chatMessagesList.size()-1));  //updates the last viewed message
        return chatMessagesList;
    }

    /**
     * Get all new messages for a user
     * PRECONDITION : Username is in the chat
     * @param username The username of the user
     * @param chatId The id of the chat containing the new messages
     * @return The new messages to that user
     */
    public List<UUID> getNewMessages(String username, UUID chatId) {
        Chat chat = allChats.get(chatId);
        UUID seenMessageId = chat.getLastViewedMessage(username);   // if the user has not seen any messages, then seenMessageId will be null.
        List<UUID> chatMessagesList = getChatMessages(username, chatId);   //get messages from helper method

        int newMessageIndex = chatMessagesList.indexOf(seenMessageId) + 1;
        return chatMessagesList.subList(newMessageIndex, chatMessagesList.size());

    }

    /**
     * Get the chat name of a chat
     * @param chatId The id of the chat
     * @return The name of the chat
     */
    public String getChatName(UUID chatId) {
        Chat chat = allChats.get(chatId);
        return chat.getChatName();
    }

    /**
     * Get the usernames of the members of the chat
     * @param chatId The id of the chat
     * @return The list of all the usernames
     */
    public List<String> getChatMemberUsernames(UUID chatId) {
        Chat chat = allChats.get(chatId);
        return chat.getMemberUsernames();
    }

    /**
     * Set the name of the chat
     * @param chatId The id of the chat
     * @param newName The new name for the chat
     */
    public void setChatName(UUID chatId, String newName) {
        Chat chat = allChats.get(chatId);
        chat.setChatName(newName);
    }

    /**
     * Add the user to the chat
     * PRECONDITION : User does not already exist in chat.
     * @param chatId The id of the chat
     * @param username The username of the user
     */
    public void addUserToChat(UUID chatId, String username) {   //make sure you call getMessages if you want the user to see the messages
        Chat chat = allChats.get(chatId);
        chat.addUser(username);
        chat.setLastViewedMessage(username, null);
    }

    /**
     * Get the message sender's username
     * @param chatId The id of the chat
     * @param messageId The id of the message
     * @return The sender's username
     */
    public String getMessageSenderUsername(UUID chatId, UUID messageId) {
        Message message = getChatMessage(chatId, messageId);
        return message.getSenderUsername();
    }

    /**
     * Get the message timestamp
     * @param chatId The id of the chat
     * @param messageId The id of the message
     * @return The time the message was sent
     */
    public LocalDateTime getMessageTimeStamp(UUID chatId, UUID messageId) {
        Message message = getChatMessage(chatId, messageId);
        return message.getTimeStamp();
    }

    /**
     * Get the content of the message
     * @param chatId The id of the chat
     * @param messageId The id of the message
     * @return The content of the message
     */
    public String getMessageContent(UUID chatId, UUID messageId) {
        Message message = getChatMessage(chatId, messageId);
        return message.getContent();
    }

    private Message getChatMessage(UUID chatId, UUID messageId){
        Chat chat = allChats.get(chatId);
        return chat.getAllMessages().get(messageId);
    }
}
