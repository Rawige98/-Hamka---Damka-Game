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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class UpdateQuestionController implements Initializable  {
	
	
	@FXML
	private  TextArea text = new TextArea();

	@FXML
	private  TextField ans1 = new TextField();

	@FXML
	private TextField ans2 = new TextField();

	@FXML
	private TextField ans3 = new TextField();

	@FXML
	private TextField ans4 = new TextField();

	@FXML
	private ComboBox<Difficulty> diff = new ComboBox<Difficulty>();

	@FXML
	private ComboBox<E_Teams> team = new ComboBox<E_Teams>();

	@FXML
	private Button update;

	@FXML
	private RadioButton r1 = new RadioButton();

	@FXML
	private ToggleGroup right;

	@FXML
	private RadioButton r2 = new RadioButton();

	@FXML
	private RadioButton r3 = new RadioButton();

	@FXML
	private RadioButton r4 = new RadioButton();
	
	public void closeWindow() {
		((Stage) r3.getScene().getWindow()).close();
	}
	
//	public void updateQuestion(ActionEvent event) throws Exception {
//
//		Stage primaryStage = new Stage();
//		Parent root = FXMLLoader.load(getClass().getResource("/View/UpdateQuestion.fxml"));
//		Scene scene = new Scene(root, 400, 400);
//		primaryStage.setScene(scene);
//		primaryStage.setTitle("update question");
//		primaryStage.show();
//
//	}
	
	public void update(ActionEvent event) throws Exception {

		String ques = text.getText();
		String answer1 = ans1.getText();
		String answer2 = ans2.getText();
		String answer3 = ans3.getText();
		String answer4 = ans4.getText();
		int rightAnswer;
		Difficulty d = diff.getValue();
		E_Teams t = team.getValue();
		if (r1.isSelected()) {
			rightAnswer = 1;
		} else if (r2.isSelected()) {
			rightAnswer = 2;
		} else if (r3.isSelected()) {
			rightAnswer = 3;
		} else {
			rightAnswer = 4;
		}
		Question q1 = new Question(ques, d, rightAnswer, t);
		q1.addAnswer(answer1);
		q1.addAnswer(answer2);
		q1.addAnswer(answer3);
		q1.addAnswer(answer4);
		System.out.println(QuestionController.updatedQ.getText());
		SysData.getInstance().addQuestion(q1);
		SysData.getInstance().removeQuestion(QuestionController.updatedQ);
		//SysData.getInstance().saveQuestions(null);
		((Stage) ans3.getScene().getWindow()).close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Questions.fxml"));
		Scene scene = new Scene(root, 450, 456);
		primaryStage.setScene(scene);
		primaryStage.setTitle("questions");
		primaryStage.show();
		
	}
	
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		

		Question q = QuestionController.updatedQ;
		//Question q = list.getSelectionModel().getSelectedItem();
		text.setText(q.getText());
		System.out.println(text.getText());
		ans1.setText(q.getAnswers().get(0));
		ans2.setText(q.getAnswers().get(1));
		ans3.setText(q.getAnswers().get(2));
		ans4.setText(q.getAnswers().get(3));
		int rightAnswer = q.getRightAnswer();
		if (rightAnswer == 1) {
			r1.setSelected(true);
		} else if (rightAnswer == 2) {
			r2.setSelected(true);
		} else if (rightAnswer == 3) {
			r3.setSelected(true);
		} else {
			r4.setSelected(true);
		}
		diff.setValue(q.getDifficulty());
		team.setValue(q.getTeam());
		ObservableList<Difficulty> list=FXCollections.observableArrayList(Difficulty.values());
		diff.setItems(list);
		ObservableList<E_Teams> list2=FXCollections.observableArrayList(E_Teams.values());
		team.setItems(list2);
		
	}

}
