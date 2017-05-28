package ua.kas.main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class ControllerChange implements Initializable {

	@FXML
	TextField tf_speed;
	@FXML
	TextField tf_fuel;
	@FXML
	TextField tf_rotate;
	@FXML
	TextField tf_block;

	public void confirm() {
		Controller.setRotateCoeff(tf_rotate.getText());
		Controller.setSpeedCoeff(tf_speed.getText());
		Controller.setFuelCoeff(tf_fuel.getText());
		Controller.setBlockCoeff(tf_block.getText());
		Platform.exit();
	}

	public void close() {
		Platform.exit();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tf_block.setText(Controller.getBlockCoeff());
		tf_fuel.setText(Controller.getFuelCoeff());
		tf_rotate.setText(Controller.getRotateCoeff());
		tf_speed.setText(Controller.getSpeedCoeff());
	}
}
