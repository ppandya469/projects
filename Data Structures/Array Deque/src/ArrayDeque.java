import java.util.List;
import java.util.ArrayList;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size; // number of items in array
    private int first;
    private int last;
    private int nextFirst;
    private int nextLast;

    public static void main(String[] args) {
        Deque<Integer> ad = new ArrayDeque<>();
    }

    public ArrayDeque() { //constructor
        items = (T[]) new Object[8];
        size = 0;
        first = -1; // -1 means the list is empty
        last = -1; // -1 means the list is empty
        nextFirst = items.length - 1;
        nextLast = last + 1;
    }

    /* Resizes the current array to capacity. */
    private void resize(int capacity) {
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
}
