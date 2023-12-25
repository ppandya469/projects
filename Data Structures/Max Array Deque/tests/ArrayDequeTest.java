import deque.ArrayDeque;
import deque.Deque;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {

    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque<String> ad1 = new ArrayDeque<>();

        ad1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(ad1.toList()).containsExactly("back").inOrder();

        ad1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(ad1.toList()).containsExactly("middle", "back").inOrder();

        ad1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(ad1.toList()).containsExactly("front", "middle", "back").inOrder();

    }

    @Test
    public void addLastTestBasic() {
        Deque<String> ad1 = new ArrayDeque<>();

        ad1.addLast("front"); // after this call we expect: ["front"]
        ad1.addLast("middle"); // after this call we expect: ["front", "middle"]
        ad1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(ad1.toList()).containsExactly("front", "middle", "back").inOrder();
        ad1.addLast("back");
        ad1.addLast("back");
        ad1.addLast("back");
        ad1.addLast("back");
        ad1.addLast("back");
        ad1.addLast("back");
        assertThat(ad1.size()).isEqualTo(9);

    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque<Integer> ad1 = new ArrayDeque<>();

        ad1.addLast(0);   // [0]
        ad1.addLast(1);   // [0, 1]
        ad1.addFirst(-1); // [-1, 0, 1]
        ad1.addLast(2);   // [-1, 0, 1, 2]
        ad1.addFirst(-2); // [-2, -1, 0, 1, 2]
        assertThat(ad1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();

    }

    @Test
    public void testIsEmpty() {
        Deque<Integer> ad1 = new ArrayDeque<>();
        assertThat(ad1.isEmpty()).isTrue();
        ad1.addLast(5);
        ad1.addFirst(10);
        ad1.addFirst(15);
        assertThat(ad1.isEmpty()).isFalse();
    }

    @Test
    public void testSize() {
        Deque<Integer> ad1 = new ArrayDeque<>();
        assertThat(ad1.size()).isEqualTo(0);
        ad1.addLast(5);
        ad1.addFirst(10);
        ad1.addFirst(15);
        assertThat(ad1.size()).isEqualTo(3);
        ad1.removeFirst();
        ad1.removeLast();
        ad1.removeLast();
        assertThat(ad1.size()).isEqualTo(0);
    }

    @Test
    public void testGet() {
        Deque<Integer> ad1 = new ArrayDeque<>();
        assertThat(ad1.get(10)).isNull();
        assertThat(ad1.get(-1)).isNull();
        assertThat(ad1.get(1)).isNull();
        ad1.addFirst(5);
        ad1.addLast(10);
        ad1.addLast(15);
        assertThat(ad1.get(2)).isEqualTo(15);
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();

        ad1.addLast(0);
        ad1.get(0);
        ad1.removeFirst();
        ad1.addFirst(3);
        ad1.addLast(4);
        ad1.removeLast();
        ad1.addFirst(6);
        ad1.removeLast();
        assertThat(ad1.get(0)).isEqualTo(6);


        ad1.addFirst(0);
        ad1.removeFirst(); //   ==> 0
        ad1.addLast(2);
        assertThat(ad1.get(0)).isEqualTo(6);
        assertThat(ad1.get(0)).isEqualTo(6);
        ad1.addFirst(5);
        ad1.addLast(6);
        ad1.removeFirst(); //    ==> 5
        ad1.addFirst(8);
        assertThat(ad1.get(2)).isEqualTo(2);
    }

    @Test
    public void testRemoveFirstAndRemoveLast() {
        Deque<Integer> ad1 = new ArrayDeque<>();
        assertThat(ad1.removeFirst()).isNull();
        assertThat(ad1.removeLast()).isNull();
        ad1.addFirst(5);
        ad1.addLast(10);
        ad1.removeLast();
        ad1.removeLast();
        assertThat(ad1.toList()).isEmpty();

        ad1.addLast(10);
        ad1.addLast(12);
        ad1.addLast(15);
        ad1.removeFirst();
        ad1.removeFirst();
        assertThat(ad1.toList()).containsExactly(15).inOrder();

        ad1.removeFirst();
        assertThat(ad1.toList()).isEmpty();

        ad1.addFirst(11);
        ad1.removeFirst();
        assertThat(ad1.toList()).isEmpty();

        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();

        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        assertThat(ad1.toList()).isEmpty();

        ad1.addLast(5);
        ad1.addLast(5);
        ad1.addLast(5);
        ad1.addLast(5);
        ad1.addLast(5);
        ad1.addLast(5);
        ad1.addLast(5);
        ad1.addLast(5);
        ad1.addLast(5);
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        assertThat(ad1.toList()).isEmpty();

        ad1.addLast(0);
        ad1.addLast(1);
        ad1.removeFirst(); //    ==> 0
        ad1.isEmpty();
        ad1.addLast(4);
        ad1.addLast(5);
        ad1.addLast(6);
        ad1.addLast(7);
        ad1.addLast(8);
        ad1.addLast(9);
        ad1.addLast(10);
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();

        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);

        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();

        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
    }

    @Test
    public void testRandom() {
        Deque<Integer> ad1 = new ArrayDeque<>();
        assertThat(ad1.isEmpty()).isTrue();
        ad1.addLast(5);
        ad1.addFirst(10);
        ad1.addFirst(15);
        assertThat(ad1.isEmpty()).isFalse();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        assertThat(ad1.size()).isEqualTo(0);
        ad1.addLast(5);
        ad1.addFirst(10);
        ad1.addFirst(15);
        assertThat(ad1.size()).isEqualTo(3);
        ad1.removeFirst();
        ad1.removeLast();
        ad1.removeLast();
        assertThat(ad1.size()).isEqualTo(0);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.addFirst(5);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.addLast(10);
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.addFirst(5);
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.addLast(7);
        ad1.addLast(8);
        ad1.addLast(9);
        ad1.addLast(10);
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        assertThat(ad1.isEmpty()).isTrue();
    }

    @Test
    public void testEqualDeque() {
        Deque<String> lld1 = new ArrayDeque<>();
        Deque<String> lld2 = new ArrayDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }

    @Test
    public void testIterator(){
        Deque<String> lld1 = new ArrayDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

    }


}
