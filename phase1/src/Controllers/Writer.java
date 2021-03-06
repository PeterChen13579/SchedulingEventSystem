package Controllers;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * A class that deals with the output of data into separate .txt files
 * @author Joyce Huang
 */
public class Writer {

    /**
     * A method that saves an object to a .txt file
     * @param filename name of the file to save to
     * @param thing the object being saved
     */
    public void writeToFile(String filename, Object thing) {
        try {
            OutputStream file = new FileOutputStream(filename);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            output.writeObject(thing);

            output.close();
        } catch (IOException e) {
            System.out.println("Save failed due to IOException.");
        }
    }

}
