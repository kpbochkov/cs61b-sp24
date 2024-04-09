package dequetests;

import deque.MaxArrayDeque61B;
import org.junit.jupiter.api.*;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;


public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("1");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }

    @Test
    public void addAndIsEmptyTest() {
        Comparator<String> cmp = Comparator.reverseOrder();

        MaxArrayDeque61B<String> deque = new MaxArrayDeque61B<>(cmp);

        assertWithMessage("A new maxArrayDeque should be empty").that(deque).isEmpty();
        deque.addFirst("front");

        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.isEmpty()).isFalse();

        deque.addLast("middle");
        assertThat(deque.size()).isEqualTo(2);

        deque.addLast("back");
        assertThat(deque.size()).isEqualTo(3);
    }

    @Test
    public void removeItemTest() {

        Comparator<Integer> cmp = Comparator.reverseOrder();

        MaxArrayDeque61B<Integer> deque = new MaxArrayDeque61B<>(cmp);
        assertThat(deque).isEmpty();

        deque.addFirst(1);
        assertThat(deque.isEmpty()).isFalse();

        deque.removeFirst();
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void emptyListShouldReturnNull() {
        Comparator<Integer> cmp = Comparator.reverseOrder();

        MaxArrayDeque61B<Integer> deque = new MaxArrayDeque61B<>(cmp);

        assertThat(deque.removeFirst()).isEqualTo(null);
    }
}
