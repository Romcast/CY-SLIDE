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
    int  i1, i2, j1, j2;
    int shuffleChoice=-1;
    int choice =-1;
    ShuffleType type;

    while (choice < 1 || choice > 10) {
            System.out.println("A quel niveau voulez-vous jouer ?");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } 
            else {
                System.out.println("\nLe choix du niveau est invalide. Veuillez entrer un entier entre 1 et 10\n");
                scanner.next(); // Lire et ignorer l'entrée non valide
            }
            if(choice < 1 || choice > 10){
                System.out.println("\nLe choix du niveau est invalide. Veuillez entrer un entier entre 1 et 10\n");
            }
        }

    
    while (shuffleChoice != 0 && shuffleChoice != 1) {
        System.out.println("\nQuel type de mélange voulez-vous : 0 = StepByStep, 1 = Random\n");

            if (scanner.hasNextInt()) {
                shuffleChoice = scanner.nextInt();

                switch (shuffleChoice) {
                    case 0:
                        type = ShuffleType.StepByStep;
                        break;
                    case 1:
                        type = ShuffleType.Random;
                        break;
                    default:
                        System.out.println("Le choix doit être un entier (0 ou 1). Veuillez réessayer.");
                        break;
                }
            } else {
                System.out.println("Le choix doit être un entier (0 ou 1). Veuillez réessayer.");
                scanner.next(); // Lire et ignorer l'entrée non valide
            }
}
   
    
    Game game = new Game(choice,ShuffleType.StepByStep);


while (!game.getGrid().gameOver()) {
    game.print();
    System.out.println("Quelle case voulez-vous modifier ?");
    
    if (scanner.hasNextInt()) {
        i1 = scanner.nextInt();

        if (scanner.hasNextInt()) {
            j1 = scanner.nextInt();

            if (game.getGrid().nbPossibleMove(i1, j1).size() > 0) {
                

                if (game.getGrid().nbPossibleMove(i1, j1).size() == 1) {
                    i2 = game.getGrid().nbPossibleMove(i1, j1).get(0)[0] + i1;
                    j2 = game.getGrid().nbPossibleMove(i1, j1).get(0)[1] + j1;
                } else {
                    System.out.println("Il y a plusieurs destinations possibles, vers laquelle voulez-vous aller ?");
                    
                    if (scanner.hasNextInt()) {
                        i2 = scanner.nextInt();
                        
                        if (scanner.hasNextInt()) {
                            j2 = scanner.nextInt();
                        } else {
                            System.out.println("Destination invalide. Veuillez saisir des entiers pour les coordonnées.");
                            scanner.next(); // Lire et ignorer l'entrée non valide
                            continue;
                        }
                    } else {
                        System.out.println("Destination invalide. Veuillez saisir des entiers pour les coordonnées.");
                        scanner.next(); // Lire et ignorer l'entrée non valide
                        continue;
                    }
                }

                try {
                    if (!game.getGrid().moveCell(game.getGrid().getGrid()[i1][j1], game.getGrid().getGrid()[i2][j2])) {
                        System.out.println("La modification est invalide");
                    }
                } catch (Exception e) {
                    System.out.println("Erreur lors de la modification des cases : " + e.getMessage());
                }
            } else {
                i2 = i1;
                j2 = j1;
                System.out.println("La case ne peut pas être déplacé ");
            }
        } else {
            System.out.println("Coordonnées invalides. Veuillez saisir des entiers pour les coordonnées.");
            scanner.next(); // Lire et ignorer l'entrée non valide
            continue;
        }
    } else {
        System.out.println("Coordonnées invalides. Veuillez saisir des entiers pour les coordonnées.");
        scanner.next(); // Lire et ignorer l'entrée non valide
        continue;
    }
}

scanner.close();

}
}