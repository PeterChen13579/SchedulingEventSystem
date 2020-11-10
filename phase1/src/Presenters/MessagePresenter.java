package Presenters;
import Entities.Chat;
import Entities.Message;
import UseCase.ChatManager;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.Duration;
import java.lang.Math;

/**
 * Presents information given by MessagingSystem by formatting and printing information to screen.
 * @author William wang
 */
public class MessagePresenter {
    ChatManager userChatManager;

    public MessagePresenter(ChatManager chatManager){
        this.userChatManager = chatManager; // I'm not sure if it is a good idea to have the Messaging presenter get the use case from the Messaging system
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
        System.out.println("\n What would you like to do?");
        for (String choice : options){
            System.out.println(choice);
        }
        System.out.println("Please enter the number corresponding to one of the options");
    }

    /**
     * Displays the chat names in a menu style. The user can select a chat.
     * @param chats The chats to be displayed.
     */
    public void displayChatNames(List<Chat> chats){
        System.out.println("\n Chats \n");
        for (int i = 0; i < chats.size(); i++){
            String chatName = userChatManager.getChatName(chats.get(i));
            System.out.println(Integer.toString(i) + ". " + chatName);
        }
        System.out.println("Select a chat by entering a number");
    }


    /**
     * Displays all new Messages in a notification style (Think how new messages look on a phone notification).
     * @param newMessages Hashmap with the chats each paired with a list of their new messages.
     */
    public void displayNewMessages(HashMap<Chat, List<Message>> newMessages){   // have to fix
        System.out.println("\n New Messages");
        for (Map.Entry<Chat, List<Message>> mapItem : newMessages.entrySet()){  //prints out each chat and associated messages
            //printing chat name
            String chatName = userChatManager.getChatName(mapItem.getKey());
            System.out.println("\n" +chatName);

            //showing time difference
            List<Message> messages = mapItem.getValue();
            LocalDateTime lastMessageTime = userChatManager.getMessageTimeStamp(messages.get(messages.size() - 1));
            Duration timeDifference = Duration.between(LocalDateTime.now(), lastMessageTime);
            System.out.println(timeDifference.toHours() + " hours ago");          // might change the format to be more clearer

            //showing last eight messages
            List<Message> last8Messages = messages.subList(messages.size()- Math.min(messages.size(), 8), messages.size());
            for (Message message : last8Messages){   //only prints last 8 Messages
                System.out.println(userChatManager.getMessageSenderUsername(message) + " : " +userChatManager.getMessageContent(message)); //might make this call helper instead
            }
        }
    }

    /**
     * Displays a chat.
     * @param username The username of the current user.
     * @param chatName The name of the chat
     * @param chatMessages The messages in the chat
     */
    public void displayChat(String username, String chatName, List<Message> chatMessages){
        System.out.println("\n" + chatName + "\n");
        for (Message message : chatMessages ){
            displayChatMessage(username, message);
        }
    }

    /**
     * Displays a chat message. Acts as a helper method for displayChat.
     * @param message A chat message.
     */
    public void displayChatMessage(String username, Message message) {  //probably shouldn't have entity parameters
        String senderUsername = userChatManager.getMessageSenderUsername(message);
        LocalDateTime timestamp = userChatManager.getMessageTimeStamp(message);
        String content = userChatManager.getMessageContent(message);

        if(username.equals(senderUsername)){
            System.out.println(senderUsername +"(Me)" + " : " + content + "                                    at " + timestamp);
        }else{
            System.out.println(senderUsername + " : " + content + "                                    at " + timestamp);
        }

    }


}
