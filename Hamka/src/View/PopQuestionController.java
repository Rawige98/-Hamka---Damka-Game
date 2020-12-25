package View;

import java.net.URL;
import java.util.ResourceBundle;

import Controller.PopQ;
import Model.Question;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    
    public void closeWindow() {
		((Stage) ans4.getScene().getWindow()).close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Question q = p.popQuestion();
		question.setText(q.getText());
		ans1.setText(q.getAnswers().get(0));
		ans2.setText(q.getAnswers().get(1));
		ans3.setText(q.getAnswers().get(2));
		ans4.setText(q.getAnswers().get(3));
		
//		question.getScene().setFill(Color.TRANSPARENT);
//		stage.initStyle(StageStyle.TRANSPARENT);
			
			
		//((Object) question.getScene().getWindow()).initStyle(StageStyle.TRANSPARENT);
		
	}

}
