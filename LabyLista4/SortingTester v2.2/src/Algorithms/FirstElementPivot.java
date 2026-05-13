package Algorithms;

import java.util.List;

public class FirstElementPivot<T> implements PivotStrategy<T> {
    @Override
    public int choosePivot(List<T> list, int left, int right) {
        return left;
    }
}

