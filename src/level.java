import java.util.*;

public class Level extends InitialLevel 
{
	private int score;
	private Player player;
	public Frame[][] grid;
	public Frame[][] shuffledGrid;
	
	public Level(Frame[][] grid) {
		this.grid=grid;
		//this.shuffledGrid=shuffledGrid;
	}

	public void shuffle() {}
	public void randomShuffle() {}
	public boolean wellShuffled() {return true;}
	
	/*public boolean accessible() {
		if(this.player.getLevelMax()==this.getNumber()) {
			return true;
		}
		else {
			return false;
		}
	}*/
	
	//public frame[][] moveFrame(frame frame, int direction ) {}
	//public frame[][] initialized() {}



	// Algo de Résolution (Je traduirais mes commentaire ultérieurement)


	public boolean isSolvable(int[][] statue) 
	{
    	int reversalCount = 0;
        int[] flattenedState = new int[statue.length * statue[0].length];
        int k = 0;
        for (int i = 0; i < statue.length; i++) {
            for (int j = 0; j < statue[0].length; j++) {
                flattenedState[k++] = statue[i][j];
            }
        }
        for (int i = 0; i < flattenedState.length - 1; i++) 
		{
            for (int j = i + 1; j < flattenedState.length; j++) 
			{
                if (flattenedState[i] > 0 && flattenedState[j] > 0 && flattenedState[i] > flattenedState[j]) 
				{
                    reversalCount++;
                }
            }
        }
        if (statue.length % 2 == 1) 
		{
            return reversalCount % 2 == 0;
        } else 
		{
            int blankRow = getBlankRow(statue);
            return (blankRow + reversalCount) % 2 == 1;
        }
    }

    private int getBlankRow(int[][] statue) // ajouter si trop ou pas assez de case vide !!!!!!!!!!!!!!
	{
        for (int i = 0; i < statue.length; i++) 
		{
            for (int j = 0; j < statue[0].length; j++) 
			{
                if (statue[i][j] == 0) 
				{
                    return i;
                }
            }
        }
        return -1; // La case vide n'a pas ete trouve
    
	}





	// juste un exemple de taquin pour tester je modifirais ultérieurement
    private final int[][] goal = {{-1, 1, 2}, {3, 4, 5}, {6, 7, 0}};
    private final int[][] move = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
    public void solve(int[][] initial) 
	{
        int depthMax = 0;
        if (!isSolvable(initial))
		{
            System.out.println("No solution :( ");

        }
        else 
		{
        while (true) {// A CHANGER 
            Set<String> visited = new HashSet<>(); // Ensemble pour stocker les statues dejà visite*s
            
            Node n = new Node(initial, 0, null);
            Deque<Node> pile = new ArrayDeque<>(); // Pile pour la recherche en profondeur
            
            pile.push(n); // Ajouter le statue initial à la pile
            visited.add(Arrays.deepToString(initial)); // Ajouter l'statue initial aux statues visited
            
            while (!pile.isEmpty()) {
                Node node = pile.pop(); // Recuperer le nœud en haut de la pile
                
                if (isSolved(node)) {
                    printSolution(node);
                    return;
                }
                
                if (node.cost < depthMax) { // Limite la profondeur de recherche actuelle
                    int[] positionEmpty = findpositionEmpty(node);
                    
                    for (int[] shifting : move) {
                        int newX = positionEmpty[0] + shifting[0];
                        int newY = positionEmpty[1] + shifting[1];
                        
                        if (isValidated(newX, newY, initial)) {
                            int[][] newStatue = clonerstatue(node.statue);
                            exchangeCases(newStatue, positionEmpty[0], positionEmpty[1], newX, newY);
                            
                            String statueString = Arrays.deepToString(newStatue);
                            if (!visited.contains(statueString)) {
                                Node newNode = new Node(newStatue, node.cost + 1, node);
                                pile.push(newNode);
                                visited.add(statueString);
                            }
                        }
                    }
                }
            }
            
            depthMax++; // Augmenter la profondeur de recherche pour la prochaine iteration
        	}
        }
    }

    private void printSolution(Node node) 
	{
        List<Node> path = new ArrayList<>();
        Node current = node;
        while (current != null) 
		{
            path.add(current);
            current = current.parent;
        }
        
        Collections.reverse(path);
        
        for (Node n : path) 
		{
            printStatue(n.statue);
            System.out.println();
        }
    }

    private boolean isSolved(Node node) 
	{
        return Arrays.deepEquals(node.statue, goal);
    }


