package Nhom02.Nhom02HappyFarm.response;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
//Xu ly cac response tra ve cho client
public class ResponseHandler {

    public Map<String,Object> failResponse(String messageFail){
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Failed: " + messageFail);
        response.put("status", false);
        return response;
    }

    public Map<String,Object> successResponse(String successMessage, Object args){
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Success: " + successMessage);
        response.put("status", true);
        response.put("data", args);
        return response;
    }

    public Map<String,Object> successResponseButNotHaveContent(String successMessage){
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Success: " + successMessage);
        response.put("status", true);
        return response;
    }

    public Map<String,Object> successAndPage(String successMessage, Object args, int numberOfPages){
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Success: " + successMessage);
        response.put("status", true);
        response.put("numberOfPages", numberOfPages);
        response.put("data", args);
        return response;
    }
}
