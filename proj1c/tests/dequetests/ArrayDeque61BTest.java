package dequetests;

import deque.ArrayDeque61B;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {

    @Test
    public void testEqualsAndToString() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        ArrayDeque61B<Integer> deque2 = new ArrayDeque61B<>();

        deque.addFirst(1);
        deque.addLast(2);
        deque.addLast(3);

        deque2.addFirst(1);
        deque2.addLast(2);
        deque2.addLast(3);

        assertThat(deque).isEqualTo(deque2);
        assertThat(deque.toString()).isEqualTo(deque2.toString());
    }
}
