package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Game;
import Model.TimeForPlayer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

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
    
	public void Runtime(TimeForPlayer myTimer) {
		Thread t = new Thread(myTimer);
		t.start();
	}

	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		player1.setText(MainPageController.getPlayer1().getUsername());
		player2.setText(MainPageController.getPlayer2().getUsername());	
		point1.setText(Integer.toString(MainPageController.getPlayer1().getScore()));
		point2.setText(Integer.toString(MainPageController.getPlayer2().getScore()));
//		TimeForPlayer t = new TimeForPlayer();
//		Runtime(t);
//		playerTimer.setText(t.getMints() +" : "+t.getSecond());
		
		
	}

}
