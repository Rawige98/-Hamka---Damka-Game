package View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controller.PlayGameController;
import Model.Game;
import Model.SysData;
import Utils.DataType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class LoadGameControl implements Initializable{
    @FXML
    private ListView<Game> LoadList;
    @FXML
    void LoadBPressed(ActionEvent event) {
    	Game game=LoadList.getSelectionModel().getSelectedItem();
		PlayGameController.getInstance().setGame(game);
		PlayGameController.getInstance().setPlayer1(game.getPlayer1());
		PlayGameController.getInstance().setPlayer2(game.getPlayer2());
		Stage primaryStage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/View/Play.fxml"));
			Scene scene = new Scene(root, 650, 600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Play");
			primaryStage.show();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    }
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SysData.getInstance().LoadGames(DataType.PAUSED_GAMES);
		LoadList.getItems().addAll(SysData.getInstance().getPausedGames());
	}
}
