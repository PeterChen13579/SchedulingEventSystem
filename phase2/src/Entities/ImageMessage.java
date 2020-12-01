package Entities;

import java.time.LocalDateTime;

public class ImageMessage extends Message{
    private String imageString;

    /**
     * Creates a new ImageMessage with imageString that represents the Base64 string of the image.
     *
     * @param senderUsername the username of the sender
     * @param time           the date/time of the message
     * @param content        the content of the message
     */
    public ImageMessage(String senderUsername, LocalDateTime time, String content) {
        super(senderUsername, time, content);
        this.imageString = "";
    }

    /**
     * Getter for the image Base64 string.
     * @return The username of a user who sent the message
     */
    public String getImageString(){
        return imageString;
    }

    public void setImageString(String imageString) {this.imageString = imageString;}
}
