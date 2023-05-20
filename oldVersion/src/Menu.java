import java.io.*;
import java.util.*;

public class Menu {
	private ArrayList<Player> playerArrayList;
	private ArrayList<InitialLevel> initialLevelArrayList;
	private Player currentPlayer;
	private Level currentLevel;
	private Scanner scan;
	
	public Menu() throws IOException, ClassNotFoundException{
		this.readPlayerFile();
		scan = new Scanner(System.in); 
	}
	
	public void scanClose() {
		scan.close();
	}
	
	public ArrayList<Player> getPlayerArrayList(){
		return this.playerArrayList;
	}
	
	public ArrayList<InitialLevel> getInitialLevelArrayList(){
		return this.initialLevelArrayList;
	}
	
	public void createPlayer() throws IOException, ClassNotFoundException{
		boolean verifyName = false;
    	String newPseudo = ""; // the pseudo used to create a player
    	while (!verifyName) {  // gets a new tempPseudo while it is not a unique pseudo
    		verifyName = true;
    		System.out.println("Enter you" + "r Pseudo : "); 
    		String tempPseudo = scan.next();
    		for(Player p : playerArrayList) {           // verifies if the pseudo is not already used for another player in the playerArrayList
    			if (tempPseudo.equals(p.getPseudo())) {
    				verifyName = false;
    				break;
    			}
    		}
    		if (!verifyName) {
    			System.out.println("Pseudo already used");
			}
    		else {
    			newPseudo = tempPseudo;
    		}
    	}
    	Player newPlayer = new Player(newPseudo);
    	playerArrayList.add(newPlayer);
    	
    	FileOutputStream fos = new FileOutputStream("data/Player.txt"); 
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(playerArrayList); // modifies the file to add the modified arrayList of players
		oos.close();
		System.out.println("Player " + newPseudo + " added");
	}
	
	public void removePlayer(int index) throws IOException, ClassNotFoundException{
		String pseudo = this.playerArrayList.get(index).getPseudo();
		this.playerArrayList.remove(index);
		FileOutputStream fos = new FileOutputStream("data/Player.txt"); 
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(playerArrayList); // modifies the file to add the modified arrayList of players
		oos.close();
		System.out.println("Player " + pseudo + " removed");
		
	}
	
	public void readPlayerFile() throws IOException, ClassNotFoundException {
		try {																// verifies if the file exists and is not empty
			FileInputStream fis = new FileInputStream("data/Player.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			ois.close();
		}
		catch (EOFException |FileNotFoundException e) {						// if not it creates it with an empty arrayList serialized
			ArrayList<Player> playerEmptyArrayList = new ArrayList<Player>();
			FileOutputStream fos = new FileOutputStream("data/Player.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(playerEmptyArrayList);
		}
		
		FileInputStream fis = new FileInputStream("data/Player.txt"); 
		ObjectInputStream ois = new ObjectInputStream(fis);
		this.playerArrayList = (ArrayList<Player>) ois.readObject(); // sets the arrayList of players
		ois.close();
	}
	
	
	public void menuPlayerChoice() throws IOException, ClassNotFoundException{
	    System.out.println("Create player (0) / Choose player (index+1)/ Remove player (-(index+1)) size : " + playerArrayList.size()); // choice between create a new player or using an already existing player with its index in playerArrayList
	    int playerChoice = scan.nextInt();  
	    
	    if (playerChoice == 0) {    // if the choice 'create a new player is selected' 
	    	this.createPlayer();
	    	this.menuPlayerChoice();
	    	
	    } 
	    
	    else {
	    	if(playerChoice > 0) {
	    		Player player = playerArrayList.get(playerChoice-1); // chooses a player with its index in the playerArrayList
	    		System.out.println("Bonjour " + player.getPseudo());
	    	}
	    	else {
	    		this.removePlayer(-(playerChoice)-1);
	    		this.menuPlayerChoice();
	    	}
	    }
	    
	    
		
		
	}

}
