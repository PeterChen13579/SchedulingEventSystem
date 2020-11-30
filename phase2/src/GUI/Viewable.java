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
    ArrayList <String> sendChatName();
    boolean sendOneMsg(String username, String content);
    String viewChatMsg(int chatNumber);
    String addFriend(String username);
    String getNewMessages();
    void msgAllAttendees(String msg);
    void msgAllSpeakers(String msg);
    void msgAllAttendeeEvent(String msg);
    boolean cancelEvent(String eventName);
    boolean changeCapacity(String eventName, int capacity);
    String createSpeakerEvent();
    boolean userIsVIP(String username);
}
