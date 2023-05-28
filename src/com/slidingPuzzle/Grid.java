import java.io.*;
import java.util.*;


public class Grid implements Serializable{
	
private static final long serialVersionUID = 3;

public static final int[][] move = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

private int nbRows;
private int nbColumns;
private int nbLevel;
private Cell[][] grid;
private Grid goal;
private int h;
private int f;
int i,j;


public Grid() {
    nbRows = 0;
    nbColumns = 0;
    grid = null;
    goal = null;
}

public Grid(File file) 
{
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line = br.readLine();
        String[] dimensions = line.split(" ");
        int nbLevel = Integer.parseInt(dimensions[0]);
        int nbRows = Integer.parseInt(dimensions[1]);
        int nbColumns = Integer.parseInt(dimensions[2]);
        Cell[][] gridOfGoal = new Cell[nbRows][nbColumns];
        this.grid = new Cell[nbRows][nbColumns];
        this.goal = new Grid();
        this.nbRows = nbRows;
        this.nbColumns = nbColumns;
        this.nbLevel=nbLevel;

        for (i = 0; i < nbRows; i++) {
            line = br.readLine();
            String[] values = line.split(" ");
            for (j = 0; j < nbColumns; j++) 
            {
                switch(values[j])
                {
                    case ".": 
                        gridOfGoal[i][j] = new Cell(i,j,0,CellType.EmptyCell);
                        break;
                    case "/": 
                        gridOfGoal[i][j] = new Cell(i,j,0,CellType.UnexistantCell);
                        break;
                    default:
                        gridOfGoal[i][j] = new Cell(i,j,Integer.parseInt(values[j]),CellType.GameCell);
                        break;
                    }
            }
            }
        this.goal.goal=null;
        this.goal.nbRows=nbRows;
        this.goal.nbColumns=nbColumns;
        this.goal.grid = new Cell[nbRows][nbColumns];

        for (i = 0; i < nbRows; i++) {
        for (j = 0; j < nbColumns; j++) {
        this.goal.grid[i][j] = new Cell(i, j, gridOfGoal[i][j].getValue(), gridOfGoal[i][j].getType());
    }
    }

        for (i = 0; i < nbRows; i++) {
          for (j = 0; j < nbColumns; j++) {
        this.grid[i][j] = new Cell(i, j, gridOfGoal[i][j].getValue(), gridOfGoal[i][j].getType());
         }
    }



        }catch (IOException e) {
            System.out.println("The directory levels isn't on good place : ");
            e.printStackTrace();
            }
}

	public int getNbRows() {
	return this.nbRows;
}

public int getNbColumns() {
	return this.nbColumns;
}
//faire setter (pas de getter)
public Cell[][] getGrid(){return this.grid;}

public void print() // Displays a grid
{
    for (i = 0; i < nbRows ; i++) 
    {
            for (j = 0; j < nbColumns; j++) 
            {
                switch(this.grid[i][j].getType())
                    {
                    case EmptyCell: 
                        System.out.print(0 + " ");
                        break;
                    case UnexistantCell: 
                        System.out.print(-1 + " ");
                        break;
                    case GameCell: 
                        System.out.print(this.grid[i][j].getValue() + " ");
                        break;
                    default:
                        break;
                                    }
            }

            System.out.print("      ");
            if(this.goal != null){
            for (j = 0; j < nbColumns; j++) 
            {
                switch(this.goal.grid[i][j].getType())
                    {
                    case EmptyCell: 
                        System.out.print(0 + " ");
                        break;
                    case UnexistantCell: 
                        System.out.print(-1 + " ");
                        break;
                    case GameCell: 
                        System.out.print(this.goal.grid[i][j].getValue() + " ");
                        break;
                    default:
                        break;
                                    }
            }
            }
            System.out.println();
    }
    System.out.println();    
        
}

public Grid getGoal() {
	return this.goal;
}
	
public boolean isValidated(int i, int j) 
/*

This method makes it possible to determine if the coordinates i and j are in the grid's play, that is to say that they are not outside the table or that they don't point to UnExistantCell
  
parametres : 
            i and j : The coordinates to check
return :    
            true  : Coordinates to check are valide (in grid's game)
            false : coordinates to check are not valide (out grid's game)

*/
{
        if((i >= 0) && i < this.nbRows && j >= 0 && j < this.nbColumns) 
        {
            return ((this.grid[i][j].getType()!=CellType.UnexistantCell));
        }

        return false;
}



