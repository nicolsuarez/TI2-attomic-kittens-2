package auxiliarStructures;

public class Queue<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public Queue() {
        head = null;
        tail = null;
        size = 0;
    }

    // getters - setters

    public Node<T> getHead() { return head; }

    public void setHead(Node<T> head) { this.head = head; }

    public Node<T> getTail() { return tail; }

    public void setTail(Node<T> tail) { this.tail = tail; }

    public int getSize() { return size; }

    public void setSize(int size) { this.size = size; }

    // methods

    public void enqueue(T element) {
        Node<T> nodeToEnqueue = new Node<>(element);
        if(isEmpty()) {
            head = nodeToEnqueue;
        } else {
            tail.setNext(nodeToEnqueue);
        }
        tail = nodeToEnqueue;
        size++;
    }

    public T dequeue() {
        if(isEmpty()) {
            return null;
        }
        T data = head.getData();
        head = head.getNext();
        size--;
        if(head == null) {
            tail = null;
        }
        return data;
    }

    public T front() {
        if(isEmpty()) {
            return null;
        }
        return head.getData();
    }

    public boolean isEmpty() {
        if(head == null) {
            return true;
        }
        return false;
    }
}

