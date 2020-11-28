package Controllers;

import GUI.Dashboard;
import GUI.Viewable;
import Presenters.EventPresenter;
import UseCase.ChatManager;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

import java.util.List;
import java.util.Scanner;

/**
 * Determines all the behaviour for the text-based UI
 * @author Joyce Huang, Peter Chen, and Amy Miao
 */
public class TechConferenceSystem implements Viewable{

    private LoginSystem loginSystem;
    private MessagingSystem messagingSystem;
    private SchedulingSystem schedulingSystem;
    private SignUpSystem signUpSystem;
    private UserManager userManager;
    private ChatManager chatManager;
    private EventManager eventManager;
    private RoomManager roomManager;
    private final Dashboard dashboard;


    /**
     * Constructor for the entire controller, begins the program
     */
    public TechConferenceSystem(final Dashboard dashboard){
        this.dashboard = dashboard;
        dashboard.setView(this);
        createProgram();
        run();
    }

    /**
     * A method that determines if an attendee account can be successfully created or not.
     *
     * @param username  The username the user wants to create
     * @param password  The Password the user wants for their account
     * @return          True if Attendee account successfully created. False Otherwise
     */
    public boolean createAttendeeButton(String username, String password){
        return userManager.createAttendeeAccount(username, password);
    }

    /**
     * A method that determines if an organizer account can be successfully created or not.
     *
     * @param username  The username the user wants to create
     * @param password  The Password the user wants for their account
     * @return          True if Organizer account successfully created. False Otherwise
     */
    public boolean createOrganizerButton(String username, String password){
        return userManager.createOrganizerAccount(username, password);
    }

    /**
     * A method that determines if a Speaker account can be successfully created or not.
     *
     * @param username  The username the user wants to create
     * @param password  The Password the user wants for their account
     * @return          True if Speaker account successfully created. False Otherwise
     */
    public boolean createSpeakerButton(String username, String password){
        return userManager.createSpeakerAccount(username, password);
    }


    /**
     * A method that loads an existing conference; Method runs when the user clicks the "load conference" button
     */
    public boolean loadConferenceButton(String filename){
        Reader reader = new Reader();
        if (reader.verifySaves(filename)) {
            Object loadedObjects[] = reader.loadData(filename);
            chatManager = (ChatManager) loadedObjects[0];
            eventManager = (EventManager) loadedObjects[1];
            roomManager = (RoomManager) loadedObjects[2];
            userManager = (UserManager) loadedObjects[3];
        } else {
            return false;
        }
        initializeManagers();
        return true;
    }


    /**
     *
     * @param username   Username that the user wants to login with
     * @param password   Password that the user wants to login with
     * @return          False if username is already in database or invalid. True otherwise.
     */
    public String LogInButton(String username, String password){
        String userType = userManager.userType(username);
        if (userType.equals("Invalid Username")){
            return "false";
        }else{
            if (loginSystem.verifyLogin(username, password)){
                return loginSystem.verifyUserType(username);
            }else{
                return "false";
            }
        }
    }


//--------------------------------------------Messaging Buttons-----------------------------------------

    /**
     * TO @William Wang and Kailas Moon; This method is for sending the info of ALL chat usernames to
     * display on GUI; method for "View Chats". Add in the username for this;
     */
    public void sendChatUsername(){

    }

    /**
     * Runs the initial controls to start the program
     */
    private void run() {
        boolean flag = true;
        while(flag){
            if (start()) {
                flag = false;
            }
        }
        mainLevel();
    }

    private boolean start(){
        Scanner input = new Scanner(System.in);
        String temp = input.nextLine();

        if (temp.equals("1")) {
            try {
                createProgram();
                return true;
            } catch (ClassCastException exception) {
//                STATEMENT_PRESENTER.printStatement("There is no existing Conference, please create a new one. ");
                return false;
            }
        } else if (temp.equals("2")) {
            createProgram();
            return true;
        } else {
//            STATEMENT_PRESENTER.printStatement("Please enter the corresponding number and try again");
            return false;
        }
    }

    private void mainLevel(){
        boolean flag = true;
        System.out.println("wtf");
        while(flag){
            if(mainLevelHelper()) {
                flag = false;
            }
        }
    }




