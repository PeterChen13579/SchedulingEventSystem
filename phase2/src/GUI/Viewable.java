package GUI;

//import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Interface containing all methods needed for Dashboard classes to interact with TechConferenceSystem
 */
public interface Viewable {

    boolean createAttendeeButton(String username, String password);
    boolean createOrganizerButton(String username, String password);
    boolean createSpeakerButton(String username, String password);
    String LogInButton(String username, String password);
    boolean loadConferenceButton(String filename);
    void saveProgram(String filename);
    String[] displayAllEvents();
    String[] displaySignedUpEvents(String username);
    boolean confirmRoom(String roomNumber, int capacity);
    int signUpForEvent(String username, String eventTitle);
    int cancelAttendEvent(String username, String eventTitle);
    ArrayList<String> sendChatName(String username);
    String sendOneMsg(String sender, String recipient, String content, String imagePath);
    String[][] viewChat(int chatNumber, String username);
    String addFriend(String mainUsername, String newFriendUsername);
    //ArrayList<JSONObject> getNewMessages(String username);   //commented out for testing purposes
    void msgAllAttendees(String sender, String msg, String imagePath);
    void msgAllSpeakers(String sender, String msg, String imagePath);
    String msgAllAttendeeEvent(String sender, List<String> eventTitles, String msg, String imagePath);
    String deleteMsg(String currentUsername, int chatIndex, int messageIndex);
    String markChatAsUnread(String currentUsername, int chatIndex);
    String archiveChats(String currentUsername, int chatIndex);
    List<String> getNewMessagesChatNames(String currentUsername);
    List<String> getNewMessagesTimestamp(String currentUsername);
    List<String[][]> getNewMessagesLast8Messages(String currentUsername);
    boolean includesImage(String currentUsername, int chatIndex, int messageIndex);
    boolean cancelEvent(String eventName, String username);
    String changeCapacity(String eventName, int capacity, String username, String rmNum);
    String createSpeakerEvent(boolean VIP, String startDate, String endDate, String startTime, String endTime,
                              String roomNum, List<String> speakerUsernames, String eventTitle, int capacity);
    boolean userIsVIP(String username);
    String createParty(boolean VIP, String startDate, String endDate, String startTime, String endTime, String roomNum, List<String>
            speakerUsernames, String eventTitle, int capacity);
    void addRequest(String username, String request);
    boolean markAddressed(int requestNumber);
    boolean markPending(int requestNumber);
    String[] displayRequests();
}
