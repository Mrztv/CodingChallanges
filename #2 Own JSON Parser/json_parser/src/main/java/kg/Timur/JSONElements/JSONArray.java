package kg.Timur.JSONElements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSONArray extends JSONValue{
    private List<JSONValue> array = new ArrayList<>();

    public JSONArray() {
    }

    public JSONArray(List<JSONValue> array) {
        this.array = array;
    }

    public List<JSONValue> getArray() {
        return array;
    }

    public void setArray(List<JSONValue> array) {
        this.array = array;
    }

    public void add(JSONValue elem) {
        array.add(elem);
    }

    @Override
    public String toString() {
        return Arrays.toString(array.stream().map(Object::toString).toArray());
    }
}