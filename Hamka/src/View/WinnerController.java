package View;

import java.net.URL;
import java.util.ResourceBundle;

import Controller.PlayGameController;
import Model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class WinnerController  implements Initializable{

	    @FXML
	    private Label winner;

	    @FXML
	    private Label score;

	    @FXML
	    private Button back;
	    
	    
	    public void closeWindow() {
			((Stage) back.getScene().getWindow()).close();
		}
		public void back(ActionEvent event) throws Exception {
			closeWindow();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
			Scene scene = new Scene(root, 581, 449);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Hamka Game");
			primaryStage.show();
		}
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		PlayGameController.getInstance().getGame();
		
			winner.setText(PlayGameController.getInstance().getGame().getWinner().getUsername());
			score.setText(Integer.toString(PlayGameController.getInstance().getGame().getWinner().getScore()));
		
		
	}

}
