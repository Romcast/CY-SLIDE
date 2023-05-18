
import java.io.*; // pour le try si il y a une erreur

public class initialLevel 
{

protected Grid goal;
protected int levelNumber;


public initialLevel(String filePath, int levelNumber)
{

	this.levelNumber=levelNumber;
		
	// On lit le fichier qui contient une grild et on la copy dans grid

	// Le fichier d'entrée
    
	try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			
            String line = br.readLine();
            String[] dimensions = line.split(" ");
            int rows = Integer.parseInt(dimensions[0]);
            int columns = Integer.parseInt(dimensions[1]);
			
            int[][] matrix = new int[rows][columns];

            for (int i = 0; i < rows; i++) {
                line = br.readLine();
                String[] values = line.split(" ");
                for (int j = 0; j < columns; j++) {
                    matrix[i][j] = Integer.parseInt(values[j]);
                }
            }
			


			Grid G = new Grid(matrix);

            this.goal=G;
			
			
			} 
			catch (IOException e) {
            e.printStackTrace();
			this.goal=null;
        }
		
		
            
	
}

public void print()
{
	System.out.println("Level :"+this.levelNumber);
	this.goal.print();
}

}


