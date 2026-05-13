public class zad1 {
    public static void main(String[] args) {
        int n = 10;
        System.out.println("Test DivisorIterator dla N = " + n);
        DivisorIterator divIt = new DivisorIterator(n);
        for (int d : divIt) {
            System.out.print(d + " ");
        }
        System.out.println();
    }
}
