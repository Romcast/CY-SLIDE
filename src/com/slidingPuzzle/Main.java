import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
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
	
	int i;
	Player[] playerArray= new Player[5];
	int j;
	 
	@Override
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
		try {
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
			                                       	vboxP2[j].getChildren().add(Levelj);
			                                       	vboxP2[j].setAlignment(Pos.CENTER);
			                                        newrootPlayer2.setCenter(vboxP2[j]);
		                                       }
											   
											   Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
											   primaryStage.setScene(new Scene(newrootPlayer2,screenBounds.getWidth(), screenBounds.getHeight()-25));
											   
											   
										   }});
									   pseudonyme1.setFont(new Font("Berlin Sans FB",50));
									   btnpseudo1.setFont(new Font("Berlin Sans FB",25));
									   vboxP1.setPadding(new Insets(0, 600, 0, 600));
									   vboxP1.getChildren().addAll(pseudonyme1,choosePseudo,btnpseudo1);
									   vboxP1.setAlignment(Pos.CENTER);
									   newrootPlayer1.setCenter(vboxP1);
									   Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
									   primaryStage.setScene(new Scene(newrootPlayer1,screenBounds.getWidth(), screenBounds.getHeight()-25));
								   }});
							   tmp.setFont(new Font("Berlin Sans FB",80));
							   
							   pseudo.put(i, tmp);
						   }else {
							   final int index = i;
							   Hyperlink tmp=new Hyperlink(playerArray[i].getPseudo());
							   tmp.setFont(new Font("Berlin Sans FB",80));
							   tmp.setOnAction(new EventHandler<ActionEvent>(){
								   
								   public void handle(ActionEvent t){
									   BorderPane newrootPlayer2 =new BorderPane();
                                       int maxLevel=playerArray[index].getLevelMax();
                                       VBox description=new VBox();
									   VBox[] vboxP2=new VBox[maxLevel];

									    Label Level= new Label("Level  Score Shuffle  ShuffleRandom ");
									    Level.setFont(new Font("Berlin Sans FB",40));
                                        
                                       // Label scoreShuffle= new Label("Score Shuffle");
                                        //Label scoreRandomShuffle=new Label("Score ShuffleRandom");
                                        description.getChildren().add(Level);
                                        description.setAlignment(Pos.CENTER);
                                        newrootPlayer2.setTop(description);
                                        for(j=0;j<maxLevel;j++){
                                        	vboxP2[j] = new VBox();
	                                        Hyperlink Levelj= new Hyperlink("Level"+(j+1));
	                                        Levelj.setFont(new Font("Berlin Sans FB",40));
	                                        //Label scoreShuffle= new Label("Score Shuffle");
	                                        //Label scoreRandomShuffle=new Label("Score ShuffleRandom");
	                                       	vboxP2[j].getChildren().add(Levelj);
	                                       	vboxP2[j].setAlignment(Pos.CENTER);
	                                        newrootPlayer2.setCenter(vboxP2[j]);
                                       }
									   
									   Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
									   primaryStage.setScene(new Scene(newrootPlayer2,screenBounds.getWidth(), screenBounds.getHeight()-25));
								   }});
							   
							   
							   pseudo.put(i, tmp); 
						   }
						   
					   }
					   vboxPlayer1.getChildren().addAll(labelPlayer1,pseudo.get(0),pseudo.get(1),pseudo.get(2),pseudo.get(3),pseudo.get(4));
					   vboxPlayer1.setAlignment(Pos.CENTER);
					   newroot.setTop(vboxPlayer1);
					 Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
				     primaryStage.setScene(new Scene(newroot,screenBounds.getWidth(), screenBounds.getHeight()-25));
				   }
				   });
			
			//vboxPlayer.getChildren().add(play);
			vbox1.setAlignment(Pos.CENTER);
			vbox2.setAlignment(Pos.CENTER);
			
			root.setTop(vbox1);
			root.setCenter(vbox2);
			
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			Scene scene1 = new Scene(root,screenBounds.getWidth(), screenBounds.getHeight()-25);
			
			
			
			//scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//primaryStage.setFullScreen(true);
			primaryStage.setScene(scene1);
			primaryStage.show();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static Player[] readPlayerFile() throws IOException, ClassNotFoundException {
		try {																// verifies if the file exists and is not empty
			FileInputStream fis = new FileInputStream("Player.txt");
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

	
	public static void main(String[] args) {
		launch(args);
	}
}

