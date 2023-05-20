import java.io.*;
import java.util.*;

public class Player implements Serializable {
	static final long serialVersionUID = 1;
	private String pseudo;
	private int levelMax;
	private int[] maxScore[10]
	private ArrayList<Level> playerLevels;
	
	public Player(String pseudo) {
		this.pseudo = pseudo;
		this.levelMax = 1;
		this.playerLevels = new ArrayList<Level>();
	}
	public int getLevelMax() {
		return this.levelMax;
	}
	public String getPseudo() {
		return this.pseudo;
	}
	
	public void createLevel(ArrayList<InitialLevel> initialLevelArrayList) {
		InitialLevel initialLevel = new InitialLevel();
		for(InitialLevel l : initialLevelArrayList) {
			if (l.getNumber() == this.levelMax) {
				initialLevel = l;
				break;
			}
			
		}
		Level newLevel = new Level(initialLevel,this);
		this.playerLevels.add(newLevel);
	}
}
