import java.util.*;

public class MaxBinomialHeap<T extends Comparable<T>> {

    // Węzeł drzewa dwumianowego
    private static class Node<T> {
        T value;
        int degree;
        Node<T> parent, child, sibling;

        Node(T value) {
            this.value = value;
            this.degree = 0;
        }
    }

    private Node<T> head;

    // Konstruktor – tworzy pusty kopiec
    public MaxBinomialHeap() {
        head = null;
    }

    // Wstawia nową wartość do kopca
    public void insert(T value) {
        MaxBinomialHeap<T> tempHeap = new MaxBinomialHeap<>();
        tempHeap.head = new Node<>(value);
        this.union(tempHeap);
    }

    // Zwraca największą wartość (bez usuwania)
    public T findMax() {
        if (head == null) return null;
        Node<T> maxNode = head;
        Node<T> current = head.sibling;
        while (current != null) {
            if (current.value.compareTo(maxNode.value) > 0) {
                maxNode = current;
            }
            current = current.sibling;
        }
        return maxNode.value;
    }

    // Usuwa i zwraca największą wartość z kopca
    public T extractMax() {
        if (head == null) return null;

        // Znajdź największy korzeń
        Node<T> maxNode = head, maxPrev = null, prev = null, curr = head;
        while (curr != null) {
            if (curr.value.compareTo(maxNode.value) > 0) {
                maxNode = curr;
                maxPrev = prev;
            }
            prev = curr;
            curr = curr.sibling;
        }

        // Usuń maxNode z listy korzeni
        if (maxPrev == null) head = maxNode.sibling;
        else maxPrev.sibling = maxNode.sibling;

        // Odwróć listę dzieci maxNode i stwórz nowy kopiec
        Node<T> child = maxNode.child;
        Node<T> reversed = null;
        while (child != null) {
            Node<T> next = child.sibling;
            child.sibling = reversed;
            child.parent = null;
            reversed = child;
            child = next;
        }

        MaxBinomialHeap<T> tempHeap = new MaxBinomialHeap<>();
        tempHeap.head = reversed;
        this.union(tempHeap);

        return maxNode.value;
    }

    // Łączy obecny kopiec z innym
    public void union(MaxBinomialHeap<T> other) {
        head = merge(this.head, other.head);
        if (head == null) return;

        Node<T> prev = null, curr = head, next = head.sibling;
        while (next != null) {
            if ((curr.degree != next.degree) ||
                    (next.sibling != null && next.sibling.degree == curr.degree)) {
                prev = curr;
                curr = next;
            } else if (curr.value.compareTo(next.value) >= 0) {
                curr.sibling = next.sibling;
                link(next, curr);
            } else {
                if (prev == null) head = next;
                else prev.sibling = next;
                link(curr, next);
                curr = next;
            }
            next = curr.sibling;
        }
    }

    // Łączy dwa drzewa o tym samym stopniu: child staje się dzieckiem parent
    private void link(Node<T> child, Node<T> parent) {
        child.parent = parent;
        child.sibling = parent.child;
        parent.child = child;
        parent.degree++;
    }

    // Scala dwa lasy drzew według rosnących stopni
    private Node<T> merge(Node<T> h1, Node<T> h2) {
        if (h1 == null) return h2;
        if (h2 == null) return h1;
        Node<T> head, tail;
        if (h1.degree <= h2.degree) {
            head = tail = h1;
            h1 = h1.sibling;
        } else {
            head = tail = h2;
            h2 = h2.sibling;
        }
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
        tail.sibling = (h1 != null) ? h1 : h2;
        return head;
    }

    public List<T> levels() {
        List<T> result = new ArrayList<>();
        Queue<Node<T>> queue = new LinkedList<>();

        // Dodaj wszystkie korzenie drzew binomialnych do kolejki
        Node<T> curr = head;
        while (curr != null) {
            queue.offer(curr);
            curr = curr.sibling;
        }

        // BFS – przetwarzamy węzły poziomami
        while (!queue.isEmpty()) {
            Node<T> node = queue.poll();
            result.add(node.value);

            // Dodaj dzieci do kolejki
            Node<T> child = node.child;
            while (child != null) {
                queue.offer(child);
                child = child.sibling;
            }
        }

        return result;
    }


    // Wypisuje strukturę kopca na ekran
    public void printHeap() {
        System.out.println("Stan kopca:");
        MaxBinomialHeap.Node<T> curr = head;
        while (curr != null) {
            System.out.println("T" + curr.degree + "(" + curr.value + "): ");
            printTree(curr.child, 1);
            System.out.println();
            curr = curr.sibling;
        }
        System.out.println();
    }

    // Rekurencyjnie wypisuje drzewo z wcięciami
    private void printTree(MaxBinomialHeap.Node<T> node, int depth) {
        while (node != null) {
            for (int i = 0; i < depth; i++) System.out.print("  "); // wcięcie
            System.out.println(node.value);
            printTree(node.child, depth + 1); // przechodzimy do dzieci
            node = node.sibling;
        }
    }

    public boolean isMaxHeap() {
        Node<T> curr = head;
        while (curr != null) {
            if (!isMaxHeapTree(curr)) {
                return false;
            }
            curr = curr.sibling;
        }
        return true;
    }

    private boolean isMaxHeapTree(Node<T> node) {
        Node<T> child = node.child;
        while (child != null) {
            if (node.value.compareTo(child.value) < 0) {
                return false;
            }
            if (!isMaxHeapTree(child)) {
                return false;
            }
            child = child.sibling;
        }
        return true;
    }

}
