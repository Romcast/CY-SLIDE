
import java.io.File;

import javafx.application.Application;

import javafx.scene.input.*;

import javafx.scene.layout.*;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;


public class Screen extends Application{   
	
	/*private Cell c1 = new Cell(0, 0, 1, CellType.GameCell);
	private Cell c2 = new Cell(0, 1, 2, CellType.GameCell);
	private Cell c3 = new Cell(0, 2, 3, CellType.GameCell);
	private Cell c4 = new Cell(1, 0, 4, CellType.GameCell);
	private Cell c5 = new Cell(1, 1, 5, CellType.GameCell);
	private Cell c6 = new Cell(1, 2, 6, CellType.GameCell);
	private Cell c7 = new Cell(2, 0, 7, CellType.GameCell);
	private Cell c8 = new Cell(2, 1, 8, CellType.GameCell);
	private Cell c0 = new Cell(2, 2, 0, CellType.EmptyCell);
	private Cell[][] cells = {{c1, c2, c3}, {c4, c5, c6}, {c7, c8, c0}};
	private Grid grid = new Grid(cells,cells);*/
	
	File fileLevel = new File("H:/Documents/cours/projet/SLIDE-main/levels/level_1.csv");
    Grid level = new Grid(fileLevel);
	/*String fileName = "../levels/level_1.csv";
    File fileLevel = new File(fileName);
    Grid level = new Grid(fileLevel);*/
    
	@Override     
	public void start(Stage primaryStage) {
		primaryStage.setTitle("CY SLIDE");
		// Definition d'un bouton avec action sur clic  
		
		//InitialLevel level1=new InitialLevel("level_1.csv",1);
		//int nbLine=level1.goal.getNbLine();
		//int nbColumn=level1.goal.getNbColumn();
		//Menu menu=new Menu();
		
		
		
		
		//Text level = new Text(20, 100, "Niveau");
		GridPane gridpane= new GridPane();
		for (int i=0; i<level.getNbRows(); i++) {
			for (int j=0; j<level.getNbColumns();j++) {
				Integer value=level.getGrid()[i][j].getValue();
				if (value==null) {
					value=0;
				}
				Button button=new Button(value.toString());
				if (value==0) {
					button.setText(" ");
				}
				
				/*button.setOnAction(event ->{
					int column=gridpane.getColumnIndex(button);
					int row=gridpane.getRowIndex(button);
					System.out.println(row+","+column);
					});*/

				gridpane.add(button,j,i);
			}
		}


		

		//grid.add(btn, 0, 0);
		Button shuffle=new Button("shuffle");
		shuffle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				level.shuffle();
				updateGrid(gridpane,level);
			}
		});
		
		VBox root=new VBox(gridpane,shuffle);
		Scene scene=new Scene(root,400,400);
		primaryStage.setScene(scene);
		primaryStage.show();
		}
		
	public void updateGrid(GridPane gridpane, Grid grid) {
		for (int i=0; i<grid.getNbRows(); i++) {
			for (int j=0; j<grid.getNbColumns();j++) {
				Integer value=grid.getGrid()[i][j].getValue();//grid.getGrid() returns the shuffled cell[][] cells
				if (value==null) {
					value=0;
				}
				Button button = (Button) gridpane.getChildren().get(i*grid.getNbColumns()+j); // getChildren() returns a list
				button.setText(value.toString());
				if (value==0) {
					button.setText(" ");
				}
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
		}
	} 



