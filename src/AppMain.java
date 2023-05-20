import java.util.Scanner;
import java.io.File;

public class AppMain {
    public static final int[][] move = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
    /*
    move[0] = UP
    move[1] = DOWN
    move[2] = LEFT
    move[3] = RIGHT
    */
    private static boolean a;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice, i1, i2, j1, j2;
        System.out.println("A quel niveau voulez-vous jouer ? (entre 1 et 10)");
        choice = scanner.nextInt();

        try {
            if (choice >= 1 && choice <= 10) {
                String fileName = "../levels/level_" + choice + ".csv";
                File fileLevel = new File(fileName);
                Grid level = new Grid(fileLevel);
                level.shuffle();
                
                level.print();
                while(true){
                System.out.println("Quelles cases voulez-vous modifier ?");
                i1 = scanner.nextInt();
                j1 = scanner.nextInt();
                i2 = scanner.nextInt();
                j2 = scanner.nextInt();
                
                try {
                    a=level.moveCell(level.getGrid()[i1][j1], level.getGrid()[i2][j2]);
                    System.out.println(a);
                } catch (Exception e) {
                    System.out.println("Erreur lors de la modification des cases : " + e.getMessage());
                }
                
                level.print();
                }
            } else {
                System.out.println("Niveau invalide !");
            }
        } catch (Exception e) {
            System.out.println("Le niveau indiqué n'est pas compris entre 1 et 10 : " + e.getMessage());
        }

        scanner.close();
    }
}
