import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int heapHeight = 3; // możesz zmienić wedle potrzeb
        int elementsCount = 20;

        Comparator<Integer> comparator = Comparator.naturalOrder();

        // Tworzymy kopiec na liczbach (porządek rosnący)
        ArrayTreeBinaryHeap<Integer> heap = new ArrayTreeBinaryHeap<>(heapHeight, comparator);

        heap.add(5);
        heap.add(3);
        heap.add(8);
        heap.add(1);
        heap.add(7);
        heap.add(2);
        heap.add(6);
        heap.add(10);
        heap.add(20);
        heap.add(15);
        heap.add(12);
        heap.add(4);
        heap.add(9);
        heap.add(11);
        heap.add(14);
        heap.add(13);
        heap.add(16);
        heap.add(18);
        heap.add(17);
        heap.add(0);
        heap.add(19);
        heap.add(-5);
        heap.add(-3);
        heap.add(-8);
        heap.add(-1);
        heap.add(-7);


        //List<Integer> list = ((SubHeapAnchor<Integer>) heap.array[4]).getLevelElements(1);
        //System.out.println("Elements at level 1 of SubHeapAnchor at index 3: " + list);
        heap.displayStructure();


        heap.minimum();
        heap.displayStructure();

        //heap.minimum();
        //heap.displayStructure();


    }
}
