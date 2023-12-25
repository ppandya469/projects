package deque;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class LinkedListDeque<T> implements Deque<T> {

    private class Node { //IntNode nested class
        private T item;
        private Node prev;
        private Node next;

        Node(T i, Node n, Node p) { //IntNode class constructor
            this.item = i;
            this.prev = p;
            this.next = n;
        }

        Node() {
            //empty constructor for self-reference
        }
    }

    private Node sentinel; //SENTINEL NODE
    private int size = 0;

    public LinkedListDeque() { //DEQUE CONSTRUCTOR
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    private class LinkedListIterator implements Iterator<T> { //iterator class
        private Node  wizPos;

        public LinkedListIterator() {
            wizPos = sentinel;
        }

        public boolean hasNext() {
            return wizPos.next != sentinel;
        }

        public T next() {
            T returnItem = wizPos.next.item;
            wizPos = wizPos.next;
            return returnItem;
        }
    }

    //MAIN
    public static void main(String[] args) {
        Deque<Integer> lld = new LinkedListDeque<>();

        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        for (String s : lld1) {
            System.out.println(s);
        }

        Deque<String> lld2 = new LinkedListDeque<>();

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        System.out.println(lld2);
    }

    @Override
    public void addFirst(T x) {
        //ADD CODE
        Node temp1 = sentinel.next;
        Node temp2 = new Node(x, temp1, sentinel);
        temp1.prev = temp2;
        sentinel.next = temp2;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node temp1 = sentinel.prev;
        Node temp2 = new Node(x, sentinel, temp1);
        sentinel.prev = temp2;
        temp1.next = temp2;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node temp = new Node();
        temp = sentinel.next;
        while (temp != sentinel) {
            returnList.add(temp.item);
            temp = temp.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            Node temp1 = sentinel.next;
            Node temp2 = sentinel.next.next;
            sentinel.next = temp2;
            temp2.prev = sentinel;
            size--;
            return temp1.item;
        }
    }


    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            Node temp1 = sentinel.prev;
            Node temp2 = sentinel.prev.prev;
            sentinel.prev = temp2;
            temp2.next = sentinel;
            size--;
            return temp1.item;
        }
    }

    @Override
    public T get(int index) {
        if ((index < 0) || (index > size) || (size == 1)) {
            return null;
        } else {
            int i = 0;
            Node temp = new Node();
            temp = sentinel.next;
            while (i < index) {
                temp = temp.next;
                i++;
            }
            return temp.item;
        }
    }

    public T getRecursiveHelper(int index, int i, Node temp) {
        if (index == i) {
            return temp.item;
        }
        i = i + 1;
        return getRecursiveHelper(index, i, temp.next);
    }

    @Override
    public T getRecursive(int index) {
        int i = 0;
        if ((index < 0) || (index > size) || (size == 1)) {
            return null;
        }
        Node temp = new Node();
        temp = sentinel.next;
        return getRecursiveHelper(index, i, temp);
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else if (other instanceof Deque otherDeque) {
            if (otherDeque.size() != this.size()) {
                return false;
            }

            for (int i = 0; i < size(); i += 1) {
                if (!otherDeque.get(i).equals(get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /* Returns true if this map contains a mapping for the specified key.
     */
    public boolean contains(T x) {

        Node current = sentinel.next;
        while (current != sentinel) {
            if (current.item.equals(x)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder returnSB = new StringBuilder("[");
        Node current = sentinel.next;
        while (current.next != sentinel) {
            returnSB.append(current.item);
            returnSB.append(", ");
            current = current.next;
        }
        returnSB.append(current.item);
        returnSB.append("]");
        return returnSB.toString();
    }
}
