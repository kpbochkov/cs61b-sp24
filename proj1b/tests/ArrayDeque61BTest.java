import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    public void addFirstTestBasic() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        lld1.addFirst("middle1"); // after this call we expect: ["front", "middle", "back"]
        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle1", "middle", "back").inOrder();
    }

    @Test
    public void addLastTest() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addLast("first"); // after this call we expect: ["first"]
        lld1.addLast("second"); // after this call we expect: ["first", "second"]
        lld1.addLast("third"); // after this call we expect: ["first", "second", "third"]
        lld1.addLast("last"); // after this call we expect: ["first", "second", "third", "last"]
        assertThat(lld1.toList()).containsExactly("first", "second", "third", "last").inOrder();
    }

    @Test
    public void addLastAndAddFirstTest() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addLast("0");   // [0]
        lld1.addLast("1");   // [0, 1]
        lld1.addFirst("-1"); // [-1, 0, 1]
        lld1.addLast("2");   // [-1, 0, 1, 2]
        lld1.addFirst("-2"); // [-2, -1, 0, 1, 2]
        assertThat(lld1.toList()).containsExactly("-2", "-1", "0", "1", "2").inOrder();
    }

    @Test
    public void addLastAndAddFirstNeedResizingTest() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addLast("0");   // [0]
        lld1.addLast("1");   // [0, 1]
        lld1.addFirst("-1"); // [-1, 0, 1]
        lld1.addLast("2");   // [-1, 0, 1, 2]
        lld1.addFirst("-2"); // [-2, -1, 0, 1, 2]
        lld1.addLast("3");   // [-2, -1, 0, 1, 2, 3]
        lld1.addFirst("-3"); // [-3, -2, -1, 0, 1, 2, 3]
        lld1.addFirst("-4"); // [-4, -3, -2, -1, 0, 1, 2, 3]
        lld1.addLast("5"); // [-4, -3, -2, -1, 0, 1, 2, 3, 5]
        lld1.addFirst("-5"); // [-5, -4, -3, -2, -1, 0, 1, 2, 3, 5]
        assertThat(lld1.toList()).containsExactly("-5", "-4", "-3", "-2", "-1", "0", "1", "2", "3", "5").inOrder();
    }

    @Test
    public void getShouldReturnValidValue() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addLast("0");   // [0]
        lld1.addLast("1");   // [0, 1]
        lld1.addFirst("-1"); // [-1, 0, 1]
        lld1.addLast("2");   // [-1, 0, 1, 2]
        lld1.addFirst("-2"); // [-2, -1, 0, 1, 2]
        lld1.addLast("3");   // [-2, -1, 0, 1, 2, 3]
        lld1.addFirst("-3"); // [-3, -2, -1, 0, 1, 2, 3]
        lld1.addFirst("-4"); // [-4, -3, -2, -1, 0, 1, 2, 3]
        lld1.addLast("5"); // [-4, -3, -2, -1, 0, 1, 2, 3, 5]
        lld1.addFirst("-5"); // [-5, -4, -3, -2, -1, 0, 1, 2, 3, 5]
        String value = lld1.get(8);
        String value2 = lld1.get(5);
        assertThat(value).isEqualTo("3");
        assertThat(value2).isEqualTo("0");
    }

    @Test
    public void getShouldReturnNullForInvalidInput() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addLast("0");   // [0]
        lld1.addLast("1");   // [0, 1]

        String value3 = lld1.get(1123);
        assertThat(value3).isEqualTo(null);
    }

    @Test
    public void testSizeAndIsEmpty() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        assertThat(lld1.isEmpty()).isTrue();

        lld1.addFirst(1);
        lld1.addLast(2);
        int size = lld1.size();

        assertThat(lld1.isEmpty()).isFalse();
        assertThat(size).isEqualTo(2);
    }

    @Test
    public void removeFirstTest() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addLast("0");   // [0]
        lld1.addLast("1");   // [0, 1]
        lld1.addFirst("-1"); // [-1, 0, 1]
        lld1.addLast("2");   // [-1, 0, 1, 2]
        lld1.addFirst("-2"); // [-2, -1, 0, 1, 2]
        lld1.addLast("3");   // [-2, -1, 0, 1, 2, 3]
        lld1.addFirst("-3"); // [-3, -2, -1, 0, 1, 2, 3]
        lld1.addFirst("-4"); // [-4, -3, -2, -1, 0, 1, 2, 3]
        lld1.addLast("5"); // [-4, -3, -2, -1, 0, 1, 2, 3, 5]
        lld1.addFirst("-5"); // [-5, -4, -3, -2, -1, 0, 1, 2, 3, 5]

        final String removedItem = lld1.removeFirst();
        assertThat(removedItem).isEqualTo("-5");
        assertThat(lld1.toList()).containsExactly("-4", "-3", "-2", "-1", "0", "1", "2", "3", "5").inOrder();

        int size = lld1.size();
        assertThat(size).isEqualTo(9);
    }

    @Test
    public void removeFirstWhenResizingTest() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addLast("0");   // [0]
        lld1.addLast("1");   // [0, 1]
        lld1.addFirst("-1"); // [-1, 0, 1]
        lld1.addLast("2");   // [-1, 0, 1, 2]
        lld1.addFirst("-2"); // [-2, -1, 0, 1, 2]
        lld1.addLast("3");   // [-2, -1, 0, 1, 2, 3]
        lld1.addFirst("-3"); // [-3, -2, -1, 0, 1, 2, 3]
        lld1.addFirst("-4"); // [-4, -3, -2, -1, 0, 1, 2, 3]
        lld1.addLast("5"); // [-4, -3, -2, -1, 0, 1, 2, 3, 5]
        lld1.addFirst("-5"); // [-5, -4, -3, -2, -1, 0, 1, 2, 3, 5]

        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeFirst();
        assertThat(lld1.toList()).isEmpty();

        int size = lld1.size();
        assertThat(size).isEqualTo(0);
    }

    @Test
    public void removeFirstWhenOneItemTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addLast(0);   // [0]

        final Integer removedItem = lld1.removeLast();
        assertThat(removedItem).isEqualTo(0);
        assertThat(lld1.toList()).isEmpty();

        int size = lld1.size();
        assertThat(size).isEqualTo(0);
    }

    @Test
    public void removeFirstInEmptyListTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addFirst(-1);
        final Integer removedItem = lld1.removeFirst();
        Integer removedItem2 = lld1.removeFirst();

        assertThat(removedItem).isEqualTo(-1);
        assertThat(removedItem2).isEqualTo(null);
    }

    @Test
    public void removeLastTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

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
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addFirst(-1);
        final Integer removedItem = lld1.removeLast();
        Integer removedItem2 = lld1.removeLast();
        assertThat(removedItem).isEqualTo(-1);
        assertThat(removedItem2).isEqualTo(null);
    }
}
