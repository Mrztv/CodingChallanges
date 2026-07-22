package kg.Timur;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
            Node root = huffmanTree.getRoot();

            System.out.println(root.getValue());


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
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