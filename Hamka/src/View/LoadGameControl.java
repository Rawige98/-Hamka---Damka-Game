package View;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LoadGameControl implements Initializable{
    @FXML
    private ListView<Game> LoadList;
    @FXML
    private Button back;

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
	@FXML
	void uploadURL(ActionEvent event) {
		Button sellectedBtn = (Button) event.getSource();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(sellectedBtn.getScene().getWindow());
		String fileName=null;
		if(file!=null) {
		 fileName=file.getAbsolutePath();
		SysData.getInstance().LoadState(fileName);
		LoadList.getItems().clear();
		LoadList.getItems().addAll(SysData.getInstance().getPausedGames());
		}
	}
    @FXML
    void BackPressed(ActionEvent event) throws IOException {
    	((Stage) back.getScene().getWindow()).close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
		Scene scene = new Scene(root, 581, 449);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Main");
		primaryStage.show();
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SysData.getInstance().LoadGames(DataType.PAUSED_GAMES);
		LoadList.getItems().addAll(SysData.getInstance().getPausedGames());
	}
}
