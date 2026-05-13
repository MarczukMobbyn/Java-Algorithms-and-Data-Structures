import java.util.*;

public class MinBinomialHeap<T extends Comparable<T>> {

    // Wewnętrzna klasa reprezentująca węzeł drzewa binomialnego
    private static class Node<T> {
        T value;           // wartość węzła
        int degree;        // stopień (liczba dzieci)
        Node<T> parent;    // rodzic
        Node<T> child;     // najstarsze dziecko
        Node<T> sibling;   // kolejne dziecko (prawo)

        Node(T value) {
            this.value = value;
            this.degree = 0;
        }
    }

    private Node<T> head; // początek listy korzeni kopca

    // Wstawia element do kopca jako osobne drzewo i wykonuje union
    public void insert(T value) {
        MinBinomialHeap<T> tempHeap = new MinBinomialHeap<>();
        tempHeap.head = new Node<>(value); // tworzy drzewo stopnia 0
        union(tempHeap); // łączy nowy kopiec z obecnym
    }

    // Zwraca najmniejszą wartość (bez usuwania)
    public T findMin() {
        if (head == null) return null;
        Node<T> minNode = head;
        Node<T> curr = head.sibling;
        // Szuka najmniejszego korzenia w liście drzew
        while (curr != null) {
            if (curr.value.compareTo(minNode.value) < 0) {
                minNode = curr;
            }
            curr = curr.sibling;
        }
        return minNode.value;
    }

    // Usuwa i zwraca najmniejszy element z kopca
    public T extractMin() {
        if (head == null) return null;

        // Znajdź korzeń z minimalną wartością
        Node<T> minNode = head, minPrev = null, prev = null, curr = head;
        while (curr != null) {
            if (curr.value.compareTo(minNode.value) < 0) {
                minNode = curr;
                minPrev = prev;
            }
            prev = curr;
            curr = curr.sibling;
        }

        // Usuń minNode z listy korzeni
        if (minPrev == null) head = minNode.sibling;
        else minPrev.sibling = minNode.sibling;

        // Odwróć dzieci minNode (bo są w kolejności od najstarszego do najmłodszego)
        Node<T> child = minNode.child;
        Node<T> reversed = null;
        while (child != null) {
            Node<T> next = child.sibling;
            child.sibling = reversed; // odwracanie listy
            child.parent = null;      // stają się nowymi korzeniami
            reversed = child;
            child = next;
        }

        // Tworzy tymczasowy kopiec z dziećmi i łączy z aktualnym
        MinBinomialHeap<T> tempHeap = new MinBinomialHeap<>();
        tempHeap.head = reversed;
        union(tempHeap);

        return minNode.value;
    }

    // Łączy dwa kopce binomialne
    public void union(MinBinomialHeap<T> other) {
        head = merge(this.head, other.head); // scala drzewa wg stopni
        if (head == null) return;

        Node<T> prev = null, curr = head, next = head.sibling;

        // Łączenie drzew o tych samych stopniach (jeśli potrzeba)
        while (next != null) {
            if ((curr.degree != next.degree) ||
                    (next.sibling != null && next.sibling.degree == curr.degree)) {
                // Nie łącz, jeśli różne stopnie lub 3 takie same z rzędu
                prev = curr;
                curr = next;
            } else if (curr.value.compareTo(next.value) <= 0) {
                // curr zostaje korzeniem (mniejszy)
                curr.sibling = next.sibling;
                link(next, curr);
            } else {
                // next zostaje korzeniem (mniejszy)
                if (prev == null) head = next;
                else prev.sibling = next;
                link(curr, next);
                curr = next;
            }
            next = curr.sibling;
        }
    }

    // Łączy dwa drzewa tego samego stopnia: child staje się dzieckiem parent
    private void link(Node<T> child, Node<T> parent) {
        child.parent = parent;
        child.sibling = parent.child; // dodajemy jako najnowsze dziecko
        parent.child = child;
        parent.degree++;
    }

    // Scala dwie listy drzew według stopni (rosnąco)
    private Node<T> merge(Node<T> h1, Node<T> h2) {
        if (h1 == null) return h2;
        if (h2 == null) return h1;

        Node<T> head, tail;

        // Ustawiamy początek nowej listy
        if (h1.degree <= h2.degree) {
            head = tail = h1;
            h1 = h1.sibling;
        } else {
            head = tail = h2;
            h2 = h2.sibling;
        }

        // Doklejamy drzewa zgodnie z kolejnością stopni
        while (h1 != null && h2 != null) {
            if (h1.degree <= h2.degree) {
                tail.sibling = h1;
                h1 = h1.sibling;
            } else {
                tail.sibling = h2;
                h2 = h2.sibling;
            }
            tail = tail.sibling;
        }

        // Dołącz pozostałe elementy z jednej z list
        tail.sibling = (h1 != null) ? h1 : h2;
        return head;
    }

    // Wypisuje strukturę kopca na ekran
    public void printHeap() {
        System.out.println("Stan kopca:");
        Node<T> curr = head;
        while (curr != null) {
            System.out.println("T" + curr.degree + "(" + curr.value + "): ");
            printTree(curr.child, 1);
            System.out.println();
            curr = curr.sibling;
        }
        System.out.println();
    }

    // Rekurencyjnie wypisuje drzewo z wcięciami
    private void printTree(Node<T> node, int depth) {
        while (node != null) {
            for (int i = 0; i < depth; i++) System.out.print("  "); // wcięcie
            System.out.println(node.value);
            printTree(node.child, depth + 1); // przechodzimy do dzieci
            node = node.sibling;
        }
    }

    // Test działania kopca
    public static void main(String[] args) {
        MinBinomialHeap<Integer> heap = new MinBinomialHeap<>();

        int[] inserts = {1, 10, 4, 15, 5, 8, 3, 2, 14, 9, 7};
        for (int x : inserts) {
            System.out.println("Wstawiam: " + x);
            heap.insert(x);
            heap.printHeap();
        }

        for (int i = 1; i <= 4; i++) {
            Integer min = heap.extractMin();
            System.out.println("Usuwam minimum (" + min + ")");
            heap.printHeap();
        }
    }
}
