package View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AdminController implements Initializable {
	
	   @FXML
	    private PasswordField pass;

	    @FXML
	    private Button login;

	    @FXML
	    private TextField userName;
	    @FXML
	    private Button back;

	  
	    void closeWindow() {
	    	((Stage) login.getScene().getWindow()).close();
	    }
		public void Wrong(String title, String header, String content) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(content);
			alert.showAndWait();
		}
	    
		public void login(ActionEvent event) throws Exception {
			String name = userName.getText();
			String pass = this.pass.getText();
			if(!name.equals("ADMIN"))
			{
				Wrong("error", "Wrong User Name", "enter a correct userName");
			}
			else
			{
				if(!pass.equals("admin"))
				{
					Wrong("error", "Wrong password", "enter a correct password");
				}
				else
				{
					closeWindow();
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/View/Questions.fxml"));
					Scene scene = new Scene(root, 456, 450);
					primaryStage.setScene(scene);
					primaryStage.setTitle("questions");
					primaryStage.show();
					
				}
			}

		}
	    
		  @FXML
		    void back(ActionEvent event) throws IOException {
			  ((Stage) back.getScene().getWindow()).close();
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
				Scene scene = new Scene(root,581, 449);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Main");
				primaryStage.show();

		    }
		    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
