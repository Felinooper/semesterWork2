package ru.itis.grebenkov.entity;

import javafx.scene.Node;
import lombok.Data;

import java.awt.*;

@Data
public class GameObject {

	private Node view;

	public GameObject(Node view) {
		this.view = view;
	}

	public double getRotate() {
		return view.getRotate();
	}

}
