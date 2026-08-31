package kg.Timur;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class cutTool {
    static List<Integer> fList = new ArrayList<>();
    static String delimiter = "\t";

    static void main(String[] args) {
        try {
            System.exit(cut(args));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    static int cut(String[] args) throws IOException {
        String fileName = "";
        for (int i = 0; i < args.length; i++) {
            String argument = args[i];
            if (argument.startsWith("-")) {
                if (argument.length() > 1) {
                    if (argument.charAt(1) == 'f') {
                        fillFList(argument);
                    } else if (argument.charAt(1) == 'd') {
                        setDelim(argument);
                    }
                }
            } else {
                fileName = argument;
            }
        }
        if (!fileName.equals("")) {
            File file = null;
            if (Files.exists(Paths.get(fileName))) {
                file = new File(fileName);
            } else throw new FileNotFoundException(fileName);

            FileReader fileReader = new FileReader(file);
            List<String> lines = fileReader.readAllLines();
            for (String line : lines) {
                String cutted = cutLine(line);
                System.out.println(cutted);
            }
        } else {
            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            List<String> lines = inputStreamReader.readAllLines();
            for (String line : lines) {
                String cutted = cutLine(line);
                System.out.println(cutted);
            }
        }

        return 0;
    }

    static String cutLine(String line) {
        String result = "";
        String[] words = line.split(delimiter);
        for (int column : fList) {
            result += words[column - 1] + delimiter;
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    private static void setDelim(String argument) {
        if (argument.length() > 2) {
            int k = argument.indexOf('"');
            String delim = null;
            if (k != -1) {
                delim = argument.split("\"")[1];
            } else {
                delim = argument.substring(2);
            }
            delimiter = delim;
            return;
        } else {
            delimiter = "\t";
        }
    }


    static void fillFList(String fString) {
        if (fString.length() > 2) {
            int k = fString.indexOf('"');
            String nums;
            if (k != -1) {
                nums = fString.split("\"")[1];
            } else {
                nums = fString.substring(2);
            }
            char delim = fString.contains(" ") ? ' ' : ',';
            String[] numArray = fString.substring(2).split(String.valueOf(delim));
            for (int i = 0; i < numArray.length; i++) {
                String num = numArray[i];
                fList.add(Integer.parseInt(num));
            }
        }


    }


}