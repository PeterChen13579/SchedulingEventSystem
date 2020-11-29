package Entities;

import java.time.LocalDateTime;

public class ImageMessage extends Message{
    private final String imageString;

    /**
     * Creates a new ImageMessage
     *
     * @param senderUsername the username of the sender
     * @param time           the date/time of the message
     * @param content        the content of the message
     * @param imageString    the Base64 String representating the image
     */
    public ImageMessage(String senderUsername, LocalDateTime time, String content, String imageString) {
        super(senderUsername, time, content);
        this.imageString = imageString;
    }

    /**
     * getter for the image Base64 string
     * @return The username of a user who sent the message
     */
    public String getImageString(){
        return imageString;
    }
}
