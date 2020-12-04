package Entities;

import com.sun.xml.internal.ws.addressing.WsaTubeHelper;

import java.time.LocalDateTime;

public class Request extends Message{
    public boolean status;
    /**
     * Creates a new Message
     *
     * @param senderUsername the username of the sender
     * @param time           the date/time of the message
     * @param content        the literal string representing the message
     */
    public Request(String senderUsername, LocalDateTime time, String content) {
        super(senderUsername, time, content);
    }

    public boolean getStatus(){
        return status;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

}
