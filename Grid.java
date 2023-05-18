import java.util.ArrayList;
import java.util.*;

public class Grid {
private final int[][] move = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

public int[][] grid;
private int nbLine;
private int nbColumn;

int i,j;

public int getNbLine(){return this.nbLine;}
public int getNbColumn(){return this.nbColumn;}
public int[][] getGrid(){return this.grid;}

public Grid(int nbLine, int nbColumn) 
{
        int value = 1;
        this.nbLine=nbLine;
        this.nbColumn=nbColumn;
        this.grid = new int[nbLine][nbColumn];

        for(i=0;i<nbLine;i++)
        {
            for(j=0;j<nbColumn;j++)
            {
                this.grid[i][j]= value;
                value++;
            }
        }
        this.grid[nbLine-1][nbColumn-1]= 0;
}

public void print() // Displays a grid
{
    for (int i = 0; i < nbLine ; i++) 
    {
            for (int j = 0; j < nbColumn; j++) 
            {
                System.out.print(this.grid[i][j] + " ");
            }
            System.out.println();
    }
    System.out.println();    
        
}

public boolean moveFrame(int[] direction,int i, int j) 
/*

This method moves a frame of the grid towards a direction while checking if that is possible
  
parametres : 
            - direction : A direction table of the form {-1,0} to determine in which direction the frame should be moved
            - i and j : Respectively the line and the column of the frame which must be moved
return :    
            true  : The frame has been moved
            false : The frame has not been moved (because it can't)

*/
{ 

    if(direction.length != 2)
    {
        System.out.println("Il y a un pb ds move la taille direction pas bonne"); // vérifier aussi si direction c bien {-1,0} ou {1,0} ou {0,-1} ou {0,1}
    }
    int tmp;
    int nextI = i+direction[0];
    int nextJ = j+direction[1];

    if (!(this.isValidated(nextI,nextJ))) {return false;}
    if (this.grid[nextI][nextJ]!=0 || this.grid[i][j]<=0 ) {return false;} // si la case elle est pas vide ou si la case sélectionne est vide ou -1

    this.exchangeFrame(nextI,nextJ,i,j);
    return true;

}

public boolean isValidated(int i, int j) 
/*

This method makes it possible to determine if the coordinates i and j are in the grid's play, that is to say that they are not outside the table or that they do not point to -1
  
parametres : 
            i and j : The coordinates to check
return :    
            true  : Coordinates to check are valide (in grid's game)
            false : coordinates to check are not valide (out grid's game)

*/
{
        if(i >= 0 && i < this.nbLine && j >= 0 && j < this.nbColumn) 
        {
            return (this.grid[i][j]>=0); // != -1 car sinon c'est une bordure
        }

        return false;
}

public void exchangeFrame(int i1, int j1, int i2, int j2) 
/*

This method swaps two frames
  
parametres : 
            - i1 and j1 : Coordinates of first Frame
            - i2 and j2 : Coordinates of second Frame

*/
{
    int tmp = this.grid[i1][j1];
    this.grid[i1][j1] = this.grid[i2][j2];
    this.grid[i2][j2] = tmp;// c forcement 0
}

public boolean isSolvable() 

/*

This method checks if a grid is solvable
  
return :    
            true  : It's solvable
            false : It's not solvable

*/
{
	int reversalCount = 0;
    int[] flattenedState = new int[this.nbLine * this.nbColumn];
    int k = 0;
    for (int i = 0; i < this.nbLine; i++) {
        for (int j = 0; j < this.nbColumn; j++) {
            flattenedState[k++] = this.grid[i][j];
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
    if (this.nbColumn % 2 == 1) 
	{
        return reversalCount % 2 == 0;
    } else 
	{
        int positionEmpty[] = this.findpositionEmpty();
        return (positionEmpty[0] + reversalCount) % 2 == 1;
    }
}

public int[] findpositionEmpty() // ajouter si trop ou pas assez de case vide !!!!!!!!!!!!!!!!!!!!!!!!!

/*

This method for fine void frame
  
return :    
            [i,j]  : Respectively the line and the column of void frame
            [-1,-1] : Void frame was not found

*/

{
    int position[] = new int[2];
    for (i = 0; i < this.nbLine; i++) 
	{
        for (j = 0; j < this.nbColumn; j++) 
		{
            if (this.grid[i][j] == 0) 
			{
                position[0]=i;
                position[1]=j;
                return (position);
            }
        }
    }
    position[0]=-1;
    position[1]=-1;
    return (position); // La case vide n'a pas ete trouve
    
}

public boolean solve(Grid goal) 

/*

This method find the best solution of one grid   A modfifier pour sauvegarder le path
  
parametres : 
            goal : The final grid, the result or in other words the solution

return :    
            true  : Solution find
            false : Solution no find

*/
{

    Grid positionInitial = this;
    int depthMax = 0;
    if (!positionInitial.isSolvable())
	{
        return false;
    }
    else 
	{
    while (true) 
    {
        positionInitial = this;
        Set<String> visited = new HashSet<>(); // Ensemble pour stocker les statues dejà visites
        Node n = new Node(positionInitial, 0, null);
        Deque<Node> pile = new ArrayDeque<>(); // Pile pour la recherche en profondeur
            
        pile.push(n); // Ajouter le statue initial à la pile
        visited.add(Arrays.deepToString(positionInitial.grid)); // Ajouter le statue initial aux statues visited
            
        while (!pile.isEmpty()) {
            Node node = pile.pop(); // Recuperer le nœud en haut de la pile
                
            if (node.isSolved(goal)) {
                node.print();
                return true;
            }
                
            if (node.getCost() < depthMax) { // Limite la profondeur de recherche actuelle
                int[] positionEmpty = node.findpositionEmpty();
                
                for (int[] shifting : move) {
                    int newX = positionEmpty[0] + shifting[0];
                    int newY = positionEmpty[1] + shifting[1];
                    
                    if (positionInitial.isValidated(newX, newY)) {
                            Grid newStatue = node.getStatue().copyGrid();
                            newStatue.exchangeFrame(positionEmpty[0], positionEmpty[1], newX, newY);
                            
                            String statueString = Arrays.deepToString(newStatue.grid);
                            if (!visited.contains(statueString)) {
                                Node newNode = new Node(newStatue, node.getCost() + 1, node);
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

    public Grid copyGrid() 
	{
        Grid newG = new Grid(this.nbLine,this.nbColumn);


        for (int i = 0; i < this.nbLine; i++) {
            for (int j = 0; j < this.nbColumn; j++) {
                newG.grid[i][j] = this.grid[i][j];
            }
        }
        newG.nbColumn=this.nbColumn;
        newG.nbLine=this.nbLine;

        return newG;
    }


}