
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
	/*String fileName = "../levels/level_1.csv";
    File fileLevel = new File(fileName);
    Grid level = new Grid(fileLevel);*/
    
	@Override     
	public void start(Stage primaryStage) {
		primaryStage.setTitle("CY SLIDE");
		//Text level = new Text(20, 100, "Niveau");
		GridPane gridpane= new GridPane();
		for (int i=0; i<level.getNbRows(); i++) {
			for (int j=0; j<level.getNbColumns();j++) {
				switch(this.level.getGrid()[i][j].getType())
                {
                case EmptyCell: 
                	String buttonText=" ";
                	Button button=new Button(buttonText);
                	button.setPrefSize(100, 100);
    				button.setMinSize(70, 70);
    				button.setMaxSize(150,150);
                	gridpane.add(button,j,i);
                	
                    break;
                case UnexistantCell: 
                	buttonText="-";
                	button=new Button(buttonText);
                	button.setPrefSize(100, 100);
    				button.setMinSize(70, 70);
    				button.setMaxSize(150,150);
                	gridpane.add(button,j,i);
                    break;
                case GameCell: 
                    buttonText=this.level.getGrid()[i][j].getValue().toString() ;
                    button=new Button(buttonText);
                    button.setPrefSize(100, 100);
    				button.setMinSize(70, 70);
    				button.setMaxSize(150,150);
                    gridpane.add(button,j,i);
                    break;
                default:
                    break;
                }
				
				
				//button.setOnDrangDetected()
				/*button.setPrefSize(100, 100);
				button.setMinSize(70, 70);
				button.setMaxSize(150,150);
				if (value==0) {
					button.setText(" ");
				}*/
				
				/*button.setOnAction(event ->{
					int column=gridpane.getColumnIndex(button);
					int row=gridpane.getRowIndex(button);
					System.out.println(row+","+column);
					});*/

				
			}
		}

		Button shuffle=new Button("shuffle");
		shuffle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				level.stepByStepShuffle();
				updateGrid(gridpane,level);
			}
		});
		
		Button randomShuffle=new Button("Random shuffle");
		randomShuffle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				level.randomShuffled();
				updateGrid(gridpane,level);
			}
		});
		
		
		
		
		
		VBox root=new VBox(gridpane,shuffle, randomShuffle);
		
		Scene scene=new Scene(root,600,600);
		primaryStage.setScene(scene);
		primaryStage.show();
		}
		
	
	
	
	
	public void updateGrid(GridPane gridpane, Grid grid) {
		for (int i=0; i<grid.getNbRows(); i++) {
			for (int j=0; j<grid.getNbColumns();j++) {
				//Integer value=grid.getGrid()[i][j].getValue();//grid.getGrid() returns the shuffled cell[][] cells
				switch(grid.getGrid()[i][j].getType())
                {
                case EmptyCell: 
                	String buttonText=" ";
                	Button button = (Button) gridpane.getChildren().get(i*grid.getNbColumns()+j); // getChildren() returns a list
    				button.setText(buttonText);
                    break;
                case UnexistantCell: 
                	buttonText="-";
                	button = (Button) gridpane.getChildren().get(i*grid.getNbColumns()+j); // getChildren() returns a list
    				button.setText(buttonText);
                    break;
                case GameCell: 
                    buttonText=this.level.getGrid()[i][j].getValue().toString() ;
                    button = (Button) gridpane.getChildren().get(i*grid.getNbColumns()+j); // getChildren() returns a list
    				button.setText(buttonText);
                    break;
                default:
                    break;
                }
				//System.out.println(value);
				/*Button button = (Button) gridpane.getChildren().get(i*grid.getNbColumns()+j); // getChildren() returns a list
				button.setText(value.toString());
				if (value==0) {
					button.setText(" ");
				}*/
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
		}
	} 



