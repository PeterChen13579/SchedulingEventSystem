package Presenters;
import UseCase.ChatManager;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.Duration;
import java.lang.Math;
import java.util.UUID;
import java.time.format.DateTimeFormatter;

/**
 * Presents information given by MessagingSystem by formatting and printing information to screen.
 * @author William wang
 */
public class MessagePresenter {
    private ChatManager userChatManager;

    public MessagePresenter(ChatManager chatManager){
        this.userChatManager = chatManager;
    }

    /**
     * Displays an error message.
     * @param errorMessage The error message that is printed
     */
    public void error(String errorMessage) {
        System.out.println("\n" +errorMessage);
    }

    /**
     * Displays a system message to the console.
     * @param consoleMessage System message sent to the console
     */
    public void displayConsoleMessage(String consoleMessage){
        System.out.println("\n" + consoleMessage);
    }

    /**
     * Displays options for a menu. The user can select one of the options
     * @param options The options that the user can select
     */
    public void displayOptions(List<String> options){
        System.out.println("\nWhat would you like to do?");
        for (String choice : options){
            System.out.println(choice);
        }
        System.out.println("Please enter the number corresponding to one of the options");
    }

    /**
     * Displays the chat names in a menu style. The user can select a chat.
     * @param chatIds The chats to be displayed.
     */
    public void displayChatNames(List<UUID> chatIds){
        System.out.println("\nChats");
        for (int i = 0; i < chatIds.size(); i++){
            String chatName = userChatManager.getChatName(chatIds.get(i));
            System.out.println((i+1) + ". " + chatName);
        }
    }

    /**
     * Displays a chat.
     * @param username The username of the current user.
     * @param chatId The id of the chat
     * @param chatMessageIds The messages in the chat
     */
    public void displayChat(String username, UUID chatId, List<UUID> chatMessageIds){
        System.out.println("\n" + userChatManager.getChatName(chatId) + "\n");
        for (UUID messageId : chatMessageIds ){
            displayChatMessage(username, chatId, messageId);
        }
    }

    private void displayChatMessage(String username, UUID chatId, UUID messageId) {
        String senderUsername = userChatManager.getMessageSenderUsername(chatId, messageId);
        LocalDateTime timestamp = userChatManager.getMessageTimeStamp(chatId, messageId);
        String content = userChatManager.getMessageContent(chatId, messageId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss , yyyy-MM-dd");    //format time
        String formattedTimestamp = timestamp.format(formatter);

        if(username.equals(senderUsername)){
            System.out.println(senderUsername +"(Me)" + "  :  " + content + "                              at  " + formattedTimestamp);
        }else{
            System.out.println(senderUsername + "  :  " + content + "                              at  " + formattedTimestamp);
        }
    }

    /**
     * Displays all new Messages in a notification style (Think how new messages look on a phone notification).
     * @param newMessages Map with the chats each paired with a list of their new messages.
     */
    public void displayNewMessages(HashMap<UUID, List<UUID>> newMessages){
        System.out.println("\nNew Messages");
        boolean newMessagesExist = false;
        for (Map.Entry<UUID, List<UUID>> mapItem : newMessages.entrySet()){  //prints out each chat and associated messages

            UUID chatId = mapItem.getKey();
            List<UUID> messageIds = mapItem.getValue();

            if (messageIds.size() != 0) {
                newMessagesExist = true;
                //printing chat name
                String chatName = userChatManager.getChatName(chatId);
                System.out.println("\n" +chatName);

                //showing time difference from now and last message
                LocalDateTime lastMessageTime = userChatManager.getMessageTimeStamp(chatId, messageIds.get(messageIds.size() - 1));
                Duration timeDifference = Duration.between(lastMessageTime, LocalDateTime.now());
                System.out.println(timeDifference.toMinutes() + " minutes ago");        // might change the format to be more clearer. Also, only prints integers

                //showing last eight messages
                List<UUID> last8Messages = messageIds.subList(messageIds.size()- Math.min(messageIds.size(), 8), messageIds.size());
                for (UUID last8Id : last8Messages){   //only prints last 8 Messages
                    System.out.println(userChatManager.getMessageSenderUsername(chatId, last8Id) + "  :  " +
                            userChatManager.getMessageContent(chatId, last8Id)); //might make this call helper instead
                }
            }
        }
        if (!newMessagesExist) {
            System.out.println("\nNo new messages.");
        }
    }

}
