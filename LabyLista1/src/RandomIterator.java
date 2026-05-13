import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.TreeSet;

public class RandomIterator<T> implements Iterator<T> {
    private int currentIndex;
    private T[] array;
    private TreeSet<Integer> usedIndices;
    private Random rand;

    public RandomIterator(T[] array) {
        this.array = array;
        this.currentIndex = 0;
        this.usedIndices = new TreeSet<>();
        this.rand = new Random();
    }

    @Override
    public boolean hasNext() {
        return currentIndex < array.length;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        int randomIndex = rand.nextInt(array.length);

        // Losuj tak długo, aż trafisz na indeks, który nie był jeszcze użyty
        while (usedIndices.contains(randomIndex)) {
            randomIndex = rand.nextInt(array.length);
        }

        usedIndices.add(randomIndex);
        currentIndex++; // Inkremacja licznika, aby hasNext() poprawnie zakończyło pętlę
        return array[randomIndex];
    }
}