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

    public Game(int nbLevel, ShuffleType type,Player player) {
        try {
            if (nbLevel >= 1 && nbLevel <= 10) {
                String filePath = "./data/levels/level_" + nbLevel + ".csv";
                File fileLevel = new File(filePath);
                this.grid = new Grid(fileLevel);
                this.level = nbLevel;
                this.nbStrokes = 0;
                this.score = 0;
                this.player=player;

                switch (type) {
                    case StepByStep:
                        this.grid.stepByStepShuffle();
                        this.isSolvable = true;
                        break;
                    case Random:
                    	
                        this.grid.randomShuffled();
                        
                        this.isSolvable = this.grid.isSolvable();//A changer
                        break;

                    default:
                        this.grid.stepByStepShuffle();
                        this.isSolvable = true;
                        break;
                }

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
    
    public void setIsSolvable(boolean solvable) {
    	this.isSolvable=solvable;
    }
    
    public void setType(ShuffleType type) {
    	this.type=type;
    	switch (type) {
        case StepByStep:
            this.grid.stepByStepShuffle();
            this.isSolvable = true;
            break;
        case Random:
        	
            this.grid.randomShuffled();
            
            this.isSolvable = this.grid.isSolvable();//A changer
            break;

        default:
            break;
    }
    	
    }
    
    public ShuffleType getType() {
    	return(this.type);
    }
    
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
    public void setScore(int score) {
    	this.score=score;
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
    		if (this.level == this.player.getLevelMax()){
    			this.player.incLevelMax();
    		}
    		return true;
    	}
    	return false;
    }

    public void print() {
        this.grid.print();
    }

}