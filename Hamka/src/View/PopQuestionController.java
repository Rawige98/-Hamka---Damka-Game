package View;

import java.net.URL;
import java.util.ResourceBundle;

import Controller.PlayGameController;
import Controller.PopQ;
import Model.Player;
import Model.Question;
import Utils.Difficulty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopQuestionController implements Initializable {

	@FXML
	private Button check;

	@FXML
	private Text question;

	@FXML
	private RadioButton ans1;

	@FXML
	private ToggleGroup answers;

	@FXML
	private RadioButton ans2;

	@FXML
	private RadioButton ans3;

	@FXML
	private RadioButton ans4;
	PopQ p = new PopQ();
	Question q;

	int rightA;

	public void closeWindow() {
		((Stage) ans4.getScene().getWindow()).close();
	}

	public void check(ActionEvent event) throws Exception {
		boolean isP1Turn = PlayGameController.getInstance().getGame().isP1Turn();
		Player currPlayer;
		if (!isP1Turn) {
			currPlayer = PlayGameController.getInstance().getGame().getPlayer1();
		} else {
			currPlayer = PlayGameController.getInstance().getGame().getPlayer2();

		}

		if ((ans1.isSelected() && rightA == 1) || (ans2.isSelected() && rightA == 2)
				|| (ans3.isSelected() && rightA == 3) || (ans4.isSelected() && rightA == 4)) {
			if (q.getDifficulty().equals(Difficulty.HARD)) {
				currPlayer.updateScore(500);
			} else if (q.getDifficulty().equals(Difficulty.MEDIUM)) {
				currPlayer.updateScore(200);

			} else if (q.getDifficulty().equals(Difficulty.EASY)) {
				currPlayer.updateScore(100);

			}

			System.out.println("true");
		} else {
			if (q.getDifficulty().equals(Difficulty.HARD)) {
				currPlayer.updateScore(-50);
			} else if (q.getDifficulty().equals(Difficulty.MEDIUM)) {
				currPlayer.updateScore(-100);

			} else if (q.getDifficulty().equals(Difficulty.EASY)) {
				currPlayer.updateScore(-250);

			}

			System.out.println("false");
		}
		closeWindow();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		q = p.popQuestion();
		ans1.setVisible(true);
		ans2.setVisible(true);
		ans3.setVisible(true);
		ans4.setVisible(true);
		rightA = q.getRightAnswer();
		question.setText(q.getText());
		ans1.setText(q.getAnswers().get(0));
		ans2.setText(q.getAnswers().get(1));
		if (q.getAnswers().get(2).equals("")) {
			ans3.setVisible(false);
			ans4.setVisible(false);
		} else {
			ans3.setText(q.getAnswers().get(2));
			ans4.setText(q.getAnswers().get(3));

		}

//		question.getScene().setFill(Color.TRANSPARENT);
//		stage.initStyle(StageStyle.TRANSPARENT);

		// ((Object) question.getScene().getWindow()).initStyle(StageStyle.TRANSPARENT);

	}

}
