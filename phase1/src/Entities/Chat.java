package Entities;
import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * A chat in our program. It stores info related to chats and preforms operations on chats (ex. sending messages).
 * <p>
 * This object also stores Messages since they are a part of chats.
 * @author William Wang
 */
public class Chat implements Serializable {
    private LinkedHashMap<UUID, Message> chatMessages;  //stores all messages by pairing them with an id. Should be sorted by time
    private Map<String, UUID> lastViewedMessage; //pairs username with a message id. If chatMessages/memberUsernames is changed, this must be changed as well.
    private List<String> memberUsernames;  //users in the chat
    private String chatName;

    /**
     * Creates a new chat
     * @param memberUsernames The users that are in this chat
     */
    public Chat(List<String> memberUsernames){
        this.chatMessages = new LinkedHashMap<>();
        this.lastViewedMessage = new HashMap<>();
        this.memberUsernames = memberUsernames;
        this.chatName = String.join(", ", this.memberUsernames); //we can overload and create another constructor to accept a chat name

        // adds username to lastViewedMessage
        for (String username : this.memberUsernames){
            this.lastViewedMessage.put(username, null);  //sets the last viewed message to null for each user
        }
    }

    /**
     * getter for the messages
     * @return Sorted LinkedHashMap of all messages in the chat
     */
    public LinkedHashMap<UUID, Message> getAllMessages(){
        return chatMessages;
    }

    /**
     * getter for last viewed message for the user
     * @param username The user who we are referencing
     * @return The last viewed message id of the user
     */
    public UUID getLastViewedMessage(String username){
        // Preconditions : Username is in this chat
        return lastViewedMessage.get(username);   //If there are no messages in this chat or the user has not viewed any messages, this will return null
    }

    /**
     * getter for members in the chat
     * @return The usernames of all the members
     */
    public List<String> getMemberUsernames() {
        return memberUsernames;
    }

    /**
     * getter for the chat name
     * @return The name of this chat
     */
    public String getChatName() {
        return chatName;
    }

    /**
     * Add a message to the chat
     * @param newMessageId The message id being added to the chat
     * @param message The message being added to the chat
     */
    public void addChatMessage(UUID newMessageId, Message message) {
        // Preconditions : Added message has a timestamp after the last message in the chat, message does not already exist in this chat or another one
        chatMessages.put(newMessageId, message); // the use case needs to update the last viewed message (since sending a message probably means they view the previous ones)
    }

    /**
     * Change the last viewed message for this user.
     * @param username The username of the user being changed
     * @param lastMessageId The message id that is being set to the last viewed one
     */
    public void setLastViewedMessage(String username, UUID lastMessageId) {
        // Precondition: the user and the message exist in this chat
        lastViewedMessage.put(username, lastMessageId);
    }

    /**
     * Add a user to this chat
     * @param username The username of the user being added
     */
    public void addUser(String username){
        // Precondition: the user does not already exist in this chat
        memberUsernames.add(username);
    }

    /**
     * Change the name of the chat
     * @param newName The new name for the chat
     */
    public void setChatName(String newName){
        chatName = newName;
    }

}
