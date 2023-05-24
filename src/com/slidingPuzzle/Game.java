import java.io.File;

public class Game {

    private int nbStrokes;
    private int scoreMin;
    private int score;
    private int level;
    private boolean isSolvable;
    private ShuffleType type;
    private Grid grid;

    public Game(int nbLevel) {
        try {
            if (nbLevel >= 1 && nbLevel <= 10) {
                String filePath = "./data/levels/level_" + nbLevel + ".csv";
                File fileLevel = new File(filePath);
                this.grid = new Grid(fileLevel);
                this.level = nbLevel;
                this.nbStrokes = 0;
                this.score = 0;
                
            } else {
                System.out.println("Niveau invalide !");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
                
public void setType(ShuffleType Type) {
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



    public Grid getGrid() {
        return this.grid;
    }
    public boolean getIsSolvable() {
    	return this.isSolvable;
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
