package module3;

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

/**
 * A deque is a generalization of a stack and a queue that supports adding
 * and removing items from either the front or the back of the data structure.
 * @param <Item> item to be stored in the deque
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int count = 0;

    private class Node {
        Item item;
        Node next;
    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null || last == null;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (isEmpty()) {
            last = first;
        }
        count++;
    }

    // add the item to the back
    public void addLast(Item item) {
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
        count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        if (first == last) {
            first = null;
            last = null;
        } else {
            first = first.next;
        }
        count--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        if (first == last) {
            first = null;
            last = null;
        } else {
            Node current = first;
            while (current.next != last) {
                current = current.next;
            }
            current.next = null;
            last = current;
        }
        count--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {

        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next()
        {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

        deque.addFirst("2");
        deque.addFirst("1");
        deque.addLast("3");
        StdOut.println("Size: " + deque.size());

        for(String str : deque) {
            StdOut.print(str + " ");
        }
        StdOut.println();

        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeFirst());

        StdOut.println("Size: " + deque.size());

        for(String str : deque) {
            StdOut.print(str + " ");
        }

        try {
            StdOut.println(deque.removeFirst());
        } catch (NoSuchElementException e) {
            StdOut.println("Cannot remove first since deque is already empty");
        }

        try {
            StdOut.println(deque.removeLast());
        } catch (NoSuchElementException e) {
            StdOut.println("Cannot remove last since deque is already empty");
        }

        deque.addFirst("2");
        deque.addFirst("1");
        deque.addLast("3");
        StdOut.println("Size: " + deque.size());

        for(String str : deque) {
            StdOut.print(str + " ");
        }
    }

}

