import java.util.*;

import java.io.*;


public class Player implements Serializable
{
private static final long serialVersionUID = 1;
private String pseudo;
private int levelMax;
private Game[] gameArray;  // curentGame, if player want continue after 
//listescore
private Integer[] bestScores;
public Player(String pseudo){
    this.pseudo=pseudo;
    this.levelMax=1;
    this.gameArray = new Game[10];
    this.bestScores = new Integer[10];
    for (int i=0;i<10;i++){
    	this.gameArray[i]=null;
    	this.bestScores[i]=null;
    	}
}

public int getLevelMax() {return this.levelMax;}
public void incLevelMax() {
	this.levelMax++;
}
public String getPseudo() {return this.pseudo;}
public Game[] getGameArray() {return this.gameArray;}
public Integer[] getBestScores() {return this.bestScores;}

public void setBestScores(int i, int score) {
	this.bestScores[i] = score;
}


public void Play()
{
    Scanner scanner = new Scanner(System.in);
    int  i1, i2, j1, j2;
    int shuffleChoice=-1;
    int choice =-1;
    ShuffleType type = ShuffleType.StepByStep;

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
   
    Game game = new Game(choice,type,this);
    //System.out.println(game.getIsSolvable());

while (!game.getGrid().gameOver()) {
	
    game.print();
  System.out.println("Quelle case voulez-vous modifier ? Taper 'Help' pour résoudre");
    String input = scanner.next();
    if (input.equalsIgnoreCase("Help")) {
        ArrayList<Grid> solution = game.getGrid().solved(game.getGrid());
        if (solution != null) {
            for (Grid grid : solution) {
                grid.print();
            }
        } else {
            System.out.println("No solution found.");
        }
        break; 
    }
    
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


}}


