package kg.Timur.JSONElements;

public class JSONString extends JSONValue {
    String string;

    public JSONString() {
        this.string = "";
    }

    public JSONString(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}