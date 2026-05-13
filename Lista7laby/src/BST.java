import java.util.Comparator;

public class BST<T> {
    private Node<T> root;
    private final Comparator<T> comparator;

    public BST(Comparator<T> comparator) {
        this.root = null;
        this.comparator = comparator;
    }

    public void insert(T data) {
        Node<T> newNode = new Node<>(data);
        if (root == null) {
            root = newNode; // Tree is empty, set root to the new node
            return;
        }

        Node<T> current = root;
        Node<T> parent = null;

        while (current != null) {
            parent = current;
            int cmp = comparator.compare(data, current.getData());
            if (cmp < 0) {
                current = current.getLeft(); // Move to the left subtree
            } else if (cmp > 0) {
                current = current.getRight(); // Move to the right subtree
            } else {
                return; // Duplicate value, do nothing
            }
        }

        // Attach the new node to the appropriate parent
        int cmp = comparator.compare(data, parent.getData());
        if (cmp < 0) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }
    }

    public void display()
    {
        inOrderTraversal(new DisplayVisitor<>());
        System.out.println();
    }

    class DisplayVisitor<T> implements Visitor<T> {
        @Override
        public void visit(T data) {
            System.out.print(data + " ");
        }
    }

    public void inOrderTraversal(Visitor<T> visitor) {
        inOrderTraversalHelper(root, visitor);
    }

    private void inOrderTraversalHelper(Node<T> node, Visitor<T> visitor) {
        if (node == null) {
            return;
        }
        inOrderTraversalHelper(node.getLeft(), visitor); // Visit left subtree
        visitor.visit(node.getData()); // Visit current node
        inOrderTraversalHelper(node.getRight(), visitor); // Visit right subtree
    }


    public boolean search(T data) {
        return searchHelper(root, data);
    }

    private boolean searchHelper(Node<T> node, T data) {
        if (node == null) {
            return false;
        }
        int cmp = comparator.compare(data, node.getData());
        if (cmp < 0) {
            return searchHelper(node.getLeft(), data);
        } else if (cmp > 0) {
            return searchHelper(node.getRight(), data);
        } else {
            return true; // data found
        }
    }

    public T findMin() {
        if (root == null) {
            return null; // or throw an exception
        }
        return findMinHelper(root);
    }

    private T findMinHelper(Node<T> node) {
        if (node.getLeft() == null) {
            return node.getData(); // Base case: leftmost node
        }
        return (T) findMinHelper(node.getLeft()); // Recursive call on the left subtree
    }

    public T findMax() {
        if (root == null) {
            return null; // or throw an exception
        }
        return findMaxHelper(root);
    }
    private T findMaxHelper(Node<T> node) {
        if (node.getRight() == null) {
            return node.getData(); // Base case: rightmost node
        }
        return (T) findMaxHelper(node.getRight()); // Recursive call on the right subtree
    }


    public T findPredecessor(T data) {
        Node<T> current = root;
        Node<T> predecessor = null;

        // Locate the node for which we want the predecessor
        while (current != null) {
            int cmp = comparator.compare(data, current.getData());
            if (cmp > 0) {
                predecessor = current; // Update predecessor when moving right
                current = current.getRight();
            } else if (cmp < 0) {
                current = current.getLeft();
            } else {
                break; // Node found
            }
        }

        if (current == null) {
            return null; // Node not found
        }

        // If the node has a left subtree, find the maximum in that subtree
        if (current.getLeft() != null) {
            Node<T> predecessorNode = findPredecessorNode(current);
            return predecessorNode.getData();
        }

        // Otherwise, return the last recorded predecessor
        return (predecessor != null) ? predecessor.getData() : null;
    }

    private Node<T> findPredecessorNode(Node<T> node) {
        Node<T> predecessor = node.getLeft();
        while (predecessor != null && predecessor.getRight() != null) {
            predecessor = predecessor.getRight();
        }
        return predecessor;
    }

    public void delete(T data) {
        Node<T> current = root;
        Node<T> parent = null;

        // Step 1: Find the node to delete
        while (current != null && comparator.compare(data, current.getData()) != 0) {
            parent = current;
            int cmp = comparator.compare(data, current.getData());
            if (cmp < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }

        if (current == null) {
            return; // Node not found
        }

        // Step 2: Handle cases
        // Case 1: Node has no children
        if (current.getLeft() == null && current.getRight() == null) {
            if (current == root) {
                root = null;
            } else if (parent.getLeft() == current) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        }
        // Case 2: Node has one child
        else if (current.getLeft() == null || current.getRight() == null) {
            Node<T> child = (current.getLeft() != null) ? current.getLeft() : current.getRight();
            if (current == root) {
                root = child;
            } else if (parent.getLeft() == current) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }
        // Case 3: Node has two children
        else {
            // Use findPredecessorNode to get the predecessor node
            Node<T> predecessor = findPredecessorNode(current);

            // Replace current's data with predecessor's data
            current.setData(predecessor.getData());

            // Find the parent of the predecessor node
            Node<T> predecessorParent = current;
            Node<T> temp = current.getLeft();
            while (temp != null && temp != predecessor) {
                predecessorParent = temp;
                temp = temp.getRight();
            }

            // Remove the predecessor Node<T><T>
            if (predecessorParent.getLeft() == predecessor) {
                predecessorParent.setLeft(predecessor.getLeft());
            } else {
                predecessorParent.setRight(predecessor.getLeft());
            }
        }
    }

    public BST<T> mostImbalancedSubtree() {
        if (root == null) return null;

        Result<T> result = new Result<>();

        findMostImbalanced(root, result);

        BST<T> subtree = new BST<>(comparator);
        copySubtree(result.node, subtree);

        System.out.println("Most imbalanced subtree root: " + result.node.getData() + ", Imbalance: " + result.maxImbalance);
        return subtree;
    }

    private int findMostImbalanced(Node<T> node, Result<T> result) {
        if (node == null) return 0;
        int leftHeight = findMostImbalanced(node.getLeft(), result);
        int rightHeight = findMostImbalanced(node.getRight(), result);
        int imbalance = Math.abs(leftHeight - rightHeight);

        if (imbalance > result.maxImbalance) {
            result.maxImbalance = imbalance;
            result.node = node;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    private void copySubtree(Node<T> node, BST<T> subtree) {
        if (node == null) return;
        subtree.insert(node.getData());
        copySubtree(node.getLeft(), subtree);
        copySubtree(node.getRight(), subtree);
    }

    // helper class for result
    private static class Result<T> {
        BST<T>.Node<T> node;
        int maxImbalance = -1;
    }


    class Node<T> {
        private T data;
        private Node<T> left, right;

        public Node(T data)
        {
            this.data = data;
        }

        public T getData()
        {
            return data;
        }
        public void setData(T data)
        {
            this.data = data;
        }
        public Node<T> getLeft()
        {
            return left;
        }
        public Node<T> getRight()
        {
            return right;
        }
        public void setLeft(Node<T> left)
        {
            this.left = left;
        }
        public void setRight(Node<T> right)
        {
            this.right = right;
        }
    }

}
