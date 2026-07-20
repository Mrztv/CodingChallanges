package kg.Timur.JSONElements;

public class JSONNumber extends JSONValue{
    Number number;

    public JSONNumber(Number number) {
        this.number = number;
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    @Override
    public String toString() {
        String result;
        if (number.doubleValue() == (long) number.doubleValue()){
            result = String.valueOf(number.longValue());
        } else {
            result = String.valueOf(number.doubleValue());
        }
        return result;
    }
}