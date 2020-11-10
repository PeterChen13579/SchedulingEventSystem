package Presenters;
import Entities.Chat;
import Entities.Message;
import UseCase.ChatManager;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

/**
 * Presents information given by MessagingSystem by formatting and printing information to screen.
 * @author William wang
 */
public class MessagePresenter {
    ChatManager userChatManager;

    public MessagePresenter(ChatManager chatManager){
        this.userChatManager = chatManager; // i'm not sure if it is a good idea to have the Messaging presenter get the use case from the Messaging system
    }

    public void format(Object anything){
        //idk
    }

    /**
     * Displays an error message.
     * @param errorMessage The error message that is printed
     */
    public void error(String errorMessage) {
        System.out.println("\n" +errorMessage);
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
     * Displays all new Messages in a notification style (Think how new messages look on a phone notification).
     * @param newMessages Hashmap with the chats each paired with a list of their new messages.
     */
    public void displayNewChatMessages(HashMap<Chat, List<Message>> newMessages){
        System.out.println("\n New Messages");
        for (Map.Entry<Chat, List<Message>> mapItem : newMessages.entrySet()){  //prints out each chat and associated messages
            String chatName = userChatManager.getChatName(mapItem.getKey());
            System.out.println("\n" +chatName);

            List<Message> messages = mapItem.getValue();
            List<Message> last10Messages = messages.subList(messages.size()- 10, messages.size());
            for (Message message : last10Messages){   //only prints last 10 Messages
                displayChatMessage(message);
            }
        }
    }

    /**
     * Displays a chat message. Acts as a helper method for displayNewChatMessages.
     * @param message A chat message.
     */
    public void displayChatMessage(Message message) {
        String senderUsername = userChatManager.getMessageSenderUsername(message);
        LocalDateTime timestamp = userChatManager.getMessageTimeStamp(message);
        String content = userChatManager.getMessageContent(message);

        System.out.println(senderUsername + " : " + content + "                                    at " + timestamp);
    }

    /**
     * Displays a system message to the console.
     * @param consoleMessage System message sent to the console
     */
    public void displayConsoleMessage(String consoleMessage){
        System.out.println("\n" + consoleMessage);
    }
}
