package Controllers;

import UseCase.UserManager;
import Controllers.LogInSystem;
import Controllers.MessagingSystem;
import Controllers.Reader;
import Controllers.SchedulingSystem;
import Controllers.SignUpSystem;

import UseCase.EventManager;
import UseCase.ChatManager;
import UseCase.RoomManager;

import java.util.Scanner;

public class UserMenuOptions {
    private Scanner input;
    private String temp;
    private boolean logInStatus;
    private static Reader reader;
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
        logInStatus = false;
    }

    /**
     *
     * @return 1 if successful login, 2 if program needs to be repeated, 3 if program should be ended
     */
    public int initialScreen() {
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

                if (userManager.createAttendeeAccount(userName, password)){
                    //Created successfully
                    System.out.println("You successfully created your account.");
                }else{
                    System.out.println("This Username is already registered.");
                    System.out.println("Please try again");
                }
            } else if (temp.equals("3")) {
                System.out.println("organizer account");
            } else if (temp.equals("4")) {
                System.out.println("Program terminated.");
                return 3;
            } else {
                System.out.println("Please input a valid option.");
            }
            return 2;
    }

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

}