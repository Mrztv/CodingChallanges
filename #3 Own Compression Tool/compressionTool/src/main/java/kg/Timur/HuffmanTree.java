package kg.Timur;

import java.util.ArrayList;

public class HuffmanTree {
    private ArrayList<Node> nodes = new ArrayList<>();
    private Node root = null;

    public void add(Node node) {
        nodes.add(node);
        sort();
    }

    public void sort() {
        nodes.sort(Node::compareTo);
    }

    public void computeRoot() {
        ArrayList<Node> tmpList = new ArrayList<>(nodes);
        while (tmpList.size() > 1) {
            Node first = tmpList.get(0);
            tmpList.removeFirst();
            Node second = tmpList.get(0);
            tmpList.removeFirst();
            Node newNode = new Node(first.getValue() + second.getValue(), first, second);
            tmpList.add(newNode);
            tmpList.sort(Node::compareTo);
        }
        root = tmpList.get(0);
    }

    public Node getRoot() {
        return root;
    }
}