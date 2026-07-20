package kg.Timur;

import kg.Timur.JSONElements.JSONObject;
import kg.Timur.JSONElements.JSONString;
import kg.Timur.JSONElements.JSONValue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;

public class JSONParser {
    static void main() {
        try (FileReader fileReader = new FileReader("src/main/resources/tests/step2/invalid.json")) {
            String fileString = fileReader.readAllAsString();
            JSONValue result = null;
            if (isValidBrackets(fileString)) {
                ParseContext context = new ParseContext(fileString);
                while (context.hasNext()) {
                    char ch = context.next();
                    if (Character.isWhitespace(ch)) continue;
                    if (ch == '{') result = parseObject(context);
                    if (ch == '"') result = parseString(context);
                }

                System.out.println(result.toString());
                System.exit(0);
            } else {
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean isValidBrackets(String json) {
        if (json.isEmpty()) return false;


        boolean inString = false;
        Queue<Character> queue = new PriorityQueue<>();
        for (int i = 0; i < json.length(); i++) {
            char ch = json.charAt(i);
            if (ch == '{' || ch == '[') {
                queue.add(ch);
            } else if ((ch == '}' || ch == ']') && !queue.isEmpty()) {
                if (ch == '}' && queue.peek() == '{') {
                    queue.poll();
                } else if (ch == ']' && queue.peek() == '[') {
                    queue.poll();
                } else return false;
            } else if (ch == '\\') {
                if (inString) {
                    i++;
                } else return false;
            } else if (ch == '"') {
                inString = !inString;
            }
        }
        if (inString) return false;
        return queue.isEmpty();
    }

    static JSONObject parseObject(ParseContext context) {
        JSONObject result = new JSONObject();
        JSONString key = new JSONString();
        JSONValue value = null;
        while (context.hasNext()) {
            // Parse key for object
            while (context.hasNext()) {
                char ch = context.next();
                if (Character.isWhitespace(ch)) continue;
                if (ch == '"') {
                    key = parseString(context);
                    break;
                } else System.exit(3);
            }
            while (context.next() != ':') {
            }
            // Parse value for object
            while (context.hasNext()) {
                char ch = context.next();
                if (Character.isWhitespace(ch)) continue;
                if (ch == '{') {
                    value = parseObject(context);
                    break;
                }
                if (ch == '"') {
                    value = parseString(context);
                    break;
                }
            }
            result.put(key, value);
            while (context.hasNext()) {
                char ch = context.next();
                if (Character.isWhitespace(ch)) continue;
                if (ch == ',') break;
                if (ch == '}') return result;
            }
        }
        return result;
    }

    static JSONString parseString(ParseContext context) {
        String result = "";
        while (context.hasNext()) {
            char ch = context.next();
            if (ch == '\\') {
                ch = context.next();
                if (ch == 'u') {
                    String unicode = "";
                    for (int i = 0; i < 4; i++) {
                        char tmpChar = context.next();
                        if ((tmpChar >= '0' && tmpChar <= '9') || (tmpChar >= 'A' && tmpChar <= 'F') || (tmpChar >= 'a' && tmpChar <= 'f')) {
                            unicode += tmpChar;
                        }
                    }
                    char parsedUNI = (char) Integer.parseInt(unicode, 16);
                    result += parsedUNI;
                } else if (ch == 't') {
                    result += '\t';
                } else if (ch == 'r') {
                    result += '\r';
                } else if (ch == 'n') {
                    result += '\n';
                } else if (ch == 'f') {
                    result += '\f';
                } else if (ch == 'b') {
                    result += '\b';
                } else if (ch == 't') {
                    result += '\t';
                } else if (ch == '/') {
                    result += '/';
                } else if (ch == '\\') {
                    result += '\\';
                } else if (ch == '"') {
                    result += '"';
                } else System.exit(2);
            } else if (ch == '"') {
                break;
            } else if (Character.isLetterOrDigit(ch)) {
                result += ch;
            }
        }
        return new JSONString(result);
    }

}