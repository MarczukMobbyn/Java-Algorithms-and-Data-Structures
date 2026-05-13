import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        Comparator<Integer> comparator = Comparator.naturalOrder();

        BST<Integer> bst = new BST<>(comparator);
        /*bst.insert(5);
        bst.insert(-2);
        bst.insert(7);
        bst.insert(2);
        bst.insert(4);
        bst.insert(6);
        bst.insert(8);
        bst.insert(0);
        bst.insert(1);

         */

        bst.insert(0);
        bst.insert(10);
        bst.insert(8);
        bst.insert(9);
        bst.insert(-5);
        bst.insert(-3);
        bst.insert(-2);
        bst.insert(-4);
        bst.insert(-9);
        bst.insert(-8);
        bst.insert(-10);


        bst.display();
        System.out.println(bst.search(10));
        System.out.println("min: " + bst.findMin());
        System.out.println("max: " + bst.findMax());

        bst.mostImbalancedSubtree().display();

    }
}