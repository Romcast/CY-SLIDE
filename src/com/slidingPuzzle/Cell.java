public class Cell{

private int row;
private int column;
private Integer value;
private CellType type;
private int i,j;
public Cell(int row,int column,Integer value,CellType type)
{
    this.row = row;
    this.column = column;
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
    else {value=null;}
}
public void setRow(int row){this.row=row;}
public void setColumn(int column){this.column=column;}

public int getRow() {return this.row;}
public int getColumn() {return this.column;}
public Integer getValue() {return this.value;}
public CellType getType() {return this.type;}

public boolean equals(Cell C1){

    if (this.row==C1.row && this.column==C1.column && this.value==C1.value && this.type==C1.type) {return true;}
    return false;
}


}