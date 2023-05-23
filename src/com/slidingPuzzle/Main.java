import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.text.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
//import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.HashMap;
public class Main extends Application {
	//static Scene players = null;
	//static Scene creation = null;
	static int i;
	static Player[] playerArray= new Player[5];
	static int j;
	static final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	
	static File fileLevel = new File("./data/levels/level_1.csv");
    static Grid level = new Grid(fileLevel);
	/*String fileName = "../levels/level_1.csv";
    File fileLevel = new File(fileName);
    Grid level = new Grid(fileLevel);*/
    static Button swap=null;
    static int row;
	static int column;
	 
	@Override
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
		try {
			
			intro(primaryStage);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static Player[] readPlayerFile() throws IOException, ClassNotFoundException {
		try {																// verifies if the file exists and is not empty
			FileInputStream fis = new FileInputStream("data/Player.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			ois.close();
		}
		catch (EOFException |FileNotFoundException e) {						// if not it creates it with an empty arrayList serialized
			Player[] playerEmptyArray = {null,null,null,null,null};
			FileOutputStream fos = new FileOutputStream("data/Player.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(playerEmptyArray);
		}
		
		FileInputStream fis = new FileInputStream("data/Player.txt"); 
		ObjectInputStream ois = new ObjectInputStream(fis);
		Player[] playerArray = (Player[]) ois.readObject(); // sets the arrayList of players
		ois.close();
		return playerArray;
	}
		public static void writePlayerFile(Player[] playerArray) throws IOException, ClassNotFoundException {
		
		FileOutputStream fos = new FileOutputStream("data/Player.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(playerArray);
		oos.close();
	}
		
		public static void intro(Stage primaryStage) {
			BorderPane root = new BorderPane();
			
			VBox vbox1=new VBox();
			VBox vbox2=new VBox();
			
			Label label = new Label("CY-SLIDE");
			label.setFont(new Font("Berlin Sans FB",164));
			
			Button button= new Button("PLAY");
			button.setFont(new Font("Berlin Sans FB",64));
			
			vbox1.getChildren().add(label);
			vbox2.getChildren().add(button);
			
			button.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent t){
					choosePlayer(primaryStage);
				}
			});
			
			vbox1.setAlignment(Pos.CENTER);
			vbox2.setAlignment(Pos.CENTER);
			
			root.setTop(vbox1);
			root.setCenter(vbox2);
			Scene scene1 = new Scene(root,screenBounds.getWidth(), screenBounds.getHeight()-25);
			
			
			
			//scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//primaryStage.setFullScreen(true);
			primaryStage.setScene(scene1);
			primaryStage.show();
		}
		
		public static void choosePlayer(Stage primaryStage) {
			// create your own Scene and then set it to primaryStage
			   BorderPane newroot =new BorderPane();
			   VBox vboxPlayer1=new VBox();
			   Label labelPlayer1= new Label("Pick a player");
			   labelPlayer1.setFont(new Font("Berlin Sans FB",100));
			   
			   //Player[] playerArray= new Player[5];
			try {
				playerArray = readPlayerFile();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   HashMap<Integer, Hyperlink> pseudo = new HashMap<Integer, Hyperlink>();

			   
			  // int i;
			   for(i=0;i<5;i++) {
				   if(playerArray[i]==null) {
					   final int index = i;
					   Hyperlink tmp=new Hyperlink("New Player");
					   tmp.setOnAction(new EventHandler<ActionEvent>(){
						   public void handle(ActionEvent t){
							   
							   createPlayer(primaryStage,index);
						   }
					   });
					   tmp.setFont(new Font("Berlin Sans FB",80));
					   pseudo.put(i, tmp);
				   }else {
					   final int index = i;
					   Hyperlink tmp=new Hyperlink(playerArray[i].getPseudo());
					   tmp.setOnAction(new EventHandler<ActionEvent>(){
						   public void handle(ActionEvent t){
							   chooseLevel(primaryStage,index);
						   }
					   });
					   tmp.setFont(new Font("Berlin Sans FB",80));
					   pseudo.put(i, tmp); 
				   }
				   
			   }
			   vboxPlayer1.getChildren().addAll(labelPlayer1,pseudo.get(0),pseudo.get(1),pseudo.get(2),pseudo.get(3),pseudo.get(4));
			   vboxPlayer1.setAlignment(Pos.CENTER);
			   newroot.setTop(vboxPlayer1);
		     primaryStage.setScene(new Scene(newroot,screenBounds.getWidth(), screenBounds.getHeight()-25));
			
		}
		
		public static void createPlayer(Stage primaryStage,int index) {
			BorderPane newrootPlayer1 =new BorderPane();
			VBox vboxP1=new VBox();
			Label pseudonyme1= new Label("New pseudo");
			TextField choosePseudo= new TextField();
			Button btnpseudo1=new Button("Select");
		    btnpseudo1.setOnAction(new EventHandler<ActionEvent>(){
		    	public void handle(ActionEvent t) {
		    		String pseudoEntered = choosePseudo.getText();
					playerArray[index]=new Player(pseudoEntered);
				    try {
					writePlayerFile(playerArray);
				    } catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();		
					}
				    chooseLevel(primaryStage,index);
		    	}
		    });
		    Button btnback=new Button("Go back to the selection");
		    btnback.setOnAction(new EventHandler<ActionEvent>(){
		    	public void handle(ActionEvent t) {
		    		choosePlayer(primaryStage);
		    	}
		    });
		    btnback.setFont(new Font("Berlin Sans FB",25));
		    pseudonyme1.setFont(new Font("Berlin Sans FB",50));
			btnpseudo1.setFont(new Font("Berlin Sans FB",25));
			vboxP1.setPadding(new Insets(0, 600, 0, 600));
		   	vboxP1.getChildren().addAll(pseudonyme1,choosePseudo,btnpseudo1,btnback);
		   	vboxP1.setAlignment(Pos.CENTER);
		   	newrootPlayer1.setCenter(vboxP1);
		   	primaryStage.setScene(new Scene(newrootPlayer1,screenBounds.getWidth(), screenBounds.getHeight()-25));
		}
		
		public static void chooseLevel(Stage primaryStage, int index) {
			BorderPane newrootPlayer2 =new BorderPane();
            int maxLevel=playerArray[index].getLevelMax();
            VBox description=new VBox();
			VBox[] vboxP2=new VBox[maxLevel];
			Label Level= new Label("Level  Score Shuffle  ShuffleRandom ");
			Level.setFont(new Font("Berlin Sans FB",40));
            description.getChildren().add(Level);
            description.setAlignment(Pos.CENTER);
            newrootPlayer2.setTop(description);
            for(j=0;j<maxLevel;j++){
            	vboxP2[j] = new VBox();
                Hyperlink Levelj= new Hyperlink("Level"+(j+1));
                Levelj.setFont(new Font("Berlin Sans FB",40));
                //Label scoreShuffle= new Label("Score Shuffle");
                //Label scoreRandomShuffle=new Label("Score ShuffleRandom");
    		    Levelj.setOnAction(new EventHandler<ActionEvent>(){
    		    	public void handle(ActionEvent t) {
    		    		playLevel(primaryStage);
    		    	}
    		    });
               	vboxP2[j].getChildren().add(Levelj);
              	vboxP2[j].setAlignment(Pos.CENTER);
                newrootPlayer2.setCenter(vboxP2[j]);
            }
			primaryStage.setScene(new Scene(newrootPlayer2,screenBounds.getWidth(), screenBounds.getHeight()-25));
		}
		public static void playLevel(Stage primaryStage) {
			primaryStage.setTitle("CY SLIDE");
			GridPane gridpane= new GridPane();
			
			for (int i=0; i<level.getNbRows(); i++) {
				for (int j=0; j<level.getNbColumns();j++) {
					Button button=new Button();
					button.setPrefSize(100, 100);
					button.setMinSize(70, 70);
					button.setMaxSize(150,150);
					button.setFont(new Font("Berlin Sans FB",30));
	            	gridpane.add(button,j,i);
	            	
					switch(level.getGrid()[i][j].getType())
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
	                    buttonText=level.getGrid()[i][j].getValue().toString() ;
	                    button.setText(buttonText);
	                    break;
	                    
	                default:
	                    break;
	                }
			
					button.setOnAction(new EventHandler<ActionEvent>(){
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
				public void handle(ActionEvent event) {
					
					level.stepByStepShuffle();
					updateGrid(gridpane,level);
				}
			});
			
			
			Button randomShuffle=new Button("Random shuffle");
			randomShuffle.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					level.randomShuffled();
					updateGrid(gridpane,level);
				}
			});
			
			HBox shuffleBox=new HBox(shuffle,randomShuffle);
			Text levelName=new Text("Level");
			//levelName.
			VBox root=new VBox(levelName,gridpane,shuffleBox);
			root.setAlignment(Pos.CENTER);
			shuffleBox.setAlignment(Pos.CENTER);
			gridpane.setAlignment(Pos.CENTER);
			root.setPadding(new Insets(20));
			root.setSpacing(10);
			Scene scene=new Scene(root,screenBounds.getWidth(), screenBounds.getHeight()-25);
			primaryStage.setScene(scene);
		}
		
		public static void updateGrid(GridPane gridpane, Grid grid) {
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
	                	button = (Button) gridpane.getChildren().get(i*grid.getNbColumns()+j); // getChildren() returns a list
	    				button.setText(buttonText);
	                    break;
	                case GameCell: 
	                    buttonText=level.getGrid()[i][j].getValue().toString() ;
	                    button = (Button) gridpane.getChildren().get(i*grid.getNbColumns()+j); // getChildren() returns a list
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

