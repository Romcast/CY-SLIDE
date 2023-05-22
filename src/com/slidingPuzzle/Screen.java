
import java.io.File;
import javafx.geometry.Insets;
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
	
	
	
	File fileLevel = new File("H:/Documents/cours/projet/SLIDE-main/levels/level_7.csv");
    Grid level = new Grid(fileLevel);
	/*String fileName = "../levels/level_1.csv";
    File fileLevel = new File(fileName);
    Grid level = new Grid(fileLevel);*/
    Button swap=null;
    int row;
	int column;
	@Override     
	public void start(Stage primaryStage) {
		primaryStage.setTitle("CY SLIDE");
		//Text level = new Text(20, 100, "Niveau");
		GridPane gridpane= new GridPane();
		
		
		for (int i=0; i<level.getNbRows(); i++) {
			for (int j=0; j<level.getNbColumns();j++) {
				Button button=new Button();
				button.setPrefSize(100, 100);
				button.setMinSize(70, 70);
				button.setMaxSize(150,150);
				//button.setStyle("-fx-background-color: white;");
            	gridpane.add(button,j,i);
            	
            	
        		
            	
				switch(this.level.getGrid()[i][j].getType())
                {
                case EmptyCell: 
                	String buttonText=" ";
                	button.setText(buttonText);
                    break;
                    
                case UnexistantCell: 
                	buttonText="-";
                	button.setText(buttonText);
                    break;
                    
                case GameCell: 
                    buttonText=this.level.getGrid()[i][j].getValue().toString() ;
                    button.setText(buttonText);
                    break;
                    
                default:
                    break;
                }
				
				/*button.setOnAction(event ->{
					int column=gridpane.getColumnIndex(button);
					int row=gridpane.getRowIndex(button);
					System.out.println(row+","+column);
					});*/
				
				button.setOnAction(new EventHandler<ActionEvent>(){
					
					
        			@Override
        			public void handle(ActionEvent event) {
        				if(swap==null) {
        					swap=button;
        					row=gridpane.getRowIndex(button);
        					column=gridpane.getColumnIndex(button);
        					System.out.println(row +","+column);
        					System.out.println("init");
        					
        				}
        				else {
        					int row2=gridpane.getRowIndex(button);
        					int column2=gridpane.getColumnIndex(button);
        					if(level.moveCell(level.getGrid()[row2][column2],level.getGrid()[row][column])) {//if moveCell authorized, swap text
        						String tempText=button.getText();
            					button.setText(swap.getText());
            					((Button)gridpane.getChildren().get(row*level.getNbColumns()+column)).setText(tempText);// () needed because setText doesn't work on every node
            					System.out.println("swap");
            					
        					}
        					/*else {  //if unauthorized movement, buttons get red for a moment
        						button.setStyle("-fx-background-color: red;");
        						((Button) gridpane.getChildren().get(row*level.getNbColumns()+column)).setStyle("-fx-background-color: red;");
        						try{
        							Thread.sleep(1000);	
        						}
        						catch(Exception e) {
        							e.printStackTrace();
        						}
    							button.setStyle("");
        						((Button) gridpane.getChildren().get(row*level.getNbColumns()+column)).setStyle("");

        						
        					}*/
        					swap=null;
        				}
        			}
        			
        		});
	
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
		
		
		
		
		HBox shuffleBox=new HBox(shuffle,randomShuffle);
		Text levelName=new Text("Level");
		VBox root=new VBox(levelName,gridpane,shuffleBox);
		//root.setPadding(new Insets(20,20,10));
		root.setSpacing(10);
		
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