public void exchangeCells(Cell C1, Cell C2) 
/*

This method swaps two cells
  
parametres : 
            

*/
{
    Integer tmpValue = C1.getValue();
    CellType tmpType = C1.getType();
    int[] tmp = new int[2];
    
    C1.setType(C2.getType());
    C1.setValue(C2.getValue());
    
    C2.setType(tmpType);
    C2.setValue(tmpValue);
    
    tmp[0]=C1.getFinalPosition()[0];
    tmp[1]=C1.getFinalPosition()[1];
    
    C1.setFinalPosition(C2.getFinalPosition());
    C2.setFinalPosition(tmp);
    
    
    
    
   
    
    
    
}

public boolean areNeighbor(Cell C1,Cell C2){

    if (!this.isValidated(C2.getRow(),C2.getColumn())){return false;}
    for (i=0;i<4;i++)
    {
        if( C2.getRow()==move[i][0]+C1.getRow() &&  C2.getColumn()==move[i][1]+C1.getColumn()){return true;}
        
    }
    return false;
}

public boolean moveCell(Cell C1, Cell C2) 
/*

This method moves a cell of the grid towards a direction while checking if that is possible
  
parametres : 

return :    
            true  : The cell has been moved
            false : The cell has not been moved (because it can't)

*/
{ 

   
    if(!this.areNeighbor(C1,C2)){return false;}
    
    if((C1.getType()==CellType.EmptyCell && C2.getType()!=CellType.EmptyCell) || (C2.getType()==CellType.EmptyCell && C1.getType()!=CellType.EmptyCell) ){
        this.exchangeCells(C1,C2);
        
        return true;}
    return false;
    
    

}


public void stepByStepShuffle() 
	{
	
		
		Random random = new Random();
        int nbEmptyCells,randomEmptyCells,randomMove,iEmpty,jEmpty,iNext,jNext;
        ArrayList<Cell> listOfEmptyCells = new ArrayList<Cell>();
        int l = 0;
        
        for (int i =0; i<this.nbRows;i++) {
        	for (int j =0; j<this.nbColumns;j++) {
        		
        		this.grid[i][j]=this.goal.grid[i][j].copyCell();
        	}	
        }
		while (!(this.wellShuffled() && l>100)) {
            
			l++;
            listOfEmptyCells = this.listOfEmptyCells();
            nbEmptyCells = listOfEmptyCells.size(); 
            randomEmptyCells = random.nextInt(nbEmptyCells);
            randomMove = random.nextInt(4);
            iEmpty=listOfEmptyCells.get(randomEmptyCells).getRow();
            jEmpty=listOfEmptyCells.get(randomEmptyCells).getColumn();
            iNext=listOfEmptyCells.get(randomEmptyCells).getRow()+move[randomMove][0];
            jNext=listOfEmptyCells.get(randomEmptyCells).getColumn()+move[randomMove][1];
            if(this.isValidated(iNext,jNext)){
                if(this.moveCell(grid[iEmpty][jEmpty],grid[iNext][jNext])){
                listOfEmptyCells.set(randomEmptyCells, grid[iNext][jNext]);
            }
            
                
            }
            


		}
	
		
	}

 public void randomShuffled() 
 {
    
    Random random = new Random();
    ArrayList<Cell> cellsList = new ArrayList<>();
    ArrayList<Cell> cpCellsList = new ArrayList<>();
    for (int i =0; i<this.nbRows;i++) {
    	for (int j =0; j<this.nbColumns;j++) {
    		
    		this.grid[i][j]=this.goal.grid[i][j].copyCell();
    	}	
    }
    int randomIndex;
    Cell randomCell;
    // Collect all the non-UnexistantCell cells from the goal grid
    for (i = 0; i < this.nbRows; i++) {
        for (j = 0; j < this.nbColumns; j++) {
            if (this.goal.grid[i][j].getType() != CellType.UnexistantCell) {
                cellsList.add(this.goal.grid[i][j]);
            }
        }
    }
    
    // Shuffle the cellsList randomly
    while (!this.wellShuffled()) {
    cpCellsList.addAll(cellsList);
   
   
        for (i = 0; i < this.nbRows; i++) {
            for (j = 0; j < this.nbColumns; j++) {
                if (this.isValidated(i, j) && !cpCellsList.isEmpty()) {
                    randomIndex = random.nextInt(cpCellsList.size());
                    randomCell = cpCellsList.get(randomIndex);
                    this.grid[i][j].setType(randomCell.getType());
                    this.grid[i][j].setValue(randomCell.getValue());
                    this.grid[i][j].setRow(i);
                    this.grid[i][j].setColumn(j);
                    cpCellsList.remove(randomIndex);
                    
                }
            }
        }
    }


}


