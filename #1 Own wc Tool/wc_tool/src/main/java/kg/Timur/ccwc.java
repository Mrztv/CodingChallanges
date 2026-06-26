package kg.Timur;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ccwc {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("0 args");
        } else {
            boolean lFlag = false;
            boolean mFlag = false;
            boolean cFlag = false;
            boolean wFlag = false;
            Map<File, String> fileStringMap = new HashMap<>();
            ArrayList<String> filePaths = new ArrayList<>();
            for (String arg : args) {
                if (arg.charAt(0) != '-') {
                    filePaths.add(arg);
                } else {
                    for (char c : arg.toCharArray()) {
                        if (c == 'l') {
                            lFlag = true;
                        } else if (c == 'c') {
                            cFlag = true;
                        } else if (c == 'm') {
                            mFlag = true;
                        } else if (c == 'w') {
                            wFlag = true;
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
                    System.out.print('\t');
                    if (cFlag) {
                        cFlag(file);
                    }
                    if (lFlag) {
                       lFlag(file);
                    }
                    if (wFlag) {
                       wFlag(file);
                    }
                    if (mFlag){
                        mFlag(file);
                    }
                    if (!cFlag && !lFlag && !wFlag && !mFlag){
                        lFlag(file);
                        wFlag(file);
                        cFlag(file);
                    }
                    System.out.println(fileStringMap.get(file));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

        }
    }

    private static void cFlag(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        System.out.print(fileInputStream.available() + " ");
    }

    private static void lFlag(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        System.out.print(fileReader.readAllLines().size() + " ");
    }

    private static void wFlag(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        String text = fileReader.readAllAsString();
        System.out.print(Arrays.stream(text.split("\\s+")).toArray().length + " ");
    }

   private static void mFlag(File file) throws IOException {
       FileReader fileReader = new FileReader(file);
       System.out.print(fileReader.readAllAsString().length() + " ");
   }
}
