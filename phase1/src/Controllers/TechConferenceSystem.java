package Controllers;

import UseCase.ChatManager;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;
import Presenters.StatementPresenter;
import java.util.Scanner;

public class TechConferenceSystem {

    private static LoginSystem loginSystem;
    private static MessagingSystem messagingSystem;
    private static SchedulingSystem schedulingSystem;
    private static SignUpSystem signUpSystem;
    private static UserManager userManager;
    private static ChatManager chatManager;
    private static EventManager eventManager;
    private static RoomManager roomManager;
    private static final StatementPresenter STATEMENT_PRESENTER = new StatementPresenter();

    public TechConferenceSystem(){

        //Basically controller does all the logic, presenter PRINTS to screen.
        boolean flag = true;
        while(flag){
            if (start()) {
                flag = false;
            }
        }
        mainLevel();
    }

    private boolean start(){

        STATEMENT_PRESENTER.printStatement("Please enter the corresponding number listed below: ");
        STATEMENT_PRESENTER.printStatement("(1) Load Existing Conference \n(2) Create New Conference");


        Scanner input = new Scanner(System.in);
        String temp = input.nextLine();

        if (temp.equals("1")) {
            try {
                createProgram(true);
                return true;
            } catch (ClassCastException exception) {
                STATEMENT_PRESENTER.printStatement("There is no existing Conference, please create a new one. ");
                return false;
            }
        } else if (temp.equals("2")) {
            createProgram(false);
            return true;
        } else {
            STATEMENT_PRESENTER.printStatement("Please enter the corresponding number and try again");
            return false;
        }
    }


    public void mainLevel(){
        boolean flag = true;
        while(flag){
            if(mainLevelHelper()) {
                flag = false;
            }
        }
    }

