package kg.Timur;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ccwc {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("0 args");
        } else {
            boolean lFlag = false;
            boolean cFlag = false;
            boolean mFlag = false;
            Map<File, String> fileStringMap = new HashMap<>();
            ArrayList<String> filePaths = new ArrayList<>();
            for (String arg : args) {
                if (arg.charAt(0) != '-') {
                    if(arg.charAt(arg.length()-1)=='\\'){

                    }
                    filePaths.add(arg);
                } else {
                    for (char c : arg.toCharArray()) {
                        if (c == 'l') {
                            lFlag = true;
                        } else if (c == 'c') {
                            cFlag = true;
                        } else if (c == 'm') {
                            mFlag = true;
                        }
                    }
                }
            }
            ArrayList<File> filesArray = new ArrayList<>();
            for (String filePath : filePaths) {
                Path path = Paths.get(filePath);
                if (path.toFile().exists()) {
                    filesArray.add(path.toFile());
                    fileStringMap.put(path.toFile(), filePath);
                }
            }
            for (File file : filesArray) {
                try {
                    if (cFlag) {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        System.out.print(fileInputStream.available() + " ");
                        System.out.println(fileStringMap.get(file));
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

        }
    }
}
