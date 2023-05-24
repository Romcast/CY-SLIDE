import java.io.File;
import java.io.Serializable;

public class Game implements Serializable {
	private static final long serialVersionUID = 2;
	private Player player;
    private int nbStrokes;
    private int scoreMin;
    private int score;
    private int level;
    private boolean isSolvable;
    private ShuffleType type;
    private Grid grid;

<<<<<<< HEAD
    public Game(int nbLevel) {
=======
    public Game(int nbLevel, ShuffleType type,Player player) {
>>>>>>> 131211ac8e90c725a86e36f1c8fa81ec32fafdda
        try {
            if (nbLevel >= 1 && nbLevel <= 10) {
                String filePath = "./data/levels/level_" + nbLevel + ".csv";
                File fileLevel = new File(filePath);
                this.grid = new Grid(fileLevel);
                this.level = nbLevel;
                this.nbStrokes = 0;
                this.score = 0;
<<<<<<< HEAD
                
            } else {
                System.out.println("Niveau invalide !");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
                
public void setType(ShuffleType Type) {
=======
                this.player=player;

>>>>>>> 131211ac8e90c725a86e36f1c8fa81ec32fafdda
                switch (type) {
                    case StepByStep:
                        this.grid.stepByStepShuffle();
                        this.isSolvable = true;
                        break;
                    case Random:
                    	
                        this.grid.randomShuffled();
                        this.isSolvable = this.grid.isSolvable();
                        break;

                    default:
                        this.grid.stepByStepShuffle();
                        this.isSolvable = true;
                        break;
                }
}


<<<<<<< HEAD
=======
            } else {
                System.out.println("Niveau invalide !");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public Game(int nbLevel,Player player) {
        try {
            if (nbLevel >= 1 && nbLevel <= 10) {
                String filePath = "./data/levels/level_" + nbLevel + ".csv";
                File fileLevel = new File(filePath);
                this.grid = new Grid(fileLevel);
                this.level = nbLevel;
                this.nbStrokes = 0;
                this.score = 0;
                this.player = player;


            } else {
                System.out.println("Niveau invalide !");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
>>>>>>> 131211ac8e90c725a86e36f1c8fa81ec32fafdda

    public Grid getGrid() {
        return this.grid;
    }
    public boolean getIsSolvable() {
    	return this.isSolvable;
    }
    
    public int getLevel() {
    	return this.level;
    }
    
    public int getScore() {
    	return this.score;
    }
    
    public boolean moveCell(Cell C1, Cell C2) {
    	if (this.grid.moveCell(C1, C2)) {
    		this.score++;
    		return true;
    	}
    	return false;
    }
    
    public boolean gameOver() {
    	if (this.grid.gameOver()) {
    		this.player.incLevelMax();
    		return true;
    	}
    	return false;
    }

    public void print() {
        this.grid.print();
    }
public boolean moveCell(Cell C1, Cell C2) {
	
	if(this.grid.moveCell(C1,C2)) {
		this.score++;
		return true;
	}
	return false;
	
}
}
