package application.utils;

import org.json.simple.JSONObject;

public class JSONError {

    public static final String ERROR_MESSAGE_KEY = "message";

    public static JSONObject jsonErrorWithMessage(String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", message);
        return jsonObject;
    }
}
