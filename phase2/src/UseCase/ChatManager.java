package UseCase;
import Entities.Chat;
import Entities.Message;
import Entities.ImageMessage;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Manages the chats in our program. It calls methods in the Chats or in the messages in the Chats.
 * @author Kailas Moon and William Wang
 */
public class ChatManager implements Serializable {

    private Map<UUID, Chat> allChats; //maps chat id to Chat entity
    private Map<String, List<UUID>> archivedChats; // maps username to list of chats

    /**
     * Create an instance of ChatManager
     */
    public ChatManager() {
        this.allChats = new HashMap<>();
        this.archivedChats = new HashMap<>();
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
     * Send an image and message to one chat
     * PRECONDITION : senderUsername is in this chat and the time is the current time
     * @param chatId The id of the chat that the message is being sent in
     * @param senderUsername The username of the sender
     * @param time The time the message was sent
     * @param content The content of the message
     */
    public void sendImageMessageToChat(UUID chatId, String senderUsername, LocalDateTime time, String content, String imageString) {//Added an extra parameter imageString
        ImageMessage message = new ImageMessage(senderUsername, time, content); //create an ImageMessage
        setBase64String(message, imageString); //Set the ImageMessage's Base64 String to be imageString
        UUID newMessageId = UUID.randomUUID(); //Add the ID

        Chat chosenChat = allChats.get(chatId); //Grab the chat
        chosenChat.addChatMessage(newMessageId, message); //Add the message with the image
        chosenChat.setLastViewedMessage(senderUsername, newMessageId); //Set the last message to be this one

    }

    /**
     * Deletes a message from a chat
     * PRECONDITION : message exists in this chat
     * @param chatId The id of the chat
     * @param messageId The id of the message to be deleted
     */
    public void deleteMessageFromChat(UUID chatId, UUID messageId){ //make sure the user can only delete their own messages when calling this. Also they should have viewed the message before deleting.
        Chat chosenChat = allChats.get(chatId);
        UUID previousMessageId = getPreviousMessage(chosenChat, messageId); // get the id of the previous chronological message

        // set the last viewed message of the users who had seen the deletable message to the previous message
        for (String userName: chosenChat.getMemberUsernames()){
            if (chosenChat.getLastViewedMessage(userName).equals(messageId)){
                chosenChat.setLastViewedMessage(userName, previousMessageId);
            }
        }
        chosenChat.removeMessage(messageId);
    }

    /**
     * Mark chat as unread for a certain user. Doesn't do anything if there are new, unread messages or if the chat is empty.
     * Note: messages sent by this specific user can be set to unread too.
     * @param username the username of the user
     * @param chatId the id of the chat
     */
    public void markChatAsUnread(String username, UUID chatId){
        Chat chosenChat = allChats.get(chatId);
        List<UUID> chatMessagesList = chosenChat.getAllMessages();
        UUID lastViewedMessage = chosenChat.getLastViewedMessage(username);

        // set the last viewed message to the previous message if the user has seen all the messages
        if (!isChatEmpty(chatId)){
            if(chatMessagesList.get(chatMessagesList.size() - 1).equals(lastViewedMessage)){
                chosenChat.setLastViewedMessage(username, getPreviousMessage(chosenChat, lastViewedMessage));
            }
        }
    }

    /**
     * Archive a chat for a specific user. The chat still exists but it is hidden from the user.
     * PRECONDITION : User exists in the userManager, the chat id is not already archived by user
     * @param username the username of the user
     * @param chatId the id of the chat to archive
     */
    public void archiveChat(String username, UUID chatId){
        if (archivedChats.containsKey(username)){
            List<UUID> hiddenChats = archivedChats.get(username);
            hiddenChats.add(chatId);
        } else {
            List<UUID> hiddenChats = new ArrayList<>();
            hiddenChats.add(chatId);
            archivedChats.put(username, hiddenChats);
        }
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
    public List<UUID> getUserChats(String username) { // make sure to remove the archived chats when displaying
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
     * Getter for a user's archived chats
     * @param username the username of the user
     * @return the user's archived chats
     */
    public List<UUID> getArchivedChats(String username) {
        return archivedChats.get(username);
    }

    /**
     * Checks if a chat has no messages
     * @param chatId The id of the chat being checked
     * @return A boolean to represent whether or not the chat is empty
     */
    public boolean isChatEmpty(UUID chatId) {
        Chat chat = allChats.get(chatId);
        return chat.getAllMessages().isEmpty();
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
        List<UUID> chatMessagesList = chat.getAllMessages();
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

//    /**
//     * Add the user to the chat
//     * PRECONDITION : User does not already exist in chat.
//     * @param chatId The id of the chat
//     * @param username The username of the user
//     */
//    public void addUserToChat(UUID chatId, String username) {   //make sure you call getMessages if you want the user to see the messages
//        Chat chat = allChats.get(chatId);
//        chat.addUser(username);
//        chat.setLastViewedMessage(username, null);
//    }

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

    /**
     * Checks if the chat has at least one message sent by one specific user
     * @param chatId The id of the chat
     * @param username The username of the sender
     * @return True iff the user has sent a message in this chat before, False otherwise.
     */
    public boolean doesChatHaveMessageFrom(UUID chatId, String username) {
        Chat chat = allChats.get(chatId);
        List<UUID> chatMessagesList = chat.getAllMessages();
        for (UUID messageId: chatMessagesList){
            Message message = chat.getMessageObject(messageId);
            if (message.getSenderUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public String[][] getMessagesInfo(UUID chatId) {
        Chat chat = allChats.get(chatId);
        List<UUID> chatMessagesList = chat.getAllMessages();
        String[][] output = new String[chatMessagesList.size()][4];

        for (int i=0; i<chatMessagesList.size(); i++) {
            Message message =  chat.getMessageObject(chatMessagesList.get(i));
            output[i][0] = chatMessagesList.get(i).toString();
            output[i][1] = message.getSenderUsername();
            output[i][2] = message.getContent();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss , yyyy-MM-dd");    //format time
            String formattedTimestamp = message.getTimeStamp().format(formatter);
            output[i][3] = formattedTimestamp;
        }

        return output;
    }

    // Make sure message exists in this chat
    private Message getChatMessage(UUID chatId, UUID messageId){
        Chat chat = allChats.get(chatId);
        return chat.getMessageObject(messageId);
    }

    private UUID getPreviousMessage(Chat chosenChat, UUID messageId){ //returns previous message or null if no previous exists
        List<UUID> chatMessagesList = chosenChat.getAllMessages();
        int messageIndex = chatMessagesList.indexOf(messageId);
        UUID previousMessageId;

        if (messageIndex > 0){
            previousMessageId = chatMessagesList.get(messageIndex - 1);
        }else{
            previousMessageId = null;
        }
        return previousMessageId;
    }

    private void setBase64String(ImageMessage message, String imageString) {
        message.setImageString(imageString);
    }

}
