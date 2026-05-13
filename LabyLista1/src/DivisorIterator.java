import java.util.Iterator;
import java.util.TreeSet;

public class DivisorIterator implements Iterator<Integer>, Iterable<Integer> {
    private TreeSet<Integer> dividers;
    private Iterator<Integer> iterator;

    public DivisorIterator(int liczba) {
        this.dividers = new TreeSet<>();
        int sqrtN = (int) Math.sqrt(liczba);
        for (int i = 1; i <= sqrtN; i++) {
            if (liczba % i == 0) {
                dividers.add(i);
                dividers.add(liczba / i);
            }
        }
        this.iterator = dividers.iterator();
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public Integer next() {
        return iterator.next();
    }

    public Iterator<Integer> iterator() {
        return this;
    }
}
