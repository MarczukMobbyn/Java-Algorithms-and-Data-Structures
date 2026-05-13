public class Zad1 {
    public static void main(String[] args) {
        TwoWayLinkedList<Integer> list = new TwoWayLinkedList<>();

        System.out.println("Dodawanie elementów na koniec:");
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list);

        System.out.println("\nDodawanie elementu na indeks 1:");
        list.add(1, 10);
        System.out.println(list);

        System.out.println("\nPobieranie elementu z indeksu 2: " + list.get(2));

        System.out.println("\nUstawianie nowej wartości na indeksie 2 (wartość 20):");
        list.set(2, 20);
        System.out.println(list);

        System.out.println("\nCzy lista zawiera 10? " + list.contains(10));
        System.out.println("Czy lista zawiera 100? " + list.contains(100));

        System.out.println("\nIndeks elementu 20: " + list.indexOf(20));
        System.out.println("Indeks elementu 100: " + list.indexOf(100));

        System.out.println("\nUsuwanie elementu z indeksu 1:");
        list.remove(1);
        System.out.println(list);

        System.out.println("\nUsuwanie elementu o wartości 3:");
        list.remove(Integer.valueOf(3));
        System.out.println(list);

        System.out.println("\nDodawanie więcej elementów:");
        list.add(4);
        list.add(5);
        list.add(6);
        System.out.println(list);

        System.out.println("\nCzyszczenie listy:");
        list.clear();
        System.out.println(list);

        System.out.println("\nDodawanie do pustej listy:");
        list.add(100);
        System.out.println(list);

        System.out.println("\nTest rozmiaru listy:");
        System.out.println("Rozmiar: " + list.size());

        System.out.println("\nTest czy lista jest pusta:");
        System.out.println("Czy pusta? " + list.isEmpty());

        // Testy wyjątków
        try {
            list.get(5);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("\nOczekiwany wyjątek przy get(5): " + e.getMessage());
        }

        try {
            list.add(5, 200);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oczekiwany wyjątek przy add(5, 200): " + e.getMessage());
        }

        try {
            list.remove(5);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oczekiwany wyjątek przy remove(5): " + e.getMessage());
        }

        list.clear();
        //Testowanie przeskakiwania next o dwie pozycje
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        System.out.println(list);
        System.out.println("\nTestowanie przeskakiwania next o dwie pozycje:");
        list.testNextSkipping();

    }


}