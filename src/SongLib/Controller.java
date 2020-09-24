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
	boolean editFlag;
	private ObservableList<String> obsList;
	private ArrayList<SongObj> obsListMirror = new ArrayList<SongObj>();
	private Stage main;
	public class SongObj {
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
			ext = artist+" "+ song+" "+year+" "+album;
			this.artist = artist;
			this.song = song;
			this.year = year;
			this.album = album;
			
		}
		public String toString() {
			return ext;
		}
	}
	
	public void Move(ActionEvent e) {
		//handles moving in the list with the right and left buttons
	}
	public void editSong(ActionEvent e) {
		//handles song edits by modifying song within list
	}
	
	public void enterSong(ActionEvent e) {
		//creates and enters a song object into the observable list
		String artist = ArtistField.getText();
		String song = SongField.getText();
		if (artist.equals("") || song.equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Input Error");
			alert.setHeaderText("Artist or Song field has not been filled");
			alert.setContentText("Both Artist and Song fields must be filled for the song to be entered.");
			alert.showAndWait();
			return;
		}
		int mark = check(artist,song);
		if (check(artist,song) > 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Duplicate Error");
			alert.setHeaderText("The song is already present in the database");
			alert.setContentText("To view the song, please select it from the window on the right.");
			alert.showAndWait();
			return;
		} else
		{
			mark*=-1;
			mark -= 1;
			String year = YearField.getText();
			String album = AlbumField.getText();
			if (year.equals("")) {year = "null";}
			if (album.equals("")) {album = "null";}
			obsListMirror.add(mark, new SongObj(artist,song, year,album));
			obsList.add(mark,obsListMirror.get(mark).ext);
			listView.setItems(obsList);
			showItem(main);
		}
		
	}
	private int check(String artist, String song) {
		//negative number means song is not in list, gives where item would be if inserted +1
		//positive number is index of matching song
		return 0; //placeholder, will implement later
		
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
			
			int ind = listView.getSelectionModel().getSelectedIndex();
			SongObj ref= obsListMirror.get(ind);
			out = ref.artist + " " + ref.song + "\n";
			if (!ref.year.equals("null")) {
				out += ref.year + " ";
			}
			if (!ref.album.equals("null")) {
				out += ref.album;
			}
		}
		SongDisplay.setText(out);
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
				cont.add(temp.artist + "-" + temp.song);
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
						for (int i = 0;i<obsList.size();i++) {
							pw.write(obsList.get(i) + "\n");
						}
					}
				});
			}
		});
	}
	

}
