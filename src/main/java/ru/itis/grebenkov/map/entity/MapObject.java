package ru.itis.grebenkov.map.entity;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapObject {
	private Node view;
	private Point2D position;
	private ImageView imageView;
	public MapObject(Node node, Point2D position) {
		this.view = node;
		this.position = position;
	}
}
