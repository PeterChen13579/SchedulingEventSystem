package GUI;

import java.util.List;
import java.util.ArrayList;

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
    ArrayList <String> sendChatName(String username);
    String sendOneMsg(String username, String content, String imagePath);
    String viewChat(int chatNumber, String username);
    String addFriend(String username);
    String getNewMessages();
    void msgAllAttendees(String msg, String imagePath);
    void msgAllSpeakers(String msg, String imagePath);
    void msgAllAttendeeEvent(List<String> eventTitles, String msg, String imagePath);
    boolean cancelEvent(String eventName, String username);
    boolean changeCapacity(String eventName, int capacity, String username);
    String createSpeakerEvent(boolean VIP, String date, String startTime, String endTime, String roomNum,
                              List<String> speakerUsernames, String eventTitle, int capacity);
    boolean userIsVIP(String username);
}
