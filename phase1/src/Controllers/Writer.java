package Controllers;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that deals with the output of data into separate .txt files
 */
public class Writer {
    // users, conferences, events, messages, rooms

    /**
     * A method that saves an object to a .txt file
     * @param filename name of the file to save to
     * @param thing the object being saved
     * @throws IOException
     */
    public void writeToFile(String filename, Object thing) {
        try {
            OutputStream file = new FileOutputStream(filename);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            output.writeObject(thing);

            output.close();
        } catch (IOException e) {
            System.out.println(":((");
        }
    }

}
