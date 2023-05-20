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
}

public int getRow() {return this.row;}
public int getColumn() {return this.column;}
public Integer getValue() {return this.value;}
public CellType getType() {return this.type;}


}