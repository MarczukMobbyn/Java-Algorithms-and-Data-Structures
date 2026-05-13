public class gowienko {

    public static void main(String[] args) {
        int tab[] = new int[10];
        for (int i = 0; i < tab.length; i++) {
            tab[i] = i;
        }

        int tab2[] = tab;
        tab2[0] = 99;



        for (int i = 0; i < tab.length; i++) {
            System.out.print(tab[i] + " ");
        }
        System.out.println();

        for (int i = 0; i < tab2.length; i++) {
            System.out.print(tab2[i] + " ");
        }
    }
}
