package ua.kas.main;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ua.kas.main.levels.level01.Level_01;

public class Controller implements Initializable {

	@FXML
	AnchorPane ancPane_mainPaine = new AnchorPane();

	@FXML
	MenuBar menuBar;

	@FXML
	TextField tf_x;
	@FXML
	TextField tf_y;
	@FXML
	TextField tf_z;
	@FXML
	TextField tf_speed;
	@FXML
	TextField tf_fuel;
	@FXML
	TextField tf_top;
	@FXML
	TextField tf_bottom;
	@FXML
	TextField tf_left;
	@FXML
	TextField tf_right;
	@FXML
	TextField tf_accident;

	@FXML
	ComboBox<String> cmb_x;
	@FXML
	ComboBox<String> cmb_y;
	@FXML
	ComboBox<String> cmb_z;
	@FXML
	ComboBox<String> cmb_speed;
	@FXML
	ComboBox<String> cmb_fuel;
	@FXML
	ComboBox<String> cmb_top;
	@FXML
	ComboBox<String> cmb_bottom;
	@FXML
	ComboBox<String> cmb_left;
	@FXML
	ComboBox<String> cmb_right;
	@FXML
	ComboBox<String> cmb_accident;

	private Timeline loop;
	private Timeline gameLoop;

	private ArrayList<Line> checkPoints = new ArrayList<>();

	private KeyCode keyPressedCode = null;

	private Polyline linesUpper;
	private Polyline linesLower;

	private Pane container = new Pane();

	private static double width = 800, height = 600;

	private static String image = "";
	private static String language = "";
	private static String storage = "";
	private static String languagePath = "";
	private static String storagePatch = "";

	private static String speedCoeff = "";
	private static String fuelCoeff = "";
	private static String blockCoeff = "";
	private static String rotateCoeff = "";

	private boolean pleaseSelectMap = false;
	private boolean pleaseAddVariable = false;
	private boolean keyPressed = false;

	private long time = 0;

	private int checks = 0;
	private int laps = 1;

	@SuppressWarnings("unused")
	private OpenModalWindow openModalWindow;

	private Car car;

	private SpeedMeter meter;

	private StatusUpdater statusUpdater = new StatusUpdater(width / 2 - 150, height / 2 - 35);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadBackground("Please, create new project!");

		ancPane_mainPaine.getChildren().addAll(container);

