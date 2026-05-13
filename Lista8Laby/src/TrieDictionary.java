import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TrieDictionary<V> {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";


    private static class Node<V> {
        char keyChar;
        V value;
        Node<V> child;
        Node<V> sibling;

        Node(char keyChar) {
            this.keyChar = keyChar;
        }
    }

    private Node<V> root;

    // Insert method
    public V insert(String key, V value) {
        if (key == null || value == null) throw new IllegalArgumentException();
        root = insert(root, key, value, 0);
        return oldValue;
    }

    private V oldValue = null; // Przechowuje starą wartość do zwrotu

    private Node<V> insert(Node<V> node, String key, V value, int depth) {
        char c = key.charAt(depth);
        if (node == null) {
            node = new Node<>(c);
        }
        if (node.keyChar == c) {
            if (depth == key.length() - 1) {
                oldValue = node.value;
                node.value = value;
            } else {
                node.child = insert(node.child, key, value, depth + 1);
            }
        } else if (node.keyChar < c) {
            node.sibling = insert(node.sibling, key, value, depth);
        } else { // Wstaw przed obecnym node
            Node<V> newNode = new Node<>(c);
            newNode.sibling = node;
            node = newNode;
            if (depth == key.length() - 1) {
                oldValue = node.value;
                node.value = value;
            } else {
                node.child = insert(node.child, key, value, depth + 1);
            }
        }
        return node;
    }

    // Search method
    public V search(String key) {
        Node<V> node = root;
        int depth = 0;
        while (node != null) {
            char c = key.charAt(depth);
            if (node.keyChar == c) {
                if (depth == key.length() - 1)
                    return node.value;
                node = node.child;
                depth++;
            } else if (node.keyChar < c) {
                node = node.sibling;
            } else {
                return null;
            }
        }
        return null;
    }

    // Remove method
    public V remove(String key) {
        oldValue = null;
        root = remove(root, key, 0);
        return oldValue;
    }

    private Node<V> remove(Node<V> node, String key, int depth) {
        if (node == null) return null;

        char c = key.charAt(depth);

        if (node.keyChar == c) {
            if (depth == key.length() - 1) {
                oldValue = node.value;
                node.value = null;
            } else {
                node.child = remove(node.child, key, depth + 1);
            }

            if (node.value == null && node.child == null) {
                return node.sibling;
            }

        } else if (node.keyChar < c) {
            node.sibling = remove(node.sibling, key, depth);
        }

        return node;
    }

    public List<String> highestValueKeys(Comparator<? super V> comparator) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        MaxHolder<V> max = new MaxHolder<>(null);

        // przechodzimy całe drzewo i znajdujemy max wartość oraz klucze
        findHighestKeys(root, sb, result, comparator, max);
        return result;
    }

    // Pomocnicza klasa do przechowywania największej wartości
    private class MaxHolder<V> {
        V maxValue;
        MaxHolder(V v) { maxValue = v; }
    }

    private void findHighestKeys(Node<V> node, StringBuilder prefix, List<String> result,
                                 Comparator<? super V> comparator, MaxHolder<V> max) {
        if (node == null) return;

        // dodaj znak do prefixu
        prefix.append(node.keyChar);

        // jeśli jest wartość (koniec słowa)
        if (node.value != null) {
            if (max.maxValue == null || comparator.compare(node.value, max.maxValue) > 0) {
                // nowa największa wartość
                max.maxValue = node.value;
                result.clear();
                result.add(prefix.toString());
            } else if (comparator.compare(node.value, max.maxValue) == 0) {
                // równa największej
                result.add(prefix.toString());
            }
        }

        // dziecko
        findHighestKeys(node.child, prefix, result, comparator, max);

        // usuń ostatnią literę (cofamy się z powrotem)
        prefix.deleteCharAt(prefix.length() - 1);

        // rodzeństwo
        findHighestKeys(node.sibling, prefix, result, comparator, max);
    }


    public void printTrieAscii() {
        printTrieAscii(root, 0, true, "", false);
    }

    private void printTrieAscii(Node<V> node, int depth, boolean isLast, String prefix, boolean isSibling) {
        if (node == null) return;

        System.out.print(prefix);

        String color = isSibling ? ANSI_RED : ANSI_GREEN;

        if (depth > 0) {
            if (isLast) {
                System.out.print(color + "└─" + ANSI_RESET);
                prefix += "   ";
            } else {
                System.out.print(color + "├─" + ANSI_RESET);
                prefix += "│  ";
            }
        }

        System.out.print("(" + node.keyChar + ")");
        if (node.value != null) {
            System.out.print(" " + ANSI_YELLOW + "[" + node.value + "]" + ANSI_RESET);
        }
        System.out.println();

        // Zliczamy liczbę rodzeństwa (potrzebne do rysowania gałązek)
        int siblings = 0;
        Node<V> tmp = node.sibling;
        while (tmp != null) {
            siblings++;
            tmp = tmp.sibling;
        }

        // Najpierw dzieci (kolejny poziom)
        printTrieAscii(node.child, depth + 1, siblings == 0, prefix, false);

        // Następnie rodzeństwo (ten sam poziom)
        printTrieAscii(node.sibling, depth, false, prefix.substring(0, Math.max(0, prefix.length() - 3)), true);
    }

    public void printAllNodes() {
        Node<V> node = root; // root.child to pierwszy korzeń
        while (node != null) {
            printAllNodesRecursive(node);
            node = node.sibling;
        }
    }

    private void printAllNodesRecursive(Node<V> node) {
        if (node == null) return;

        System.out.print("Węzeł: '" + node.keyChar + "'");
        if (node.child != null)
            System.out.print(" | dziecko: '" + node.child.keyChar + "'");
        else
            System.out.print(" | dziecko: null");

        if (node.sibling != null)
            System.out.print(" | sibling: '" + node.sibling.keyChar + "'");
        else
            System.out.print(" | sibling: null");

        if (node.value != null)
            System.out.print(" | wartość: [" + node.value + "]");
        System.out.println();

        // PRZECHODZIMY PO WSZYSTKICH DZIECIACH!
        Node<V> child = node.child;
        while (child != null) {
            printAllNodesRecursive(child);
            child = child.sibling;
        }
    }


}
