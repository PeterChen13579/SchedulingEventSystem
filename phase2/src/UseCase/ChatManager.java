package UseCase;
import Entities.Chat;
import Entities.Message;
import Entities.ImageMessage;

import java.io.Serializable;
import java.time.LocalDateTime;
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
     * @param chatId The id of the chat
     * @param messageId The id of the message to be deleted
     */
    public void deleteMessageFromChat(UUID chatId, UUID messageId){ //make sure the user can only delete their own messages when calling this.
        Chat chosenChat = allChats.get(chatId);
        UUID previousMessageId = getPreviousMessage(chosenChat, messageId); // get the id of the previous chronological message

        // set the last viewed message of the users who had seen the deletable message to the previous message
        for (String userName: chosenChat.getMemberUsernames()){
            if (messageId.equals(chosenChat.getLastViewedMessage(userName))){
                chosenChat.setLastViewedMessage(userName, previousMessageId);
            }
        }
        chosenChat.removeMessage(messageId);
    }

    /**
     * Mark chat as unread for a certain user. Doesn't do anything if there are unread messages for this user in this chat.
     * PRECONDITION : The chat is not empty.
     * Note: messages sent by this specific user can be set to unread too.
     * @param username the username of the user
     * @param chatId the id of the chat
     */
    public void markChatAsUnread(String username, UUID chatId){
        Chat chosenChat = allChats.get(chatId);
        UUID lastViewedMessage = chosenChat.getLastViewedMessage(username);

        // set the last viewed message to the previous message if the user has seen all the messages
        if (!areNewMessages(username, chatId)){  //if true, lastViewedMessage is not null
            UUID previousMessage = getPreviousMessage(chosenChat, lastViewedMessage); //previous message or null
            chosenChat.setLastViewedMessage(username, previousMessage);
        }
    }

    /**
     * Archive a chat for a specific user. The chat still exists but it is hidden from the user. Also marks the chat as read.
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
        markChatAsRead(username, chatId);   //call helper
    }

    /**
     * Unarchive a chat for a specific user. It is now no longer hidden.
     * PRECONDITION : the chat has been archived.
     * @param username The username of the user
     * @param chatId The id of the chat
     */
    public void unarchiveChat(String username, UUID chatId){
        if (archivedChats.containsKey(username)){
            List<UUID> hiddenChats = archivedChats.get(username);
            hiddenChats.remove(chatId);
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
        if (archivedChats.containsKey(username)){
            return new ArrayList<>(archivedChats.get(username));
        }else{
            archivedChats.put(username, new ArrayList<>());
            return new ArrayList<>();
        }
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
     * Checks if there are unread messages for the user in this chat. Doesn't return the messages.
     * @param username The username of the user
     * @param chatId The id of the chat
     * @return True if the user has unread messages in this chat, False otherwise
     */
    public boolean areNewMessages(String username, UUID chatId){
        Chat chat = allChats.get(chatId);
        List<UUID> chatMessagesList = chat.getAllMessages();
        UUID lastViewedMessage = chat.getLastViewedMessage(username);
        if (!isChatEmpty(chatId)){
            UUID lastMessage = chatMessagesList.get(chatMessagesList.size() - 1);
            return !lastMessage.equals(lastViewedMessage);
        }
        return false;
    }

    /**
     * Get all messages of a chat and updates the last viewed message.
     * PRECONDITION : user exists in chat
     * @param chatId The id of the chat being looked at
     * @return A list of all the messages in the chat
     */
    public List<UUID> getChatMessages(String username, UUID chatId) {
        Chat chat = allChats.get(chatId);
        markChatAsRead(username, chatId);  //call helper
        return chat.getAllMessages();
    }

    /**
     * Get all new messages for a user
     * PRECONDITION : Username is in the chat
     * @param username The username of the user
     * @param chatId The id of the chat containing the new messages
     * @param peek Whether the user wants to mark the messages as read or not
     * @return The new messages to that user
     */
    public List<UUID> getNewMessages(String username, UUID chatId, Boolean peek) {
        Chat chat = allChats.get(chatId);
        UUID seenMessageId = chat.getLastViewedMessage(username);   // if the user has not seen any messages, then seenMessageId will be null.
        List<UUID> chatMessagesList;
        if (peek){  //If user wants to peek, the messages are not marked as viewed
            chatMessagesList = chat.getAllMessages();
        }else {
            chatMessagesList = getChatMessages(username, chatId);   //get messages from helper method
        }

        int newMessageIndex;
        if(seenMessageId == null){ //checks if seenMessageId is null
            newMessageIndex = 0;
        } else{
            newMessageIndex = chatMessagesList.indexOf(seenMessageId) + 1;
        }
        return chatMessagesList.subList(newMessageIndex, chatMessagesList.size()); //return only the new messages
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

    /**
     * Get Message UUID by index
     * PRECONDITION : messageIndex is 1 less than the number of messages in the chat
     * @param chatId The id of the chat
     * @param messageIndex The index of the message
     * @return The message uuid corresponding to the index
     */
    public UUID getMessageUUIDbyIndex(UUID chatId, int messageIndex) {
        Chat chosenChat = allChats.get(chatId);
        List<UUID> chatMessagesList = chosenChat.getAllMessages();
        return chatMessagesList.get(messageIndex);
    }

    public boolean hasImage(UUID chatId, UUID messageId) {
        return getChatMessage(chatId, messageId).isImageMessage();
    }

    public String getImage(UUID chatId, UUID messageId) {
        return getChatMessage(chatId, messageId).getImageString();
    }

    // Make sure message exists in this chat
    private Message getChatMessage(UUID chatId, UUID messageId){
        Chat chat = allChats.get(chatId);
        return chat.getMessageObject(messageId);
    }

    //make sure messageId is not null
    private UUID getPreviousMessage(Chat chosenChat, UUID messageId){ //returns previous message or null if no previous exists
        List<UUID> chatMessagesList = chosenChat.getAllMessages();
        int messageIndex = chatMessagesList.indexOf(messageId);
        UUID previousMessageId;

        if (messageIndex > 0){
            previousMessageId = chatMessagesList.get(messageIndex - 1); //change this to use the new message uuid by index later
        }else{
            previousMessageId = null;
        }
        return previousMessageId;
    }

    private void setBase64String(ImageMessage message, String imageString) {
        message.setImageString(imageString);
    }

    private void markChatAsRead(String username, UUID chatId){
        if (!isChatEmpty(chatId)){ //only marks if chat is not empty
            Chat chat = allChats.get(chatId);
            List<UUID> chatMessagesList = chat.getAllMessages();
            chat.setLastViewedMessage(username, chatMessagesList.get(chatMessagesList.size()-1));  //updates the last viewed message
        }
    }


}
