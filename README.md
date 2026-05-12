# 📚 Java Algorithms and Data Structures

Projekt realizowany w ramach przedmiotu **Algorytmy i Struktury Danych**. Repozytorium to tzw. *monorepo*, które zawiera zbiór własnych, zbudowanych od zera implementacji zaawansowanych struktur danych oraz algorytmów w języku Java. Kod skupia się na optymalizacji, poprawności złożoności obliczeniowej oraz braku użycia wbudowanych kolekcji Javy (tam, gdzie wymagały tego zasady przedmiotu).

---

## 📂 Zawartość repozytorium

Projekt został podzielony na pakiety odpowiadające kolejnym listom zadań z laboratoriów. Poniżej znajduje się opis kluczowych struktur i algorytmów zaimplementowanych w poszczególnych folderach:

### 🔹 LabyLista1: Niestandardowe Iteratory (Custom Iterators)
Moduł skupiający się na projektowaniu własnych iteratorów o unikalnych zachowaniach:
* **DivisorIterator**: Iterator zwracający wszystkie dzielniki podanej liczby (wykorzystujący optymalne wyszukiwanie do $\sqrt{N}$ i sortowanie przez `TreeSet`).
* **ByteIterator**: Nakładka na `Iterator<Integer>`, która rozbija liczby 32-bitowe na pojedyncze bajty (8-bitowe), pomijając wiodące zera.
* **RandomIterator**: Iterator przechodzący przez tablicę w całkowicie losowej kolejności bez powtórzeń.
* **SubsequenceIterator**: Iterator generujący wszystkie spójne podciągi danej tablicy, zwracający kolejne iteratory dla każdego z podciągów.

### 🔹 LabyLista2: Zaawansowane Listy Wiązane
W tym module znajduje się implementacja niestandardowej, złożonej struktury listowej zoptymalizowanej pod kątem wyszukiwania.
* **OneWaySquareList**: Autorska lista kwadratowa wykorzystująca dekompozycję pierwiastkową (*Sqrt Decomposition*) oraz dynamiczne rozszczepianie węzłów (splitting). Pozwala to na optymalizację czasu operacji. Struktura została zaimplementowana w 100% na "gołych" wskaźnikach (z pominięciem domyślnych kolekcji takich jak `ArrayList`).
* **TwoWayLinkedList**: W pełni wskaźnikowa dwukierunkowa lista wiązana z wdrożonymi węzłami wartowniczymi (sentinel nodes) i algorytmem "skaczących" wskaźników (Next Skipping).
* **Interfejs IList**: Zunifikowany kontrakt dla struktur listowych wymuszający implementację m.in. operacji dodawania, usuwania i wyszukiwania.

### 🔹 LabyLista3: Dwukierunkowa Lista Wiązana z Przeskokami
W tym module zaimplementowano specyficzną strukturę listy dwukierunkowej, modyfikującą standardowe podejście do nawigacji po węzłach.
* **TwoWayLinkedList**: W pełni wskaźnikowa dwukierunkowa lista wiązana z wdrożonymi węzłami wartowniczymi (*sentinel nodes* dla `head` i `tail`). Struktura wyróżnia się unikalnym algorytmem "skaczących" wskaźników – nawigacja wstecz (`prev`) działa standardowo, natomiast wskaźniki w przód (`next`) celowo przeskakują o dwie pozycje.
* **Interfejs IList**: Zunifikowany kontrakt dla struktur listowych (dodawanie, usuwanie, wyszukiwanie), zaimplementowany przez powyższą listę.
* **Zad1**: Klasa testująca niezawodność struktury, poprawność rzucanych wyjątków (`IndexOutOfBoundsException`) oraz weryfikująca mechanizm przeskakiwania wskaźników.

### 🔹 LabyLista4: Algorytmy Sortujące i Framework Testowy
W tym module znajduje się zestaw implementacji algorytmów sortujących wraz z autorską infrastrukturą do pomiaru ich wydajności i poprawności.
* **Algorytmy Sortujące**: Implementacje `MergeSortForArrays` oraz `QuickSort` (wykorzystujący wzorzec projektowy Strategii do wyboru elementu osiowego: `FirstElementPivot` lub `RandomPivot`).
* **Framework Testowy**: Klasa `Tester` uruchamiająca wielokrotnie badane algorytmy i agregująca statystyki (średni czas wykonania, odchylenie standardowe, liczba porównań).
* **Generatory Danych**: Narzędzia (`RandomIntegerArrayGenerator`, `LinkedListGenerator`, `MarkingGenerator`) tworzące zróżnicowane zestawy testowe dla tablic i list.
* **Weryfikacja Stabilności**: Zastosowanie specjalnego typu `MarkedValue` z dedykowanym komparatorem, co pozwala jednoznacznie udowodnić stabilność (lub jej brak) w testowanych algorytmach.

