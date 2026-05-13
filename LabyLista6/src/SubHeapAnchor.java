import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SubHeapAnchor<T> extends BranchNode<T> {

    private BranchNode<T> leftBranch;
    private BranchNode<T> rightBranch;
    private int elementsInside;
    private Comparator<T> comparator;

    public SubHeapAnchor(T value, Comparator<T> comparator) {
        super(value);
        this.leftBranch = null;
        this.rightBranch = null;
        this.elementsInside = 1;
        this.comparator = comparator;
    }

    public void add(T element) {
        BranchNode<T> newNode = new BranchNode<>(element);
        heapifyUpTree(newNode, findPath(elementsInside++));
    }

    public ArrayList<String> findPath(int index) {
        ArrayList<String> path = new ArrayList<>();
        while(index > 0) {
            if(index % 2 == 1) {
                path.addFirst( "left");
            }
            else {
                path.addFirst( "right");
            }
            index = (index - 1) / 2;
        }
        return path;
    }

    public void heapifyUpTree(BranchNode<T> node, ArrayList<String> path) {
        BranchNode<T> current = this;
        if (comparator.compare((T) node.value, (T) current.value) < 0){
            swap(node, current);
        }
        for (String s : path) {
            BranchNode<T> prevNode = current;
            if (Objects.equals(s, "left")) {
                current = (BranchNode<T>) current.leftBranch;
            } else {
                current = (BranchNode<T>) current.rightBranch;
            }
            if (current == null) {
                if (Objects.equals(s, "left")) {
                    prevNode.leftBranch = node;
                } else {
                    prevNode.rightBranch = node;
                }
                break;
            }
            if (comparator.compare((T) node.value, (T) current.value) < 0) {
                swap(node, current);
            }
        }
    }

    private void swap(BranchNode<T> node1, BranchNode<T> node2) {
        T temp = (T) node1.value;
        node1.value = node2.value;
        node2.value = temp;
    }

    public BaseNode<T> getNode(int index) {
        ArrayList<String> path = findPath(index);
        BranchNode<T> node = this;
        for (String s : path) {
            if (Objects.equals(s, "left") && node != null) {
                node = (BranchNode<T>) node.leftBranch;
            } else if (Objects.equals(s, "right") && node != null) {
                node = (BranchNode<T>) node.rightBranch;
            }
        }
        return node;
    }

    public void heapifyDownTree(BranchNode<T> node) {
        while (true) {
            BranchNode<T> smallest = node;

            if (node.leftBranch != null && comparator.compare(node.leftBranch.value, smallest.value) < 0) {
                smallest = (BranchNode<T>) node.leftBranch;
            }

            if (node.rightBranch != null && comparator.compare(node.rightBranch.value, smallest.value) < 0) {
                smallest = (BranchNode<T>) node.rightBranch;
            }

            if (smallest != node) {
                swap(node, smallest);
                node = smallest;
            } else {
                break;
            }
        }
    }

    public List<T> getLevelElements(int level) {
        List<T> result = new ArrayList<>();
        collectLevelElements(this, level, 0, result);
        return result;
    }

    private void collectLevelElements(BranchNode<T> node, int targetLevel, int currentLevel, List<T> result) {
        if (node == null || node.value == null) {
            return; // Stop if the node is null or has no value
        }
        if (currentLevel == targetLevel) {
            result.add(node.value); // Add the node's value if it's at the target level
            return;
        }
        // Recursively traverse left and right branches
        collectLevelElements(node.leftBranch, targetLevel, currentLevel + 1, result);
        collectLevelElements(node.rightBranch, targetLevel, currentLevel + 1, result);
    }
}


