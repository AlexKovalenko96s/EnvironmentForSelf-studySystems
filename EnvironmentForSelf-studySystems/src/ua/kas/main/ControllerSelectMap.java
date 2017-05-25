package ua.kas.main;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ua.kas.main.levels.level01.Level_01;

public class ControllerSelectMap implements Initializable {

	@FXML
	ComboBox<String> cmb_map;

	@FXML
	ImageView iv_map;

	private ObservableList<String> list_map = FXCollections
			.observableArrayList(Arrays.asList("map1.png", "map2.png", "map3.png"));

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cmb_map.setItems(list_map);
	}

	public void selectEcryptionImage() throws IOException {
		Image image = new Image(Level_01.class.getResourceAsStream(cmb_map.getSelectionModel().getSelectedItem()));
		iv_map.setImage(image);
	}

	public void confirm() {
		if (iv_map != null) {
			Controller.setImage(cmb_map.getSelectionModel().getSelectedItem());
			OpenModalWindow.getStage().close();
		} else
			JOptionPane.showMessageDialog(null, "Please, select map paint!");
	}
}
