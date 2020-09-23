package SongLib;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

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
	@FXML Label SongDisplay;
	@FXML ListView<String> listView;
	
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
		String out;
		if (listView.getSelectionModel().getSelectedItem().equals(null)) {
			out =" ";
		} else {
			
			String text[] = listView.getSelectionModel().getSelectedItem().split(" ");
			out = text[0] + " " + text[1] + "\n";
			if (!text[2].equals("null")) {
				out += text[2] + " ";
			}
			if (!text[3].equals("null")) {
				out += text[3];
			}
		}
		SongDisplay.setText(out);
	}
	
	public void start(Stage mainStage) {
		//creates obslist and sets listview to display it. will need to modify later.
		File temp = new File(SongLib.class.getResource("SongData.txt").getFile());
		try {
			obsList = FXCollections.observableArrayList(Files.readAllLines(temp.toPath(),StandardCharsets.UTF_8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		listView.setItems(obsList);
		//select first item
		listView.getSelectionModel().select(0);
		//set listener for the items
		listView.getSelectionModel().selectedIndexProperty().addListener((obs,oldVal,newVal)->showItem(mainStage));
		//Song format should be "name artist year album" where year and album may be replaced with null if they do not exist.
	}

}
