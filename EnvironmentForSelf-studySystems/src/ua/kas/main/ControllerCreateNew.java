package ua.kas.main;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;

public class ControllerCreateNew implements Initializable {

	@FXML
	Button btn_selectLanguage;
	@FXML
	Button btn_selectStorage;

	@FXML
	ComboBox<String> comB_language;
	@FXML
	ComboBox<String> comB_storage;

	private String languagePath;
	private String storagePath;

	private ObservableList<String> list_language = FXCollections.observableArrayList();
	private ObservableList<String> list_storage = FXCollections.observableArrayList();

	public void selectLocation(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = null;

		try {
			if (event.getSource().equals(btn_selectLanguage))
				extFilter = new FileChooser.ExtensionFilter("File (*.java, *.py, *.cpp)", "*.java", "*.py", "*.cpp");
			else if (event.getSource().equals(btn_selectStorage))
				extFilter = new FileChooser.ExtensionFilter("File (*.txt)", "*.txt");

			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showOpenDialog(null);

			if (event.getSource().equals(btn_selectLanguage))
				languagePath = file.getAbsolutePath();
			else if (event.getSource().equals(btn_selectStorage))
				storagePath = file.getAbsolutePath();
		} catch (Exception e) {
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connect = DriverManager.getConnection("jdbc:sqlite::resource:ua/kas/main/studySystem.db");
			Statement statement = connect.createStatement();
			String query = "SELECT language FROM scene GROUP BY language ORDER BY language";
			ResultSet res = statement.executeQuery(query);
			while (res.next()) {
				list_language.add(res.getString("language"));
			}
			comB_language.setItems(list_language);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void selectStorageFile() {
		list_storage.clear();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connect = DriverManager.getConnection("jdbc:sqlite::resource:ua/kas/main/studySystem.db");
			String query = "SELECT storageFile FROM scene WHERE language = ? GROUP BY storageFile ORDER BY storageFile";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setString(1, comB_language.getSelectionModel().getSelectedItem());
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				list_storage.add(res.getString("storageFile"));
			}
			comB_storage.setItems(list_storage);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void resetStorageFile() {
		comB_storage.getSelectionModel().clearSelection();
	}

	public void confirm() {
		if (!comB_language.getSelectionModel().isEmpty() && !comB_storage.getSelectionModel().isEmpty()) {
			if (languagePath != null) {
				if (storagePath != null) {
					Controller.setLanguage(comB_language.getSelectionModel().getSelectedItem());
					Controller.setStorage(comB_storage.getSelectionModel().getSelectedItem());
					Controller.setLanguagePath(languagePath);
					Controller.setStoragePatch(storagePath);
					OpenModalWindow.getStage().close();
				} else
					JOptionPane.showMessageDialog(null, "Please, select storage file! ");
			} else
				JOptionPane.showMessageDialog(null, "Please, select logical language file!");
		} else
			JOptionPane.showMessageDialog(null, "Please, select language and storage type! ");
	}
}