public boolean wellShuffled() {
	

        if(nbRows != goal.nbRows || nbColumns != goal.nbColumns) {
            System.out.println("Error");
            return false;}

		for (i = 0; i < nbRows; i++) 
        {
			for (j = 0; j < nbColumns; j++) 
            {
				if (this.goal.grid[i][j].getType() == CellType.GameCell && this.goal.grid[i][j].getValue() == this.grid[i][j].getValue()) {return false; }
			}
		}
        
		return true;
	}

public ArrayList<Cell> listOfEmptyCells() {
    ArrayList<Cell> listOfEmptyCells = new ArrayList<Cell>();

    for (int i = 0; i < nbRows; i++) {
        for (int j = 0; j < nbColumns; j++) {
            if (grid[i][j].getType() == CellType.EmptyCell) {
                listOfEmptyCells.add(grid[i][j]);
            }
        }
    }

    return listOfEmptyCells;
}


public boolean isSolvable() {
    int reversalCount = 0;
    int emptyRow;
    int[] flattenedState = new int[this.nbRows * this.nbColumns];
    int k = 0;
    
    for (i = 0; i < this.nbRows; i++) {
        for (j = 0; j < this.nbColumns; j++) {
            if (this.grid[i][j].getType() == CellType.GameCell) {
                flattenedState[k++] = this.grid[i][j].getValue();
            }
        }
    }
    
    for (i = 0; i < flattenedState.length - 1; i++) {
        for (j = i + 1; j < flattenedState.length; j++) {
            if (flattenedState[i] > flattenedState[j]) {
                reversalCount++;
            }
        }
    }
    
    if (flattenedState.length % 2 == 1) {
        return reversalCount % 2 == 0;
    } else {
    	
    	ArrayList<Cell> listOfEmptyCells = this.listOfEmptyCells();
    	int emptyRowSum = 0;
    	for (Cell emptyCell : listOfEmptyCells) {
    	    emptyRow = emptyCell.getRow();
    	    emptyRowSum += emptyRow;
    	}
    	return (emptyRowSum + reversalCount) % 2 == 1;
    }
}



public boolean gameOver()
{

    for (i = 0; i < this.nbRows; i++) 
    {
        for (j = 0; j < this.nbColumns; j++) 
        {
            if (!this.grid[i][j].equals(this.goal.grid[i][j])) {return false; }
            
        }
    }
    return true;
}

public ArrayList<int[]> nbPossibleMove(int i,int j){
    ArrayList<int[]> possibleMove = new ArrayList<>();
    int m,nextI,nextJ;
    for (m=0;m<4;m++)
    {
        nextI=i+move[m][0];
        nextJ=j+move[m][1];

        if (this.isValidated(nextI,nextJ))
        {
            if(this.grid[nextI][nextJ].getType() == CellType.EmptyCell)
            {
                possibleMove.add(move[m]);
                
            }
        }
            
    }

    return possibleMove;

}

public Grid clone() {
    Grid cloneGrid = new Grid();
    cloneGrid.goal = new Grid();
    cloneGrid.grid = new Cell[this.nbRows][this.nbColumns]; 
    cloneGrid.goal.grid = new Cell[this.nbRows][this.nbColumns]; 
    cloneGrid.nbRows = this.nbRows;
    cloneGrid.nbColumns = this.nbColumns;
    cloneGrid.nbLevel = this.nbLevel;

    for (int i = 0; i < this.nbRows; i++) {
        for (int j = 0; j < this.nbColumns; j++) {
            cloneGrid.grid[i][j] = this.grid[i][j].copyCell();
            cloneGrid.goal.grid[i][j] = this.goal.grid[i][j].copyCell();
        }
    }
    cloneGrid.f = this.f;
    cloneGrid.h = this.h;

    return cloneGrid;
}

public int getf() {
    return f;
}

public int geth() {
    return h;
}

public void setf(int f) {
    this.f = f;
}

