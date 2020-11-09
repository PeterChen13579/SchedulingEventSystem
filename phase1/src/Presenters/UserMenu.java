package Presenters;
import UseCase.UserManager;

import java.util.Scanner;

public class UserMenu {
    private Scanner input;
    private String temp;
    private boolean terminate;

    public UserMenu() {
        input = new Scanner(System.in);
        temp = new String("0");
        terminate = false;

        System.out.println("(1) Log In \n (2) Create Attendee Account \n (3) Create Organizer Account" +
                "\n (4) Quit");
        while (!terminate) {
            temp = input.nextLine();
            if (temp.equals("1")) {
                //add login method here
                System.out.println("log in");
                terminate = true;
            } else if (temp.equals("2")) {
                //add create attendee account here
                System.out.println("Please enter a Username:");
                String userName = input.nextLine();
                System.out.println("Please enter a Password.");
                String password = input.nextLine();

                UserManager manage = new UserManager();
                if (manage.createAttendeeAccount(userName, password)){
                    //Created successfully
                    System.out.println("You successfully created your account.");
                }else{
                    System.out.println("This Username is already registered.");
                    System.out.println("Please try again");
                }
                terminate = true;
            } else if (temp.equals("3")) {
                System.out.println("organizer account");
                terminate = true;
            } else if (temp.equals("4")) {
                System.out.println("quit");
                terminate = true;
            } else {
                System.out.println("try again");
            }
        }
        new UserMenu();
    }



}
