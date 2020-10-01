package SongLib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
//this class gets fetched from AnchorPane in FXML
//Edward Wang
public class Controller {
	//FXML directive links widget to fxml element
	@FXML Button Edit;
	@FXML Button Delete;
	@FXML Button Enter;
	@FXML TextField ArtistField;
	@FXML TextField SongField;
	@FXML TextField YearField;
	@FXML TextField AlbumField;
	@FXML ScrollPane SongList;
	@FXML Label SongDisplay;
	@FXML ListView<String> listView;
	boolean editFlag = false;
	int save = -1;
	private ObservableList<String> obsList;
	private ArrayList<SongObj> obsListMirror = new ArrayList<SongObj>();
	private Stage main;
	
	public class SongObj implements Comparable<SongObj> {
		String artist,song,year, album,ext;
		public SongObj(String item) {
			ext = item;
			String[] data = item.split(" ");
			this.artist = data[0];
			this.song = data[1];
			this.year = data[2];
			this.album = data[3];
		}
		public SongObj(String artist,String song,String year,String album) {
			ext = artist.replace(" ", "_")+" "+ song.replace("_", " ")+" "+year.replace("_", " ")+" "+album.replace("_", " ");
			this.artist = artist;
			this.song = song;
			this.year = year;
			this.album = album;
			
		}
		public String resetExt() {
			ext = artist+" "+ song+" "+year+" "+album;
			return ext;
		}
		public String toString() {
			return ext;
		}
		@Override
		public int compareTo(SongObj o) {
			// TODO Auto-generated method stub
			if (this.song.compareTo(o.song) > 0) {
				return 1;
			} 
			if (this.song.compareTo(o.song) < 0){
				return -1;
			}
			//if the songs are equal, compare based off of artist.
			if (this.artist.compareTo(o.artist) >0 ) {
				return 1;
			} 
			if (this.artist.compareTo(o.artist) < 0) {
				return -1;
			}
			//if both artist and song are equal, return 0
			return 0;
		}
		
	}
	
	public void Move(ActionEvent e) {
		//handles moving in the list with the right and left buttons
	}
	public void editSong(ActionEvent e) {
		//happens on edit button press
		if (obsList.size() == 0) {return;}
		Enter.setText("Enter Edit");
		editFlag = true;
		save = listView.getSelectionModel().getSelectedIndex();
	
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Entering Edits");
		alert.setHeaderText("Fill fields and then press the Enter Edit button.");
		alert.setContentText("Any fields not filled will remain the same.\n Enter empty into a field(year or album) if you wish to erase it.");
		alert.showAndWait();
		
	}
	
	public void enterSong(ActionEvent e) {
		//creates and enters a song object into the observable list
		String artist = ArtistField.getText();
		String song = SongField.getText();
		String year = YearField.getText();
		String album = AlbumField.getText();
		if (artist.trim().contains(" ")) {
			artist.replace(" ", "_");
		}
		if (album.trim().contains(" ")) {
			album.replace(" ", "_");
		}
		if (song.trim().contains(" ")) {
			song.replace(" ", "_");
		}
		if (editFlag) {
			int ind = listView.getSelectionModel().getSelectedIndex();
			SongObj toBeEdited = obsListMirror.get(ind);
			if (!artist.trim().equals("")) {toBeEdited.artist = artist;}
			if (!song.trim().equals("")) {toBeEdited.song = song;}
			if (!album.equals("")) {
				if (album.equals("empty"))
					{toBeEdited.album = "null";}
				else {toBeEdited.album = year;}
			}
			if (!year.equals("")) {
				if (year.equals("empty"))
					{toBeEdited.year = "null";}
				else {toBeEdited.year = year;}
			}
			toBeEdited.resetExt();
			obsList.set(ind, toBeEdited.artist.replace("_", " ") + " " + toBeEdited.song.replace("_", " "));
			listView.setItems(obsList);
			listView.getSelectionModel().select(ind);
			showItem(main);
			editFlag = false;
			save = -1;
			Enter.setText("Enter Song");
			
			clearFields();
			return;
			
			
			
		}
		
		if (year.equals("")) {year = "null";}
		if (album.equals("")) {album = "null";}
		SongObj out = new SongObj(artist,song,year,album);
		
		if (artist.equals("") || song.equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Input Error");
			alert.setHeaderText("Artist or Song field has not been filled");
			alert.setContentText("Both Artist and Song fields must be filled for the song to be entered.");
			alert.showAndWait();
			clearFields();
			return;
		}
		if (check(out)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Duplicate Error");
			alert.setHeaderText("The song is already present in the database");
			alert.setContentText("To view the song, please select it from the window on the right.");
			alert.showAndWait();
			clearFields();
			return;
		} else
		{
			obsListMirror.add(out);
			Collections.sort(obsListMirror);
			int ind = obsListMirror.indexOf(out);
			obsList.add(ind, out.artist.replace("_", " ")+" "+out.song.replace("_", " "));
			listView.setItems(obsList);
			listView.getSelectionModel().select(ind);
			showItem(main);
		}
		clearFields();
		
	}
	public void clearFields() {

		ArtistField.clear();
		SongField.clear();
		AlbumField.clear();
		YearField.clear();
	}
	private boolean check(SongObj out) {
		//returns boolean telling whether object is in list.
		for (int i =0;i< obsListMirror.size();i++) {
			if (obsListMirror.get(i).compareTo(out) == 0) {
				return true;
			}
		}
		return false; 
		
	}
	
