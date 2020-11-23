package GUI;

import java.util.List;

public interface Viewable {

    //public void loadMenu();
    //public void loginSignup();
    //public void loggedInAttendee();
    //public void signUpEventMenu();
    //public void messagingMenu();
    //public void browseEventMenu();
    //public void loggedInOrganizer();
    //public void schedulingMenu();
    //public void loggedInSpeaker();
    //public void sendMessageMenu();
    //public void usernamePassword();
    boolean createAttendeeButton(String username, String password);
    boolean createOrganizerButton(String username, String password);
    boolean createSpeakerButton(String username, String password);
    String LogInButton(String username, String password);
    boolean loadConferenceButton(String filename);
    void saveProgram(String filename);
    String[] displayAllEvents();
    String[] displaySignedUpEvents(String username);
    boolean confirmRoom(String roomNumber);

}
