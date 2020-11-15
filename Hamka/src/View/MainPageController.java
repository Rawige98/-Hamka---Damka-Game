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
import javafx.stage.Stage;

public class MainPageController implements Initializable {
	
	@FXML
    private Button play;

    @FXML
    private Button rules;

    @FXML
    private Button question;

    @FXML
    private Button history;

    @FXML
    private Button exit;

	
	
	
    public void closeWindow()
    {
		((Stage) exit.getScene().getWindow()).close();
	}

	
	public void Question(ActionEvent event) throws Exception {
		closeWindow();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Questions.fxml"));
		Scene scene = new Scene(root, 450, 456);
		primaryStage.setScene(scene);
		primaryStage.setTitle("questions");
		primaryStage.show();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	

}

