package auxiliarStructures;

import customExceptions.NotFoundException;
import java.util.ArrayList;

public class HashTable<K, V> {

    private static final int capacity = 10;
    private final LinkedList<Input<K, V>>[] buckets;
    private int size;

    public HashTable() {
        buckets = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
        size = 0;
    }

    // getters

    public LinkedList<Input<K, V>>[] getBuckets() {
        return buckets;
    }

    public int size() {
        return size;
    }

    public void insert(K key, V value) {
        int index = obtainIndex(key);
        LinkedList<Input<K, V>> cubeta = buckets[index];

        for (int i = 0; i < cubeta.getSize(); i++) {
            Input<K, V> input = cubeta.search(i);
            if (input.key.equals(key)) {
                input.value = value;
                return;
            }
        }

         /*
         since the for already evaluates if there are duplicates, it should never throw the exception,
         but since the LinkedList insert throws the exception (to avoid duplicates) the try-catch must be implemented.
         it won't affect the way the elements are inserted.
          */
        try {
            cubeta.insert(new Input<>(key, value));
        } catch (NotFoundException e) {
            System.out.println("Exception occurred: " + e.getMessage());
            return;
        }
        size++;
    }

    public V obtain(K key) {
        int index = obtainIndex(key);
        LinkedList<Input<K, V>> cubeta = buckets[index];

        for (int i = 0; i < cubeta.getSize(); i++) {
            Input<K, V> input = cubeta.search(i);
            if (input.key.equals(key)) {
                return input.value;
            }
        }
        return null;
    }

    public void delete(K key) {
        int index = obtainIndex(key);
        LinkedList<Input<K, V>> bucket = buckets[index];

        for (int i = 0; i < bucket.getSize(); i++) {
            Input<K, V> input = bucket.search(i);
            if (input.key.equals(key)) {
                bucket.delete(i);
                size--;
                return;
            }
        }
    }

    private int obtainIndex(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    public static class Input<K, V> {
        K key;
        V value;

        Input(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public ArrayList<V> toArrayList() {
        ArrayList<V> values = new ArrayList<>();

        for (LinkedList<Input<K, V>> bucket : buckets) {
            for (int i = 0; i < bucket.getSize(); i++) {
                Input<K, V> input = bucket.search(i);
                values.add(input.value);
            }
        }

        return values;
    }
}

