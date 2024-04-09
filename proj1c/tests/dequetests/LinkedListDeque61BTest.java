package dequetests;

import deque.LinkedListDeque61B;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class LinkedListDeque61BTest {

    @Test
    public void testEqualsAndToString() {
        LinkedListDeque61B<Integer> lldeque = new LinkedListDeque61B<>();
        LinkedListDeque61B<Integer> lldeque2 = new LinkedListDeque61B<>();

        lldeque.addFirst(1);
        lldeque.addLast(2);
        lldeque.addLast(3);

        lldeque2.addFirst(1);
        lldeque2.addLast(2);
        lldeque2.addLast(3);

        assertThat(lldeque).isEqualTo(lldeque2);
        assertThat(lldeque.toString()).isEqualTo(lldeque2.toString());
    }
}
