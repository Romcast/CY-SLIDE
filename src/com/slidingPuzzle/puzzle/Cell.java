package puzzle;

import java.io.*;
import java.util.*;
import java.io.*;
import java.util.*;
/**
 * This class represents the cells of the grid
 * @author CYTech Student
 *
 */
public class Cell implements Serializable{
private static final long serialVersionUID = 4;
private int row;
private int column;
private int[] finalPosition;
private Integer value;
private CellType type;
/**
 * This is the constructor of the cells that set the different parameters
 * @param row
 * @param column
 * @param value
 * @param type
 */
public Cell(int row,int column,Integer value,CellType type)
{
    this.row = row;
    this.column = column;
    this.finalPosition = new int[2];
    this.finalPosition[0]=row;
    this.finalPosition[1]=column;
    this.type = type;
    switch(type)
    {
        case GameCell: 
            this.value=value;
            break;

        default:
            this.value=null;
            break;
    }

}

/**
 * This is the Setter of the type of cell
 * @param type
 */
public void setType(CellType type){this.type=type;}
/**
 * This is the setter of the value of the cell
 * @param value
 */
public void setValue(Integer value)
{

    if(this.type == CellType.GameCell) 
    {this.value=value;}
    else {this.value=null;}
}
/**
 * This is the setter of the row of the grid where the cell is
 * @param row
 */
public void setRow(int row){this.row=row;}
/**
 * This is the setter of the column of the grid where the cell is
 * @param column
 */
public void setColumn(int column){this.column=column;}
/**
 * This is the setter of the final position of the cell on the grid
 * @param finalPosition
 */
public void setFinalPosition(int[] finalPosition) {
	
	this.finalPosition[0] = finalPosition[0] ;this.finalPosition[1] = finalPosition[1]; }
/**
 * This is the getter of the final position of the cell on the grid
 * @return
 */

public int[] getFinalPosition() {return this.finalPosition;}
/**
 * This is the getter of the row of the grid where the cell is
 * @return
 */
public int getRow() {return this.row;}
/**
 * This is the getter of the column of the grid where the cell is
 * @return
 */
public int getColumn() {return this.column;}
/**
 * This is the getter of the value of the cell in the grid
 * @return
 */
public Integer getValue() {return this.value;}
/**
 * This is the getter of the type of the cell
 * @return
 */
public CellType getType() {return this.type;}
/**
 * This is a method that permit to verify if two cells are equals
 */
@Override
public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
        return false;
    }
    Cell otherCell = (Cell) obj;
    return 
           this.value == otherCell.value &&
           this.type == otherCell.type;
}

/**
 * This method permit to clone a cell
 * @return
 */
public Cell copyCell(){
    Cell cp = new Cell(this.row,this.column,this.value,this.type);
    cp.setFinalPosition(this.finalPosition);
    return cp;
}
/**
 * This method permit to change the hashcode
 */
/*public int hashCode() {
    int result = Objects.hash(value, type); // Utilisez les champs pertinents dans le calcul du hashCode
    return result;
}*/

}

