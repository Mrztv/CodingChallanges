package kg.Timur;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void get_t() throws IOException {
        FileReader reader = new FileReader("src/main/resources/test.txt");
        int ts = Main.getFrequency(reader.readAllAsString()).get('t');
        Assertions.assertEquals(223000, ts);
    }

    @Test
    void get_X() throws IOException {
        FileReader reader = new FileReader("src/main/resources/test.txt");
        int Xs = Main.getFrequency(reader.readAllAsString()).get('X');
        Assertions.assertEquals(333, Xs);
    }

    @Test
    void openDSATest1(){
        HashMap<Character, Integer> frequencyMap = new HashMap<>();

        frequencyMap.put('C', 32);
        frequencyMap.put('D', 42);
        frequencyMap.put('E', 120);
        frequencyMap.put('K', 7);
        frequencyMap.put('L', 42);
        frequencyMap.put('M', 24);
        frequencyMap.put('U', 37);
        frequencyMap.put('Z', 2);

        HuffmanTree huffmanTree = new HuffmanTree();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            Node node = new Node(entry.getValue(), entry.getKey());
            huffmanTree.add(node);
        }
        huffmanTree.computeRoot();
        Node root = huffmanTree.getRoot();

        Map<Character, String> indexes = huffmanTree.setIndexes();
        for (Map.Entry<Character, String> entry : indexes.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        Assertions.assertEquals(306, root.getValue());

    }
}