package kg.Timur;

import java.io.*;
import java.util.*;

public class JSONParser {
    static void main() {
        try (FileReader fileReader = new FileReader("/home/timur/Projects/Coding Challanges/#2 Own JSON Parser/tests/step1/valid.json")) {
            System.out.println(is_valid_brackets(fileReader));
            if (is_valid_brackets(fileReader)){
                System.exit(0);
            }
            else {
                System.exit(1);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static boolean is_valid_brackets(FileReader fileReader) throws IOException {
        String fileString = fileReader.readAllAsString();
        if (fileString.length() == 0 ) return false;
        Set<String> openers = new HashSet<>();
        openers.add("[");
        openers.add("{");
        openers.add("(");
        Set<String> closers = new HashSet<>();
        closers.add("]");
        closers.add("}");
        closers.add(")");
        Queue<String> queue = new PriorityQueue<>();

        for (char ch : fileString.toCharArray()){
            if (openers.contains(String.valueOf(ch))){
                queue.add(String.valueOf(ch));
            } else if (closers.contains(String.valueOf(ch))) {
                if(queue.isEmpty()){
                    return false;
                }

                switch (ch){
                    case ']':
                        if (queue.peek().equals("[")){
                            queue.poll();
                        }
                        else return false;
                        break;
                    case '}':
                        if (queue.peek().equals("{")){
                            queue.poll();
                        }
                        else return false;
                        break;
                    case ')':
                        if (queue.peek().equals("(")){
                            queue.poll();
                        }
                        else return false;
                        break;
                }
            }
        }
        return queue.isEmpty();
    }

}
