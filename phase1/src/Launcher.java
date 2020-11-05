import Controllers.Reader;
import Controllers.Writer;
import Entities.Event;
import Entities.User;
import UseCase.ChatManager;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

import java.util.ArrayList;
import java.util.List;

public class Launcher {
    private static Writer writer;
    private static Reader reader;
    private static ChatManager chatManager;
    private static EventManager eventManager;
    private static RoomManager roomManager;
    private static UserManager userManager;

    public static void main(String[] args) {
        reader = new Reader();
        List tempList = reader.loadData("save.txt");
        // initialize all following with inputs from tempList
        // might change depending on how we plan to connect all the code
        chatManager = new ChatManager();
        eventManager = new EventManager();
        roomManager = new RoomManager();
        userManager = new UserManager();
    }

}
