package kg.Timur.JSONElements;

import java.util.HashMap;
import java.util.Map;

public class JSONObject extends JSONValue {
    private Map<JSONString, JSONValue> object;

    public JSONObject(){
        this.object = new HashMap<>();
    }

    public JSONObject(Map<JSONString, JSONValue> object) {
        this.object = object;
    }

    public Map<JSONString, JSONValue> getObject() {
        return object;
    }

    public void setObject(Map<JSONString, JSONValue> object) {
        this.object = object;
    }

    public void put(JSONString key, JSONValue value){
        if(object == null){
            object = new HashMap<>();
        }
        object.put(key, value);
    }

    @Override
    public String toString() {
        String result = "{\n";
        for (Map.Entry<JSONString, JSONValue> entry : object.entrySet()) {
            result += entry.getKey() + ":" + entry.getValue().toString() + "\n";
        }
        result += "}";
        return result;
    }
}