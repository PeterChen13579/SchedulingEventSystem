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

        Scanner input = new Scanner(System.in);
        userMenu.printStatement("(1) Load Existing Conference \n(2) Create New Conference");
        temp = input.nextLine();

        boolean flag = true;
        while(flag) {
            if (temp.equals("1")) {
                createProgram(true);
                flag = false;
            } else if (temp.equals("2")) {
                createProgram(false);
                flag = false;

            } else {
                userMenu.printStatement("Please enter the corresponding number and try again");
            }
        }
        mainLevel();

    }


    public void mainLevel(){
        Scanner in = new Scanner(System.in);

        boolean flag = true;
        userMenu.printStatement("(1) Log In \n(2) Create Attendee Account  \n(3) Create Organizer Account \n(4) Quit");
        String temp2 = in.nextLine();
        if (temp2.equals("1")){
            while (flag) {
                if (loginSystem.run()) {
                    userMenu.printStatement("Please type your username: ");
                    String username = in.nextLine();
                    if (userManager.userType(username).equals("Attendee")){
                        loggedInMenuAttendee(username);
                    }else if (userManager.userType(username).equals("Organizer")){
                        loggedInMenuOrganizer(username);
                    }else{
                        loggedInMenuSpeaker(username);
                    }
                    flag = false;
                }else {
                    userMenu.printStatement("You have entered an incorrect username or password.\n Please try again.");
                }
            }

        }else if (temp2.equals("2")){
            while(flag) {
                userMenu.printStatement("Please enter a username:");
                String userName = in.nextLine();
                userMenu.printStatement("Please enter a password:");
                String password = in.nextLine();
                if (userManager.createAttendeeAccount(userName, password)){
                    userMenu.printStatement("You have successfully created an Attendee Account!");
                    flag = false;
                }else {
                    userMenu.printStatement("This username is already in our database.");
                    userMenu.printStatement("Please enter a different username");
                }
            }
        }else if (temp2.equals("3")){
            while(flag) {
                userMenu.printStatement("Please enter a username:");
                String userName = in.nextLine();
                userMenu.printStatement("Please enter a password:");
                String password = in.nextLine();
                if (userManager.createOrganizerAccount(userName, password)){
                    userMenu.printStatement("You have successfully created an Organizer Account!");
                    flag = false;
                }else {
                    userMenu.printStatement("This username is already in our database.");
                    userMenu.printStatement("Please enter a different username");
                }
            }
        }else if (temp2.equals("4")){
            userMenu.printStatement("You have exited the program.");
            saveProgram();
        }else{
            userMenu.printStatement("Please enter the corresponding number and try again");
        }
        if (! temp2.equals("4")) {
            mainLevel();
        }
    }

    public void loggedInMenuAttendee(String username){
        Scanner in = new Scanner(System.in);

        boolean flag = true;
        userMenu.printStatement("(1) Sign Up Menu \n(2) Message Menu  \n(3) Quit");
        String temp2 = in.nextLine();
        if (temp2.equals("1")){
            while (flag) {
                signUpSystem.run(username);
                flag = false;
            }

        }else if (temp2.equals("2")){
            while(flag) {
                messagingSystem.run(username);
                flag = false;
            }
        }else if (temp2.equals("3")){
            userMenu.printStatement("You have exited the program.");
            saveProgram();
        }else{
            userMenu.printStatement("Please enter the corresponding number and try again");
        }

        if (! temp2.equals("3")) {
            mainLevel();
        }
    }

    public void loggedInMenuOrganizer(String username){
        Scanner in = new Scanner(System.in);

        boolean flag = true;
        userMenu.printStatement("(1) Sign Up Menu \n(2) Message Menu  \n(3) Schedule Menu \n(4) Create Speaker " +
                "account \n(5) Quit");
        String temp2 = in.nextLine();
        if (temp2.equals("1")){
            while (flag) {
                signUpSystem.run(username);
                flag = false;
            }

        }else if (temp2.equals("2")){
            while(flag) {
                messagingSystem.run(username);
                flag = false;
            }
        }else if (temp2.equals("3")){
            while(flag){
                schedulingSystem.run();
                flag = false;
            }
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
                flag = false;
            }
        }else if (temp2.equals("5")){
            userMenu.printStatement("You have exited the program.");
            saveProgram();
        }else{
            userMenu.printStatement("Please enter the corresponding number and try again");
        }
        if (! temp2.equals("5")) {
            mainLevel();
        }
    }

    public void loggedInMenuSpeaker(String username){
        Scanner in = new Scanner(System.in);

        boolean flag = true;
        userMenu.printStatement("(1) Message Menu  \n(2) Quit");
        String temp2 = in.nextLine();
        if (temp2.equals("1")){
            while (flag) {
                messagingSystem.run(username);
                flag = false;
            }

        }else if (temp2.equals("2")){
            userMenu.printStatement("You have exited the program.");
            saveProgram();
        }else{
            userMenu.printStatement("Please enter the corresponding number and try again");
        }

        if (! temp2.equals("2")) {
            mainLevel();
        }
    }

    /**
     * creates all necessary use cases and controllers based off whether the user loads or not
     * @param load
     */
    private void createProgram(boolean load) {
        if (load) {
            reader = new Reader();
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
