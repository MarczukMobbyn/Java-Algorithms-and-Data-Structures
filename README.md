# 📚 Java Algorithms and Data Structures

Project realized as part of the **Algorithms and Data Structures** course. This repository is a *monorepo* containing a collection of custom, built-from-scratch implementations of advanced data structures and algorithms in Java. The code focuses on optimization, computational complexity correctness, and avoiding the use of built-in Java collections (where required by the course rules).

---

## 📂 Repository Contents

The project is divided into packages corresponding to subsequent laboratory assignment lists. Below is a description of the key structures and algorithms implemented in each folder:

### 🔹 LabyLista1: Custom Iterators
A module focused on designing custom iterators with unique behaviors:
* **DivisorIterator**: An iterator returning all divisors of a given number (utilizing optimal search up to $\sqrt{N}$ and sorting via `TreeSet`).
* **ByteIterator**: A wrapper for `Iterator<Integer>` that splits 32-bit integers into single bytes (8-bit), skipping leading zeros.
* **RandomIterator**: An iterator that traverses an array in a completely random order without repetition.
* **SubsequenceIterator**: An iterator generating all contiguous subsequences of a given array, returning subsequent iterators for each subsequence.

### 🔹 LabyLista2: Advanced Linked Lists
This module contains the implementation of a custom, complex list structure optimized for searching.
* **OneWaySquareList**: A proprietary square list utilizing root decomposition (*Sqrt Decomposition*) and dynamic node splitting. This allows for the optimization of operation time. The structure is implemented 100% on "bare" pointers (bypassing default collections like `ArrayList`).
* **TwoWayLinkedList**: A fully pointer-based doubly linked list with implemented sentinel nodes and a "Next Skipping" pointer algorithm.
* **IList Interface**: A unified contract for list structures enforcing the implementation of operations such as addition, removal, and searching.

### 🔹 LabyLista3: Doubly Linked List with Skips
This module implements a specific doubly linked list structure, modifying the standard approach to node navigation.
* **TwoWayLinkedList**: A fully pointer-based doubly linked list with implemented *sentinel nodes* (for `head` and `tail`). The structure stands out with its unique "skipping" pointers algorithm – backward navigation (`prev`) works standardly, while forward pointers (`next`) intentionally skip two positions.
* **IList Interface**: A unified contract for list structures (addition, removal, searching), implemented by the above list.
* **Zad1**: A class testing the reliability of the structure, the correctness of thrown exceptions (`IndexOutOfBoundsException`), and verifying the pointer skipping mechanism.

### 🔹 LabyLista4: Sorting Algorithms and Testing Framework
This module contains a set of sorting algorithm implementations along with a custom infrastructure for measuring their performance and correctness.
* **Sorting Algorithms**: Implementations of `MergeSortForArrays` and `QuickSort` (using the Strategy design pattern for pivot selection: `FirstElementPivot` or `RandomPivot`).
* **Testing Framework**: The `Tester` class that repeatedly runs the tested algorithms and aggregates statistics (average execution time, standard deviation, number of comparisons).
* **Data Generators**: Tools (`RandomIntegerArrayGenerator`, `LinkedListGenerator`, `MarkingGenerator`) that create diverse test sets for arrays and lists.
* **Stability Verification**: Application of a special `MarkedValue` type with a dedicated comparator, allowing for unambiguous proof of stability (or lack thereof) in the tested algorithms.

### 🔹 LabyLista6: Hybrid Binary Heap (Array-Tree Heap)
This module implements an innovative, hybrid heap structure (priority queue) that combines the advantages of fast array access with the memory flexibility of tree structures.
* **ArrayTreeBinaryHeap**: The main heap structure. Its upper part (up to a defined height) is stored in a flat array ensuring high performance. Upon exceeding this limit, the lower array nodes become anchor points for dynamic subtrees.
* **Polymorphic node hierarchy**: The use of inheritance (`BaseNode` -> `ArrayNode`, `BranchNode`, `SubHeapAnchor`) to handle the differences between typical array elements and those with pointers to children.
* **Complex Heapify operations**: Seamless transition between mathematical array indexing (e.g., `(i-1)/2`) and pointer navigation along tree branches (`leftBranch`, `rightBranch`) during insertion (`add`) and retrieving the minimum (`minimum`). Additionally, a `findPath` algorithm was implemented to calculate the binary path to the last leaf in the tree.

### 🔹 LabyLista7: Binary Search Trees (BST)
This module implements a generic Binary Search Tree from scratch based on a pointer structure.
* **Full node lifecycle**: Implementation of insertion, searching, finding extremes (`findMin`, `findMax`), finding the predecessor (`findPredecessor`), and comprehensive node removal (`delete`), supporting all 3 cases (node with no children, with one child, and with two children).
* **Visitor Design Pattern**: Application of the `Visitor` interface, separating the tree structure from operations performed on its elements (used, among other things, to print elements during an *In-Order* traversal).
* **Tree Balance Analysis**: Implementation of an optimized `mostImbalancedSubtree()` algorithm. It utilizes a bottom-up *post-order* traversal, allowing it to calculate heights, find the most imbalanced subtree, and generate its deep copy in $O(N)$ time.

### 🔹 LabyLista8: Trie Trees (LCRS)
This module implements a dictionary based on a prefix tree (Trie) utilizing an advanced, memory-optimal node representation.
* **TrieDictionary**: The main dictionary structure built in the **Left-Child Right-Sibling (LCRS)** architecture. Thanks to this, each node always has only two pointers (regardless of the alphabet size), which drastically reduces memory consumption compared to classic child arrays.
* **Safe Removal (Pruning)**: An advanced, recursive `remove` method that not only deletes the value but also cascades upwards to clean "ghost nodes" (empty paths lacking children and values), ensuring the perfect state of the structure.
* **Analytical tools and visualization**: Built-in methods for finding keys with the highest value (`highestValueKeys`) using an efficient DFS traversal and a dynamic `StringBuilder`. Additionally, the module features a colorful, fully functional visualization of the tree structure in ASCII format.

### 🔹 LabyLista9: Binomial Heaps
This module implements advanced, tree-based priority queues based on the structure of binomial tree forests.
* **MinBinomialHeap and MaxBinomialHeap**: Full, pointer-based implementations of a binomial heap in Min and Max variants.
* **Operations on tree forests**: Effective implementation of difficult operations combining entire structures (`union`, `merge`, `link`), which ensure the strict properties of binomial trees are maintained along with proper pointer reassignment in the *Left-Child Right-Sibling* structure.
* **Pointer management during removal**: Correct reversal of child pointer lists (from oldest to youngest) and creating a new forest from them during extreme removal operations (`extractMin` / `extractMax`).
* **Additional tools**: A built-in method verifying the mathematical correctness of the heap (`isMaxHeap`), a Breadth-First Search (BFS) algorithm returning the structure by levels (`levels()`), and a clear visualization of the heap's content in the console.

---

## 🛠️ Technologies and Tools
* **Language:** Java
* **Environment:** IntelliJ IDEA
* **Architecture:** Design patterns (Strategy, Visitor), pointer-based approach without using default `java.util.Collections` to build main structures.

---
*Author: Szymon Marczuk*