* ### 🔹 LabyLista6: Hybrydowy Kopiec Binarny (Array-Tree Heap)
W tym module zaimplementowano innowacyjną, hybrydową strukturę kopca (kolejki priorytetowej), która łączy zalety szybkiego dostępu tablicowego z elastycznością pamięciową struktur drzewiastych.
* **ArrayTreeBinaryHeap**: Główna struktura kopca. Jej górna część (do zdefiniowanej wysokości) przechowywana jest w płaskiej tablicy zapewniającej wysoką wydajność. Po przekroczeniu tego limitu, dolne węzły tablicy stają się punktami kotwiczenia dla dynamicznych poddrzew.
* **Polimorficzna hierarchia węzłów**: Zastosowanie dziedziczenia (`BaseNode` -> `ArrayNode`, `BranchNode`, `SubHeapAnchor`) do obsługi różnic między elementami typowo tablicowymi a tymi posiadającymi wskaźniki na dzieci.
* **Złożone operacje Heapify**: Płynne przechodzenie między matematycznym indeksowaniem tablicy (np. `(i-1)/2`), a wskaźnikową nawigacją po gałęziach drzewa (`leftBranch`, `rightBranch`) podczas wstawiania (`add`) oraz pobierania minimum (`minimum`). Dodatkowo zaimplementowano algorytm `findPath` wyliczający binarną ścieżkę do ostatniego liścia w drzewie.

### 🔹 LabyLista7: Drzewa Poszukiwań Binarnych (BST)
W tym module zaimplementowano od podstaw generyczne drzewo poszukiwań binarnych (Binary Search Tree) oparte na strukturze wskaźnikowej.
* **Pełen cykl życia węzła**: Zaimplementowano wstawianie, wyszukiwanie, szukanie ekstremów (`findMin`, `findMax`), wyszukiwanie poprzednika (`findPredecessor`) oraz kompleksowe usuwanie węzłów (`delete`), obsługujące wszystkie 3 przypadki (węzeł bez dzieci, z jednym dzieckiem oraz z dwojgiem dzieci).
* **Wzorzec Projektowy Visitor**: Zastosowano interfejs `Visitor` oddzielający strukturę drzewa od operacji wykonywanych na jego elementach (wykorzystany m.in. do wypisywania elementów podczas przejścia *In-Order*).
* **Analiza Balansu Drzewa**: Zaimplementowano zoptymalizowany algorytm `mostImbalancedSubtree()`. Wykorzystuje on przejście *post-order* z dołu do góry, co pozwala w czasie $O(N)$ obliczyć wysokości, znaleźć najbardziej niezbalansowane poddrzewo i wygenerować jego głęboką kopię.

### 🔹 LabyLista8: Drzewa Trie (LCRS)
W tym module zaimplementowano słownik oparty na drzewie prefiksowym (Trie) z wykorzystaniem zaawansowanej, optymalnej pamięciowo reprezentacji węzłów.
* **TrieDictionary**: Główna struktura słownika zbudowana w architekturze **Left-Child Right-Sibling (LCRS)**. Dzięki temu każdy węzeł posiada zawsze tylko dwa wskaźniki (niezależnie od rozmiaru alfabetu), co drastycznie redukuje zużycie pamięci względem klasycznych tablic dzieci.
* **Bezpieczne usuwanie (Pruning)**: Zaawansowana, rekurencyjna metoda `remove`, która nie tylko usuwa wartość, ale kaskadowo w górę czyści "węzły-widma" (puste ścieżki pozbawione dzieci i wartości), dbając o idealny stan struktury.
* **Narzędzia analityczne i wizualizacja**: Wbudowane metody do znajdowania kluczy o najwyższej wartości (`highestValueKeys`) przy użyciu efektywnego przejścia DFS i dynamicznego `StringBuilder`a. Dodatkowo moduł posiada kolorową, w pełni funkcjonalną wizualizację struktury drzewa w formacie ASCII.

### 🔹 LabyLista9: Kopce Dwumianowe (Binomial Heaps)
W tym module zaimplementowano zaawansowane, drzewiaste kolejki priorytetowe oparte na strukturze lasów drzew dwumianowych.
* **MinBinomialHeap oraz MaxBinomialHeap**: Pełne, wskaźnikowe implementacje kopca dwumianowego w wariantach Min oraz Max.
* **Operacje na lasach drzew**: Skuteczna implementacja trudnych operacji łączenia całych struktur (`union`, `merge`, `link`), które dbają o zachowanie rygorystycznych właściwości drzew dwumianowych i odpowiednie przepinanie wskaźników w strukturze *Left-Child Right-Sibling*.
* **Zarządzanie wskaźnikami podczas usuwania**: Prawidłowe odwracanie list wskaźników dzieci (od najstarszego do najmłodszego) i tworzenie z nich nowego lasu podczas operacji usuwania ekstremum (`extractMin` / `extractMax`).
* **Narzędzia dodatkowe**: Wbudowana metoda weryfikująca poprawność matematyczną kopca (`isMaxHeap`), algorytm przeszukiwania wszerz (BFS) zwracający strukturę poziomami (`levels()`) oraz czytelna wizualizacja zawartości kopca w konsoli.

---

## 🛠️ Technologie i narzędzia
* **Język:** Java
* **Środowisko:** IntelliJ IDEA
* **Architektura:** Wzorce projektowe (Strategy, Visitor), podejście wskaźnikowe bez użycia domyślnego `java.util.Collections` do budowy głównych struktur.

---
*Autor: Szymon Marczuk*
