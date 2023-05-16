import java.util.ArrayList;

public class AppMain
{

public static void main(String[] args) {
    
level level1 = new level();

    // Definir l'etat initial du jeu de taquin
    int[][] statueInitial = {
        {-1, 1, 2},
        {3, 5, 7},
        {6, 4, 0}
    };

    // Resoudre le jeu de taquin
    level1.solve(statueInitial);
}



}