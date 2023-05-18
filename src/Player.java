import java.io.*;
import java.util.*;

public class Player implements Serializable {
	static final long serialVersionUID = 1;
	private String pseudo;
	private int levelMax;
	private ArrayList<Level> playerLevels;
	
	public Player(String pseudo) {
		this.pseudo = pseudo;
	}
	public void play(Level level) {}
	//public level createLevel(level level) {}
	public int getLevelMax() {
		return this.levelMax;
	}
	public String getPseudo() {
		return this.pseudo;
	}
}
