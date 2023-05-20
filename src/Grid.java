import java.io.*;

public class Grid{

public static final int[][] move = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

private int nbRows;
private int nbColumns;
private int scoreMin;
private int nbLevel;
private Cell[][] grid;// ++level
private Grid goal;
int i,j;

public Grid() {
    nbRows = 0;
    nbColumns = 0;
    scoreMin = 0;
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

        this.scoreMin=100;//solve
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
            System.out.println();
    }
    System.out.println();    
        
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



private void exchangeCell(Cell C1, Cell C2) 
/*

This method swaps two cells
  
parametres : 
            

*/
{
    Integer tmpValue = C1.getValue();
    CellType tmpType = C1.getType();
    
    C1.setType(C2.getType());
    C1.setValue(C2.getValue());
    
    C2.setType(tmpType);
    C2.setValue(tmpValue);
    
}

public boolean isNeighbor(Cell C1,Cell C2){

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

   
    if(!this.isNeighbor(C1,C2)){return false;}
    
    if((C1.getType()==CellType.EmptyCell && C2.getType()!=CellType.EmptyCell) || (C2.getType()==CellType.EmptyCell && C1.getType()!=CellType.EmptyCell) ){
        this.exchangeCell(C1,C2);
        
        return true;}
    return false;
    
    

}

}


