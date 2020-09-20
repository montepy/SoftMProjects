package SongLib;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
//this class gets fetched from AnchorPane in FXML
public class ListController {
	@FXML
	ListView<String> listView;
	
	private ObservableList<String> obsList;
	
	public void start() {
		//creates obslist and sets listview to display it. will need to modify later.
		obsList = FXCollections.observableArrayList();
		listView.setItems(obsList);
	}

}
