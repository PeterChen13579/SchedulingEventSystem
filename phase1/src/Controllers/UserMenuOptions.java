package Controllers;

import UseCase.UserManager;
import UseCase.EventManager;
import UseCase.ChatManager;
import UseCase.RoomManager;

import java.util.Scanner;

public class UserMenuOptions {
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

    public UserMenuOptions() {
        input = new Scanner(System.in);
        temp = new String("0");
        terminated = false;
    }

    /**
     * lets the user input whether or not they want to load the previous conference
     */
    public void load() {
        terminated = false;
        while (!terminated) {
            temp = input.nextLine();
            if (temp.equals("1")) {
                createProgram(true);
                terminated = true;
            } else if (temp.equals("2")) {
                createProgram(false);
                terminated = true;
            }
        }
    }

    /**
     * functionality of all the logging in and signing up options
     * @return 1 if successful login, 2 if program needs to be repeated, 3 if program should be ended
     */
    public int logInSignUp() {
        terminated = false;
        while (!terminated) {
            temp = input.nextLine();
            if (temp.equals("1")) {
                //add login method here
                logInSystem.run();
                return 1;
            } else if (temp.equals("2")) {
                //add create attendee account here
                System.out.println("Please enter a Username:");
                String userName = input.nextLine();
                System.out.println("Please enter a Password.");
                String password = input.nextLine();

                if (userManager.createAttendeeAccount(userName, password)) {
                    //Created successfully
                    System.out.println("You successfully created your account.");
                } else {
                    System.out.println("This Username is already registered.");
                    System.out.println("Please try again");
                }
                return 2;
            } else if (temp.equals("3")) {
                System.out.println("organizer account");
                return 2;
            } else if (temp.equals("4")) {
                System.out.println("Program terminated.");
                return 3;
            } else {
                System.out.println("Please input a valid option.");
            }
        }
        return 2;
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