    private boolean mainLevelHelper(){
        Scanner in = new Scanner(System.in);

        STATEMENT_PRESENTER.printStatement("What do you want to do? ;)");
        STATEMENT_PRESENTER.printStatement("Please enter the corresponding number listed below: ");
        STATEMENT_PRESENTER.printStatement("(1) Log In \n(2) Create Attendee Account  \n(3) Create Organizer Account \n(4) Quit");
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
                STATEMENT_PRESENTER.printStatement("Please enter a username:");
                String userName = in.nextLine();
                STATEMENT_PRESENTER.printStatement("Please enter a password:");
                String password = in.nextLine();
                if (userManager.createAttendeeAccount(userName, password)) {
                    STATEMENT_PRESENTER.printStatement("You have successfully created an Attendee Account!");
                } else {
                    STATEMENT_PRESENTER.printStatement("This username is already in our database.");
                    STATEMENT_PRESENTER.printStatement("Please enter a different username");
                }
                return false;
            }
            case "3": {
                STATEMENT_PRESENTER.printStatement("Please enter a username:");
                String userName = in.nextLine();
                STATEMENT_PRESENTER.printStatement("Please enter a password:");
                String password = in.nextLine();
                if (userManager.createOrganizerAccount(userName, password)) {
                    STATEMENT_PRESENTER.printStatement("You have successfully created an Organizer Account!");
                } else {
                    STATEMENT_PRESENTER.printStatement("This username is already in our database.");
                    STATEMENT_PRESENTER.printStatement("Please enter a different username");
                }
                return false;
            }
            case "4":
                STATEMENT_PRESENTER.printStatement("You have exited the program.");
                saveProgram();
                return true;
            default:
                STATEMENT_PRESENTER.printStatement("Please enter the corresponding number and try again");
                break;
        }
        return false;
    }

    public void loggedInMenuAttendee(String username){
        boolean flag = true;
        while (flag){
            if (loggedInMenuAttendeeHelper(username)) { flag = false; }
        }
    }

    private boolean loggedInMenuAttendeeHelper(String username){
        Scanner in = new Scanner(System.in);

        STATEMENT_PRESENTER.printStatement("What do you want to do? ");
        STATEMENT_PRESENTER.printStatement("(1) Sign Up Menu \n(2) Message Menu  \n(3) Log Out");
        STATEMENT_PRESENTER.printStatement("Please type the corresponding number of the options: ");
        String temp2 = in.nextLine();

        switch (temp2) {
            case "1":
                signUpSystem.run(username);
                return false;
            case "2":
                messagingSystem.run(username);
                return false;
            case "3":
                STATEMENT_PRESENTER.printStatement("You have logged out successfully! ;)");
                mainLevel();
                return true;
            default:
                STATEMENT_PRESENTER.printStatement("Please enter the corresponding number and try again");
                return false;
        }
    }

    public void loggedInMenuOrganizer(String username){
        boolean flag = true;
        while(flag){
            if (loggedInMenuOrganizerHelper(username)){
                flag = false;
            }
        }
    }

    private boolean loggedInMenuOrganizerHelper(String username){
        Scanner in = new Scanner(System.in);

        STATEMENT_PRESENTER.printStatement("(1) Sign Up Menu \n(2) Message Menu  \n(3) Schedule Menu " +
                "\n(4) Create Speaker account \n(5) Log Out");
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
                STATEMENT_PRESENTER.printStatement("Please enter a Username:");
                String userName = in.nextLine();
                STATEMENT_PRESENTER.printStatement("Please enter a Password:");
                String password = in.nextLine();
                if (userManager.createSpeakerAccount(userName, password)) {
                    STATEMENT_PRESENTER.printStatement("You have successfully created a speaker account.");
                } else {
                    STATEMENT_PRESENTER.printStatement("This username is already in our database.");
                    STATEMENT_PRESENTER.printStatement("Please enter a different username.");
                }
                return false;
            case "5":
                STATEMENT_PRESENTER.printStatement("You have logged out successfully! ;)");
                mainLevel();
                return true;
            default:
                STATEMENT_PRESENTER.printStatement("Please enter the corresponding number and try again");
                return false;
        }
    }

    public void loggedInMenuSpeaker(String username){
        boolean flag = true;

        while (flag){
            if (loggedInMenuSpeakerHelper(username)){
                flag = false;
            }
        }
    }

    private boolean loggedInMenuSpeakerHelper(String username){
        Scanner in = new Scanner(System.in);

        STATEMENT_PRESENTER.printStatement("(1) Message Menu  \n(2) Log Out");
        String temp2 = in.nextLine();

        if (temp2.equals("1")){
            messagingSystem.run(username);
            return false;
        }else if (temp2.equals("2")){
            STATEMENT_PRESENTER.printStatement("You have logged out successfully! ;)");
            mainLevel();
            return true;
        }else{
            STATEMENT_PRESENTER.printStatement("Please enter the corresponding number and try again");
            return false;
        }
    }

    /**
     * creates all necessary use cases and controllers based off whether the user loads or not
     * @param load true iff you want to call an existing TechConference.
     */
    private void createProgram(boolean load) {
        if (load) {
            Reader reader = new Reader();
            if (reader.verifySaves()) {
                chatManager = (ChatManager) reader.loadData("cm.txt");
                eventManager = (EventManager) reader.loadData("em.txt");
                roomManager = (RoomManager) reader.loadData("rm.txt");
                userManager = (UserManager) reader.loadData("um.txt");
            } else {
                chatManager = new ChatManager();
                eventManager = new EventManager();
                roomManager = new RoomManager();
                userManager = new UserManager();
            }
        } else {
            chatManager = new ChatManager();
            eventManager = new EventManager();
            roomManager = new RoomManager();
            userManager = new UserManager();
        }
        loginSystem = new LoginSystem(userManager);
        messagingSystem = new MessagingSystem(chatManager, userManager, eventManager);
        schedulingSystem = new SchedulingSystem(eventManager, roomManager, userManager);
        signUpSystem = new SignUpSystem(eventManager, userManager);
    }

    private void saveProgram() {
        Writer writer = new Writer();
        writer.writeToFile("cm.txt", chatManager);
        writer.writeToFile("em.txt", eventManager);
        writer.writeToFile("rm.txt", roomManager);
        writer.writeToFile("um.txt", userManager);
    }
}
