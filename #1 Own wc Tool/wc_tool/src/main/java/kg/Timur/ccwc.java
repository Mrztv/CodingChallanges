package kg.Timur;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ccwc {
    public static void main(String[] args) throws IOException {

        boolean lFlag = false;
        boolean mFlag = false;
        boolean cFlag = false;
        boolean wFlag = false;
        Map<File, String> fileStringMap = new HashMap<>();
        ArrayList<String> filePaths = new ArrayList<>();


        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (i == 0 && arg.charAt(0) == '-') {
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
            } else {
                filePaths.add(arg);
            }
        }


        if (!filePaths.isEmpty()) {
            ArrayList<File> filesArray = new ArrayList<>();
            for (String filePath : filePaths) {
                Path path = Paths.get(filePath);
                if (path.toFile().exists()) {
                    filesArray.add(path.toFile());
                    fileStringMap.put(path.toFile(), filePath);
                }
            }
            for (File file : filesArray) {
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
                if (mFlag) {
                    mFlag(file);
                }
                if (!cFlag && !lFlag && !wFlag && !mFlag) {
                    lFlag(file);
                    wFlag(file);
                    cFlag(file);
                }
                System.out.println(fileStringMap.get(file));
            }


        } else {
            if (System.in.available() > 0) {
                byte[] bytes = System.in.readAllBytes();
                String fileContent = new String(bytes, StandardCharsets.UTF_8);
                File file = new File("/tmp/test");
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(fileContent);
                fileWriter.close();

                if(cFlag || wFlag || lFlag || mFlag){
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
                    if (mFlag) {
                        mFlag(file);
                    }
                } else {
                    lFlag(file);
                    wFlag(file);
                    cFlag(file);
                }
                System.out.println();
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
