package Presenters;
import Entities.Chat;
import Entities.Message;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Presents information given by MessagingSystem by formatting and printing information to screen.
 * @author William wang
 */
public class MessagePresenter {

    public MessagePresenter(){
        //idk
    }

    public void format(Object anything){
        //idk
    }

    /**
     * Displays an error message.
     * @param errorMessage The error message that is printed
     */
    public void error(String errorMessage) {
        System.out.println(errorMessage);
    }

    /**
     * Displays options for a menu. The user can select one of the options
     * @param options The options that the user can select
     */
    public void displayOptions(List<String> options){
        System.out.println("What would you like to do?");
        for (String choice : options){
            System.out.println(choice);
        }
        System.out.println("Please enter the number corresponding to one of the options");
    }

    public void displayNewMessages(Dictionary<Chat, List<Message>> newMessages){
        System.out.println("New Messages");
        //Will fix later
    }

    public void displayMessage(String message) {

    }
}
