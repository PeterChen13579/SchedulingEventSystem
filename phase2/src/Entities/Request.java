package Entities;

//import com.sun.xml.internal.ws.addressing.WsaTubeHelper;

import java.time.LocalDateTime;

public class Request extends Message{
    public Integer requestNum;
    public LocalDateTime time;
    public boolean status;
    /**
     * Creates a new Request
     *
     * @param senderUsername the username of the sender
     * @param time           the date/time of the request
     * @param content        the literal string representing the request
     */
    public Request(int requestNum, String senderUsername, LocalDateTime time, String content) {
        super(senderUsername, time, content);
        this.requestNum = requestNum;
    }

    public boolean getStatus(){
        return status;
    }

    public void setStatusAddressed(){
        this.status = true;
    }

    public void setStatusPending(){
        this.status = false;
    }

    public Integer getRequestNum() {
        return requestNum;
    }

}
