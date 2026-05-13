public class Main {
    public static void main(String[] args) {
        MaxBinomialHeap<Integer> heap = new MaxBinomialHeap<>();

        System.out.println("Wstawiam: 5, 3, 17, 10, 84, 19, 6, 22, 9");
        heap.insert(5);
        heap.insert(3);
        heap.insert(17);
        heap.insert(10);
        heap.insert(84);
        heap.insert(19);
        heap.insert(6);
        heap.insert(22);
        heap.insert(9);

        System.out.println("Aktualny kopiec (wartości w drzewach):");
        heap.printHeap();

        System.out.println("\nCzy kopiec jest kopcem maksymalnym? " + heap.isMaxHeap());

        System.out.println("\nMaksymalny element: " + heap.findMax());

        System.out.println("\nWypisuję wszystkie elementy kopca poziomami:");
        System.out.println(heap.levels());
        /*System.out.println("\nUsuwam maksymalny element: " + heap.extractMax());
        System.out.println("Kopiec po usunięciu maksimum:");
        heap.printHeap();

        System.out.println("\nWstawiam 99 do kopca:");
        heap.insert(99);
        heap.printHeap();

        System.out.println("\nMaksymalny element: " + heap.findMax());

         */
    }
}
