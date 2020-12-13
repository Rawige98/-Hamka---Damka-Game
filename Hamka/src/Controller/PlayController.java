package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Game;
import Model.TimeForPlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class PlayController implements Initializable {
	
    @FXML
    private Circle player1image;

    @FXML
    private Label player1;

    @FXML
    private Circle player2image;

    @FXML
    private Label player2;
    @FXML
    private Label point1;

    @FXML
    private Label playerTimer;
    @FXML
    private Label point2;
    
	@FXML
	void closeWindow(ActionEvent event) {
		((Stage) player1.getScene().getWindow()).close();
	}
	public void back(ActionEvent event) throws Exception {
		((Stage) player1.getScene().getWindow()).close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
		Scene scene = new Scene(root, 581, 449);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hamka game");
		primaryStage.show();
	}


	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		player1.setText(MainPageController.getPlayer1().getUsername());
		player2.setText(MainPageController.getPlayer2().getUsername());	
		point1.setText(Integer.toString(MainPageController.getPlayer1().getScore()));
		point2.setText(Integer.toString(MainPageController.getPlayer2().getScore()));

		
		
	}

}
