package Entities;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Dictionary;
import Entities.Message;

/**
 * A chat in our program. It stores info related to chats and preforms operations on chats (ex. sending messages).
 * <p>
 * This object also stores Messages since they are a part of chats.
 * @author William Wang
 */
public class Chat {
    private List<Message> chatMessages;  //stores all messages. should be sorted by time
    private Dictionary<String, Message> lastViewedMessage; //pairs username with a message. If chatMessages/memberUsernames is changed, this must be changed as well.
    private List<String> memberUsernames;  //users in the chat
    private String chatName;

    /**
     * Creates a new chat
     *
     * @param memberUsernames The users that are in this chat
     */
    public Chat(List<String> memberUsernames){
        this.chatMessages = new ArrayList<Message>();
        this.lastViewedMessage = new Hashtable<>();
        this.memberUsernames = memberUsernames;
        this.chatName = String.join("", this.memberUsernames); //we can overload and create another constructor to accept a chat name
        }

    /**
     * getter for the messages
     * @return Sorted list of all messages in the chat
     */
    public List<Message> getAllMessages(){
        return chatMessages;
    }

    /**
     * getter for new messages for a user
     * @param username The username of the user who has not seen the new messages
     * @return The new messages in the chat
     */
    public List<Message> getNewMessages(String username){
        // Precondition : Username is in this chat
        // Precondition : chatMessages is sorted by date/time
        Message seenMessage = lastViewedMessage.get(username);
        int newMessageIndex = chatMessages.indexOf(seenMessage) + 1;
        return chatMessages.subList(newMessageIndex, chatMessages.size()); // the use case needs to update the last viewed message after calling this
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
     * add a message to the chat by giving message constructors
     * @param senderUsername The username of the sender of the message
     * @param timestamp The timestamp of the message
     * @param content The content of the message
     */
    public void addChatMessage(String senderUsername, LocalDateTime timestamp, String content) {
        // Precondition : Added message has a timestamp after the last message in the chat
        Message newMessage = new Message(senderUsername, timestamp, content);
        chatMessages.add(newMessage);
    }

    /**
     * Change the last viewed message for this user.
     * @param username The username of the user being changed
     * @param lastMessage The message that is being set to the last viewed one
     */
    public void setLastViewedMessage(String username, Message lastMessage) {
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
