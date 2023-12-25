package deque;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size; // number of items in array
    private int first;
    private int last;
    private int nextFirst;
    private int nextLast;
    private boolean resizeOffFlag;

    public static void main(String[] args) {
        Deque<Integer> ad = new ArrayDeque<>();

        Deque<String> ad1 = new ArrayDeque<>();

        ad1.addLast("front");
        ad1.addLast("middle");
        ad1.addLast("back");
        for (String s : ad1) {
            System.out.println(s);
        }

        Deque<String> ad2 = new ArrayDeque<>();

        ad2.addLast("front");
        ad2.addLast("middle");
        ad2.addLast("back");

        System.out.println(ad2);

        for (String x : ad2) {
            System.out.println(x);
        }

        ad2.addLast("1");
        ad2.addLast("2");
        ad2.addFirst("a");
        ad2.addFirst("b");
        ad2.addLast("c");
        ad2.addFirst("d");
        ad2.removeFirst();
        ad2.removeFirst();
        ad2.removeLast();

        for (String x : ad2) {
            System.out.println(x);
        }
    }

    public ArrayDeque() { //constructor
        items = (T[]) new Object[8];
        size = 0;
        first = -1; // -1 means the list is empty
        last = -1; // -1 means the list is empty
        nextFirst = items.length - 1;
        nextLast = last + 1;
        resizeOffFlag = false;
    }

    public ArrayDeque(int totalItemSize, boolean resizeOf) { //constructor
        items = (T[]) new Object[totalItemSize];
        size = 0;
        first = -1; // -1 means the list is empty
        last = -1; // -1 means the list is empty
        nextFirst = items.length - 1;
        nextLast = last + 1;
        resizeOffFlag = resizeOf;
    }

    public T[] getItems() {
        return items;
    }

    public void setItems(int index, T value) {
        items[index] = value;
    }

    private class ArrayDequeIterator implements Iterator<T> { //iterator class
        private int wizPos;

        public ArrayDequeIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
        }
    }

    /* Resizes the current array to capacity. */
    public void resize(int capacity) {
        if (resizeOffFlag) {
            return;
        }
        T[] tempArray = (T[]) new Object[capacity];
        if (first < last) {
            System.arraycopy(items, first, tempArray, 0, size);
            last = last - first;
            first = 0;
            nextFirst = capacity - 1;
            nextLast = last + 1;
            if (nextLast >= capacity) {
                nextLast = 0;
            }
            items = tempArray;
        } else if (first == last) { // single element list
            if (first < tempArray.length) {
                tempArray[first] = items[first];
                nextFirst = first - 1;
                if (nextFirst <= -1) {
                    nextFirst = capacity - 1;
                }
                nextLast = last + 1;
                if (nextLast >= capacity) {
                    nextLast = 0;
                }
            } else {
                int oldItemsLength = items.length;
                int newItemsLength = tempArray.length;
                int deltaIndex = oldItemsLength - newItemsLength;
                if (((first - deltaIndex) >= 0) && ((first - deltaIndex) < newItemsLength)) {
                    tempArray[first - deltaIndex] = items[first];
                    first = first - deltaIndex;
                    last = first;
                }
                nextFirst = first - 1;
                if (nextFirst <= -1) {
                    nextFirst = capacity - 1;
                }
                nextLast = last + 1;
                if (nextLast >= capacity) {
                    nextLast = 0;
                }
            }
        } else {
            int oldItemsLength = items.length;
            int newItemsLength = tempArray.length;
            int j = (newItemsLength - 1);
            for (int i = (oldItemsLength - 1); i >= first; i--) {
                tempArray[j] = items[i];
                j--;
            }
            first = j + 1;
            for (int i = 0; i <= last; i++) {
                tempArray[i] = items[i];
            }

            nextFirst = first - 1;
            if (nextFirst <= -1) {
                nextFirst = capacity - 1;
            }
            nextLast = last + 1;
            if (nextLast >= capacity) {
                nextLast = 0;
            }
        }
        items = tempArray;
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length) { // if the array is full
            resize(size * 2);
        }

        if (size == 0) { // empty array
            first = 0;
            last = 0;

            items[size] = x;
            first = size;
            nextLast = last + 1;
            nextFirst = items.length - 1;
        } else {
            items[nextFirst] = x;
            first = nextFirst;
            nextFirst--;
            if (nextFirst <= -1) {
                nextFirst = items.length - 1;
            }
        }
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) { // if the array is full
            resize(size * 2);
        }

        if (size == 0) { // empty array
            first = 0;
            nextFirst = items.length - 1;
            last = 0;

            items[size] = x;
            last = size;
            nextLast = last + 1;
        } else {
            items[nextLast] = x;
            last = nextLast;
            nextLast++;

            if (nextLast >= items.length) {
                nextLast = 0;
            }

        }
        size++; // counting number of items
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        if ((first == -1) || (last == -1)) {
            return returnList;
        } else if (first <= last) {
            for (int index = first; index <= last; index++) {
                returnList.add(items[index]);
            }
        } else { // first is greater than last
            for (int index = first; index < (items.length); index++) {
                returnList.add(items[index]);
            }

            for (int index = 0; index <= last; index++) {
                returnList.add(items[index]);
            }
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

    private boolean areWeLoitering() {
        float usage = ((float) size / (float) items.length);
        return (usage < 0.25);
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            first = -1;
            last = -1;
            nextFirst = 0;
            nextLast = 0;
            return null;
        } else {
            T x = items[first]; // makes a copy of the first item
            items[first] = null; // sets first item to null (for garbage collector to delete)
            size--; // decrement size
            if (isEmpty()) {
                first = -1;
                last = -1;
                nextFirst = 0;
                nextLast = 0;
            } else {
                if (first == items.length - 1) {
                    first = 0;
                    nextFirst = items.length - 1;
                } else if (first == last) {
                    first = -1;
                    last = -1;
                    nextFirst = 0;
                    nextLast = 0;
                } else {
                    first++;
                    if (first >= items.length) {
                        first = 0;
                    }
                    nextFirst = first - 1;
                    if (nextFirst <= -1) {
                        nextFirst = items.length - 1;
                    }
                }
            }
            if (areWeLoitering() && !isEmpty()) {
                resize(size * 4);
            }
            return x; // return the copy of the first time
        }
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            first = -1;
            last = -1;
            nextFirst = 0;
            nextLast = 0;
            return null;
        } else {
            T x = items[last]; // makes a copy of the last item
            items[last] = null; // sets last item to null (for garbage collector to delete)
            size--; // decrement size
            if (isEmpty()) {
                last = -1;
                first = -1;
                nextFirst = 0;
                nextLast = 0;
            } else {
                last--;
                if (last == -1) {
                    last = (items.length - 1);
                }
                nextLast = last + 1;
                if (nextLast >= items.length) {
                    nextLast = 0;
                }
            }
            if (areWeLoitering() && !isEmpty()) {
                resize(size * 4);
            }
            return x; // return the copy of the last time
        }
    }

    @Override
    public T get(int index) {
        if (index > size || index < 0 || size == 0) {
            return null;
        }
        int calcIndex = (index + first) % items.length;

        return items[calcIndex];
    }

    @Override
    public T getRecursive(int index) {
        return get(index);
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
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
        for (int i = 0; i < size; i += 1) {
            if (items[i].equals(x)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder returnSB = new StringBuilder("[");
        for (int i = 0; i < size - 1; i += 1) {
            returnSB.append(items[i].toString());
            returnSB.append(", ");
        }
        returnSB.append(items[size - 1]);
        returnSB.append("]");
        return returnSB.toString();
    }

}

