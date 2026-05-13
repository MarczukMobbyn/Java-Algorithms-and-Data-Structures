public class Main {
    public static void main(String[] args) {
        OneWaySquareList<String> list = new OneWaySquareList<>();

        System.out.println("Dodajemy 10 elementów i obserwujemy strukturę listy:\n");

        for (int i = 1; i <= 10; i++) {
            list.add("E" + i); // Dodajemy E1, E2, ..., E10
            list.debugPrint();
        }

        System.out.println("\nDodanie elementu 'X' na indeks 5:");
        list.add(5, "X");
        list.debugPrint();

        System.out.println("\nUsunięcie elementu na indeksie 2:");
        list.remove(2);
        list.debugPrint();

        System.out.println("\nZamiana elementu na indeksie 0 na 'Z':");
        list.set(0, "Z");
        list.debugPrint();

        System.out.println("\nDodanie null do listy:");
        list.add(null);
        list.debugPrint();
        System.out.println("Czy lista zawiera null? " + list.contains(null));
        System.out.println("Indeks null: " + list.indexOf(null));

        System.out.println("\nSprawdzenie contains i indexOf:");
        System.out.println("Zawiera 'X': " + list.contains("X"));
        System.out.println("Indeks 'X': " + list.indexOf("X"));
        System.out.println("Zawiera 'Y': " + list.contains("Y"));
        System.out.println("Rozmiar listy: " + list.size());

        //Usuwanie elementów — test przebudowy
        System.out.println("\nUsunięcie elementu o wartości 'X':");
        list.remove("X");
        list.debugPrint();

        System.out.println("\nUsunięcie kilku elementów (E2, E4, E5):");

        list.remove("E2");
        list.debugPrint();

        list.remove("E4");
        list.debugPrint();

        list.remove("E5");
        list.debugPrint();

        System.out.println("\nOstateczna struktura po usuwaniu:");
        list.debugPrint();

        System.out.println("\nCzyszczenie listy...");
        list.clear();
        list.debugPrint();
    }
}
