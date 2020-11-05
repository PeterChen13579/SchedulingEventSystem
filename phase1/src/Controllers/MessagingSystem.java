package Controllers;
import Entities.Chat;
import Entities.Message;
import UseCase.ChatManager;
import Presenters.MessagePresenter;

import java.util.Hashtable;
import java.util.List;
import java.util.Dictionary;

public abstract class MessagingSystem {
    ChatManager userChatManager;
    MessagePresenter MessagingPresenter;

    public MessagingSystem(){
        this.userChatManager = new ChatManager();
        this.MessagingPresenter = new MessagePresenter();
    }

    abstract void MessageUsers(); // subclasses should implement this method in a way that enforces the program's rules

    public void viewChats(String userName){
        List<Chat> userChats = userChatManager.getUserChats(userName); //might change the method since it might be redundant in the use case
        MessagingPresenter.format(userChats);
    }

    public void viewMessagesInChat(List<String> allUsers){      //allUsers is a list of usernames
        Chat userChats = userChatManager.getChatContainingUsers(allUsers);
        MessagingPresenter.format(userChats.getAllMessages());
    }

    public void viewAllNewMessages(String userName){   //Will probably be formatted to be seperate the messages by chat
        List<Chat> userChats = userChatManager.getUserChats(userName);
        Dictionary<Chat, List<Message>> newMessages = new Hashtable<>();
        for (Chat i: userChats){
            List<Message> chatNewMessages = userChatManager.getNewMessages(userName, i);
            newMessages.put(i, chatNewMessages);
        }
        MessagingPresenter.format(newMessages);
    }




}
