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
    private static LogInSystem logInSystem;
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
            userMenu.printStatement("Please enter your username:");
            String userName = in.nextLine();
            userMenu.printStatement("Please enter your password:");
            String password = in.nextLine();
            //
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

        }else{
            userMenu.printStatement("Please enter the corresponding number and try again");
        }
        if (! temp2.equals("4")) {
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
            chatManager = new ChatManager(reader.loadData("chats.txt"));
            eventManager = new EventManager(reader.loadData("events.txt"));
            roomManager = new RoomManager(reader.loadData("rooms.txt"));
            userManager = new UserManager(reader.loadData("attendees.txt"),
                    reader.loadData("organizers.txt"), reader.loadData("speakers.txt"));
        } else {
            chatManager = new ChatManager();
            eventManager = new EventManager();
            roomManager = new RoomManager();
            userManager = new UserManager();
        }
        logInSystem = new LogInSystem(userManager);
        messagingSystem = new MessagingSystem(chatManager, userManager, eventManager);
        schedulingSystem = new SchedulingSystem(eventManager, roomManager, userManager);
        signUpSystem = new SignUpSystem(eventManager, userManager);
    }



    /**
     * Saves all information of the current session
     */
    public void saveProgram() {
        writer = new Writer();
        writer.writeToFile("organizers.txt", userManager.getAllOrganizer());
        writer.writeToFile("attendees.txt", userManager.getAllAttendee());
        writer.writeToFile("speakers.txt", userManager.getAllSpeaker());
        writer.writeToFile("events.txt", eventManager.getAllEvents());
        writer.writeToFile("rooms.txt", roomManager.getAllRoom());
        writer.writeToFile("chats.txt", chatManager.getAllChats());
    }


}
