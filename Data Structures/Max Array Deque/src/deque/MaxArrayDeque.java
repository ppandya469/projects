package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private int maxIndex = 0;
    private Comparator<T> myComparator;

    public MaxArrayDeque(Comparator<T> c) { //constructor
        myComparator = c;
    }

    public static void main(String[] args) {
    }

    public T max() {
        return max(myComparator);
    }

    public T max(Comparator<T> c) {
        // check if is empty, return null
        if (toList().isEmpty()) {
            return null;
        }

        // otherwise return maxIndex
        Comparator<T> currentComparator = c;
        maxIndex = 0;
        for (int i = 0; i < size(); i += 1) {
            int cmp = currentComparator.compare(get(i), get(maxIndex));

            if (cmp > 0) {
                maxIndex = i;
            }
        }
        return get(maxIndex);
    }
}
