package SongLib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SongLib extends Application {
	
	public void start(Stage primaryStage) throws Exception {
		//overrides inherited start method
		FXMLLoader loading = new FXMLLoader();
		//fetch fxml file, which will be written later
		loading.setLocation(getClass().getResource(""));
		AnchorPane root = (AnchorPane)loading.load();
		
		Controller listController = loading.getController();
		listController.start();
		
		Scene scene = new Scene(root, 200,300);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	} 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

}
