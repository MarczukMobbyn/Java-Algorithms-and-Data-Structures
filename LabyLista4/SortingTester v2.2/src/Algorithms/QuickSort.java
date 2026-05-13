package Algorithms;

import core.AbstractSortingAlgorithm;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

// Klasa implementująca sortowanie szybkie (QuickSort)
// Wykorzystuje strategię wyboru pivota przekazaną przez konstruktor
public class QuickSort<T> extends AbstractSortingAlgorithm<T> {
    private final PivotStrategy<T> pivotStrategy;

    // Konstruktor: przekazujemy komparator i strategię wyboru pivota
    public QuickSort(Comparator<? super T> comparator, PivotStrategy<T> strategy) {
        super(comparator);
        this.pivotStrategy = strategy;
    }

    // Główna metoda sortująca
    @Override
    public List<T> sort(List<T> list) {
        if (list == null || list.size() < 2) return list; // nic do sortowania
        quickSort(list, 0, list.size() - 1); // uruchamiamy sortowanie od zera do końca listy
        return list;
    }

    // Właściwa implementacja algorytmu QuickSort (rekurencyjna)
    private void quickSort(List<T> list, int left, int right) {
        if (left >= right) return; // warunek zakończenia rekurencji

        // Wybierz pivot na podstawie strategii
        int pivotIndex = pivotStrategy.choosePivot(list, left, right);
        T pivotValue = list.get(pivotIndex);

        // Przesuń pivot na koniec podlisty (technika ułatwiająca dalsze operacje)
        Collections.swap(list, pivotIndex, right);

        // Podział listy i wskazuje miejsce, gdzie wrzucimy kolejny mniejszy element
        int i = left;
        for (int j = left; j < right; j++) {
            // Jeśli bieżący element jest mniejszy niż pivot, przesuń go na lewo
            if (compare(list.get(j), pivotValue) < 0) {
                Collections.swap(list, i++, j);
            }
        }

        // Wstaw pivot na jego ostateczne miejsce
        Collections.swap(list, i, right);

        // Rekurencyjnie posortuj lewą i prawą stronę pivota
        quickSort(list, left, i - 1);
        quickSort(list, i + 1, right);
    }
}
