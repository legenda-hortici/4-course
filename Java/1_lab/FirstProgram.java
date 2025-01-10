import FirstPackage.*; 

class FirstClass {
    public static void main(String[] s) {
        SecondClass o = new SecondClass();
        int i, j;
        for (i = 1; i <= 9; i++) {
            for (j = 1; j <= 9; j++) {
                o.setFirstNumber(i);
                o.setSecondNumber(j);
                System.out.print(o.performAction() + " ");
            }
            System.out.println();
        }
    }
}
