import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/**
 * Performs some basic linked list tests.
 */
public class LinkedListDeque61BTest {

    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
    }

    @Test
    /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
     *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
    public void addLastTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]
    }

    // Below, you'll write your own tests for LinkedListDeque61B.

    @Test
    public void testSizeAndIsEmpty() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        assertThat(lld1.isEmpty()).isTrue();

        lld1.addFirst(1);
        lld1.addLast(2);
        int size = lld1.size();

        assertThat(lld1.isEmpty()).isFalse();
        assertThat(size).isEqualTo(2);
    }

    @Test
    public void getShouldReturnValidValue() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst(1);
        lld1.addLast(2);
        final Integer value = lld1.get(0);
        Integer value2 = lld1.get(1);
        assertThat(value).isEqualTo(1);
        assertThat(value2).isEqualTo(2);
    }

    @Test
    public void getRecursiveShouldReturnValidValue() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst(1);
        lld1.addLast(2);
        final Integer value = lld1.getRecursive(0);
        Integer value2 = lld1.getRecursive(1);
        assertThat(value).isEqualTo(1);
        assertThat(value2).isEqualTo(2);
    }

    @Test
    public void getRecursiveShouldReturnNullOnInvalidInput() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst(1);
        lld1.addLast(2);
        final Integer value = lld1.getRecursive(-3);
        assertThat(value).isEqualTo(null);
    }

    @Test
    public void getShouldReturnNullOnInvalidInput() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst(1);
        lld1.addLast(2);
        final Integer value = lld1.get(100);
        assertThat(value).isEqualTo(null);
    }

    @Test
    public void removeFirstTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        final Integer removedItem = lld1.removeFirst();
        assertThat(removedItem).isEqualTo(-2);
        assertThat(lld1.toList()).containsExactly(-1, 0, 1, 2).inOrder();

        int size = lld1.size();
        assertThat(size).isEqualTo(4);
    }

    @Test
    public void removeFirstWhenOneItemTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(0);   // [0]

        final Integer removedItem = lld1.removeFirst();
        assertThat(removedItem).isEqualTo(0);
        assertThat(lld1.toList()).isEmpty();

        int size = lld1.size();
        assertThat(size).isEqualTo(0);
    }

    @Test
    public void removeFirstInEmptyListTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst(-1);
        final Integer removedItem = lld1.removeFirst();
        Integer removedItem2 = lld1.removeFirst();

        assertThat(removedItem).isEqualTo(-1);
        assertThat(removedItem2).isEqualTo(null);
    }

    @Test
    public void removeLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        final Integer removedItem = lld1.removeLast();
        assertThat(removedItem).isEqualTo(2);
        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1).inOrder();

        lld1.removeLast();
        int size = lld1.size();
        assertThat(size).isEqualTo(3);
    }

    @Test
    public void removeLastInEmptyListTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst(-1);
        final Integer removedItem = lld1.removeLast();
        Integer removedItem2 = lld1.removeLast();
        assertThat(removedItem).isEqualTo(-1);
        assertThat(removedItem2).isEqualTo(null);
    }
}