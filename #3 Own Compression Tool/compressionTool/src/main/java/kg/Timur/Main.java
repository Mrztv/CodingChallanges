package kg.Timur;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.random.RandomGenerator;

public class Main {
    static void main(String[] args) {

        Set<Character> flags = new HashSet<>();

        if (args.length != 2) {
            System.out.println("Argument error");
            return;
        }
        for (String arg : args) {
            if (arg.trim().charAt(0) == '-') {
                for (char ch : arg.substring(1).toCharArray()) {
                    flags.add(ch);
                }
            }
        }


        //writer starts here
        if (flags.contains('e')) {
            try (FileReader fileReader = new FileReader(args[1])) {
                String fileString = fileReader.readAllAsString();
                String outputFileName = "src/main/resources/output.txt";
                Map<Character, Integer> frequencyMap = getFrequency(fileString);
                HuffmanTree huffmanTree = new HuffmanTree();
                long totalLength = 0;
                for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
                    Node node = new Node(entry.getValue(), entry.getKey());
                    totalLength += entry.getValue();
                    huffmanTree.add(node);
                }
                huffmanTree.computeRoot();
                FileWriter fileWriter = new FileWriter(outputFileName);
                fileWriter.write(String.valueOf(totalLength) + '\n');
                Map<Character, String> indexes = huffmanTree.setIndexes();
                for (Map.Entry<Character, String> entry : indexes.entrySet()) {
                    if (entry.getKey() == '\n') fileWriter.write("\\n" + ": " + entry.getValue() + '\n');
                    else if (entry.getKey() == '\r') fileWriter.write("\\r" + ": " + entry.getValue() + '\n');
                    else if (entry.getKey() == '\t') fileWriter.write("\\t" + ": " + entry.getValue() + '\n');
                    else fileWriter.write(entry.getKey() + ": " + entry.getValue() + '\n');
                }
                fileWriter.write("#########################\n");
                fileWriter.close();

                FileOutputStream fileOutputStream = new FileOutputStream(outputFileName, true);
                BitOutputStream bitOutStream = new BitOutputStream(fileOutputStream);

                for (int i = 0; i < fileString.length(); i++) {
                    char ch = fileString.charAt(i);
                    System.out.print(ch);
                    String bitString = indexes.get(ch);
                    for (int j = 0; j < bitString.length(); j++) {
                        char bitCh = bitString.charAt(j);
                        int bit = bitCh - 48;
                        bitOutStream.writeBit(bit);
                    }
                }

                bitOutStream.close();

            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        //reader starts here
        if (flags.contains('d')) {
            String fileName = "src/main/resources/output.txt";

            Map<String, Character> decodeMap = new HashMap<>();
            try {


                byte[] byteArray = Files.readAllBytes(Paths.get(fileName));

                String byteString = new String(byteArray);

                List<String> lines = List.of(byteString.split("\n"));
                long totalLength = Long.parseLong(lines.getFirst());
                lines = lines.subList(1, lines.size() - 1);
                boolean endOfIndexes = false;
                String content = "";
                for (String line : lines) {
                    if (endOfIndexes) {
                        content += line;
                    }
                    if (line.contains("#####")) {
                        endOfIndexes = true;
                    }
                    if (!endOfIndexes) {

                        String[] tokens = line.split(":");
                        String key = tokens[1].trim();
                        String valueStr = tokens[0];
                        char valueCharacter;
                        if (line.charAt(0) == ':') {
                            valueCharacter = ':';
                            key = tokens[2].trim();
                        } else {
                            if (valueStr.equals("\\n")) {
                                valueCharacter = '\n';
                            } else if (valueStr.equals("\\r")) {
                                valueCharacter = '\r';
                            } else if (valueStr.equals("\\t")) {
                                valueCharacter = '\t';
                            } else valueCharacter = valueStr.charAt(0);

                        }

                        decodeMap.put(key, valueCharacter);
                    }

                }

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
                FileInputStream fis = new FileInputStream(fileName);
                while (fis.available() > 0) {
                    int b = fis.read();
                    if (b == '#') {
                        b = fis.read();
                        if (b == '#'){
                            do {
                                b = fis.read();
                            } while (b != '\n');
                            break;
                        }
                    }
                }
                BitInputStream bitInputStream = new BitInputStream(fis);

                String code = "";
                StringBuilder result = new StringBuilder();
                while (totalLength > 0) {
                    code += bitInputStream.read();
                    if (decodeMap.containsKey(code)) {
                        totalLength--;
                        char ch = decodeMap.get(code);
                        code = "";
                        result.append(ch);
                    }
                }

                bitInputStream.close();
                byteArrayInputStream.close();
                FileWriter fileWriter = new FileWriter("src/main/resources/decode.txt");
                fileWriter.write(result.toString());
                fileWriter.close();


            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static Map<Character, Integer> getFrequency(String file) {
        Map<Character, Integer> result = new HashMap<>();
        for (int i = 0; i < file.length(); i++) {
            char ch = file.charAt(i);
            result.merge(ch, 1, Integer::sum);
        }
        return result;
    }

}