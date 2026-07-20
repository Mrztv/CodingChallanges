package kg.Timur;

import kg.Timur.JSONElements.*;

import java.io.File;
import java.io.FileReader;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Stack;

public class JSONParser {

    static void main(String[] args) throws ParseException {
        String fileName = args[0];
        try (FileReader fileReader = new FileReader(fileName)) {
            String fileString = fileReader.readAllAsString().trim();
            JSONValue result = null;
            if (isValidBrackets(fileString)) {
                ParseContext context = new ParseContext(fileString);
                result = parseJsonValue(context);
                if(!context.isEnd()) throw new ParseException("Smt after top level", context.getIndex());
                if (result == null) throw new ParseException("Result is null", -1);
                System.out.println(result.toString());
            } else {
                throw new ParseException("Non valid brackets", 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static private boolean isValidBrackets(String json) {
        if (json.isEmpty()) return false;
        boolean inString = false;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < json.length(); i++) {
            char ch = json.charAt(i);
            if (ch == '{' || ch == '[') {
                stack.add(ch);
            } else if ((ch == '}' || ch == ']') && !stack.isEmpty()) {
                if (ch == '}' && stack.peek() == '{') {
                    stack.pop();
                } else if (ch == ']' && stack.peek() == '[') {
                    stack.pop();
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
        return stack.isEmpty();
    }

    static private JSONObject parseObject(ParseContext context) throws ParseException {
        JSONObject result = new JSONObject();
        JSONString key = new JSONString();
        JSONValue value = null;
        boolean mustBeNext = false;
        while (context.hasNext()) {
            // Parse key for object
            while (context.hasNext()) {
                char ch = context.next();
                if (ch == '}' && mustBeNext) throw new ParseException("Unexpected \',\'", context.getIndex());
                if (Character.isWhitespace(ch)) continue;
                if (ch == '}') return result;
                if (ch == '"') {
                    key = parseString(context);
                    break;
                } else throw new ParseException("Error in key finding", context.getIndex());
            }
            while (context.hasNext()) {
                char ch = context.next();
                if(Character.isWhitespace(ch)) continue;
                if(ch == ':') break;
                else throw new ParseException("Need to be colon", context.getIndex());
            }
            // Parse value for object
            value = parseJsonValue(context);
            result.put(key, value);
            while (context.hasNext()) {
                char ch = context.next();
                if (Character.isWhitespace(ch)) continue;
                else if (ch == ',') {
                    mustBeNext = true;
                    break;
                }
                else if (ch == '}') return result;
                else throw new ParseException("Unexpected symbol %c".formatted(ch), context.getIndex());
            }
        }
        throw new ParseException("Object not closed", context.getIndex());
    }

    static private JSONString parseString(ParseContext context) throws ParseException {
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
                } else throw new ParseException("Unexpected esc combination", 0);
            } else if (ch == '"') {
                break;
            } else if (Character.isLetterOrDigit(ch)) {
                result += ch;
            } else if (ch == '\t') {
                throw new ParseException("Tab is not allowed in JSON string", context.getIndex());
            } else if (ch == '\n') {
                throw new ParseException("New line is not allowed in JSON string", context.getIndex());
            }
        }
        return new JSONString(result);
    }

    private static JSONBoolean parseBoolean(ParseContext context) throws ParseException {
        context.previous();
        while (context.hasNext()) {
            char ch = context.next();
            if (ch == 't' && context.next() == 'r' && context.next() == 'u' && context.next() == 'e') {
                return new JSONBoolean(true);
            } else if (ch == 'f' && context.next() == 'a' && context.next() == 'l' && context.next() == 's' && context.next() == 'e') {
                return new JSONBoolean(false);
            } else throw new ParseException("Wrong boolean type", context.getIndex());
        }
        throw new ParseException("End of file", -1);
    }

    private static JSONValue parseJsonValue(ParseContext context) throws ParseException {
        JSONValue result = null;
        while (context.hasNext()) {
            char ch = context.next();
            if (Character.isWhitespace(ch)) continue;
            else if (ch == '{') {
                result = parseObject(context);
                break;
            } else if (ch == '"') {
                result = parseString(context);
                break;
            } else if (ch == 't' || ch == 'f') {
                result = parseBoolean(context);
                break;
            } else if (ch == 'n') {
                result = parseNull(context);
                break;
            } else if (Character.isDigit(ch) || ch == '-') {
                result = parseNumber(context);
                break;
            } else if (ch == '[') {
                result = parseArray(context);
                break;
            } else throw new ParseException("Unexpected symbol %c".formatted(ch), context.getIndex());
        }
        return result;
    }

    private static JSONArray parseArray(ParseContext context) throws ParseException {
        JSONArray result = new JSONArray();
        JSONValue value = null;
        boolean mustBeNext = false;
        while (context.hasNext()) {
            if (Character.isWhitespace(context.next())) continue;
            context.previous();
            if(context.next() == ']' && !mustBeNext) return result;
            context.previous();
            if(context.next() == ']' && mustBeNext) throw new ParseException("Expected next value", context.getIndex());
            context.previous();
            value = parseJsonValue(context);
            result.add(value);
            mustBeNext = false;
            while (context.hasNext()) {
                char ch = context.next();
                if (Character.isWhitespace(ch)) continue;
                else if (ch == ',') {
                    mustBeNext = true;
                    break;
                }
                else if (ch == ']') return result;
                else throw new ParseException("Unexpected symbol %c".formatted(ch), context.getIndex());
            }
        }
        return result;
    }

    private static JSONNumber parseNumber(ParseContext context) throws ParseException {
        context.previous();
        String number = "";
        while (context.hasNext()) {
            char ch = context.next();
            if (Character.isWhitespace(ch)) break;
            if (ch == ','){
                context.previous();
                break;
            }
            if (ch == ']'){
                context.previous();
                break;
            }
            number += ch;
        }
        String tmpNum = number.replaceAll("-", "");
        if (tmpNum.length() > 1){
            if(tmpNum.charAt(0) == '0' && tmpNum.charAt(1) != '.') throw new ParseException("Num cant have lead zero", context.getIndex());
        }
        number.trim();
        Number result = Double.parseDouble(number);
        return new JSONNumber(result);
    }

    private static JSONNull parseNull(ParseContext context) throws ParseException {
        context.previous();
        while (context.hasNext()) {
            char ch = context.next();
            if (ch == 'n' && context.next() == 'u' && context.next() == 'l' && context.next() == 'l') {
                return new JSONNull();
            } else throw new ParseException("Wrong boolean type", context.getIndex());
        }
        throw new ParseException("End of file", -1);
    }
}