package Entities;

import com.sun.xml.internal.ws.addressing.WsaTubeHelper;

import java.time.LocalDateTime;

public class Request extends Message{
    public Integer requestNum;
    public boolean status = false;
    /**
     * Creates a new Request
     *
     * @param senderUsername the username of the sender
     * @param time           the date/time of the request
     * @param content        the literal string representing the request
     */
    public Request(String senderUsername, LocalDateTime time, String content) {
        super(senderUsername, time, content);
        requestNum = 0;
    }

    public boolean getStatus(){
        return status;
    }

    public void setStatus(){
        this.status = true;
    }

    public Integer getRequestNum() {
        return requestNum;
    }

    public void setRequestNum(int requestNum) {
        this.requestNum = requestNum;
    }

}
