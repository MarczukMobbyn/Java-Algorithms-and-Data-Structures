package Algorithms;
import java.util.List;


public interface PivotStrategy<T> {
    int choosePivot(List<T> list, int left, int right);
}

