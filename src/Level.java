import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Level extends InitialLevel 
{
	private int score;
	private Player player;
	public Grid grid;
	public Grid shuffledGrid;
	
	public Level(InitialLevel initialLevel, Player player) {
		this.goal=initialLevel.goal;
		this.grid = initialLevel.goal;
		this.levelNumber = initialLevel.levelNumber;
		this.player = player;
		this.score = 0;
	}

	public Grid shuffle(Grid baseGrid) {
		Grid shuffledGrid = baseGrid.copyGrid();
		Random random = new Random();
	
		while (!wellShuffled(baseGrid, shuffledGrid)) {
			int[][] grid = shuffledGrid.getGrid();
			int nbLine = shuffledGrid.getNbLine();
			int nbColumn = shuffledGrid.getNbColumn();
	
			List<int[]> emptyPositions = new ArrayList<>();
	
			for (int i = 0; i < nbLine; i++) {
				for (int j = 0; j < nbColumn; j++) {
					if (grid[i][j] == 0) {
						emptyPositions.add(new int[]{i, j});
					}
				}
			}
	
			for (int[] emptyPos : emptyPositions) {
				int emptyI = emptyPos[0];
				int emptyJ = emptyPos[1];
	
				List<int[]> validMoves = new ArrayList<>();
	
				// Check adjacent cells
				int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
				for (int[] move : moves) {
					int neighborI = emptyI + move[0];
					int neighborJ = emptyJ + move[1];
	
					if (shuffledGrid.isValidated(neighborI, neighborJ) && grid[neighborI][neighborJ] > 0) {
						validMoves.add(move);
					}
				}
	
				if (!validMoves.isEmpty()) {
					int[] randomMove = validMoves.get(random.nextInt(validMoves.size()));
					shuffledGrid.moveFrame(randomMove, emptyI, emptyJ);
				}
			}
		}
	
		return shuffledGrid;
	}
	public Grid randomShuffle(Grid grid) {
        	int[][] originalGrid = grid.getGrid();
        	int nbrow = grid.getNbLine();
        	int nbColumn = grid.getNbColumn();

        	List<Integer> values = new ArrayList<>();
        	for (int i = 0; i < nbrow; i++) {
            	for (int j = 0; j < nbColumn; j++) {
                	if (originalGrid[i][j] > 0) {
                    	values.add(originalGrid[i][j]);
                	}
            	}
       	 }

        	Collections.shuffle(values);

        	int index = 0;
        	int[][] shuffled = new int[nbrow][nbColumn];
        	for (int i = 0; i < nbrow; i++) {
            	for (int j = 0; j < nbColumn; j++) {
                	if (originalGrid[i][j] > 0) {
                    	shuffled[i][j] = values.get(index++);
                	} else {
                    	shuffled[i][j] = originalGrid[i][j];
                	}	
            	}
        	}

        	return new Grid(shuffled);
    	}
	public boolean wellShuffled(Grid baseGrid, Grid grid) {
		int[][] base = baseGrid.getGrid();
		int[][] compare = grid.getGrid();
	
		int nbrow = baseGrid.getNbLine();
		int nbColumn = baseGrid.getNbColumn();
	
		for (int i = 0; i < nbrow; i++) {
			for (int j = 0; j < nbColumn; j++) {
				if (base[i][j] > 0 && base[i][j] == compare[i][j]) {
					return false;
				}
			}
		}
	
		return true;
	}
	
	
