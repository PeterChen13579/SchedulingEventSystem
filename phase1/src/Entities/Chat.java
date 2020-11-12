package Entities;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A chat in our program. It stores info related to chats and preforms operations on chats (ex. sending messages).
 * <p>
 * This object also stores Messages since they are a part of chats.
 * @author William Wang
 */
public class Chat implements Serializable {
    private List<UUID> chatMessages;  //stores all messages. should be sorted by time
    private HashMap<String, UUID> lastViewedMessage; //pairs username with a message. If chatMessages/memberUsernames is changed, this must be changed as well.
    private List<String> memberUsernames;  //users in the chat
    private String chatName;

    /**
     * Creates a new chat
     *
     * @param memberUsernames The users that are in this chat
     */
    public Chat(List<String> memberUsernames){
        this.chatMessages = new ArrayList<>();
        this.lastViewedMessage = new HashMap<>();
        this.memberUsernames = memberUsernames;
        this.chatName = String.join(", ", this.memberUsernames); //we can overload and create another constructor to accept a chat name
        }

    /**
     * getter for the messages
     * @return Sorted list of all messages in the chat
     */
    public List<UUID> getAllMessages(){
        return chatMessages;
    }

    /**
     * getter for last viewed message for the user
     * @param username The user who we are referencing
     * @return The last viewed message of the user
     */
    public UUID getLastViewedMessage(String username){
        // Preconditions : Username is in this chat
        return lastViewedMessage.get(username);   //If there are no messages in this chat, this will return null
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
     * @param newMessage The message being added to the chat
     */
    public void addChatMessage(UUID newMessage) {
        // Preconditions : Added message has a timestamp after the last message in the chat, Message does not already exist in another chat
        chatMessages.add(newMessage); // maybe the use case needs to update the last viewed message (since sending a message probably means they view the previous ones)
    }

    /**
     * Change the last viewed message for this user.
     * @param username The username of the user being changed
     * @param lastMessage The message that is being set to the last viewed one
     */
    public void setLastViewedMessage(String username, UUID lastMessage) {
        // Precondition: the user and the message exist in this chat
        lastViewedMessage.put(username, lastMessage);
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