    private boolean mainLevelHelper(){
        Scanner in = new Scanner(System.in);

//        STATEMENT_PRESENTER.printStatement("What do you want to do? ;)");
//        STATEMENT_PRESENTER.printStatement("Please enter the corresponding number listed below: ");
//        STATEMENT_PRESENTER.printStatement("(1) Log In \n(2) Create Attendee Account  \n(3) Create Organizer Account \n(4) Quit");
        String temp2 = in.nextLine();

        switch (temp2) {
            case "1":
                if (loginSystem.run()) {
                    String username = loginSystem.getUsername();
                    if (userManager.userType(username).equals("Attendee")) {
                        loggedInMenuAttendee(username);
                    } else if (userManager.userType(username).equals("Organizer")) {
                        loggedInMenuOrganizer(username);
                    } else {
                        loggedInMenuSpeaker(username);
                    }
                    return true;
                } else {
                    return false;
                }

            case "2": {
//                STATEMENT_PRESENTER.printStatement("Please enter a username:");
                String userName = in.nextLine();
//                STATEMENT_PRESENTER.printStatement("Please enter a password:");
                String password = in.nextLine();
                if (userManager.createAttendeeAccount(userName, password)) {
//                    STATEMENT_PRESENTER.printStatement("You have successfully created an Attendee Account!");
                } else {
//                    STATEMENT_PRESENTER.printStatement("This username is already in our database.");
//                    STATEMENT_PRESENTER.printStatement("Please enter a different username");
                }
                return false;
            }
            case "3": {
//                STATEMENT_PRESENTER.printStatement("Please enter a username:");
                String userName = in.nextLine();
//                STATEMENT_PRESENTER.printStatement("Please enter a password:");
                String password = in.nextLine();
                if (userManager.createOrganizerAccount(userName, password)) {
//                    STATEMENT_PRESENTER.printStatement("You have successfully created an Organizer Account!");
                } else {
//                    STATEMENT_PRESENTER.printStatement("This username is already in our database.");
//                    STATEMENT_PRESENTER.printStatement("Please enter a different username");
                }
                return false;
            }
            case "4":
//                STATEMENT_PRESENTER.printStatement("You have exited the program.");
                saveProgram("hi.txt");
                return true;
            default:
//                STATEMENT_PRESENTER.printStatement("Please enter the corresponding number and try again");
                break;
        }
        return false;
    }

    private void loggedInMenuAttendee(String username){
        boolean flag = true;
        while (flag){
            if (loggedInMenuAttendeeHelper(username)) { flag = false; }
        }
    }

    private boolean loggedInMenuAttendeeHelper(String username){
        Scanner in = new Scanner(System.in);

//        STATEMENT_PRESENTER.printStatement(" Hey, " + username + "! \n What do you want to do? ");
//        STATEMENT_PRESENTER.printStatement("Please enter the corresponding number listed below: ");
//        STATEMENT_PRESENTER.printStatement("(1) Sign Up Menu \n(2) Message Menu  \n(3) Log Out");
        String temp2 = in.nextLine();

        switch (temp2) {
            case "1":
                signUpSystem.run(username);
                return false;
            case "2":
                messagingSystem.run(username);
                return false;
            case "3":
//                STATEMENT_PRESENTER.printStatement("You have logged out successfully! ;)");
                mainLevel();
                return true;
            default:
//                STATEMENT_PRESENTER.printStatement("Please enter the corresponding number and try again");
                return false;
        }
    }

    private void loggedInMenuOrganizer(String username){
        boolean flag = true;
        while(flag){
            if (loggedInMenuOrganizerHelper(username)){
                flag = false;
            }
        }
    }

    private boolean loggedInMenuOrganizerHelper(String username){
        Scanner in = new Scanner(System.in);

//        STATEMENT_PRESENTER.printStatement(" Hey, " + username + "! \n What do you want to do? ");
//        STATEMENT_PRESENTER.printStatement("Please enter the corresponding number listed below: ");
//        STATEMENT_PRESENTER.printStatement("(1) Sign Up Menu \n(2) Message Menu  \n(3) Schedule Menu " +
//                "\n(4) Create Speaker account \n(5) Log Out");
        String temp2 = in.nextLine();

        switch (temp2) {
            case "1":
                signUpSystem.run(username);
                return false;
            case "2":
                messagingSystem.run(username);
                return false;
            case "3":
                schedulingSystem.run();
                return false;
            case "4":
//                STATEMENT_PRESENTER.printStatement("Please enter a Username:");
                String userName = in.nextLine();
//                STATEMENT_PRESENTER.printStatement("Please enter a Password:");
                String password = in.nextLine();
                if (userManager.createSpeakerAccount(userName, password)) {
//                    STATEMENT_PRESENTER.printStatement("You have successfully created a speaker account.");
                } else {
//                    STATEMENT_PRESENTER.printStatement("This username is already in our database.");
//                    STATEMENT_PRESENTER.printStatement("Please enter a different username.");
                }
                return false;
            case "5":
//                STATEMENT_PRESENTER.printStatement("You have logged out successfully! ;)");
                mainLevel();
                return true;
            default:
//                STATEMENT_PRESENTER.printStatement("Please enter the corresponding number and try again");
                return false;
        }
    }

