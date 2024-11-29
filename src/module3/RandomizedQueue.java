package module3;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A randomized queue is similar to a stack or queue, except that the item
 * removed is chosen uniformly at random among items in the data structure.
 * @param <Item> item to be stored in the queue
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {

    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return first == null || last == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item;
        if (first == last) {
            item = last.item;
            first = null;
            last = null;
        } else {
            int targetIndex = StdRandom.uniformInt(size) + 1;
            int currIndex = 1;
            Node current = first;
            Node prev = null;
            while (currIndex != targetIndex) {
                currIndex++;
                prev = current;
                current = current.next;
            }
            item = current.item;
            if (current == first) {
                first = current.next;
            } else if (current == last) {
                prev.next = null;
                last = prev;
            } else {
                prev.next = current.next;
            }
        }
        size--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int targetIndex = StdRandom.uniformInt(size) + 1;
        int currIndex = 1;
        Node current = first;
        while (currIndex != targetIndex) {
            currIndex++;
            current = current.next;
        }
        return current.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private int remaining;
        private final Item[] shuffledItems;

        public RandomizedQueueIterator() {
            shuffledItems = (Item[]) new Object[size];
            Node current = first;
            remaining = size;
            int i = 0;
            while (current != null) {
                shuffledItems[i++] = current.item;
                current = current.next;
            }
            StdRandom.shuffle(shuffledItems, 0, size);
        }

        public boolean hasNext() {
            return remaining > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return shuffledItems[--remaining];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue("1");
        randomizedQueue.enqueue("2");
        randomizedQueue.enqueue("3");
        randomizedQueue.enqueue("4");
        assert (randomizedQueue.size() == 4);
        StdOut.println("Size: " + randomizedQueue.size());

        StdOut.println("Randomized sampling");
        for (int i = 0; i < randomizedQueue.size(); i++) {
            StdOut.print(randomizedQueue.sample() + " ");
        }
        StdOut.println();

        StdOut.println("Randomized iterator");
        for(String str : randomizedQueue) {
            StdOut.print(str + " ");
        }
        StdOut.println();

        StdOut.println("Randomized dequeuing");
        int size = randomizedQueue.size();
        for (int i = 0; i < size; i++) {
            StdOut.print(randomizedQueue.dequeue() + " ");
        }
        StdOut.println();
        StdOut.println("Size: " + randomizedQueue.size());
        assert (randomizedQueue.size() == 0);
    }
}
