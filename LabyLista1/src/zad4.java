public class zad4 {
    public static void main(String[] args) {
        Integer[] tablica = {1, 2, 3, 4, 5};
        RandomIterator<Integer> randomIt = new RandomIterator<>(tablica);

        while (randomIt.hasNext()) {
            System.out.print(randomIt.next() + " ");
        }
    }
}
