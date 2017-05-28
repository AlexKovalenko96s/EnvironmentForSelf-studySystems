package ua.kas.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerLogin {

	@FXML
	TextField tf_email;
	@FXML
	PasswordField pf_password;

	public void checkUsers(ActionEvent e) throws ClassNotFoundException, SQLException, IOException {
		if (!tf_email.getText().equals("") || !pf_password.getText().equals("")) {
			Class.forName("org.sqlite.JDBC");
			Connection connect = DriverManager.getConnection("jdbc:sqlite::resource:ua/kas/main/studySystem.db");
			String query = "SELECT * FROM users WHERE email = ? and password = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setString(1, tf_email.getText());
			statement.setString(2, pf_password.getText());
			ResultSet res = statement.executeQuery();

			if (res.next()) {
				Scene login = new Scene(FXMLLoader.load(getClass().getResource("Change.fxml")));
				login.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage login_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				login_stage.setScene(login);
				login_stage.show();
			} else
				JOptionPane.showMessageDialog(null, "Not correct login or password!");
		} else
			JOptionPane.showMessageDialog(null, "Please fill all field!");
	}
}
