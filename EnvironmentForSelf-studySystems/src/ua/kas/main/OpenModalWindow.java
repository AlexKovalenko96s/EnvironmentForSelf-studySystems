package ua.kas.main;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OpenModalWindow implements Runnable {

	public static Stage stage;

	private int x, y;
	private String title, fxmlPath;

	public OpenModalWindow(int x, int y, String fxmlPath, String title) {
		this.x = x;
		this.y = y;
		this.fxmlPath = fxmlPath;
		this.title = title;
		run();
	}

	public void showDialog() throws Exception {
		stage = new Stage();
		Parent root = FXMLLoader.load(this.getClass().getResource(fxmlPath));
		stage.setTitle(title);
		stage.setResizable(false);
		Scene scene = new Scene(root, x - 10, y - 10);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner((Main.getWindow()).getScene().getWindow());
		stage.getIcons().add(new Image(this.getClass().getResourceAsStream("neuralNetwork.png")));
		stage.show();
	}

	@Override
	public void run() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					showDialog();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static Stage getStage() {
		return stage;
	}
}
