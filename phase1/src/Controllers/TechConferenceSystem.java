package Controllers;

import UseCase.ChatManager;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;
import Presenters.UserMenu;
import java.util.Scanner;

public class TechConferenceSystem {

    private Scanner input;
    private String temp;
    private String temp1;
    private boolean terminated;
    private static Reader reader;
    private static Writer writer;
    private static LoginSystem loginSystem;
    private static MessagingSystem messagingSystem;
    private static SchedulingSystem schedulingSystem;
    private static SignUpSystem signUpSystem;
    private static UserManager userManager;
    private static ChatManager chatManager;
    private static EventManager eventManager;
    private static RoomManager roomManager;
    private static UserMenu userMenu = new UserMenu();

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

        userMenu.printStatement("(1) Load Existing Conference \n(2) Create New Conference");
        userMenu.printStatement("Please enter the corresponding number of the options: ");

        Scanner input = new Scanner(System.in);
        temp = input.nextLine();

        if (temp.equals("1")) {
            try {
                createProgram(true);
                return true;
            } catch (ClassCastException exception) {
                userMenu.printStatement("There is no existing Conference, please create a new one. ");
                return false;
            }
        } else if (temp.equals("2")) {
            createProgram(false);
            return true;
        } else {
            userMenu.printStatement("Please enter the corresponding number and try again");
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

        userMenu.printStatement("What do you want to do? ;)");
        userMenu.printStatement("(1) Log In \n(2) Create Attendee Account  \n(3) Create Organizer Account \n(4) Quit");
        userMenu.printStatement("Please enter the corresponding number of the options: ");
        String temp2 = in.nextLine();

        if (temp2.equals("1")){
            if (loginSystem.run()) {
                String username = loginSystem.getUsername();
                if (userManager.userType(username).equals("Attendee")){
                    loggedInMenuAttendee(username);
                }else if (userManager.userType(username).equals("Organizer")){
                    loggedInMenuOrganizer(username);
                }else{
                    loggedInMenuSpeaker(username);
                }
                return true;
            }else {
                userMenu.printStatement("You have entered an incorrect username or password.\n Please try again.");
                return false;
            }

        }else if (temp2.equals("2")){
                userMenu.printStatement("Please enter a username:");
                String userName = in.nextLine();
                userMenu.printStatement("Please enter a password:");
                String password = in.nextLine();
                if (userManager.createAttendeeAccount(userName, password)){
                    userMenu.printStatement("You have successfully created an Attendee Account!");
                }else {
                    userMenu.printStatement("This username is already in our database.");
                    userMenu.printStatement("Please enter a different username");
                }
                return false;
        }else if (temp2.equals("3")){
                userMenu.printStatement("Please enter a username:");
                String userName = in.nextLine();
                userMenu.printStatement("Please enter a password:");
                String password = in.nextLine();
                if (userManager.createOrganizerAccount(userName, password)){
                    userMenu.printStatement("You have successfully created an Organizer Account!");
                }else {
                    userMenu.printStatement("This username is already in our database.");
                    userMenu.printStatement("Please enter a different username");
                }
                return false;
        }else if (temp2.equals("4")){
            userMenu.printStatement("You have exited the program.");
            saveProgram();
        }else{
            userMenu.printStatement("Please enter the corresponding number and try again");
        }
        return true;
    }

    public void loggedInMenuAttendee(String username){
        boolean flag = true;
        while (flag){
            loggedInMenuAttendeeHelper(username);
        }
    }

    private void loggedInMenuAttendeeHelper(String username){
        Scanner in = new Scanner(System.in);

        userMenu.printStatement("What do you want to do? ");
        userMenu.printStatement("(1) Sign Up Menu \n(2) Message Menu  \n(3) Log Out");
        userMenu.printStatement("Please type the corresponding number of the options: ");
        String temp2 = in.nextLine();

        if (temp2.equals("1")){
            signUpSystem.run(username);
        }else if (temp2.equals("2")){
            messagingSystem.run(username);
        }else if (temp2.equals("3")){
            userMenu.printStatement("You have logged out successfully! ;)");
            mainLevel();
        }else{
            userMenu.printStatement("Please enter the corresponding number and try again");
        }
    }

    public void loggedInMenuOrganizer(String username){
        boolean flag = true;
        while(flag){
            loggedInMenuOrganizerHelper(username);
        }
    }

    private void loggedInMenuOrganizerHelper(String username){
        Scanner in = new Scanner(System.in);

        userMenu.printStatement("(1) Sign Up Menu \n(2) Message Menu  \n(3) Schedule Menu \n(4) Create Speaker " +
                "account \n(5) Log Out");
        String temp2 = in.nextLine();

        if (temp2.equals("1")){
            signUpSystem.run(username);
        }else if (temp2.equals("2")){
            messagingSystem.run(username);
        }else if (temp2.equals("3")){
            schedulingSystem.run();
        }else if (temp2.equals("4")){
            userMenu.printStatement("Please enter a Username:");
            String userName = in.nextLine();
            userMenu.printStatement("Please enter a Password:");
            String password = in.nextLine();
            if (userManager.createSpeakerAccount(userName, password)){
                userMenu.printStatement("You have successfully created a speaker account.");
            }else {
                userMenu.printStatement("This username is already in our database.");
                userMenu.printStatement("Please enter a different username");
            }
        }else if (temp2.equals("5")){
            userMenu.printStatement("You have logged out successfully! ;)");
            mainLevel();
        }else{
            userMenu.printStatement("Please enter the corresponding number and try again");
        }
    }

    public void loggedInMenuSpeaker(String username){
        boolean flag = true;

        while (flag){
            loggedInMenuSpeakerHelper(username);
        }
    }

    private void loggedInMenuSpeakerHelper(String username){
        Scanner in = new Scanner(System.in);

        userMenu.printStatement("(1) Message Menu  \n(2) Log Out");
        String temp2 = in.nextLine();

        if (temp2.equals("1")){
            messagingSystem.run(username);
        }else if (temp2.equals("2")){
            userMenu.printStatement("You have loggged out successfully! ;)");
            mainLevel();
        }else{
            userMenu.printStatement("Please enter the corresponding number and try again");
        }
    }

    /**
     * creates all necessary use cases and controllers based off whether the user loads or not
     * @param load true iff you want to call an existing TechConference.
     */
    private void createProgram(boolean load) {
        if (load) {
            reader = new Reader();
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
        writer = new Writer();
        writer.writeToFile("cm.txt", chatManager);
        writer.writeToFile("em.txt", eventManager);
        writer.writeToFile("rm.txt", roomManager);
        writer.writeToFile("um.txt", userManager);
    }
}
