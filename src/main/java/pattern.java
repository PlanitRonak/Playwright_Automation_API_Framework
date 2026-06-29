public class pattern {
    public static void main(String[] args) {
        for (int i = 0 ; i < 5 ; i++) {
            for (int row = 4 ; row > i ; row--) {
                System.out.print(" ");
            }
            for (int j = 0 ; j <= i ; j++) {
                System.out.print(" *");
            }
            System.out.println();
        }
    }
}