		loop = new Timeline(new KeyFrame(Duration.millis(1000 / 30), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (!language.equals("") && !storage.equals("") && !languagePath.equals("") && !storagePatch.equals("")
						&& !pleaseSelectMap) {
					loadBackground("Please, select map paint!");
					pleaseSelectMap = true;
				}
				/*
				 * if (!language.equals("") && !storage.equals("") &&
				 * !languagePath.equals("") && !storagePatch.equals("") &&
				 * !image.equals("") && pleaseSelectMap && !pleaseAddVariable) {
				 */
				if (!image.equals("") && !pleaseAddVariable) {
					loadMap(Level_01.lowerBounds, Level_01.upperBounds, container.getChildren());

					meter = new SpeedMeter();
					container.getChildren().add(meter);
					meter.setLayoutX(110);
					meter.setLayoutY(height - 10);

					pleaseAddVariable = true;

					Rectangle r = new Rectangle(width, height);
					r.setFill(Color.WHITE);
					r.setOpacity(0.6);
					container.getChildren().addAll(r);
					Text t = new Text("Please, select needed variables!");
					ProgressIndicator p = new ProgressIndicator();

					p.setLayoutX(width / 2 + 150);
					p.setLayoutY(height / 2 - 33);
					t.setX(width / 2 - 125);
					t.setY(height / 2);
					t.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 17));
					t.setFill(new Color(107 / 255.0, 162 / 255.0, 252 / 255.0, 1.0));
					container.getChildren().addAll(t, p);
					loop.stop();

					car = new Car();
					car.setLocationByVector(Level_01.startCar1[0] - car.w, height - Level_01.startCar1[1]);
					car.setDirection(40);
					car.getGraphics().setFill(Color.MEDIUMPURPLE);

					container.getChildren().addAll(car.getGraphicsImg());
					// container.getChildren().addAll(car.getRec());

					container.setOnKeyPressed(new EventHandler<KeyEvent>() {

						public void handle(KeyEvent e) {
							keyPressed = true;
							keyPressedCode = e.getCode();
						}
					});

					// key released
					container.setOnKeyReleased(new EventHandler<KeyEvent>() {

						public void handle(KeyEvent e) {
							keyPressed = false;
						}
					});

					tf_x.setText((new DecimalFormat("#0.000").format(car.getLocationX().get())));
					tf_y.setText((new DecimalFormat("#0.000").format(car.getLocationY().get())));
					tf_z.setText(Double.toString(car.getDirection()));
					tf_speed.setText(new DecimalFormat("#0.0").format(car.getSpeed() * 30));
					tf_fuel.setText(new DecimalFormat("#0.0").format(car.fuel));

					gameLoop = new Timeline(new KeyFrame(Duration.millis(1000 / 30), new EventHandler<ActionEvent>() {

						public void handle(ActionEvent e) {
							if (keyPressed) {
								if (car.getSpeed() != 0) {

									if (keyPressedCode == KeyCode.LEFT) {
										if (car.getSpeed() != 0) {
											car.setDirection(car.direction += (3));
										}

									} else if (keyPressedCode == KeyCode.RIGHT) {
										if (car.getSpeed() != 0)
											car.setDirection(car.direction -= (3));
									}
								}

								if (keyPressedCode == KeyCode.UP) {
									car.setSpeed(car.getSpeed() + 0.05);
									car.setFuel(car.getFuel() - 0.05);
								} else if (keyPressedCode == KeyCode.DOWN) {
									car.setSpeed(car.getSpeed() - 0.05);
									car.setFuel(car.getFuel() - 0.05);
								}
							}

							car.translateByRadius(car.getSpeed());
							meter.setSpeed(car.getSpeed());

							checkForCollisions(car);

							tf_x.setText((new DecimalFormat("#0.000").format(car.getLocationX().get())));
							tf_y.setText((new DecimalFormat("#0.000").format(car.getLocationY().get())));
							tf_z.setText(Double.toString(car.getDirection()));
							tf_speed.setText(new DecimalFormat("#0.0").format(car.getSpeed() * 30));
							tf_fuel.setText(new DecimalFormat("#0.0").format(car.fuel));
						}
					}));
					container.setFocusTraversable(true);
					gameLoop.setCycleCount(Timeline.INDEFINITE);
					gameLoop.play();
				}
			}
		}));
		loop.setCycleCount(Timeline.INDEFINITE);
		loop.play();
	}

	public void createNew() {
		openModalWindow = new OpenModalWindow(475, 200, "CreateNew.fxml", "Create new scene");
	}

	public void author() {
		// openModalWindow = new OpenModalWindow(350, 200, "Author.fxml",
		// "Author");
	}

	public void login() {
		openModalWindow = new OpenModalWindow(300, 200, "Login.fxml", "Login");
	}

	public void selectMap() {
		// if (pleaseSelectMap)
		openModalWindow = new OpenModalWindow(475, 250, "SelectMap.fxml", "Select map");
		// else
		// JOptionPane.showMessageDialog(null, "Please, create \"New scene\"!");
	}

	private void loadBackground(String text) {
		Rectangle rect_enterTheProgram = new Rectangle(800, 600);
		rect_enterTheProgram.setFill(Color.LIGHTGRAY);

		Text text_enterTheProgram = new Text(text);
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
	}

	private void checkForCollisions(Car car) {

		Path p3;

		// checking for checkpoints
		for (int i = 0; i < checkPoints.size(); i++) {
			p3 = (Path) Shape.intersect(car.bounds, checkPoints.get(i));
			if (!p3.getElements().isEmpty()) {
				// if start/finish
				if (checks == i && checks == 0) {
					// race finish
					if (laps == 0) {
						time = System.currentTimeMillis() - time;
						statusUpdater.setText("Finished in: " + String.format("%.2f", time / 1000.0) + " seconds");
						gameLoop.stop();
					} // lap finish
					else {
						statusUpdater.setTextAndAnimate("Laps Left: " + laps);
						laps--;
						checks++;
						for (int j = 1; j < checkPoints.size(); j++) {
							checkPoints.get(j).setStroke(Color.GREEN);
						}
						break;
					}

				} // crossed checkpoints
				else if (checks == i) {
					checkPoints.get(i).setStroke(Color.RED);
					statusUpdater.setTextAndAnimate("CheckPoint : " + checks);
					checks++;
					break;
				}

				// new lap
				if (checks == checkPoints.size()) {
					checks = 0;
				}
			}
		}

		// car crashed into the wall
		if (CollisionDetectors.PolylineIntersection(car.bounds, linesLower)
				|| CollisionDetectors.PolylineIntersection(car.bounds, linesUpper)) {
			if (!car.isColliding) {
				car.setSpeed(car.getSpeed() * (-0.5));
			}
		} else {
			car.isColliding = false;
		}
	}

	public static void setLanguage(String language) {
		Controller.language = language;
	}

	public static void setStorage(String storage) {
		Controller.storage = storage;
	}

	public static void setLanguagePath(String languagePath) {
		Controller.languagePath = languagePath;
	}

	public static void setStoragePatch(String storagePatch) {
		Controller.storagePatch = storagePatch;
	}

	public static void setImage(String image) {
		Controller.image = image;
	}

	public static String getSpeedCoeff() {
		return speedCoeff;
	}

	public static void setSpeedCoeff(String speedCoeff) {
		Controller.speedCoeff = speedCoeff;
	}

	public static String getFuelCoeff() {
		return fuelCoeff;
	}

	public static void setFuelCoeff(String fuelCoeff) {
		Controller.fuelCoeff = fuelCoeff;
	}

	public static String getBlockCoeff() {
		return blockCoeff;
	}

	public static void setBlockCoeff(String blockCoeff) {
		Controller.blockCoeff = blockCoeff;
	}

	public static String getRotateCoeff() {
		return rotateCoeff;
	}

	public static void setRotateCoeff(String rotateCoeff) {
		Controller.rotateCoeff = rotateCoeff;
	}
}
