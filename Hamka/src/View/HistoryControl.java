package View;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import Model.Game;
import Model.SysData;
import Utils.DataType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import java.io.IOException;

public class HistoryControl implements Initializable {

	@FXML
	private ListView<Game> HistoryList;
	ObservableList<Game> observableList = FXCollections.observableArrayList();
	Set<Game> finishedGames;
	ArrayList<Game> arr = SysData.getInstance().getGames();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		SysData.getInstance().loadData(DataType.FINISHED_GAMES);
		finishedGames = new HashSet<Game>();
		finishedGames.addAll(arr);
		setListView();
	}

	public void setListView() {

		observableList.setAll(finishedGames);
		HistoryList.setItems(observableList);
		HistoryList.setCellFactory(new Callback<ListView<Game>, javafx.scene.control.ListCell<Game>>() {
			@Override
			public ListCell<Game> call(ListView<Game> listView) {
				return new ListViewCell();
			}

		});

	}

	public class ListViewCell extends ListCell<Game> {
		@Override
		public void updateItem(Game string, boolean empty) {
			super.updateItem(string, empty);
			if (string != null) {
				Data data = new Data();
				
				data.setInfo(string);
				setGraphic(data.getBox());
			}
		}

	}

}
