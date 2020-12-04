package UseCase;

import Entities.Event;
import Entities.Request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages all the requests for the program. Calls methods in Request.
 * @author Xinyi Chen and Xinpeng Shan
 */
public class RequestManager implements Serializable {
    private final List<Request> allRequests;

    /**
     * Initialize a new RequestManager
     */
    public RequestManager(){
        allRequests = new ArrayList<>();
    }


    public void markedAsAddressed(Integer requestNum){
        for(Request request: allRequests ){
            if(request.getRequestNum().equals(requestNum)){
                request.setStatus();
            }
        }
    }

    /**
     * Get all request numbers
     * @return a list of request numbers (where all request numbers are unique integers)
     */
    public List<Integer> allRequestNum() {
        List<Integer> requestNumList = new ArrayList<>();
        for(Request request: allRequests){
            requestNumList.add(request.getRequestNum());
        }
        return requestNumList;
    }

    /**
     * Private helper method to get the request info of a request
     * @param request a Request
     * @return the string description of the request contains the request number, request content and request status
     */
    private String getRequestInfo(Request request){
        String status;
        if(request.getStatus()){
            status = "addressed";
        }else {status = "pending";}
        String requestContent = request.getContent();
        Integer requestNum = request.getRequestNum();
        return  "Request No." + requestNum + " : " + requestContent + " [ " + status + " ] ";
    }

    /**
     * Get all request info
     * @return a Arraylist of request info of all requests
     */
    public String[] allRequestInfo(){
        List<String> requestInfo = new ArrayList<>();
        for(Request request : allRequests){
            requestInfo.add(getRequestInfo(request));
        }
        return requestInfo.toArray(new String[0]);
    }
}
