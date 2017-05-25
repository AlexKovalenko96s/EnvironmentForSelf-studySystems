package ua.kas.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	static Stage window;

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
			Scene scene = new Scene(root, 1100 - 10, 625 - 10);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Study Artificial Intelligence");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("neuralNetwork.png")));
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			Main.window = primaryStage;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Stage getWindow() {
		return window;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
