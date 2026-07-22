package kg.Timur;

import javax.xml.transform.Source;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

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

    public void printTree() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        int space = 128;
        int phantomes = 0;
        while (!queue.isEmpty()) {
            Queue<Node> tmpQueue = new LinkedList<>();
            System.out.print(" ".repeat(space));
            for (int i = 0; i < phantomes; i++) {
                System.out.print(" ".repeat(space));
            }
            while (!queue.isEmpty()) {
                Node node = queue.poll();
                if (node.getLeftChild() != null) {
                    tmpQueue.add(node.getLeftChild());
                } else {
                    if (!queue.isEmpty()) {
                        phantomes++;
                    }
                }
                if (node.getRightChild() != null) {
                    tmpQueue.add(node.getRightChild());
                } else {
                    if (!queue.isEmpty()) {
                        phantomes++;
                    }
                }
                if (node.getCharacter() != null) {
                    System.out.print(node.getCharacter());
                } else System.out.print(node.getValue());

                if (!queue.isEmpty()) {
                    System.out.print(" ".repeat(space * 2));
                }
            }
            System.out.println();
            space /= 2;
            phantomes *= 2;
            queue = tmpQueue;
        }


    }

    public Map<Character, String> setIndexes() {
        Map<Character, String> result = new HashMap<>();
        Map<Node, String> tmp = new HashMap<>();
        tmp.put(root, "");
        Stack<Node> stack = new Stack<>();
        stack.add(root);
        Set<Node> grey = new HashSet<>();
        Set<Node> black = new HashSet<>();
        while (!stack.isEmpty()) {
            Node node = stack.peek();
            if (!grey.contains(node) && !black.contains(node)) {
                grey.add(node);
                if (node.getRightChild() != null) {
                    tmp.put(node.getRightChild(), tmp.get(node) + "1");
                    stack.add(node.getRightChild());
                }
                if (node.getLeftChild() != null) {
                    tmp.put(node.getLeftChild(), tmp.get(node) + "0");
                    stack.add(node.getLeftChild());
                }
            } else if (grey.contains(node)) {
                black.add(node);
                stack.pop();
            } else if (black.contains(node)) {
                stack.pop();
            }
        }
        for (Map.Entry<Node, String> entry : tmp.entrySet()) {
            if (entry.getKey().getCharacter() != null) {
                result.put(entry.getKey().getCharacter(), entry.getValue());
            }
        }
        return result;
    }

}