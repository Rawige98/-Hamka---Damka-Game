package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Player;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainPageController implements Initializable {
	
	public static Player getPlayer1() {
		return player1;
	}

	public static void setPlayer1(Player player1) {
		MainPageController.player1 = player1;
	}

	public static Player getPlayer2() {
		return player2;
	}

	public static void setPlayer2(Player player2) {
		MainPageController.player2 = player2;
	}

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
    @FXML
    private ImageView cloud;

    @FXML
    private ImageView cloud1;

    @FXML
    private ImageView cloud2;

    @FXML
    private ImageView cloud21;
    @FXML
    private ImageView cloud3;
    @FXML
    private Button unmute;

    @FXML
    private Button mute;
    @FXML
    private Button start;

    @FXML
    private Button back;
    @FXML
    private Button closeInst;

    @FXML
    private TextField p1;

    @FXML
    private TextField p2;
    
    private static Player player1;
    private static Player player2;

    @FXML
    void closeWindowInst(ActionEvent event) {
    	((Stage) closeInst.getScene().getWindow()).close();
    }
	
    public void closeWindow()
    {
		((Stage) exit.getScene().getWindow()).close();
	}

	public void mute(ActionEvent event) throws Exception {
		unmute.setVisible(false);
		mute.setVisible(true);
	}
	public void unmute(ActionEvent event) throws Exception {
		mute.setVisible(false);
		unmute.setVisible(true);
	
	}
	public void playColor()  {
		play.setStyle("-fx-background-color: transparent;"+"-fx-background-radius: 30;"+""
				+ "-fx-border-color: silver;"+"-fx-border-radius: 30;"+"-fx-border-width: 3");
		
	}
	public void ColorBack()  {
		play.setStyle("-fx-background-color: silver;"+"-fx-background-radius: 30;");
		rules.setStyle("-fx-background-color: silver;"+"-fx-background-radius: 30;");
		history.setStyle("-fx-background-color: silver;"+"-fx-background-radius: 30;");
		
	}
	
	public void Admin(ActionEvent event) throws Exception {
		closeWindow();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/AdminLogin.fxml"));
		Scene scene = new Scene(root, 454, 236);
		primaryStage.setScene(scene);
		primaryStage.setTitle("login");
		primaryStage.show();
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
	
	public void Play(ActionEvent event) throws Exception {
		closeWindow();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/NickName.fxml"));
		Scene scene = new Scene(root, 475, 226);
		primaryStage.setScene(scene);
		primaryStage.setTitle("enter nick Name");
		primaryStage.show();
	}
	public void Rules(ActionEvent event) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Rules.fxml"));
		Scene scene = new Scene(root, 625, 504);
		primaryStage.setScene(scene);
		primaryStage.setTitle("RULES");
		primaryStage.show();
	}
	
	public void Start(ActionEvent event) throws Exception {
		player1 = new Player(p1.getText());
		player2 = new Player(p2.getText());
		((Stage) back.getScene().getWindow()).close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Play.fxml"));
		Scene scene = new Scene(root, 650, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Play");
		primaryStage.show();
	}
	public void back(ActionEvent event) throws Exception {
		((Stage) back.getScene().getWindow()).close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
		Scene scene = new Scene(root, 581, 449);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hamka game");
		primaryStage.show();
	}
	public void startColor()  {
		start.setStyle("-fx-background-color: transparent;"+"-fx-background-radius: 30;"+""
				+ "-fx-border-color: transparent;"+"-fx-border-radius: 30;"+"-fx-text-fill: #cd4f06");
		
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Line l = new Line(3,50, 150, 50);
		Line l2 = new Line(50,10, 100, 10);
		Line l3 = new Line(100,40, 3, 40);
		//Line l4 = new Line(50,50, 150, 150);
		
		
		PathTransition transition = new PathTransition();
		transition.setNode(cloud);
		transition.setDuration(Duration.seconds(10));
		transition.setPath(l);
		transition.setCycleCount(PathTransition.INDEFINITE);
		transition.setAutoReverse(true);
		transition.play();
		PathTransition transition1 = new PathTransition();
		transition1.setNode(cloud2);
		transition1.setDuration(Duration.seconds(10));
		transition1.setPath(l);
		transition1.setCycleCount(PathTransition.INDEFINITE);
		transition1.setAutoReverse(true);
		transition1.play();
		PathTransition transition2 = new PathTransition();
		transition2.setNode(cloud21);
		transition2.setDuration(Duration.seconds(10));
		transition2.setPath(l3);
		transition2.setCycleCount(PathTransition.INDEFINITE);
		transition2.setAutoReverse(true);
		transition2.play();
		PathTransition transition3 = new PathTransition();
		transition3.setNode(cloud1);
		transition3.setDuration(Duration.seconds(10));
		transition3.setPath(l3);
		transition3.setCycleCount(PathTransition.INDEFINITE);
		transition3.setAutoReverse(true);
		transition3.play();
		PathTransition transition4 = new PathTransition();
		transition4.setNode(cloud3);
		transition4.setDuration(Duration.seconds(10));
		transition4.setPath(l);
		transition4.setCycleCount(PathTransition.INDEFINITE);
		transition4.setAutoReverse(true);
		transition4.play();
		PathTransition transition5 = new PathTransition();
		transition5.setNode(start);
		transition5.setDuration(Duration.seconds(1));
		transition5.setPath(l2);
		transition5.setCycleCount(PathTransition.INDEFINITE);
		transition5.setAutoReverse(true);
		transition5.play();
		
	}

    @FXML
    void ColorBackWhite(MouseEvent event) {
    	start.setStyle("-fx-background-color: transparent;"+"-fx-background-radius: 30;"+""
				+ "-fx-border-color: transparent;"+"-fx-border-radius: 30;"+"-fx-text-fill: #ffffff");
    }
}