public void seth(int h) {
    this.h = h;
}

@Override
public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
        return false;
    }
    Grid other = (Grid) obj;
    if (nbRows != other.nbRows || nbColumns != other.nbColumns || nbLevel != other.nbLevel) {
        return false;
    }
    for (int i = 0; i < nbRows; i++) {
        for (int j = 0; j < nbColumns; j++) {
            if (!grid[i][j].equals(other.grid[i][j])) {
                return false;
            }
        }
    }
    return true;
}

@Override

public int hashCode() 
{
    int result = 1;
    result = 31 * result + Arrays.deepHashCode(this.grid);
    return result;
}



public ArrayList<Grid> solved(Grid initialGrid) {
    PriorityQueue<Grid> openSet = new PriorityQueue<>(Comparator.comparingInt(Grid::getf));
    Set<Grid> closedSet = new HashSet<>();
    Map<Grid, Grid> cameFrom = new HashMap<>();
    Map<Grid, Integer> gScore = new HashMap<>();
    gScore.put(initialGrid, 0);

    initialGrid.seth(calculateHeuristicCost(initialGrid));
    initialGrid.setf(initialGrid.geth()); // Calculer le coût total initial f

    openSet.add(initialGrid);
    
    while (!openSet.isEmpty()) {
        
        Grid currentGrid = openSet.poll();
        
        
        if (currentGrid.gameOver()) {
            return reconstructPath(cameFrom, currentGrid);
        }
        
        
        ArrayList<Cell> emptyCells = currentGrid.listOfEmptyCells();

        for (Cell emptyCell : emptyCells) {
            for (int[] moves : move) {
                int nextI = emptyCell.getRow() + moves[0];
                int nextJ = emptyCell.getColumn() + moves[1];

                if (!currentGrid.isValidated(nextI, nextJ)) {
                    continue;
                }

                Grid children = currentGrid.clone();
                boolean moved = children.moveCell(children.getGrid()[emptyCell.getRow()][emptyCell.getColumn()], children.getGrid()[nextI][nextJ]);

                if (moved) {
                    int newG = gScore.get(currentGrid) + 1;

                    if (closedSet.contains(children) && newG >= gScore.getOrDefault(children, Integer.MAX_VALUE)) {
                    	System.out.println("!!!!!!!!!!!!!!!!!!!!");
                        continue;
                    }

                    if (!openSet.contains(children) || newG < gScore.getOrDefault(children, Integer.MAX_VALUE)) {
                        cameFrom.put(children, currentGrid);
                        //System.out.println("Last G = "+gScore.getOrDefault(children, Integer.MAX_VALUE)+" New G = "+newG);
                        	
                        
                        
                        
                        gScore.put(children, newG);
                        
                        //System.out.println("Last G = "+gScore.getOrDefault(children, Integer.MAX_VALUE)+" New G = "+newG);
                        children.seth(calculateHeuristicCost(children));
                        int f = newG + children.geth();
                        children.setf(f);
                        
                        
                        

                        if (!openSet.contains(children) && !closedSet.contains(children)) {
                            openSet.add(children);
                        }
                    }
                }
            }
        }
    }

    return null; // No solution found
}

private static int calculateHeuristicCost(Grid grid) {
    int cost = 0;
    grid.print();
    for (int i = 0; i < grid.getNbRows(); i++) {
        for (int j = 0; j < grid.getNbColumns(); j++) {
            Cell cell = grid.grid[i][j].copyCell();

            if (cell.getType() != CellType.UnexistantCell && grid.getGoal() != null && grid.getGoal().getGrid() != null) {
                int goalRow = cell.getFinalPosition()[0];
                int goalColumn = cell.getFinalPosition()[1];
                System.out.println("Value : "+cell.getValue()+" Goal Row = " + goalRow + " et Goal Column = "+goalColumn);
                cost += Math.abs(i - goalRow) + Math.abs(j - goalColumn);
            }
            
        }
    }
    //System.out.println("h = "+cost);
    return cost;
}

private static ArrayList<Grid> reconstructPath(Map<Grid, Grid> cameFrom, Grid currentGrid) {
    ArrayList<Grid> path = new ArrayList<>();
    path.add(currentGrid);

    while (cameFrom.containsKey(currentGrid)) {
        currentGrid = cameFrom.get(currentGrid);
        path.add(0, currentGrid);
    }

    return path;
}

}



