import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class NumberGenerator {

    // Generuje listę losowych Integerów
    public static List<Integer> generateRandomIntegers(int count, int min, int max) {
        List<Integer> result = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int num = min + random.nextInt(max - min + 1);
            result.add(num);
        }
        return result;
    }
}
