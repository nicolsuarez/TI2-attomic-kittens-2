package auxiliarStructures;

import customExceptions.NotFoundException;
import java.util.ArrayList;

public class LinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Getters and setters

    public Node<T> getHead() { return head; }

    public void setHead(Node<T> head) { this.head = head; }

    public Node<T> getTail() { return tail; }

    public void setTail(Node<T> tail) { this.tail = tail; }

    public int getSize() { return size; }

    public void setSize(int size) { this.size = size; }

    // methods

    public void insert(T data) throws NotFoundException {

        if(searchData(data)) { throw new NotFoundException("The data was already registered."); }

        Node<T> node = new Node<>(data);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            Node<T> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            tail = node;
            current.setNext(node);
        }
        size++;
    }

    public boolean searchData(T data) {
        Node<T> current = head;

        while (current != null) {
            if (current.getData().equals(data)) {
                return true;
            }
            current = current.getNext();
        }

        return false;
    }

    public void delete(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        if(head == null) { return; }

        if (index == 0) {
            head = head.getNext();
            if (size == 1) {
                tail = null;
            }
        } else {
            Node<T> prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.getNext();
            }
            Node<T> toDelete = prev.getNext();
            prev.setNext(toDelete.getNext());
            if (toDelete == tail) {
                tail = prev;
            }
        }
        size--;
    }

    public T search(int index) throws IndexOutOfBoundsException{
        Node<T> temp = head;
        int counter = 0;
        while (temp != null) {
            if (counter == index) return temp.getData();
            temp = temp.getNext();
            counter++;
        }
        throw new IndexOutOfBoundsException("Index out of range");
    }

    /*
       just in case we need to turn the linked list into an arraylist,
       it doesn't affect the functionality of the linked list
     */
    public ArrayList<T> toList() {
        ArrayList<T> list = new ArrayList<>();
        Node<T> current = head;

        while (current != null) {
            list.add(current.getData());
            current = current.getNext();
        }
        return list;
    }
}
