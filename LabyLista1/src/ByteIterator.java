import java.util.Iterator;
import java.util.NoSuchElementException;

public class ByteIterator implements Iterator<Integer>, Iterable<Integer> {
    private Iterator<Integer> baseIterator;
    private byte[] currentBytes;
    private int byteIndex;
    private int firstByteIndexDifferentThanZero;

    public ByteIterator(Iterator<Integer> baseIterator) {
        this.baseIterator = baseIterator;
        this.currentBytes = new byte[0];
        this.byteIndex = 0;
        firstByteIndexDifferentThanZero = 0;
    }

    @Override
    public boolean hasNext() {
        while (byteIndex >= currentBytes.length && baseIterator.hasNext()) {
            int nextInt = baseIterator.next();
            currentBytes = new byte[] {
                    (byte) ((nextInt >> 24) & 0xFF),
                    (byte) ((nextInt >> 16) & 0xFF),
                    (byte) ((nextInt >> 8) & 0xFF),
                    (byte) (nextInt & 0xFF),
            };

            for (int i = 0; i < 4; i++) {
                if (currentBytes[i] != 0) {
                    firstByteIndexDifferentThanZero = i;
                    break;
                }
            }

            byteIndex = 0;
        }
        return byteIndex < currentBytes.length || baseIterator.hasNext();
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (byteIndex < firstByteIndexDifferentThanZero) {
            byteIndex = firstByteIndexDifferentThanZero;
        }
        return currentBytes[byteIndex++] & 0xFF;
    }

    @Override
    public Iterator<Integer> iterator() {
        return this;
    }

}