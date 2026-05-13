import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class OneWaySquareList<E> implements IList<E> {

    private static class Node<E> {
        E value;
        Node<E> next;

        Node(E value) {
            this.value = value;
        }
    }

    private static class Row<E> {
        Node<E> head;
        Node<E> tail;
        int size = 0;

        Row<E> nextRow;

        void addLast(E element) {
            Node<E> newNode = new Node<>(element);
            if (head == null) {
                head = tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
            size++;
        }

        void addAt(int index, E element) {
            if (index == size) {
                addLast(element);
                return;
            }
            Node<E> newNode = new Node<>(element);
            if (index == 0) {
                newNode.next = head;
                head = newNode;
                if (size == 0) tail = newNode;
            } else {
                Node<E> prev = head;
                for (int i = 0; i < index - 1; i++) prev = prev.next;
                newNode.next = prev.next;
                prev.next = newNode;
            }
            size++;
        }

        E removeAt(int index) {
            if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
            Node<E> curr = head, prev = null;
            for (int i = 0; i < index; i++) {
                prev = curr;
                curr = curr.next;
            }
            if (prev == null) {
                head = curr.next;
                if (head == null) tail = null;
            } else {
                prev.next = curr.next;
                if (curr.next == null) tail = prev;
            }
            size--;
            return curr.value;
        }

        E getAt(int index) {
            Node<E> curr = head;
            for (int i = 0; i < index; i++) curr = curr.next;
            return curr.value;
        }

        E setAt(int index, E data) {
            Node<E> curr = head;
            for (int i = 0; i < index; i++) curr = curr.next;
            E old = curr.value;
            curr.value = data;
            return old;
        }
    }

    private Row<E> firstRow;
    private Row<E> lastRow;
    private int totalSize;

    public OneWaySquareList() {
        firstRow = null;
        lastRow = null;
        totalSize = 0;
    }

    private int getLimit() {
        return Math.max(4, 2 * (int) Math.ceil(Math.sqrt(totalSize)));
    }

    private void splitRowIfNeeded(Row<E> row) {
        if (row.size > getLimit()) {
            Row<E> newRow = new Row<>();
            int mid = row.size / 2;

            Node<E> prev = null;
            Node<E> curr = row.head;
            for (int i = 0; i < mid; i++) {
                prev = curr;
                curr = curr.next;
            }

            // Rozcięcie powiązań w starym wierszu
            prev.next = null;
            row.tail = prev;
            row.size = mid;

            // Przepisanie reszty elementów do nowego wiersza
            newRow.head = curr;
            Node<E> tailFinder = curr;
            int newSize = 1;
            while (tailFinder.next != null) {
                tailFinder = tailFinder.next;
                newSize++;
            }
            newRow.tail = tailFinder;
            newRow.size = newSize;

            // Wpięcie nowego wiersza w zewnętrzną listę jednokierunkową wierszy
            newRow.nextRow = row.nextRow;
            row.nextRow = newRow;

            // Jeśli podzieliliśmy ostatni wiersz, nowy staje się nowym ostatnim
            if (row == lastRow) {
                lastRow = newRow;
            }
        }
    }

    @Override
    public boolean add(E e) {
        if (firstRow == null) {
            firstRow = new Row<>();
            lastRow = firstRow;
        }
        lastRow.addLast(e);
        totalSize++;

        splitRowIfNeeded(lastRow);
        return true;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > totalSize) throw new IndexOutOfBoundsException();
        if (index == totalSize) {
            add(element);
            return;
        }

        int count = 0;
        Row<E> currRow = firstRow;

        while (currRow != null) {
            if (index <= count + currRow.size) {
                // Skok do następnego wiersza, by nie wstawiać na absolutny koniec obecnego,
                // chyba że to wiersz na samym końcu struktury
                if (index == count + currRow.size && currRow.nextRow != null) {
                    count += currRow.size;
                    currRow = currRow.nextRow;
                    continue;
                }
                currRow.addAt(index - count, element);
                totalSize++;
                splitRowIfNeeded(currRow);
                return;
            }
            count += currRow.size;
            currRow = currRow.nextRow;
        }
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= totalSize) throw new IndexOutOfBoundsException();
        int count = 0;
        Row<E> currRow = firstRow;

        while (currRow != null) {
            if (index < count + currRow.size) {
                return currRow.getAt(index - count);
            }
            count += currRow.size;
            currRow = currRow.nextRow;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= totalSize) throw new IndexOutOfBoundsException();
        int count = 0;
        Row<E> currRow = firstRow;

        while (currRow != null) {
            if (index < count + currRow.size) {
                return currRow.setAt(index - count, element);
            }
            count += currRow.size;
            currRow = currRow.nextRow;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= totalSize) throw new IndexOutOfBoundsException();
        int count = 0;
        Row<E> prevRow = null;
        Row<E> currRow = firstRow;

        while (currRow != null) {
            if (index < count + currRow.size) {
                E removed = currRow.removeAt(index - count);
                totalSize--;

                // Zwalniamy wiersz, jeśli zrobiliśmy go pustym (przebudowa łączy)
                if (currRow.size == 0) {
                    if (prevRow == null) {
                        firstRow = currRow.nextRow;
                        if (firstRow == null) lastRow = null;
                    } else {
                        prevRow.nextRow = currRow.nextRow;
                        if (prevRow.nextRow == null) lastRow = prevRow;
                    }
                }
                return removed;
            }
            count += currRow.size;
            prevRow = currRow;
            currRow = currRow.nextRow;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public boolean remove(E element) {
        int index = indexOf(element);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        firstRow = null;
        lastRow = null;
        totalSize = 0;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public int indexOf(E element) {
        int index = 0;
        Row<E> currRow = firstRow;

        while (currRow != null) {
            Node<E> currNode = currRow.head;
            while (currNode != null) {
                if (Objects.equals(currNode.value, element)) return index;
                currNode = currNode.next;
                index++;
            }
            currRow = currRow.nextRow;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return totalSize == 0;
    }

    @Override
    public int size() {
        return totalSize;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("iterator() is not implemented (assignment instructions).");
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException("listIterator() is not implemented (assignment instructions).");
    }

    public void debugPrint() {
        System.out.println("\nDebug print:");
        System.out.println("Total elements: " + totalSize);
        System.out.println("Current Limit (max row size): " + getLimit());

        int rowCount = 0;
        Row<E> r = firstRow;
        while(r != null) { rowCount++; r = r.nextRow; }

        System.out.println("Number of rows: " + rowCount);
        System.out.println("List content (by rows):");

        int rowNum = 0;
        Row<E> currRow = firstRow;
        while (currRow != null) {
            System.out.print("Row " + rowNum++ + " (size " + currRow.size + "): ");
            Node<E> curr = currRow.head;
            while (curr != null) {
                System.out.print(curr.value + " ");
                curr = curr.next;
            }
            System.out.println();
            currRow = currRow.nextRow;
        }
    }
}