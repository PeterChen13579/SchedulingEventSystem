package Controllers;

import UseCase.UserManager;
import UseCase.RoomManager;
import UseCase.EventManager;
import UseCase.ChatManager;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * A class that reads data from a file and converts said data to their original form
 * @author Joyce Huang
 */
public class Reader {

    /**
     * A method that information from a previous session from a .txt file
     * @param filename the name of the file to open
     * @return a list of lists that contains all the data needed
     */
    public Object loadData(String filename) {
        File file = new File(filename);
        Object helper = new Object();
        try {
            if (file.exists()) {
                if (file.length() != 0) {
                    InputStream inputFile = new FileInputStream(file);
                    InputStream buffer = new BufferedInputStream(inputFile);
                    ObjectInput input = new ObjectInputStream(buffer);

                    helper = input.readObject();
                    input.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Load failed due to IOException. Creating new conference.");
        } catch (ClassNotFoundException h) {
            System.out.println("Load failed due to ClassNotFoundException. Creating new conference.");
        }
        System.out.println(helper);
        return helper;
    }

    /**
     * Checks all the save files contain a valid object to load
     * @return a boolean stating whether or not the save files can be loaded
     */
    public boolean verifySaves() {
        File[] files = new File[4];
        files[0] = new File("em.txt");
        files[1] = new File("um.txt");
        files[2] = new File("rm.txt");
        files[3] = new File("cm.txt");
        InputStream inputFile;
        InputStream buffer;
        ObjectInput input;
        Object helper;
        try {
            for (int i = 0; i < 4; i++) {
                inputFile = new FileInputStream(files[i]);
                buffer = new BufferedInputStream(inputFile);
                input = new ObjectInputStream(buffer);

                switch(i) {
                    case 0:
                        helper = (EventManager) input.readObject();
                        break;
                    case 1:
                        helper = (UserManager) input.readObject();
                        break;
                    case 2:
                        helper = (RoomManager) input.readObject();
                        break;
                    case 3:
                        helper = (ChatManager) input.readObject();
                        break;
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Load failed due to FileNotFoundException. Creating new conference.");
            return false;
        } catch (ClassCastException cc) {
            System.out.println("Load failed due to ClassCastException. Creating new conference.");
            return false;
        } catch (IOException er) {
            System.out.println("Load failed due to IOException. Creating new conference.");
            return false;
        } catch (ClassNotFoundException cnf) {
            System.out.println("Load failed due to ClassNotFoundException. Creating new conference.");
            return false;
        }
        return true;
    }

}
