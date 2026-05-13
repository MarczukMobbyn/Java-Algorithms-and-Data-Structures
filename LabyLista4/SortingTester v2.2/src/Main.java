import java.util.Comparator;

import Algorithms.*;

import core.AbstractSortingAlgorithm;
import testing.*;
import testing.comparators.*;
import testing.generation.*;
import testing.generation.conversion.*;
import testing.results.Result;

public class Main {

	public static void main(String[] args) {
		Comparator<MarkedValue<Integer>> markedComparator = new MarkedValueComparator<>(new IntegerComparator());
		Generator<MarkedValue<Integer>> arrayGenerator = new MarkingGenerator<>(new RandomIntegerArrayGenerator(100));
		Generator<MarkedValue<Integer>> listGenerator = new LinkedListGenerator<>(arrayGenerator);

		AbstractSortingAlgorithm<MarkedValue<Integer>> algorithm = new MergeSortForArrays<>(markedComparator);
		AbstractSortingAlgorithm<MarkedValue<Integer>> algorithm1 = new QuickSort<>(markedComparator, new FirstElementPivot<>());
		AbstractSortingAlgorithm<MarkedValue<Integer>> algorithm2 = new QuickSort<>(markedComparator, new RandomPivot<>());


		System.out.println("=== MergeSortForArrays on ArrayList ===");
		testing.results.Result result = Tester.runNTimes(algorithm, arrayGenerator, 1000, 50);
		printInfo(result);

		System.out.println("\n=== MergeSortForArrays on LinkedList ===");
		testing.results.Result result1 = Tester.runNTimes(algorithm, listGenerator, 1000, 50);
		printInfo(result1);

		System.out.println("\n=== QuickSort with first element pivot===");
		testing.results.Result result2 = Tester.runNTimes(algorithm1, arrayGenerator, 1000, 50);
		printInfo(result2);

		System.out.println("\n=== QuickSort with random pivot===");
		testing.results.Result result3 = Tester.runNTimes(algorithm2, arrayGenerator, 1000, 50);
		printInfo(result3);

	}

	private static void printStatistic(String label, double average, double stdDev) {
		System.out.println(label + ": " + double2String(average) + " +- " + double2String(stdDev));
	}

	private static String double2String(double value) {
		return String.format("%.12f", value);
	}

	private static void printInfo(Result result)
	{
		printStatistic("time [ms]", result.averageTimeInMilliseconds(), result.timeStandardDeviation());
		printStatistic("comparisons", result.averageComparisons(), result.comparisonsStandardDeviation());

		System.out.println("always sorted: " + result.sorted());
		System.out.println("always stable: " + result.stable());
	}
}
