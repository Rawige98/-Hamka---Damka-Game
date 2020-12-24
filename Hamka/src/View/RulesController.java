package View;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RulesController implements Initializable {
	
	

    @FXML
    private Button score;

    @FXML
    private Button square;

    @FXML
    private Button overView;

    @FXML
    private Button goal;
    @FXML
    private AnchorPane viewPane;
    @FXML
    private AnchorPane goalPane;
    @FXML
    private AnchorPane scorePane;
    @FXML
    private AnchorPane squaresPane;
    
    public void closeWindow() {
		((Stage) goal.getScene().getWindow()).close();
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

	public void OpenOverView(ActionEvent event) throws Exception {
		goalPane.setVisible(false);
		scorePane.setVisible(false);
		squaresPane.setVisible(false);
		viewPane.setVisible(true);
	
	}
	public void OpensquarePane(ActionEvent event) throws Exception {
		goalPane.setVisible(false);
		scorePane.setVisible(false);
		viewPane.setVisible(false);
		squaresPane.setVisible(true);
	
	}
	public void OpenscorePane(ActionEvent event) throws Exception {
		goalPane.setVisible(false);
		viewPane.setVisible(false);
		squaresPane.setVisible(false);
		scorePane.setVisible(true);
	
	}
	
	public void OpenGoalPane(ActionEvent event) throws Exception {
		viewPane.setVisible(false);
		scorePane.setVisible(false);
		squaresPane.setVisible(false);
		goalPane.setVisible(true);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		viewPane.setVisible(true);
		goalPane.setVisible(false);
		scorePane.setVisible(false);
		squaresPane.setVisible(false);
		
	}

}
