package ua.kas.main;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Car {
	public Polygon up;
	double w = 35, h = 18;
	public Rectangle graphics, graphicsUp;
	public DoubleProperty locationX, locationY, locationXUP, locationYUP;
	public double direction = 0;
	double speed = 0;
	double fuel = 100;
	Line edgeLeft, edgeRight, edgeDown, edgeUp;
	boolean isColliding;
	ImageView graphicsImg;

	public Rectangle getGraphics() {
		return graphics;
	}

	public Polygon getRec() {
		return up;
	}

	Polyline bounds = new Polyline(38.0, 17.0, 38.0, 7.0, 33.0, 6.0, 31.0, 4.0, 30.0, 3.0, 6.0, 3.0, 5.0, 4.0, 3.0, 6.0,
			0.0, 7.0, 0.0, 17.0, 3.0, 18.0, 5.0, 20.0, 6.0, 21.0, 30.0, 21.0, 31.0, 20.0, 33.0, 18.0);
	Polyline boundsUp = new Polyline(22.0, 12.0, 42.0, 32.0, 49.0, 32.0, 68.0, 13.0, 68.0, 11.0, 49.0, -8.0, 42.0,
			-8.0);

	public Car() {
		graphicsImg = new ImageView(new Image(Controller.class.getResourceAsStream("car1.png")));

		locationX = new SimpleDoubleProperty(0);
		locationY = new SimpleDoubleProperty(0);
		graphics = new Rectangle(w, h);
		graphics.setStroke(Color.BLACK);
		graphics.setRotationAxis(Rotate.Z_AXIS);
		graphics.xProperty().bind(locationX.add((w / 2)));
		graphics.yProperty().bind(locationY.multiply(-1).add((600 - w / 2)));
		graphicsImg.setRotationAxis(Rotate.Z_AXIS);
		graphicsImg.xProperty().bind(graphics.xProperty());
		graphicsImg.yProperty().bind(graphics.yProperty());
		graphicsImg.rotateProperty().bind(graphics.rotateProperty());
		bounds.translateXProperty().bind(graphics.xProperty());
		bounds.translateYProperty().bind(graphics.yProperty());
		bounds.rotateProperty().bind(graphics.rotateProperty());
		graphics.setFill(Color.MEDIUMPURPLE);
		graphics.setWidth(w);
		graphics.setHeight(h);

		locationXUP = new SimpleDoubleProperty(0);
		locationYUP = new SimpleDoubleProperty(0);
		up = new Polygon(22.0, 12.0, 42.0, 32.0, 49.0, 32.0, 68.0, 13.0, 68.0, 11.0, 49.0, -8.0, 42.0, -8.0);
		up.setStroke(Color.RED);
		up.setRotationAxis(Rotate.Z_AXIS);
		up.layoutXProperty().bind(locationX.add((w / 2)));
		up.layoutYProperty().bind(locationY.multiply(-1).add((600 - w / 2)));
		up.setFill(Color.TRANSPARENT);

	}

	public void translateByVector(Vector v) {
		locationX.set(locationX.get() + v.getX());
		locationY.set(locationY.get() + v.getY());

	}

	public void translateByVector(double x, double y) {
		locationX.set(locationX.get() + x);
		locationY.set(locationY.get() + y);

	}

	public void setLocationByVector(Vector vector) {
		locationX.set(vector.getX());
		locationY.set(vector.getY());

	}

	public void setLocationByVector(double x, double y) {
		locationX.set(x);
		locationY.set(y);
	}

	public void setDirection(double angle) {
		graphics.setRotate(180 - angle);
		up.setRotate(graphics.getRotate());
		direction = angle;
	}

	public void translateByRadius(double d) {
		Vector v = new Vector(d, direction / 180.0 * Math.PI, Vector.Polar);
		translateByVector(v);
	}

	public ImageView getGraphicsImg() {
		return graphicsImg;
	}

	public Polyline getBounds() {
		return bounds;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getFuel() {
		return fuel;
	}

	public void setFuel(double fuel) {
		this.fuel = fuel;
	}
}
