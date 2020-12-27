package View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import Model.Player;
import Model.SysData;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
	@FXML
	private Pane newPane;

	@FXML
	private ImageView menu;

	private static Player player1;
	private static Player player2;
	public static boolean isSoundOn = true;

	/***************************************
	 * main page
	 *****************************************************/
	public void closeWindow() {
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

	public void playColor() {
		play.setStyle("-fx-background-color: transparent;" + "-fx-background-radius: 30;" + ""
				+ "-fx-border-color: silver;" + "-fx-border-radius: 30;" + "-fx-border-width: 3");

	}

	public void rulesColor() {
		rules.setStyle("-fx-background-color: transparent;" + "-fx-background-radius: 30;" + ""
				+ "-fx-border-color: silver;" + "-fx-border-radius: 30;" + "-fx-border-width: 3");

	}

	public void historyColor() {
		history.setStyle("-fx-background-color: transparent;" + "-fx-background-radius: 30;" + ""
				+ "-fx-border-color: silver;" + "-fx-border-radius: 30;" + "-fx-border-width: 3");
	}

	public void ColorBack() {
		play.setStyle("-fx-background-color: silver;" + "-fx-background-radius: 30;");
		rules.setStyle("-fx-background-color: silver;" + "-fx-background-radius: 30;");
		history.setStyle("-fx-background-color: silver;" + "-fx-background-radius: 30;");

	}

	@FXML
	public void Admin(ActionEvent event) throws Exception {
		closeWindow();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/AdminLogin.fxml"));
		Scene scene = new Scene(root, 454, 236);
		primaryStage.setScene(scene);
		primaryStage.setTitle("login");
		primaryStage.show();
	}

	@FXML
	public void Play(ActionEvent event) throws Exception {
		isSoundOn = false;
		closeWindow();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/NickName.fxml"));
		Scene scene = new Scene(root, 475, 226);
		primaryStage.setScene(scene);
		primaryStage.setTitle("enter nick Name");
		primaryStage.show();
	}

	@FXML
	public void Rules(ActionEvent event) throws Exception {
		isSoundOn = false;
		closeWindow();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Rules.fxml"));
		Scene scene = new Scene(root, 672, 456);
		primaryStage.setScene(scene);
		primaryStage.setTitle("RULES");
		primaryStage.show();
	}

	@FXML
	void History(ActionEvent event) throws IOException {
		closeWindow();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/History.fxml"));
		Scene scene = new Scene(root, 680, 333);
		primaryStage.setScene(scene);
		primaryStage.setTitle("History");
		primaryStage.show();
	}

	@FXML
	void soundClicked(MouseEvent event) {
		if (MainPageController.isSoundOn) {
			unmute.setVisible(false);
			mute.setVisible(true);
			Sound.getInstance().stop("Piano Solo #1 RF.mp3");
			MainPageController.isSoundOn = false;
		} else {
			unmute.setVisible(true);
			mute.setVisible(false);
			Sound.getInstance().play("Piano Solo #1 RF.mp3");
			MainPageController.isSoundOn = true;
		}
	}

	public void pop(ActionEvent event) throws Exception {

		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/PopQuestion.fxml"));
		Scene scene = new Scene(root, 473, 310);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		scene.setFill(Color.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Play");
		primaryStage.show();
	}

	/**********************************************
	 * nick name page
	 *****************************************/

	@FXML
	void closeWindowInst(ActionEvent event) {
		((Stage) closeInst.getScene().getWindow()).close();
	}

	@FXML
	void menu(MouseEvent event) {
		newPane.setVisible(true);
	}

	@FXML
	void deletemenu(MouseEvent event) {
		newPane.setVisible(false);
	}

	public void Start(ActionEvent event) throws Exception {
		ArrayList<Player> players = new ArrayList<Player>();

		player1 = new Player(p1.getText());
		player2 = new Player(p2.getText());
		players.add(player1);
		players.add(player2);
		Player firstPlayer = players.get(new Random().nextInt(players.size()));
		Player secondPlayer;
		if (firstPlayer.equals(player1)) {
			secondPlayer = player2;
		} else {
			secondPlayer = player1;
		}
		player1 = firstPlayer;
		player2 = secondPlayer;
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

	public void startColor() {
		start.setStyle("-fx-background-color: transparent;" + "-fx-background-radius: 30;" + ""
				+ "-fx-border-color: transparent;" + "-fx-border-radius: 30;" + "-fx-text-fill: #cd4f06");

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (MainPageController.isSoundOn)
			Sound.getInstance().play("Piano Solo #1 RF.mp3");
		else
			Sound.getInstance().stop("Piano Solo #1 RF.mp3");

		Line l = new Line(3, 50, 150, 50);
		Line l2 = new Line(50, 10, 100, 10);
		Line l3 = new Line(100, 40, 3, 40);
		// Line l4 = new Line(50,50, 150, 150);

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
		start.setStyle("-fx-background-color: transparent;" + "-fx-background-radius: 30;" + ""
				+ "-fx-border-color: transparent;" + "-fx-border-radius: 30;" + "-fx-text-fill: #ffffff");
	}
}
