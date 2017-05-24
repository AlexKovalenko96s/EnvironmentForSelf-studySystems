package ua.kas.main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import ua.kas.main.levels.level01.Level_01;

public class Controller implements Initializable {

	@FXML
	AnchorPane ancPane_mainPaine = new AnchorPane();

	@FXML
	MenuBar menuBar;

	private ArrayList<Line> checkPoints = new ArrayList<>();

	private Polyline linesUpper;
	private Polyline linesLower;

	private Pane container = new Pane();

	private static double width = 800, height = 600;

	@SuppressWarnings("unused")
	private OpenModalWindow openModalWindow;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// loadBackground();
		loadMap(Level_01.lowerBounds, Level_01.upperBounds, container.getChildren());
		ancPane_mainPaine.getChildren().addAll(container);
	}

	public void createNew(ActionEvent event) {
		openModalWindow = new OpenModalWindow(event);
	}

	private void loadBackground() {
		Rectangle rect_enterTheProgram = new Rectangle(800, 600);
		rect_enterTheProgram.setFill(Color.LIGHTGRAY);

		Text text_enterTheProgram = new Text("Please, create new project!");
		text_enterTheProgram.setX(width / 2 - 100);
		text_enterTheProgram.setY(height / 2);
		text_enterTheProgram.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 17));
		text_enterTheProgram.setFill(new Color(107 / 255.0, 162 / 255.0, 252 / 255.0, 1.0));

		container.getChildren().addAll(rect_enterTheProgram, text_enterTheProgram);
	}

	public void exit() {
		Platform.exit();
		System.exit(0);
	}

	private void loadMap(double[] level_upper, double[] level_lower, ObservableList<Node> list) {
		linesUpper = new Polyline(level_upper);
		linesLower = new Polyline(level_lower);
		list.add(Level_01.levelGraphics);
		Line l;
		for (int i = 0; i < Level_01.checkPoints.length; i += 4) {

			l = new Line(Level_01.checkPoints[i], Level_01.checkPoints[i + 1], Level_01.checkPoints[i + 2],
					Level_01.checkPoints[i + 3]);
			l.setStroke(Color.GREEN);
			l.setStrokeWidth(5);
			l.setOpacity(0.2);
			checkPoints.add(l);
			list.add(l);
		}
		// list.addAll(statusUpdater);
	}

	public void createNew() {

	}
}
