package View;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class QuestionController implements Initializable {
	
	 @FXML
	    private Button back;

	    @FXML
	    private Button add;

	    @FXML
	    private Button update;

	    @FXML
	    private Button delete;

	    @FXML
	    private ListView<?> list;
	    @FXML
	    private ImageView image;

	
	
    public void closeWindow()
    {
		((Stage) back.getScene().getWindow()).close();
	}

	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Circle req = new Circle(30);
		Line l = new Line(3,50, 150, 50);
		
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
