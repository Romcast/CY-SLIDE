
import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.application.Application;
//import javafx.css.*;
import javafx.scene.input.*;

import javafx.scene.layout.*;

import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;


public class Screen extends Application{   
	
	int nbLevel=1;
	Game game=new Game(nbLevel);
    Button swap=null;
    int row;
	int column;
	int countMove=0;
	
	@Override     
	public void start(Stage primaryStage) {
		primaryStage.setTitle("CY SLIDE");
		GridPane gridpane= new GridPane();
		//int countMove=0;
		Label countLabel= new Label("nombre de coups: " + Integer.toString(countMove));
		
		//displays initial chosen level
		for (int i=0; i<game.getGrid().getNbRows(); i++) {
			for (int j=0; j<game.getGrid().getNbColumns();j++) {
				Button button=new Button();
				button.setPrefSize(100, 100);
				button.setMinSize(70, 70);
				button.setMaxSize(150,150);
				button.setFont(new Font("Berlin Sans FB",30));
            	gridpane.add(button,j,i);
            	
            	
				switch(this.game.getGrid().getGrid()[i][j].getType()) //Class Grid has an attribute grid and an attribute goal
                {
                case EmptyCell: 
                	String buttonText=" ";
                	button.setText(buttonText);
                    break;
                    
                case UnexistantCell: 
                	buttonText="";
                	button.setText(buttonText);
                	button.setStyle("-fx-background-color: grey;");
                    break;
                    
                case GameCell: 
                    buttonText=this.game.getGrid().getGrid()[i][j].getValue().toString() ;
                    button.setText(buttonText);
                    break;
                    
                default:
                    break;
                }
				
				//button of grid, click on two of them to swap them
				button.setOnAction(new EventHandler<ActionEvent>(){
					
					
        		@Override
        		public void handle(ActionEvent event) {
        			if(swap==null) {// register first button as source
        				swap=button;
        				row=gridpane.getRowIndex(button);
        				column=gridpane.getColumnIndex(button);
        				System.out.println(row +","+column);
        				System.out.println("init");
        				
        			}
        			else { // register 2nd one as target
        				int row2=gridpane.getRowIndex(button);
        				int column2=gridpane.getColumnIndex(button);
        				if(game.getGrid().moveCell(game.getGrid().getGrid()[row2][column2],game.getGrid().getGrid()[row][column])) {//if moveCell authorized, swap text
        					String tempText=button.getText();
            				button.setText(swap.getText());
            				((Button)gridpane.getChildren().get(row*game.getGrid().getNbColumns()+column)).setText(tempText);// () needed because setText doesn't work on every node
            				System.out.println("swap");
            				game.getGrid().print();
            				countMove+=1;
            				countLabel.setText("nombre de coups: " + Integer.toString(countMove));
            				if(game.getGrid().gameOver()) {
            					Alert win = new Alert(AlertType.INFORMATION);
            					win.setContentText("BRAVO");
            					win.showAndWait();
            				}	
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
			
			game.getGrid().stepByStepShuffle();
			updateGrid(gridpane,game.getGrid());
		}
	});
		
		
	Button randomShuffle=new Button("Random shuffle");
	randomShuffle.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			game.getGrid().randomShuffled();
			updateGrid(gridpane,game.getGrid());
			//ajouter une condition pour verifier solvalble
		}
	});
		
		
	HBox shuffleBox=new HBox(shuffle,randomShuffle);
	Text levelName=new Text("Level "+nbLevel);

	VBox root=new VBox(levelName,countLabel,gridpane,shuffleBox);
	root.setAlignment(Pos.CENTER);
	shuffleBox.setAlignment(Pos.CENTER);
	gridpane.setAlignment(Pos.CENTER);
	root.setPadding(new Insets(20));
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
                	buttonText="";
                	button = (Button) gridpane.getChildren().get(i*grid.getNbColumns()+j); 
    				button.setText(buttonText);
                    break;
                case GameCell: 
                    buttonText=this.game.getGrid().getGrid()[i][j].getValue().toString() ;
                    button = (Button) gridpane.getChildren().get(i*grid.getNbColumns()+j); 
    				button.setText(buttonText);
                    break;
                default:
                    break;
                }

			}
		}
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
		}
	} 



