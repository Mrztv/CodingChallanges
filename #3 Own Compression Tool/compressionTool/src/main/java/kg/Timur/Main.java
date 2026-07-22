package kg.Timur;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Argument error");
            return;
        }
        try (FileReader fileReader = new FileReader(args[0])) {
            String fileString = fileReader.readAllAsString();
            Map<Character, Integer> frequencyMap = getFrequency(fileString);
            HuffmanTree huffmanTree = new HuffmanTree();
            for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
                Node node = new Node(entry.getValue(), entry.getKey());
                huffmanTree.add(node);
            }
            huffmanTree.computeRoot();

            FileWriter fileWriter = new FileWriter("src/main/resources/output.txt");
            Map<Character, String> indexes = huffmanTree.setIndexes();
            for (Map.Entry<Character, String> entry : indexes.entrySet()){
                if (entry.getKey() =='\n') fileWriter.write("\\n" + ": " + entry.getValue() + '\n');
                else if (entry.getKey() =='\r') fileWriter.write("\\r" + ": " + entry.getValue() + '\n');
                else if (entry.getKey() =='\t') fileWriter.write("\\t" + ": " + entry.getValue() + '\n');
                else fileWriter.write(entry.getKey() + ": " + entry.getValue() + '\n');
            }
            fileWriter.write("#########################\n");
            fileWriter.close();

            FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/output.txt", true);
            BitOutputStream bitOutStream = new BitOutputStream(fileOutputStream);

            for (int i = 0; i < fileString.length(); i++) {
                char ch = fileString.charAt(i);

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

    public static Map<Character, Integer> getFrequency(String file) {
        Map<Character, Integer> result = new HashMap<>();
        for (int i = 0; i < file.length(); i++) {
            char ch = file.charAt(i);
            result.merge(ch, 1, Integer::sum);
        }
        return result;
    }

}