	public void deleteSong(ActionEvent e) {
		//removes a song from the list
		if(obsList.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("CAUTION");
			alert.setHeaderText("There is nothing to delete");
			alert.setContentText("The song list is empty - there is nothing to delete.");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("CAUTION");
			alert.setHeaderText("The selected song is about to be deleted from your song library");
			alert.setContentText("Are you sure you want to delete the selected song from your library?");
			alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {
				int ind = listView.getSelectionModel().getSelectedIndex();
				obsListMirror.remove(ind);
				obsList.remove(ind);
				/*if (ind == obsList.size()-1) {
					ind = 0;
				}else {
					ind++;
				}*/
				listView.setItems(obsList);
				if (obsList.size() != 0) {
					showItem(main);
					listView.getSelectionModel().select(ind);
				}
				
			} else {
				return;
			}
		}

	}
	
	private void showItem(Stage mainStage) {
		//handles selection and display of items
		String out;
		if (listView.getSelectionModel().getSelectedItem() == null) {
			out =" ";
		} else {
			
			int ind = listView.getSelectionModel().getSelectedIndex();
			SongObj ref= obsListMirror.get(ind);
			
			out = ref.artist.replace("_", " ") + ", " + ref.song.replace("_"," ") + "\n";
			if (!ref.year.equals("null")) {
				out += ref.year + " ";
			}
			if (!ref.album.equals("null")) {
				out += ref.album.replace("_", " ");
			}
		}
		SongDisplay.setText(out);
		if (editFlag && listView.getSelectionModel().getSelectedIndex() != save) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Edits have been canceled");
			alert.setHeaderText("Edits have been canceled.");
			alert.showAndWait();
			clearFields();
			editFlag = false;
			Enter.setText("Enter Song");
			save = -1;
		}
	}
	
	public void start(Stage mainStage) {
		//creates obslist and sets listview to display it. will need to modify later.
		Scanner input;
		ArrayList<String> cont = new ArrayList<String>();
		this.main = mainStage;
		try {
			input = new Scanner(new File("SongData.txt"));
			while (input.hasNextLine()) {
				SongObj temp = new SongObj(input.nextLine());
				obsListMirror.add(temp);
				cont.add(temp.artist.replace("_", " ") + ", " + temp.song.replace("_", " "));
			}
			obsList = FXCollections.observableArrayList(cont);
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//obsList = FXCollections.observableArrayList(Files.readAllLines(temp.toPath(),StandardCharsets.UTF_8));
		listView.setItems(obsList);
		//select first item
		listView.getSelectionModel().select(0);
		showItem(mainStage);
		//set listener for the items
		listView.getSelectionModel().selectedIndexProperty().addListener((obs,oldVal,newVal)->showItem(mainStage));
		//Song format should be "name artist year album" where year and album may be replaced with null if they do not exist.
		
		mainStage.setOnHidden(new EventHandler<WindowEvent> () {
			@Override
			public void handle(WindowEvent event) {
				Platform.runLater(new Runnable() {
					@Override
					public void run()  {
						PrintWriter pw = null;
						try {
							pw = new PrintWriter(new FileOutputStream("SongData.txt"));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						for (int i = 0;i<obsListMirror.size();i++) {
							System.out.println(obsListMirror.get(i).ext);
							pw.write(obsListMirror.get(i).ext + "\n");
						}
						pw.flush();
					}
				});
			}
		});
	}
	

}
