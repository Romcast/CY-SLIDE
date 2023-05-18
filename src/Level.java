import java.util.*;

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

	public void shuffle() {}
	public void randomShuffle() {}
	public boolean wellShuffled() {return true;
	}
	}
	
	