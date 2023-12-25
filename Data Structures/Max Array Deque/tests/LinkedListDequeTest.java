import deque.Deque;
import deque.ArrayDeque;
import deque.LinkedListDeque;

import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDequeTest {
    /*@Test
    @DisplayName("LinkedListDeque has no fields besides nodes and primitives")
    void noNonTrivialFields() {
        Class<?> nodeClass = NodeChecker.getNodeClass(LinkedListDeque.class, true);
        List<Field> badFields = Reflection.getFields(LinkedListDeque.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(nodeClass) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not nodes or primitives").that(badFields).isEmpty();
    }*/

    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

    }

    @Test
    /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
     * *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
    public void addLastTestBasic() {
        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque<Integer> lld1 = new LinkedListDeque<>();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]
        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();

    }

    //Below, you'll write your own tests for LinkedListDeque.
    @Test
    public void testIsEmpty() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        assertThat(lld1.isEmpty()).isTrue();
        lld1.addLast(5);
        lld1.addFirst(10);
        lld1.addFirst(15);
        assertThat(lld1.isEmpty()).isFalse();
    }

    @Test
    public void testSize() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        assertThat(lld1.size()).isEqualTo(0);
        lld1.addLast(5);
        lld1.addFirst(10);
        lld1.addFirst(15);
        assertThat(lld1.size()).isEqualTo(3);
    }

    @Test
    public void testGet() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        assertThat(lld1.get(10)).isNull();
        assertThat(lld1.get(-1)).isNull();
        assertThat(lld1.get(1)).isNull();
        lld1.addFirst(5);
        lld1.addLast(10);
        lld1.addLast(15);
        assertThat(lld1.get(2)).isEqualTo(15);
    }

    @Test
    public void testGetRecursive() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        assertThat(lld1.getRecursive(10)).isNull();
        assertThat(lld1.getRecursive(-1)).isNull();
        assertThat(lld1.getRecursive(1)).isNull();
        lld1.addFirst(5);
        lld1.addLast(10);
        lld1.addLast(15);
        assertThat(lld1.getRecursive(2)).isEqualTo(15);

    }

    @Test
    public void testRemoveFirstAndRemoveLast() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        assertThat(lld1.removeFirst()).isNull();
        assertThat(lld1.removeLast()).isNull();
        lld1.addFirst(5);
        lld1.addLast(10);
        lld1.removeLast();
        lld1.removeLast();
        assertThat(lld1.toList()).isEmpty();

        lld1.addLast(10);
        lld1.addLast(12);
        lld1.addLast(15);
        lld1.removeFirst();
        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly(15).inOrder();

        lld1.removeFirst();
        assertThat(lld1.toList()).isEmpty();

        lld1.removeFirst();
        assertThat(lld1.toList()).isEmpty();
    }

    @Test
    public void testEqualDeques() {
        Deque<String> lld1 = new LinkedListDeque<>();
        Deque<String> lld2 = new ArrayDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }

}