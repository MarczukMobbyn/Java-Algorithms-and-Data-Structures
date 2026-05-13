import java.util.ArrayList;
import java.util.List;

public class TwoWayLinkedList<E> implements IList<E> {

    public class Node {
        private E data;
        private Node next;
        private Node prev;

        Node(E data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    public Node head;
    public Node tail;
    private int size;

    public TwoWayLinkedList() {
        head = new Node(null, null, null); // sentinel head
        tail = new Node(null, null, head); // sentinel tail
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    @Override
    public boolean add(E e) {
        add(size, e);
        return true;
    }

    @Override
    public void add(int index, E element) {
        checkPositionIndex(index);

        Node prevNode;
        Node nextNode;

        if (index == 0) {
            nextNode = head.next != tail ? head.next : tail;
            prevNode = head;
        } else if (index == size) {
            prevNode = tail.prev;
            nextNode = tail;
        } else {
            nextNode = getNode(index);
            prevNode = nextNode.prev;
        }

        Node newNode = new Node(element, null, prevNode);
        prevNode.next = newNode;
        newNode.prev = prevNode;
        newNode.next = nextNode;
        nextNode.prev = newNode;

        size++;

        fixNextPointers();
    }

    @Override
    public void clear() {
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public E get(int index) {
        checkElementIndex(index);
        return getNode(index).data;
    }

    @Override
    public E set(int index, E element) {
        checkElementIndex(index);
        Node node = getNode(index);
        E oldData = node.data;
        node.data = element;
        return oldData;
    }

    @Override
    public int indexOf(E element) {
        Node current = tail.prev;
        int index = size - 1;
        while (current != head) {
            if ((element == null && current.data == null) || (element != null && element.equals(current.data))) {
                return index;
            }
            current = current.prev;
            index--;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E remove(int index) {
        checkElementIndex(index);
        Node target = getNode(index);
        Node prevNode = target.prev;
        Node nextNode;

        if (index == size - 1) {
            nextNode = tail;
        } else {
            nextNode = getNode(index + 1);
        }

        prevNode.next = nextNode;
        nextNode.prev = prevNode;

        size--;

        fixNextPointers();

        return target.data;
    }


    @Override
    public boolean remove(E element) {
        Node current = tail.prev;
        int index = size - 1;
        while (current != head) {
            if ((element == null && current.data == null) || (element != null && element.equals(current.data))) {
                remove(index);
                return true;
            }
            current = current.prev;
            index--;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    private Node getNode(int index) {
        Node current = tail.prev;
        int pos = size - 1;
        while (pos > index) {
            current = current.prev;
            pos--;
        }
        return current;
    }

    private void fixNextPointers() {
        if (size == 0) {
            head.next = tail;
            return;
        }

        Node current = tail.prev;
        Node next1 = tail; // Zawsze wskazuje na element i+1
        Node next2 = tail; // Zawsze wskazuje na element i+2

        // Idziemy od końca do początku używając poprawnych wskaźników prev
        while (current != head) {
            current.next = next2; // Przeskok o 2 pozycje do przodu

            // Przesuwamy "okno" podglądu do tyłu
            next2 = next1;
            next1 = current;
            current = current.prev;
        }

        // head musi wskazywać na pierwszy element sekwencyjny
        head.next = next1;
    }



    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    @Override
    public java.util.Iterator<E> iterator() {
        throw new UnsupportedOperationException("Iterator not supported");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lista: ");

        Node current = tail.prev;
        while (current != head) {
            sb.insert(7, current.data + " ");
            current = current.prev;
        }

        return sb.toString();
    }

    public void testNextSkipping() {
        Node current = head.next;
        while (current != tail && current.next != tail) {
            System.out.println("Current element: " + current.data);
            System.out.println("Next element (should be two positions ahead): " + current.next.data);
            current = current.next;
        }
    }
}
