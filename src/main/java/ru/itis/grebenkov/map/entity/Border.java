package ru.itis.grebenkov.map.entity;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class Border extends MapObject{
	public Border(double x, double y, double rotation, double width, double height) {
		super(new Rectangle(x, y, width, height), new Point2D(x, y));
		getView().setRotate(rotation);
		Image image = new Image(new File("src/main/resources/img.car/Road_01_Tile_07.png").toURI().toString());
		setImageView(new ImageView(image));
		getImageView().setViewport(new Rectangle2D(0, 0, 53, 94));
		getImageView().setY(y);
		getImageView().setX(x);
	}
}
