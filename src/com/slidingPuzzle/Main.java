import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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
	 
	static Button swap=null;
    static int row;
	static int column;
	static int countMove=0;
	
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
			   HashMap<Integer, HBox> hBoxHashMap = new HashMap<Integer, HBox>();
			   
			  // int i;
			   for(i=0;i<5;i++) {
				   if(playerArray[i]==null) {
					   final int indexPlayer = i;
					   Hyperlink tmp=new Hyperlink("New Player");
					   tmp.setOnAction(new EventHandler<ActionEvent>(){
						   public void handle(ActionEvent t){
							   
							   createPlayer(primaryStage,indexPlayer);
						   }
					   });
					   HBox hBoxPlayerRemove = new HBox();
					   hBoxPlayerRemove.getChildren().addAll(tmp);
					   tmp.setFont(new Font("Berlin Sans FB",80));
					   hBoxPlayerRemove.setAlignment(Pos.CENTER);
					   hBoxHashMap.put(i, hBoxPlayerRemove);
				   }else {
					   final int indexPlayer = i;
					   Hyperlink tmp=new Hyperlink(playerArray[i].getPseudo());
					   tmp.setOnAction(new EventHandler<ActionEvent>(){
						   public void handle(ActionEvent t){
							   chooseLevel(primaryStage,indexPlayer);
						   }
					   });
					   HBox hBoxPlayerRemove = new HBox();
					   hBoxPlayerRemove.setSpacing(25);
					   Button btnRemove = new Button("Remove");
					   btnRemove.setOnAction(new EventHandler<ActionEvent>(){
						   public void handle(ActionEvent t){
							   removePlayer(primaryStage,indexPlayer);
						   }
					   });
					   btnRemove.setFont(new Font("Berlin Sans FB",30));
					   tmp.setFont(new Font("Berlin Sans FB",80));
					   hBoxPlayerRemove.getChildren().addAll(tmp,btnRemove);
					   hBoxPlayerRemove.setAlignment(Pos.CENTER);
					   hBoxHashMap.put(i, hBoxPlayerRemove); 
				   }
				   
			   }
			   vboxPlayer1.getChildren().addAll(labelPlayer1,hBoxHashMap.get(0),hBoxHashMap.get(1),hBoxHashMap.get(2),hBoxHashMap.get(3),hBoxHashMap.get(4));
			   vboxPlayer1.setAlignment(Pos.CENTER);
			   newroot.setTop(vboxPlayer1);
		     primaryStage.setScene(new Scene(newroot,screenBounds.getWidth(), screenBounds.getHeight()-25));
			
		}
		
		public static void createPlayer(Stage primaryStage,int indexPlayer) {
			BorderPane newrootPlayer1 =new BorderPane();
			VBox vboxP1=new VBox();
			Label pseudonyme1= new Label("New pseudo");
			TextField choosePseudo= new TextField();
			Button btnpseudo1=new Button("Select");
		    btnpseudo1.setOnAction(new EventHandler<ActionEvent>(){
		    	public void handle(ActionEvent t) {
		    		String pseudoEntered = choosePseudo.getText();
					playerArray[indexPlayer]=new Player(pseudoEntered);
				    try {
					writePlayerFile(playerArray);
				    } catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();		
					}
				    chooseLevel(primaryStage,indexPlayer);
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
		
		public static void removePlayer(Stage primaryStage,int indexPlayer) {
			playerArray[indexPlayer]=null;
		    try {
			writePlayerFile(playerArray);
		    } catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
			}
		    choosePlayer(primaryStage);
		}
		
		public static void chooseLevel(Stage primaryStage, int indexPlayer) {
			BorderPane newrootPlayer2 =new BorderPane();
            int maxLevel=playerArray[indexPlayer].getLevelMax();
            VBox description=new VBox();
			VBox vboxP2=new VBox();
			Label Level= new Label("Level  Score Shuffle  ShuffleRandom ");
			Level.setFont(new Font("Berlin Sans FB",40));
            description.getChildren().add(Level);
            description.setAlignment(Pos.CENTER);
            newrootPlayer2.setTop(description);
            for(j=0;j<maxLevel;j++){
            	final int indexLevel = j;
            	HBox hboxP2 = new HBox();
                Hyperlink Levelj= new Hyperlink("Level"+(j+1));
                Levelj.setFont(new Font("Berlin Sans FB",40));
                //Label scoreShuffle= new Label("Score Shuffle");
                //Label scoreRandomShuffle=new Label("Score ShuffleRandom");
    		    Levelj.setOnAction(new EventHandler<ActionEvent>(){
    		    	public void handle(ActionEvent t) {
    		    		if (playerArray[indexPlayer].getGameArray()[indexLevel] == null){
    		    			playerArray[indexPlayer].getGameArray()[indexLevel] = new Game(j,playerArray[indexPlayer]);
    		    		}
    		    		playLevel(primaryStage,playerArray[indexPlayer].getGameArray()[indexLevel],indexPlayer);
    		    	}
    		    });
    		    Label score = new Label();
    		    score.setFont(new Font("Berlin Sans FB",40));
    		    if (playerArray[indexPlayer].getGameArray()[indexLevel] == null) {
    		    	score.setText("Not played yet");
    		    }
    		    else {
    		    	score.setText(Integer.toString(playerArray[indexPlayer].getGameArray()[j].getScore()));
    		    	
    		    }
    		    hboxP2.getChildren().addAll(Levelj,score);
    		    hboxP2.setAlignment(Pos.CENTER);
               	vboxP2.getChildren().add(hboxP2);
            }
            vboxP2.setAlignment(Pos.CENTER);
            Button btnBack = new Button("Go back to selection");
			btnBack.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					choosePlayer(primaryStage);
				}
				
			});
			description.getChildren().add(btnBack);
			newrootPlayer2.setCenter(vboxP2);
			primaryStage.setScene(new Scene(newrootPlayer2,screenBounds.getWidth(), screenBounds.getHeight()-25));
		}
		public static void playLevel(Stage primaryStage,Game game,int indexPlayer) {
			GridPane gridpane= new GridPane();
			//int countMove=0;
			Label countLabel= new Label("Nombre de coups: " + game.getScore());
			countLabel.setFont(new Font("Berlin Sans FB",30));
			//displays initial chosen level
			for (int i=0; i<game.getGrid().getNbRows(); i++) {
				for (int j=0; j<game.getGrid().getNbColumns();j++) {
					Button button=new Button();
					button.setPrefSize(100, 100);
					button.setMinSize(70, 70);
					button.setMaxSize(150,150);
					button.setFont(new Font("Berlin Sans FB",40));
	            	gridpane.add(button,j,i);
	            	
	            	
					switch(game.getGrid().getGrid()[i][j].getType()) //Class Grid has an attribute grid and an attribute goal
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
	                    buttonText=game.getGrid().getGrid()[i][j].getValue().toString() ;
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
	        				if(game.moveCell(game.getGrid().getGrid()[row2][column2],game.getGrid().getGrid()[row][column])) {//if moveCell authorized, swap text
	        					String tempText=button.getText();
	            				button.setText(swap.getText());
	            				((Button)gridpane.getChildren().get(row*game.getGrid().getNbColumns()+column)).setText(tempText);// () needed because setText doesn't work on every node
	            				System.out.println("swap");
	            				game.getGrid().print();
	            				countMove+=1;
	            				countLabel.setText("Nombre de coups: " + game.getScore());
	            				if(game.gameOver()) {
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

			Button shuffle=new Button("Shuffle");
			shuffle.setFont(new Font("Berlin Sans FB",30));
			shuffle.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
					game.getGrid().stepByStepShuffle();
					game.setType(ShuffleType.StepByStep);
					game.setIsSolvable(true);
					updateGrid(gridpane,game.getGrid());
					
				}
			});
				
				
			Button randomShuffle=new Button("Random shuffle");
			randomShuffle.setFont(new Font("Berlin Sans FB",30));
			randomShuffle.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					game.getGrid().randomShuffled();
					game.setType(ShuffleType.Random);
					updateGrid(gridpane,game.getGrid());
					//ajouter une condition pour verifier solvalble
					
				}
			});
			
			Button btnBack = new Button("Save and quit");
			btnBack.setFont(new Font("Berlin Sans FB",30));
			btnBack.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					try {
						writePlayerFile(playerArray);
					    } catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();		
						}
					chooseLevel(primaryStage,indexPlayer);
				}
				
			});
			
			HBox shuffleBox=new HBox(shuffle,randomShuffle);
			shuffleBox.setSpacing(10);
			shuffleBox.setAlignment(Pos.CENTER);
			
			Text levelName=new Text("Level "+game.getLevel());
			levelName.setFont(new Font("Berlin Sans FB",80));
			gridpane.setAlignment(Pos.CENTER);
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setContent(gridpane);
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);
			scrollPane.setMaxSize(600,600);
			
			GridPane goalGridpane=createGoal(primaryStage,100,game);
			goalGridpane.setAlignment(Pos.CENTER);
			VBox informationBox= new VBox(levelName,countLabel);
			informationBox.setAlignment(Pos.CENTER);
			VBox buttonsBox=new VBox(shuffleBox,btnBack);
			buttonsBox.setAlignment(Pos.CENTER);
			buttonsBox.setSpacing(10);
			VBox infoButtonBox = new VBox(informationBox,buttonsBox);
			infoButtonBox.setSpacing(50);
			infoButtonBox.setAlignment(Pos.CENTER);
			HBox root = new HBox(scrollPane,infoButtonBox,goalGridpane);
			root.setAlignment(Pos.CENTER);
			root.setPadding(new Insets(20));
			root.setSpacing(100);	
			Scene scene=new Scene(root,screenBounds.getWidth(), screenBounds.getHeight()-25);
			primaryStage.setScene(scene);
			
		}
		
		public static GridPane createGoal(Stage primaryStage,int prefSize, Game game) {
			GridPane goalGridpane=new GridPane();
			
			for (int i=0; i<game.getGrid().getNbRows(); i++) {
				for (int j=0; j<game.getGrid().getNbColumns();j++) {
					Label cellLabel=new Label();
					cellLabel.setPrefSize(prefSize, prefSize);
					cellLabel.setMinSize(50, 50);
					cellLabel.setMaxSize(150,150);
					cellLabel.setFont(new Font("Berlin Sans FB",30));
					cellLabel.setStyle("-fx-border-color: black");
					cellLabel.setAlignment(Pos.CENTER);
	            	goalGridpane.add(cellLabel,j,i);
	            	
	            	
	            	
					switch(game.getGrid().getGrid()[i][j].getType()) //Class Grid has an attribute grid and an attribute goal
	                {
	                case EmptyCell: 
	                	String cellText=" ";
	                	cellLabel.setText(cellText);
	                    break;
	                    
	                case UnexistantCell: 
	                	cellText="";
	                	cellLabel.setText(cellText);
	                	cellLabel.setStyle("-fx-background-color: grey;");
	                    break;
	                    
	                case GameCell: 
	                    cellText=game.getGrid().getGrid()[i][j].getValue().toString() ;
	                    cellLabel.setText(cellText);
	                    break;
	                    
	                default:
	                    break;
	                }
				}
			}
			return goalGridpane;
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
	                	button = (Button) gridpane.getChildren().get(i*grid.getNbColumns()+j); 
	    				button.setText(buttonText);
	                    break;
	                case GameCell: 
	                    buttonText=grid.getGrid()[i][j].getValue().toString() ;
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
