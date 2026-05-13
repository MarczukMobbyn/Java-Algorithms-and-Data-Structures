import java.util.*;

public class ArrayTreeBinaryHeap<T> {
    public BaseNode<T>[] array;
    private int height;
    private int numberOfElements;
    private Comparator<T> comparator;
    private int arrayPartSize; // ile elementów przechowuje część tablicowa

    public ArrayTreeBinaryHeap(int height, Comparator<T> comparator) {
        this.height = height;
        this.numberOfElements = 0;
        this.comparator = comparator;
        this.arrayPartSize = (int) Math.pow(2, height) - 1; // rozmiar części tablicowej
        this.array = new BaseNode[arrayPartSize];
    }

    public void add(T value) {
        numberOfElements++;
        if (numberOfElements < (arrayPartSize + 1) / 2) {
            // Add to the array part as ArrayNode
            array[numberOfElements - 1] = new ArrayNode<>(value);
            heapifyUpArray(numberOfElements - 1);
            //System.out.println("Added " + value + " to class "+ array[numberOfElements - 1].getClass() +" at index " + (numberOfElements - 1));
        } else if (numberOfElements <= arrayPartSize) {
            // Add to the array part as SubHeapAnchor
            array[numberOfElements - 1] = new SubHeapAnchor<>(value, comparator);
            heapifyUpArray(numberOfElements - 1);
            //System.out.println("Added " + value + " to class "+ array[numberOfElements - 1].getClass() +" at index " + (numberOfElements - 1));
        } else {
            // Add to the tree part
            int index = numberOfElements - 1;
            while (index >= arrayPartSize) {
                index = (index - 1) / 2;
            }
            if (array[index] instanceof SubHeapAnchor) {
                ((SubHeapAnchor<T>) array[index]).add(value);
            } else {
                throw new IllegalStateException("Expected SubHeapAnchor at index " + index + " but found " +
                        (array[index] == null ? "null" : array[index].getClass().getSimpleName()));
            }
            heapifyUpArray(index);
        }
    }

    public void displayArray()
    {
        for (int i = 0; i < numberOfElements && i < arrayPartSize; i++) {
            if (array[i] != null) {
                System.out.print(array[i].value + " ");
            } else {
                System.out.print("null ");
            }
        }
    }

    private void heapifyUpArray(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (comparator.compare(array[index].value, array[parentIndex].value) < 0) {
                // Swap the values using the swap function
                swap(array[index], array[parentIndex]);
                index = parentIndex; // Move up to the parent
            } else {
                break; // Heap property is satisfied
            }
        }
    }

    private void swap(BaseNode<T> node1, BaseNode<T> node2) {
        T temp = node1.value;
        node1.value = node2.value;
        node2.value = temp;
    }

    public T minimum(){
        if (array[0] == null){
            throw new IllegalArgumentException("Heap is empty");
        }
        T value = array[0].value;
        System.out.println("Swapped " + value + " with " + getNode(numberOfElements-1).value);
        swap(array[0], getNode(numberOfElements - 1));
        displayStructure();
        deleteLastNode();
        int index = heapifyDownArray(0);
        if (index >= (arrayPartSize)/ 2){
            ((SubHeapAnchor<T>) array[index]).heapifyDownTree((SubHeapAnchor) array[index]);
        }
        return value;
    }

    private BaseNode<T> getNode(int index){
        if (index < arrayPartSize){
            return array[index];
        }
        else if (index < numberOfElements){
            int treeIndex = index;
            while (treeIndex >= arrayPartSize){
                treeIndex = (treeIndex - 1) / 2;
            }
            int steps = 0;
            int indexGiven = index;
            while (index > treeIndex){
                if (index % 2 == 1){
                    index = (index - 1) / 2;
                }
                else {
                    index = (index - 2) / 2;
                }
                steps++;
            }
            int indexInsideTree = (int) (indexGiven - Math.pow(2,steps) * treeIndex);
            return ((SubHeapAnchor) array[treeIndex]).getNode(indexInsideTree);
        }
        else {
            throw new IllegalArgumentException("Index out of bounds");
        }
    }

    private int heapifyDownArray(int index){
        if (index < (arrayPartSize) / 2){
            int minIndex = index;

            if (array[index * 2 + 1] != null && comparator.compare((T) array[(index * 2) + 1].value, (T) array[index].value) < 0) {
                minIndex = index * 2 + 1;
            }
            if (array[index * 2 + 2] != null && comparator.compare((T) array[(index * 2) + 2].value, (T) array[minIndex].value) < 0) {
                minIndex = index * 2 + 2;
            }
            if (index != minIndex){
                swap(array[index], array[minIndex]);
                return heapifyDownArray(minIndex);
            }
            else {
                return index;
            }
        }
        else {
            return index;
        }
    }

    private void deleteLastNode() {
        if (numberOfElements <= 0) {
            throw new IllegalStateException("Heap is empty");
        }

        if (numberOfElements <= arrayPartSize) {
            // Handle the array part
            array[numberOfElements - 1] = null;
        } else {
            // Handle the tree part

            int treeIndex = numberOfElements-1;
            while (treeIndex >= arrayPartSize){
                treeIndex = (treeIndex - 1) / 2;
            }
            ArrayList<String> pathToLast = ((SubHeapAnchor<T>) array[treeIndex]).findPath(numberOfElements - 1);
            System.out.println("Path to last node: " + pathToLast);
            BranchNode<T> parent = ((SubHeapAnchor<T>) array[treeIndex]);
            for (int i = height-1; i < pathToLast.size() - 1; i++) {
                if (pathToLast.get(i).equals("left")) {
                    parent = (BranchNode<T>) parent.leftBranch;
                    System.out.println("Traversing left to " + parent.value);
                } else {
                    parent = (BranchNode<T>) parent.rightBranch;
                    System.out.println("Traversing right to " + parent.value);
                }
            }

            // Set the appropriate child to null
            if (pathToLast.get(pathToLast.size() - 1).equals("left")) {
                parent.leftBranch = null;
            } else {
                parent.rightBranch = null;
            }
        }

        numberOfElements--;
    }

    public void displayStructure() {
        int level = 0;
        int elementsInLevel = 1;
        int index = 0;

        // Display array part level by level
        while (index < arrayPartSize && index < numberOfElements) {
            for (int i = 0; i < elementsInLevel && index < arrayPartSize && index < numberOfElements; i++, index++) {
                System.out.print(array[index].value + " ");
            }
            System.out.println();
            elementsInLevel *= 2; // Double the number of elements in the next level
            level++;
        }

        // Display SubHeapAnchors level by level
        level = 1;
        boolean hasMoreLevels = true;
        while (hasMoreLevels) {
            hasMoreLevels = false;
            List<T> levelElements = new ArrayList<>();
            for (int i = 0; i < numberOfElements && i < array.length; i++) {
                if (array[i] instanceof SubHeapAnchor) {
                    SubHeapAnchor<T> subHeap = (SubHeapAnchor<T>) array[i];
                    List<T> elements = subHeap.getLevelElements(level);
                    levelElements.addAll(elements);
                    if (!elements.isEmpty()) {
                        hasMoreLevels = true;
                    }
                }
            }
            if (!levelElements.isEmpty()) {
                for (int i = 0; i < levelElements.size(); i++) {
                    System.out.print(levelElements.get(i) + " ");
                }
                System.out.println();
            } else if (hasMoreLevels) {
                System.out.println("Level " + level + ": No elements");
            }
            level++;
        }
    }
}
