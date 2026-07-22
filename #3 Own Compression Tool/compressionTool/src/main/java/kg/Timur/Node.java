package kg.Timur;

public class Node implements Comparable<Node>{
    private int value;
    private Node leftChild = null;
    private Node rightChild = null;
    private Character character = null;

    public Node(int value, Character character) {
        this.value = value;
        this.character = character;
    }

    public Node(int value, Node leftChild, Node rightChild) {
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public Node(int value, Node leftChild, Node rightChild, Character character) {
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.character = character;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    @Override
    public int compareTo(Node o) {
        return this.value - o.value;
    }
}