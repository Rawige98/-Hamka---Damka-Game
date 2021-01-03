package View;


import java.io.IOException;

import java.sql.SQLException;
import java.text.ParseException;

import Model.SysData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	Stage window;

	 @SuppressWarnings("deprecation")
	public static void main(String[] args) throws SQLException, IOException, ParseException {
		 launch(args);

	}

	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		SysData sys = SysData.getInstance();
		sys.loadQuestions(null);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
			Scene scene = new Scene(root, 581, 449);
			window.setScene(scene);
			window.setTitle("Hamka game");
			window.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

