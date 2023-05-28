import java.io.*;
import java.util.*;

public class Cell implements Serializable{
private static final long serialVersionUID = 4;
private int row;
private int column;
private int[] finalPosition;
private Integer value;
private CellType type;

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


public void setType(CellType type){this.type=type;}
public void setValue(Integer value)
{

    if(this.type == CellType.GameCell) 
    {this.value=value;}
    else {this.value=null;}
}
public void setRow(int row){this.row=row;}
public void setColumn(int column){this.column=column;}
public void setFinalPosition(int[] finalPosition) {
	
	this.finalPosition[0] = finalPosition[0] ;this.finalPosition[1] = finalPosition[1]; }

public int[] getFinalPosition() {return this.finalPosition;}
public int getRow() {return this.row;}
public int getColumn() {return this.column;}
public Integer getValue() {return this.value;}
public CellType getType() {return this.type;}

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


public Cell copyCell(){
    Cell cp = new Cell(this.row,this.column,this.value,this.type);
    cp.setFinalPosition(this.finalPosition);
    return cp;
}
/*
public int hashCode() {
    int result = Objects.hash(value, type); // Utilisez les champs pertinents dans le calcul du hashCode
    return result;
}*/

}

