import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import Algorithms.*;
import core.AbstractSortingAlgorithm;
import testing.*;
import testing.comparators.*;
import testing.generation.*;
import testing.generation.conversion.*;
import testing.results.Result;

public class CsvExporter {

    private static final int REPETITIONS = 50;
    private static final int[] SIZES = new int[]{
            1, 2, 5, 10, 15, 20, 30, 40, 50, 60,
            80, 100, 150, 200, 250, 500, 1000, 2000, 5000, 10000
    };

    public static void main(String[] args) throws IOException {
        Comparator<MarkedValue<Integer>> comparator = new MarkedValueComparator<>(new IntegerComparator());

        Generator<MarkedValue<Integer>>[] generators = new Generator[]{
                new MarkingGenerator<>(new OrderedIntegerArrayGenerator()),
                new MarkingGenerator<>(new ReversedIntegerArrayGenerator()),
                new MarkingGenerator<>(new RandomIntegerArrayGenerator(100)),
                new LinkedListGenerator<>(new MarkingGenerator<>(new ShuffledIntegerArrayGenerator(123)))
        };

        String[] generatorNames = new String[]{
                "Ordered", "Reversed", "Random", "Shuffled"
        };

        AbstractSortingAlgorithm<MarkedValue<Integer>>[] algorithms = new AbstractSortingAlgorithm[]{
                new MergeSortForArrays<>(comparator),
                new MergeSortForArrays<>(comparator),
                new QuickSort<>(comparator, new FirstElementPivot<>()),
                new QuickSort<>(comparator, new RandomPivot<>())
        };

        String[] algorithmNames = new String[]{
                "Merge_ArrayList", "Merge_LinkedList", "QuickFirst", "QuickRandom"
        };

        // Formatter dla PL - przecinek jako separator dziesiętny
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.forLanguageTag("pl-PL"));
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("0.000000", symbols);

        for (int m = 0; m < generators.length; m++) {
            String generatorName = generatorNames[m];
            Generator<MarkedValue<Integer>> generator = generators[m];

            // Eksport czasu
            File czasFile = new File("results/czas_" + generatorName + ".csv");
            czasFile.getParentFile().mkdirs();
            FileWriter czasWriter = new FileWriter(czasFile);
            czasWriter.write("algorytm;rozmiar danych;srednia;odchylenie\n");

            // Eksport porównań
            File porFile = new File("results/porownania_" + generatorName + ".csv");
            porFile.getParentFile().mkdirs();
            FileWriter porWriter = new FileWriter(porFile);
            porWriter.write("algorytm;rozmiar danych;srednia;odchylenie\n");

            for (int a = 0; a < algorithms.length; a++) {
                AbstractSortingAlgorithm<MarkedValue<Integer>> algorithm = algorithms[a];
                String algorithmName = algorithmNames[a];

                for (int size : SIZES) {
                    Generator<MarkedValue<Integer>> activeGenerator = generator;
                    if (algorithmName.equals("Merge_LinkedList")) {
                        activeGenerator = new LinkedListGenerator<>(generator);
                    }

                    Result result = Tester.runNTimes(algorithm, activeGenerator, size, REPETITIONS);

                    czasWriter.write(String.format("%s;%d;%s;%s\n",
                            algorithmName, size,
                            df.format(result.averageTimeInMilliseconds()),
                            df.format(result.timeStandardDeviation())));

                    porWriter.write(String.format("%s;%d;%s;%s\n",
                            algorithmName, size,
                            df.format(result.averageComparisons()),
                            df.format(result.comparisonsStandardDeviation())));
                }
            }

            czasWriter.close();
            porWriter.close();
        }

        System.out.println("Eksport zakonczony.");
    }
}
