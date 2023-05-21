package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.geometry.Pos;
//import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.event.*;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			VBox vbox1=new VBox();
			VBox vbox2=new VBox();
			
			Label label = new Label("CY-SLIDE");
			label.setFont(new Font("Berlin Sans FB",164));
			
			Button button= new Button("PLAY");
			button.setFont(new Font("Berlin Sans FB",64));
			Label play = new Label("Choose your player");
			VBox vboxPlayer=new VBox();
	        button.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                root.getChildren().setAll(vboxPlayer);
	            }
	        });
	        
			vbox1.getChildren().add(label);
			vbox2.getChildren().add(button);
			vboxPlayer.getChildren().add(play);
			vbox1.setAlignment(Pos.CENTER);
			vbox2.setAlignment(Pos.CENTER);
			root.setTop(vbox1);
			root.setCenter(vbox2);
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			Scene scene = new Scene(root,screenBounds.getWidth(), screenBounds.getHeight()-25);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//primaryStage.setFullScreen(true);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

