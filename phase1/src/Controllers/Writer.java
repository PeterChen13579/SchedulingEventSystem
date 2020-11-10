package Controllers;
import java.io.*;
import java.util.List;

/**
 * A class that deals with the output of data into separate .txt files
 */
public class Writer {
    // users, conferences, events, messages, rooms

    /**
     * A method that saves all the information in a session to a .txt file
     * @param filename name of the file to save to
     * @param allThings all the information needed to save
     * @throws IOException
     */
    public void writeToFile(String filename, List allThings) {
        try {
            OutputStream file = new FileOutputStream(filename);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            output.writeObject(allThings);

            output.close();
        } catch (IOException e) {
            System.out.println(":((");
        }
    }

}
