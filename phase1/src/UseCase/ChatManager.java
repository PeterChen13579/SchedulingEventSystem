package UseCase;

import Entities.Chat;
import Entities.Message;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Manages the chats in our program. It calls methods in the Chats or in the messages in the Chats.
 * @author Kailas Moon
 */
public class ChatManager implements Serializable {

    private HashMap<UUID, Chat> allChats;
    //private HashMap<UUID, Message> allMessages;

    /**
     * Create an instance of ChatManager
     */
    public ChatManager() {
        this.allChats = new HashMap<>();
        //this.allMessages = new HashMap<>();
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
        this.allChats.put(newChatId, newChat);
        return newChatId;
    }

    /**
     * Send a message to one chat
     * @param chatId The id of the chat that the message is being sent in
     * @param senderUsername The username of the sender
     * @param time The time the message was sent
     * @param content The content of the message
     */
    public void sendMessageToChat(UUID chatId, String senderUsername, LocalDateTime time, String content) {
        // Precondition: senderUsername is in this chat
        Message message = new Message(senderUsername, time, content);
        UUID newMessageId = UUID.randomUUID();
        //this.allMessages.put(newMessageId, message);

        Chat chosenChat = allChats.get(chatId);
        chosenChat.addChatMessage(newMessageId, message);
        chosenChat.setLastViewedMessage(senderUsername, newMessageId);
    }

    /**
     * Get the chat containing only the specified users. Returns null if no such chat exists
     * @param usernames A list of the usernames of the users in the chat
     * @return The id of the chat containing all of the given users if it exists, or null otherwise
     */
    public UUID getChatContainingUsers(List<String> usernames) {
        // Precondition: There is only one chat between all the users in usernames

//        for (int i=0; i<this.allChats.size(); i++) {
//            Chat chat = this.allChats.get(i);
//            boolean rightChat = true;
//            for (int j=0; j<usernames.size(); j++) {
//                if (!chat.getMemberUsernames().contains(usernames.get(j))) {
//                    rightChat = false;
//                }
//            }
//            if (rightChat && chat.getMemberUsernames().size() == usernames.size()) {
//                return chat;
//            }
//        }
//        return null;

        for (Map.Entry<UUID, Chat> allChatsItem : allChats.entrySet()){
            UUID chatId = allChatsItem.getKey();
            Chat chat = allChatsItem.getValue();
            boolean rightChat = true;
            for (int j=0; j<usernames.size(); j++) {
                if (!chat.getMemberUsernames().contains(usernames.get(j))) {
                    rightChat = false;
                }
            }
            if (rightChat && chat.getMemberUsernames().size() == usernames.size()) {
                return chatId;
            }
        }
        return null;

    }

    /**
     * Get all new messages for a user
     * @param username The username of the user
     * @param chatId The id of the chat containing the new messages
     * @return The new messages to that user
     */
    public List<UUID> getNewMessages(String username, UUID chatId) {
        // Precondition: Username is in the chat

        if (isChatEmpty(chatId)){
            return new ArrayList<>();  // returns empty list if chat is empty
        }

        Chat chat = allChats.get(chatId);
        LinkedHashMap<UUID, Message> chatMessages = chat.getAllMessages();
        List<UUID> chatMessagesList = new ArrayList<>(chatMessages.keySet());
        UUID seenMessageId = chat.getLastViewedMessage(username);   // if the user has not seen any messages, then seenMessageId will be null.
        int newMessageIndex = chatMessagesList.indexOf(seenMessageId) + 1;

        chat.setLastViewedMessage(username, chatMessagesList.get(chatMessagesList.size() - 1)); // Update the last viewed message
        return chatMessagesList.subList(newMessageIndex, chatMessages.size());
    }

    /**
     * Getter for all of a user's chats
     * @param username The username of the user
     * @return All of the user's chats
     */
    public List<UUID> getUserChats(String username) {
//        List<Chat> output = new ArrayList<Chat>();
//        for (int i=0; i<this.allChats.size(); i++) {
//            if (this.allChats.get(i).getMemberUsernames().contains(username)) {
//                output.add(this.allChats.get(i));
//            }
//        }
//        return output;

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
        return new ArrayList<>(this.allChats.keySet());
    }

    /**
     * Checks if a chat has no messages
     * @param chatId The id of the chat being checked
     * @return A boolean to represent whether or not the chat is empty
     */
    public boolean isChatEmpty(UUID chatId) {
        Chat chat = this.allChats.get(chatId);
        return chat.getAllMessages().size() == 0;
    }

    /**
     * Get all messages of a chat
     * @param chatId The id of the chat being looked at
     * @return A list of all the messages in the chat
     */
    public List<UUID> getChatMessages(String username, UUID chatId) {
        // Precondition : user exists in chat
        if (isChatEmpty(chatId)){
            return new ArrayList<>(); // returns empty list if chat is empty
        }

        Chat chat = this.allChats.get(chatId);
        LinkedHashMap<UUID, Message> chatMessages = chat.getAllMessages();
        List<UUID> chatMessagesList = new ArrayList<>(chatMessages.keySet());
        chat.setLastViewedMessage(username, chatMessagesList.get(chatMessagesList.size()-1));
        return chatMessagesList;
    }

    public String getChatName(UUID chatId) {
        Chat chat = this.allChats.get(chatId);
        return chat.getChatName();
    }

    public List<String> getChatMemberUsernames(UUID chatId) {
        Chat chat = this.allChats.get(chatId);
        return chat.getMemberUsernames();
    }

    public void setChatName(UUID chatId, String newName) {
        Chat chat = this.allChats.get(chatId);
        chat.setChatName(newName);
    }


    public void addUserToChat(UUID chatId, String username) {   //make sure you call getMessages if you want the user to see the messages
        //Precondition: User does not already exist in chat.
        Chat chat = this.allChats.get(chatId);
        chat.addUser(username);
        chat.setLastViewedMessage(username, null);
    }

    public String getMessageSenderUsername(UUID chatId, UUID messageId) {
        Message message = getChatMessage(chatId, messageId);
        return message.getSenderUsername();
    }

    public LocalDateTime getMessageTimeStamp(UUID chatId, UUID messageId) {
        Message message = getChatMessage(chatId, messageId);
        return message.getTimeStamp();
    }

    public String getMessageContent(UUID chatId, UUID messageId) {
        Message message = getChatMessage(chatId, messageId);
        return message.getContent();
    }

    // Private method
    private Message getChatMessage (UUID chatId, UUID messageId){
        Chat chat = this.allChats.get(chatId);
        return chat.getAllMessages().get(messageId);
    }
}
