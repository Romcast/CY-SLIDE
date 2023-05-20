import java.util.ArrayList;
import java.util.*;


public class AppMain
{
private static final int[][] move = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
public int i,j;
/*
move[0]= UP
move[1]= DOWN 
move[2]= LEFT
move[3]= RIGHT
*/

public static void main(String[] args) 
{

    String level1Path = "../levels/level_1.csv";
    initialLevel level_1 = new initialLevel(level1Path,1);

    level_1.print();

    // On cree un objet level
    // On melange l'objet level
    
    /*
    String level1MixPath = "../levels/level_1.csv";
    initialLevel level_1Mix = new initialLevel(level1MixPath,11);

    level_1Mix.print();

    level_1Mix.goal.solve(level_1.goal);

    level_1Mix.print();
    */


    

    

}

}