    private void loggedInMenuSpeaker(String username){
        boolean flag = true;

        while (flag){
            if (loggedInMenuSpeakerHelper(username)){
                flag = false;
            }
        }
    }

    private boolean loggedInMenuSpeakerHelper(String username){
        Scanner in = new Scanner(System.in);

//        STATEMENT_PRESENTER.printStatement(" Hey, " + username + "! \n What do you want to do? ");
//        STATEMENT_PRESENTER.printStatement("Please enter the corresponding number listed below: ");
//        STATEMENT_PRESENTER.printStatement("(1) Message Menu \n(2) View List of Events Speaking \n(3) Log Out");
        String temp2 = in.nextLine();

        switch (temp2) {
            case "1":
                messagingSystem.run(username);
                return false;
            case "2":
                if (userManager.getEventsSpeaking(username).isEmpty()){
//                    STATEMENT_PRESENTER.printStatement("You do not have any events scheduled to speak in.");
                }else{
                    EventPresenter eventPresenter = new EventPresenter(eventManager, userManager);
                    eventPresenter.displaySignedUpEvents(username);
                }
                return false;
            case "3":
//                STATEMENT_PRESENTER.printStatement("You have logged out successfully! ;)");
                mainLevel();
                return true;
            default:
//                STATEMENT_PRESENTER.printStatement("Please enter the corresponding number and try again");
                return false;
        }
    }

    public boolean createProgram() {
        chatManager = new ChatManager();
        eventManager = new EventManager();
        roomManager = new RoomManager();
        userManager = new UserManager();
        initializeManagers();
        return true;
    }

    private void initializeManagers() {
        loginSystem = new LoginSystem(userManager);
        messagingSystem = new MessagingSystem(chatManager, userManager, eventManager);
        schedulingSystem = new SchedulingSystem(eventManager, roomManager, userManager);
        signUpSystem = new SignUpSystem(eventManager, userManager, roomManager);
    }

    public void saveProgram(String filename) {
        Writer writer = new Writer();
        Object saveObjects[] = new Object[4];
        saveObjects[0] = chatManager;
        saveObjects[1] = eventManager;
        saveObjects[2] = roomManager;
        saveObjects[3] = userManager;
        writer.writeToFile(filename, saveObjects);
    }

    public String[] displayAllEvents() {
        List<String> events = eventManager.getAllEventTitle();
        String[] toReturn = events.toArray(new String[0]);
        return toReturn;
    }

    public String[] displaySignedUpEvents(String username) {
        List<String> events;
        if (userManager.userType(username).equals("Speaker")){
            events = userManager.getEventsSpeaking(username);
        }else {
            events = userManager.getEventAttending(username);
        }
        String[] toReturn = events.toArray(new String[0]);
        return toReturn;
    }

    public boolean confirmRoom(String roomNumber, int capacity){
        if (roomManager.doesRoomExist(roomNumber)){
            return false;
        }else{
            roomManager.createRoom(roomNumber, capacity);
            return true;
        }

    }

    public int signUpForEvent(String username, String eventTitle) {
        try {
            if (eventManager.isAttendeeAdded(username, eventTitle)) {
//            sp.printStatement("You have signed up for this event before.");
                return 1;
            }
            if (eventManager.canAddAttendee(username, eventTitle)) {
                eventManager.addAttendee(username, eventTitle);
                userManager.signUpEventAttendee(username, eventTitle);
//            sp.printStatement("You have successfully signed up for this event.");
            }
           // if (!signUpSystem.roomNotFull(eventTitle)) {
//            sp.printStatement("The event you have entered is already full.");
              //  return 2;
           // }
        } catch (IllegalArgumentException e) {
//            sp.printStatement("The event title you have entered is invalid.");
            return 3;
        }
        return 0;
    }
    
    public int cancelAttendEvent(String username, String eventTitle) {
        try {
            if (!eventManager.isAttendeeAdded(username, eventTitle)) {
//            sp.printStatement("You haven't signed up for this event yet.");
                return 1;
            }
            if (eventManager.canDeleteAttendee(username, eventTitle)) {
                eventManager.deleteAttendee(username, eventTitle);
                userManager.cancelSpotAttendee(username, eventTitle);
//            sp.printStatement("You have cancelled the spot for this event.");
            }
        } catch (IllegalArgumentException e) {
            return 2;
        }
        return 0;
    }
}
