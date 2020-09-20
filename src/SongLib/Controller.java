package SongLib;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
//this class gets fetched from AnchorPane in FXML
public class Controller {
	//FXML directive links widget to fxml element
	@FXML Button Edit;
	@FXML Button Left;
	@FXML Button Right;
	@FXML Button Delete;
	@FXML Button Enter;
	@FXML TextField ArtistField;
	@FXML TextField SongField;
	@FXML TextField YearField;
	@FXML TextField AlbumField;
	@FXML ScrollPane SongList;
	@FXML Pane SongDisplay;
	
	ListView<String> listView;
	
	private ObservableList<String> obsList;
	
	public void Move(ActionEvent e) {
		//handles moving in the list with the right and left buttons
	}
	public void editSong(ActionEvent e) {
		//handles song edits by modifying song within list
	}
	
	public void enterSong(ActionEvent e) {
		//creates and enters a song object into the observable list
	}
	
	public void deleteSong(ActionEvent e) {
		//removes a song from the list
	}
	
	private void showItem(Stage mainStage) {
		//handles selection and display of items
	}
	
	public void start() {
		//creates obslist and sets listview to display it. will need to modify later.
		try {
			File songData = new File("SongData.txt");
			if (songData.createNewFile()) {
				obsList = FXCollections.observableArrayList();
			} else {
				obsList = FXCollections.observableArrayList(Files.readAllLines(Paths.get("../SongData.txt")));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		listView.setItems(obsList);
	}

}
