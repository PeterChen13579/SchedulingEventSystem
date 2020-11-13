package UseCase;

import Entities.Chat;
import Entities.Message;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Manages the chats in our program. It calls methods in the Chats or messages in the Chats.
 * @author Kailas Moon
 */
public class ChatManager {

    private HashMap<UUID, Chat> allChats;
    private HashMap<UUID, Message> allMessages;

    /**
     * Create an instance of ChatManager
     */
    public ChatManager() {
        this.allChats = new HashMap<>();
        this.allMessages = new HashMap<>();
    }

    public ChatManager(HashMap<UUID, Chat> chat) {
        allChats = chat;
    }

    /**
     * Create a new chat and add it to the list of all chats
     * @param memberUsernames The users who are in the new chat
     * @return The new chat
     */
    public UUID createChat(List<String> memberUsernames) {
        Chat newChat = new Chat(memberUsernames);
        UUID newChatId = UUID.randomUUID();
        this.allChats.put(newChatId, newChat);
        return newChatId;
    }

    /**
     * Send a message to one chat
     * @param chatId The chat that the message is being sent in
     * @param senderUsername The username of the sender
     * @param time The time the message was sent
     * @param content The content of the message
     */
    public void sendMessageToChat(UUID chatId, String senderUsername, LocalDateTime time, String content) {
        // Precondition: senderUsername is in this chat
        Message message = new Message(senderUsername, time, content);
        UUID newMessageId = UUID.randomUUID();
        this.allMessages.put(newMessageId, message);

        Chat chosenChat = allChats.get(chatId);
        chosenChat.addChatMessage(newMessageId);
        chosenChat.setLastViewedMessage(senderUsername, newMessageId);
    }

    /**
     * Get the chat containing only the specified users. Returns null if no such chat exists
     * @param usernames A list of the usernames of the users in the chat
     * @return The chat containing all of the given users if it exists, or null otherwise
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
     * @param chatId The chat containing the new messages
     * @return The new messages to that user
     */
    public List<UUID> getNewMessages(String username, UUID chatId) {
        // Precondition: Username is in the chat

        Chat chat = allChats.get(chatId);
        List<UUID> chatMessages = chat.getAllMessages();
        UUID seenMessageId = chat.getLastViewedMessage(username);
        int newMessageIndex = chatMessages.indexOf(seenMessageId) + 1;

        chat.setLastViewedMessage(username, chatMessages.get(chatMessages.size() - 1)); // Update the last viewed message
        return chatMessages.subList(newMessageIndex, chatMessages.size());
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
     * @param chatId The chat being checked
     * @return A boolean to represent whether or not the chat is empty
     */
    public boolean isChatEmpty(UUID chatId) {
        Chat chat = this.allChats.get(chatId);
        return chat.getAllMessages().size() == 0;
    }

    /**
     * Get all messages of a chat
     * @param chatId The chat being looked at
     * @return A list of all the messages in the chat
     */
    public List<UUID> getChatMessages(String username, UUID chatId) {
        Chat chat = this.allChats.get(chatId);
        List<UUID> chatMessages = chat.getAllMessages();
        chat.setLastViewedMessage(username, chatMessages.get(chatMessages.size()-1));
        return chat.getAllMessages();  // can this be changed to just returning chatMessages?
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


    public void addUserToChat(UUID chatId, String user) {
        Chat chat = this.allChats.get(chatId);
        chat.addUser(user);
    }

    public String getMessageSenderUsername(UUID chatId, UUID messageId) {
        Message message = this.allMessages.get(messageId);
        return message.getSenderUsername();
    }

    public LocalDateTime getMessageTimeStamp(UUID chatId, UUID messageId) {
        Message message = this.allMessages.get(messageId);
        return message.getTimeStamp();
    }

    public String getMessageContent(UUID chatId, UUID messageId) {
        Message message = this.allMessages.get(messageId);
        return message.getContent();
    }

    public List<Chat> getChatsByUUID(List<UUID> allIds) {
        List<Chat> output = new ArrayList<Chat>();
        for (UUID id : allIds) {
            output.add(this.allChats.get(id));
        }
        return output;
    }

    public List<Message> getMessagesByUID(List<UUID> allIds) {
        List<Message> output = new ArrayList<Message>();
        for (UUID id : allIds) {
            output.add(this.allMessages.get(id));
        }
        return output;
    }
}
