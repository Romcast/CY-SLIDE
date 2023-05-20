import java.util.Scanner;
import java.io.File;


public class Player{

private String pseudo;
private int levelMax;
//listescore
public Player(String pseudo){
    this.pseudo=pseudo;
    this.levelMax=1;

    

}
public void Play()
{
    Scanner scanner = new Scanner(System.in);
    int choice, i1, i2, j1, j2;
    System.out.println("A quel niveau voulez-vous jouer ? (entre 1 et 10)");
    choice = scanner.nextInt();

    Game game = new Game(choice,ShuffleType.StepByStep);
            
    
    int i=0;
    while(i<10)
    {
        i++;
        game.print();
        System.out.println("Quelles cases voulez-vous modifier ?");
        i1 = scanner.nextInt();
        j1 = scanner.nextInt();
        i2 = scanner.nextInt();
        j2 = scanner.nextInt();
                
        try {
                if(!game.getGrid().moveCell(game.getGrid().getGrid()[i1][j1], game.getGrid().getGrid()[i2][j2])){
                    System.out.println("La modification est invalide");
                    };
                    
            } catch (Exception e) 
            {
                    System.out.println("Erreur lors de la modification des cases : " + e.getMessage());
            }
                
                
    }        
            
    scanner.close();


}
    
}