import java.util.Iterator;

public class zad3 {
    public static void main(String[] args) {
        Integer[] tab = {1, 2, 3, 4, 5};
        SubsequenceIterator<Integer> subIt = new SubsequenceIterator<>(tab);
        while (subIt.hasNext()) {
            Iterator<Integer> it = subIt.next();
            while (it.hasNext()) {
                System.out.print(it.next() + " ");
            }
            System.out.println();
        }
    }
}
