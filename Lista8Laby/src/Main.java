import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TrieDictionary<Integer> dict = new TrieDictionary<>();

        // Dodajemy słowa tak jak w przykładzie z PDF-a
        dict.insert("baby", 1);
        dict.insert("bad", 6);
        dict.insert("bank", 6);
        dict.insert("box", 4);
        dict.insert("dad", 5);
        dict.insert("dance", 6);

        // Wyświetlamy słownik w formie drzewa
        System.out.println("Wizualizacja drzewa trie:");
        dict.printTrieAscii();

        dict.printAllNodes();

        System.out.println("\nhighest value keys: ");
        // Wyświetlamy najwyższe wartości
        List<String> highestKeys = dict.highestValueKeys(Comparator.naturalOrder());
        System.out.println("Najwyższe wartości: " + highestKeys);

        // Proste testy wyszukiwania
        System.out.println("\nTest wyszukiwania:");
        System.out.println("search('box') = " + dict.search("box"));
        System.out.println("search('bank') = " + dict.search("bank"));
        System.out.println("search('dance') = " + dict.search("dance"));
        System.out.println("search('bake') = " + dict.search("bake")); // powinno być null

        // Test usuwania
        System.out.println("\nUsuwamy 'bank'");
        dict.remove("bank");
        dict.printTrieAscii();
    }
}