    private int[][] clonerstatue(int[][] statue) 
	{
        int[][] newStatue = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newStatue[i][j] = statue[i][j];
            }
        }

        return newStatue;
    }

    private void exchangeCases(int[][] statue, int x1, int y1, int x2, int y2) 
	{
        int temp = statue[x1][y1];
        statue[x1][y1] = statue[x2][y2];
        statue[x2][y2] = temp;
    }

    private int[] findpositionEmpty(Node node) 
	{
        int[] position = new int[2];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (node.statue[i][j] == 0) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }

        return position;
    }
    private boolean isValidated(int x, int y, int[][] initial) 
	{
        
        return x >= 0 && x < 3 && y >= 0 && y < 3; //>=0 signifie que les cases existe
    }
    private void printStatue(int[][] statue) 
	{
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(statue[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public void initializeVoidNeighbour(Frame[][]grid) {//count the number of void neighbours of each frame 
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid[i].length; j++) {
				if (grid[i][j-1].exists() && grid[i][j-1].isVoid()) {
					grid[i][j].changeVoidNeighbour(1);
				}
				if(grid[i][j+1].exists() && grid[i][j+1].isVoid()){
					grid[i][j].changeVoidNeighbour(1);
				}
				if(grid[i+1][j].exists() && grid[i+1][j].isVoid()) {
					grid[i][j].changeVoidNeighbour(1);
				}
				if(grid[i-1][j].exists() && grid[i-1][j].isVoid()) {
					grid[i][j].changeVoidNeighbour(1);
				}
			}
		}
	}
	
	public void moveFrame(Frame frame, int direction ) {
		//1: left; 2:up; 3:right; 4: down
		int nbLine=grid.length;
		int nbColumn=grid[0].length;
		
		if (frame.getVoidNeighbour()>=1 && frame.getValue()!=-2) {
			switch(direction) {
				case 1://move left
					if ( frame.getColumn()-1>=0 && grid[frame.getLine()][frame.getColumn()-1].exists() && grid[frame.getLine()][frame.getColumn()-1].isVoid()) {  //verify if the left frame is void
						Frame voidFrame=grid[frame.getLine()][frame.getColumn()-1];
						grid[frame.getLine()][frame.getColumn()-1]=frame;
						frame=voidFrame;
						frame.moveColumn(-1);
						grid[frame.getLine()][frame.getColumn()-1].moveColumn(1);// move the void frame to the right
						//change the number of void neighbours for concerned frames
						if(frame.getColumn()-1>=0 && grid[frame.getLine()][frame.getColumn()-1].exists()) {
							grid[frame.getLine()][frame.getColumn()-1].changeVoidNeighbour(-1);
						}
						if(frame.getLine()-1>=0 && grid[frame.getLine()-1][frame.getColumn()].exists()) {
							grid[frame.getLine()-1][frame.getColumn()].changeVoidNeighbour(-1);
						}
						
						if(frame.getColumn()+1<nbColumn && frame.getLine()-1>=0 && grid[frame.getLine()-1][frame.getColumn()+1].exists()) {
							grid[frame.getLine()-1][frame.getColumn()+1].changeVoidNeighbour(1);
						}
						if(frame.getColumn()+2<nbColumn && grid[frame.getLine()][frame.getColumn()+2].exists()) {
							grid[frame.getLine()][frame.getColumn()+2].changeVoidNeighbour(1);
						}
						if(frame.getColumn()+1<nbColumn && frame.getLine()+1<nbLine && grid[frame.getLine()+1][frame.getColumn()+1].exists()) {
							grid[frame.getLine()+1][frame.getColumn()+1].changeVoidNeighbour(1);
						}
						if(frame.getLine()+1<nbLine && grid[frame.getLine()+1][frame.getColumn()].exists()) {
							grid[frame.getLine()+1][frame.getColumn()].changeVoidNeighbour(-1);
						}		
					}
					
					else {
						System.out.println("error move");
					}
					break;
					
				
				case 2://move up
					if (frame.getLine()-1>=0 && grid[frame.getLine()-1][frame.getColumn()].exists() && grid[frame.getLine()-1][frame.getColumn()].isVoid()) {
						Frame voidFrame=grid[frame.getLine()-1][frame.getColumn()];
						grid[frame.getLine()-1][frame.getColumn()]=frame;
						frame=voidFrame;
						frame.moveLine(-1);
						voidFrame.moveLine(1);
						
						if(frame.getColumn()-1>=0 && grid[frame.getLine()][frame.getColumn()-1].exists()) {
							grid[frame.getLine()][frame.getColumn()-1].changeVoidNeighbour(-1);
						}
						if(frame.getLine()-1>=0 && grid[frame.getLine()-1][frame.getColumn()].exists()) {
							grid[frame.getLine()-1][frame.getColumn()].changeVoidNeighbour(-1);
						}
						if(frame.getColumn()+1<nbColumn && grid[frame.getLine()][frame.getColumn()+1].exists()) {
							grid[frame.getLine()][frame.getColumn()+1].changeVoidNeighbour(-1);
						}
						if(frame.getColumn()+1<nbColumn && frame.getLine()+1<nbLine && grid[frame.getLine()+1][frame.getColumn()+1].exists()) {
							grid[frame.getLine()+1][frame.getColumn()+1].changeVoidNeighbour(1);
						}
						if(frame.getLine()+2<nbLine && grid[frame.getLine()+2][frame.getColumn()].exists()) {
							grid[frame.getLine()+2][frame.getColumn()].changeVoidNeighbour(1);
						}
						if(frame.getColumn()-1>=0 && frame.getLine()+1<nbLine && grid[frame.getLine()+1][frame.getColumn()-1].exists()) {
							grid[frame.getLine()+1][frame.getColumn()-1].changeVoidNeighbour(1);
						}
					}
					else {
						System.out.println("error move");
					}
					break;
					
				case 3://move right
					if (frame.getColumn()+1<nbColumn && grid[frame.getLine()][frame.getColumn()+1].exists() && grid[frame.getLine()][frame.getColumn()+1].isVoid()) {
						Frame voidFrame=grid[frame.getLine()][frame.getColumn()+1];
						grid[frame.getLine()][frame.getColumn()+1]=frame;
						frame=voidFrame;
						frame.moveColumn(1);
						voidFrame.moveColumn(-1);
						if(frame.getColumn()+1<nbColumn && grid[frame.getLine()][frame.getColumn()+1].exists()) {
							grid[frame.getLine()][frame.getColumn()+1].changeVoidNeighbour(-1);
						}
						if(frame.getLine()+1<nbLine && grid[frame.getLine()+1][frame.getColumn()].exists()) {
							grid[frame.getLine()+1][frame.getColumn()].changeVoidNeighbour(-1);
						}
						
						if(frame.getLine()+1<nbLine && grid[frame.getLine()+1][frame.getColumn()-1].exists() && frame.getColumn()-1>=0) {
							grid[frame.getLine()+1][frame.getColumn()-1].changeVoidNeighbour(1);
						}
						if(frame.getColumn()-2>=0 && grid[frame.getLine()][frame.getColumn()-2].exists()) {
							grid[frame.getLine()][frame.getColumn()-2].changeVoidNeighbour(1);
						}
						if(frame.getColumn()-1>=0 && frame.getLine()-1>=0 && grid[frame.getLine()-1][frame.getColumn()-1].exists()) {
							grid[frame.getLine()-1][frame.getColumn()-1].changeVoidNeighbour(1);
						}
						if(frame.getLine()-1>=0 && grid[frame.getLine()-1][frame.getColumn()].exists()) {
							grid[frame.getLine()-1][frame.getColumn()].changeVoidNeighbour(-1);
						}
					}
					else {
						System.out.println("error move");
					}
					break;
					
				case 4://move down
					if (frame.getLine()+1<nbLine && grid[frame.getLine()+1][frame.getColumn()].exists() && grid[frame.getLine()+1][frame.getColumn()].isVoid()) {
						Frame voidFrame=grid[frame.getLine()+1][frame.getColumn()];
						grid[frame.getLine()+1][frame.getColumn()]=frame;
						frame=voidFrame;
						frame.moveLine(1);
						voidFrame.moveLine(-1);
						if(frame.getColumn()+1<nbColumn && grid[frame.getLine()][frame.getColumn()+1].exists()) {
							grid[frame.getLine()][frame.getColumn()+1].changeVoidNeighbour(-1);
						}
						if(frame.getLine()+1<nbLine && grid[frame.getLine()+1][frame.getColumn()].exists()) {
							grid[frame.getLine()+1][frame.getColumn()].changeVoidNeighbour(-1);
						}
						if( frame.getColumn()-1>=0 && grid[frame.getLine()][frame.getColumn()-1].exists()) {
							grid[frame.getLine()][frame.getColumn()-1].changeVoidNeighbour(-1);
						}
						if(frame.getColumn()-1>=0 && frame.getLine()-1>=0 && grid[frame.getLine()-1][frame.getColumn()-1].exists()) {
							grid[frame.getLine()-1][frame.getColumn()-1].changeVoidNeighbour(1);
						}
						if(frame.getLine()-2>=0 && grid[frame.getLine()-2][frame.getColumn()].exists()) {
							grid[frame.getLine()-2][frame.getColumn()].changeVoidNeighbour(1);
						}
						if(frame.getColumn()+1<nbColumn && frame.getLine()-1>=0 && grid[frame.getLine()-1][frame.getColumn()+1].exists()) {
							grid[frame.getLine()-1][frame.getColumn()+1].changeVoidNeighbour(1);
						}
						
					}
					else {
						System.out.println("error move");
					}
					break;
			}
		}
			
		
	}

}




