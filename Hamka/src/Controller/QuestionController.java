package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import Model.Question;
import Model.SysData;
import Utils.Difficulty;
import javafx.animation.PathTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class QuestionController implements Initializable {

	@FXML
	private Button back;

	static Question updatedQ;
	static int flag = 1;

	@FXML
	private ListView<Question> list;
	@FXML
	private ImageView image;
	private HashMap<Difficulty, ArrayList<Question>> questions;
	SysData sysData = SysData.getInstance();
	private ArrayList<Question> level = new ArrayList<Question>();

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

	public void deleteQuestion(ActionEvent event) throws Exception {

		Question q = list.getSelectionModel().getSelectedItem();
		list.getItems().remove(q);
		SysData.getInstance().removeQuestion(q);
		//System.out.println(SysData.getInstance().getQuestions());
		//SysData.getInstance().saveQuestions(null);
		//System.out.println(SysData.getInstance().getQuestions());
		//SysData.getInstance().loadQuestions(null);
	}

	public void addQuestion(ActionEvent event) throws Exception {
	
		closeWindow();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/AddQuestion.fxml"));
		Scene scene = new Scene(root, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("add question");
		primaryStage.show();

	}

	public void updateQuestion(ActionEvent event) throws Exception {

		updatedQ = list.getSelectionModel().getSelectedItem();
		closeWindow();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/UpdateQuestion.fxml"));
		Scene scene = new Scene(root, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("update question");
		primaryStage.show();

	}

	/*************************************************************************************************/

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			SysData.getInstance().loadQuestions(null);
	
		questions = sysData.getQuestions();
		for (Difficulty d : questions.keySet()) {
			for (Question q : questions.get(d)) {
				if (!level.contains(q)) {
					level.add(q);
				}

			}
		}

		ObservableList<Question> question = FXCollections.observableArrayList(level);
		list.setItems(question);

		Circle l = new Circle(20);
		// Line l = new Line(3,50, 150, 50);

		PathTransition transition = new PathTransition();
		transition.setNode(image);
		transition.setDuration(Duration.seconds(2));
		transition.setPath(l);
		transition.setCycleCount(PathTransition.INDEFINITE);
		transition.setAutoReverse(true);
		transition.play();

		// TODO Auto-generated method stub

	}

}
