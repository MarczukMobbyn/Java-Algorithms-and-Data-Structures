import java.util.Iterator;
import java.util.NoSuchElementException;

public class SubsequenceIterator<T> implements Iterator<Iterator<T>> {
    T[] array;
    int length = 1;
    int from = 0;

    public SubsequenceIterator(T[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array cannot be null");
        }
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return from < array.length;
    }

    @Override
    public Iterator<T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        Iterator<T> subIterator = new Iterator<>() {
            private int index = from;
            private final int end = from + length;

            @Override
            public boolean hasNext() {
                return index < end;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return array[index++];
            }
        };

        length++;
        if (from + length > array.length) {
            from++;
            length = 1;
        }
        return subIterator;
    }
}