package Algorithms;

import core.AbstractSortingAlgorithm;
import java.util.*;

public class MergeSortForArrays<T> extends AbstractSortingAlgorithm<T> {

    public MergeSortForArrays(Comparator<? super T> comparator) {
        super(comparator);
    }

    @Override
    public List<T> sort(List<T> list) {
        // Jeżeli lista jest pusta lub ma jeden element, to już jest posortowana
        if (list == null || list.size() < 2) return list;

        int n = list.size();

        // Zewnętrzna pętla: rozmiar aktualnie scalanych fragmentów (1, 2, 4, 8, ...)
        for (int size = 1; size < n; size *= 2) {
            // Przechodzimy przez całą listę w skokach co 2*size
            for (int left = 0; left < n - size; left += 2 * size) {
                // Wyznacz środkowy i prawy indeks przedziału
                int mid = left + size - 1;
                int right = Math.min(left + 2 * size - 1, n - 1);

                // Scal fragment listy [left..mid] z [mid+1..right]
                merge(list, left, mid, right);
            }
        }

        return list;
    }

    // Funkcja scalająca dwa posortowane podprzedziały listy
    private void merge(List<T> list, int left, int mid, int right) {
        List<T> temp = new ArrayList<>();
        int i = left, j = mid + 1;

        // Porównuj elementy z dwóch części i dodawaj do tymczasowej listy
        while (i <= mid && j <= right) {
            if (compare(list.get(i), list.get(j)) <= 0) {
                temp.add(list.get(i++)); // mniejszy z lewej części
            } else {
                temp.add(list.get(j++)); // mniejszy z prawej części
            }
        }

        // Dodaj pozostałe elementy z lewej (jeśli zostały)
        while (i <= mid) temp.add(list.get(i++));

        // Dodaj pozostałe elementy z prawej (jeśli zostały)
        while (j <= right) temp.add(list.get(j++));

        // Nadpisz oryginalną listę posortowanymi danymi
        for (int k = 0; k < temp.size(); k++) {
            list.set(left + k, temp.get(k));
        }
    }
}
