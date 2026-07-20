package kg.Timur;

public class ParseContext {
    private String string;
    private int index;

    public ParseContext(String string) {
        this.string = string;
        index = 0;
    }

    public void setIndex(int index) {
        if (string.isEmpty()){
            return;
        }
        if (index >= string.length() || index < 0){
            return;
        }
        this.index = index;
    }

    public boolean hasNext(){
        return index < string.length();
    }

    public Character next() throws IndexOutOfBoundsException{
        if (index < string.length()){
            index++;
            return string.charAt(index-1);
        }
        throw new IndexOutOfBoundsException("Cannot read next character");
    }


    public boolean hasPrevious(){
        return index > 0;
    }

    public Character previous() throws IndexOutOfBoundsException{
        if (index > 0){
            index--;
            return string.charAt(index+1);
        }
        throw new IndexOutOfBoundsException("Cannot read previous character");
    }
}