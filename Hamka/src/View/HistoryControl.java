package View;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import Controller.PlayGameController;
import Model.Game;
import Model.Player;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;

public class HistoryControl implements Initializable {

	@FXML
	private ListView<Game> HistoryList;
	ObservableList<Game> observableList = FXCollections.observableArrayList();
	TreeSet<Game> finishedGames;
	ArrayList<Game> arr ;
	 @FXML
	    private Button back;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		SysData.getInstance().LoadGames(DataType.FINISHED_GAMES);
		finishedGames = new TreeSet<Game>(new Comparator<Game>() {

			@Override
			public int compare(Game o1, Game o2) {
				if(o1.getWinner().getScore()>=o2.getWinner().getScore())
					return -1;
				else
					return 1;
			}
		});
		arr=SysData.getInstance().getGames();
		finishedGames.addAll(arr);
		setListView();
	}
	  @FXML
	    void BackPressed(ActionEvent event) throws IOException {
		  ((Stage) back.getScene().getWindow()).close();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
			Scene scene = new Scene(root,581, 449);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Main");
			primaryStage.show();
	    }

	public void setListView() {
		while(finishedGames.size()>10) {
			finishedGames.remove(finishedGames.last());
		}
		SysData.getInstance().saveGame(DataType.FINISHED_GAMES,PlayGameController.getInstance().getGame());
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
