package Algorithms;

import java.util.List;
import java.util.Random;

public class RandomPivot<T> implements PivotStrategy<T> {
    private final Random rand = new Random();

    @Override
    public int choosePivot(List<T> list, int left, int right) {
        return left + rand.nextInt(right - left + 1);
    }
}


