import java.util.Iterator;

public class zad2 {
    public static void main(String[] args) {
        Iterator<Integer> baseIterator = new Iterator<>() {
            private int value = 1;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                int current = value;
                value *= 2;
                return current;
            }
        };

        ByteIterator byteIt = new ByteIterator(baseIterator);

        int count = 40;
        for (int b : byteIt) {
            byteIt.hasNext();
            System.out.print(b + " ");
            if (--count == 0) break;
        }
        System.out.println();
    }
}
