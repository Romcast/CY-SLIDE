
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
	
	File fileLevel = new File("H:/Documents/cours/projet/SLIDE-main/levels/level_1.csv");
    Grid level = new Grid(fileLevel);
    
	@Override     
	public void start(Stage primaryStage) {
		primaryStage.setTitle("CY SLIDE");
		
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



