import java.io.File;
import java.io.Serializable;

/**
 * This class permit to implement a game session per player
 * @author CYTech Student
 *
 */
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
/**
 * This Constructor permit to set the game session with the number of the level played the shuffle type chose by the player and the player
 * @param nbLevel
 * @param type
 * @param player
 */
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
    /**
     * This constructor permit to create a game without the type of shuffle
     * @param nbLevel
     * @param player
     */
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
    /**
     * this setter permit to set if the game session is solvable
     * @param solvable
     */
    public void setIsSolvable(boolean solvable) {
    	this.isSolvable=solvable;
    }
    /** 
     * this setter permit to set the type of shuffle chosen by the player
     * @param type
     */
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
    /**
     * this getter permit to get the type of the shuffle chosen by the player
     * @return
     */
    
    public ShuffleType getType() {
    	return(this.type);
    }
    /**
     * this getter permit to get the grid in which their is the current grid shuffled and changed by the player and the shuffled grid
     * @return
     */
    public Grid getGrid() {
        return this.grid;
    }
    /**
     * this getter permit to get if the grid is solvable or not
     * @return
     */
    public boolean getIsSolvable() {
    	return this.isSolvable;
    }
    /**
     * this getter permit to get the level of the game
     * @return
     */
    public int getLevel() {
    	return this.level;
    }
    /**
     * this getter permit to get the score of the game session so how many move the player has made
     * @return
     */
    public int getScore() {
    	return this.score;
    }
    /**
     * This setter permit to set the score of the game
     * @param score
     */
    public void setScore(int score) {
    	this.score=score;
    }
    /**
     * This method permit to increase the score which is the number of move of the player in the game session every time he make a move
     * @param C1
     * @param C2
     * @return
     */
    public boolean moveCell(Cell C1, Cell C2) {
    	if (this.grid.moveCell(C1, C2)) {
    		this.score++;
    		return true;
    	}
    	return false;
    }
    /**
     * this method permit to increase the level of the player once the game is over
     * @return
     */
    public boolean gameOver() {
    	if (this.grid.gameOver()) {
    		if (this.level == this.player.getLevelMax()){
    			this.player.incLevelMax();
    		}
    		return true;
    	}
    	return false;
    }
    /**
     * this method permit to print the grid in the console
     */
    public void print() {
        this.grid.print();
    }

}
