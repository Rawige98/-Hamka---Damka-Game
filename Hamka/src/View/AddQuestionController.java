package View;



import java.net.URL;
import java.util.ResourceBundle;

import Model.Question;
import Model.SysData;
import Utils.Difficulty;
import Utils.E_Teams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddQuestionController implements Initializable {
	

	/**********************************************
	 * add question controller
	 ****************************/

	@FXML
	private TextArea text;

	@FXML
	private TextField ans1;

	@FXML
	private TextField ans2;

	@FXML
	private TextField ans3;

	@FXML
	private TextField ans4;

	@FXML
	private ComboBox<Difficulty> diff;

	@FXML
	private ComboBox<E_Teams> team;

	@FXML
	private Button add;
	@FXML
	private RadioButton r1;

	@FXML
	private RadioButton r3;

	@FXML
	private RadioButton r2;

	@FXML
	private RadioButton r4;
	@FXML
	private ToggleGroup right;
	
	public void closeWindow() {
		((Stage) add.getScene().getWindow()).close();
	}
	public void back(ActionEvent event) throws Exception {
	closeWindow();
	Stage primaryStage = new Stage();
	Parent root = FXMLLoader.load(getClass().getResource("/View/Questions.fxml"));
	Scene scene = new Scene(root, 456, 450);
	primaryStage.setScene(scene);
	primaryStage.setTitle("questions");
	primaryStage.show();
	}
	

	public void add(ActionEvent event) throws Exception {
		String ques = text.getText();
		String answer1 = ans1.getText();
		String answer2 = ans2.getText();
		String answer3 = ans3.getText();
		String answer4 = ans4.getText();
		int rightAnswer;
		
		
		if (r1.isSelected()) {
			rightAnswer = 1;
		} else if (r2.isSelected()) {
			rightAnswer = 2;
		} else if (r3.isSelected()) {
			rightAnswer = 3;
		} else {
			rightAnswer = 4;
		}
		if(!r1.isSelected() && !r2.isSelected() && !r3.isSelected()&&!r4.isSelected())
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("right answer");
			alert.setContentText("You must select the right answer!");
			alert.show();
			
		}
		else
		{
			if(diff.getValue()==null || team.getValue()==null)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("difficulty\team");
				alert.setContentText("You must select difficulty and team!");
				alert.show();
				
			}
			
		
			else
			{
				Difficulty d = diff.getValue();
				E_Teams t =team.getValue();
				Question q = new Question(ques, d, rightAnswer, t);
				if(q.getAnswers().contains(answer1) ||q.getAnswers().contains(answer2) ||q.getAnswers().contains(answer3)
						||q.getAnswers().contains(answer4))
				{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("same answer");
					alert.setContentText("You must enter different answers!");
					alert.show();
					 
				}
			
				else
				{
				
				q.addAnswer(answer1);
				q.addAnswer(answer2);
				q.addAnswer(answer3);
				q.addAnswer(answer4);
				SysData.getInstance().addQuestion(q);
				//SysData.getInstance().loadQuestions(null);
				//SysData.getInstance().saveQuestions(null);
				closeWindow();
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/View/Questions.fxml"));
				Scene scene = new Scene(root, 450, 456);
				primaryStage.setScene(scene);
				primaryStage.setTitle("questions");
				primaryStage.show();
				}
			}
			
			
		}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		ObservableList<Difficulty> list=FXCollections.observableArrayList(Difficulty.values());
		diff.setItems(list);
		ObservableList<E_Teams> list2=FXCollections.observableArrayList(E_Teams.values());
		team.setItems(list2);
		
	}


}
