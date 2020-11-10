package Presenters;
import java.util.Scanner;

public class UserMenu {
    private Scanner input;
    private String temp;
    private boolean terminate;

    public UserMenu() {
        input = new Scanner(System.in);
        temp = new String("0");
        terminate = false;
        run();
    }

    public void run() {
        initialMenu();
    }

    private void initialMenu() {
        System.out.println("(1) Log In \n (2) Create Attendee Account \n (3) Create Organizer Account" +
                "\n (4) Quit");
        while (!terminate) {
            temp = input.nextLine();
            if (temp.equals("1")) {
                System.out.println("log in");
                terminate = true;
            } else if (temp.equals("2")) {
                System.out.println("attendee account");
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
    }

}
