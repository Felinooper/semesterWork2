package ru.itis.grebenkov.entity.ai;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import lombok.SneakyThrows;
import ru.itis.grebenkov.entity.Player;
import ru.itis.grebenkov.game.GameLoop;
import ru.itis.grebenkov.map.GameMap;
import ru.itis.grebenkov.map.entity.MapObject;
import ru.itis.grebenkov.menu.WinMenu;

import java.io.File;
import java.util.List;

public class Bot extends Player implements Runnable{

	public Bot(double x, double y, Text nick) {
		super(x, y, nick, false);
		this.getView().setRotate(90);
		Image image = new Image(new File("src/main/resources/img.car/Car_1_01.png").toURI().toString());
		imageView = new ImageView(image);
		imageView.setViewport(new Rectangle2D(0, 0, 20, 40));
		imageView.setY(x);
		imageView.setX(y);
	}


	@SneakyThrows
	@Override
	public void run() {
		Thread.sleep(2500);
		setDriving(true);
	}

	public void moveBot(List<MapObject> mapObjects){
		moveForward(isDriving());

		if (getView().getTranslateY() == 510) {
			getView().setRotate(180);
		}

		if (getView().getTranslateX() == 600) {
			getView().setRotate(90);
		}

		if (getView().getTranslateY() == 400) {
			getView().setRotate(180);
		}

		if (getView().getTranslateX() == 690) {
			getView().setRotate(90);
		}

		if (isCollideWithMap(mapObjects.get(mapObjects.size() - 1))){
			new WinMenu(GameLoop.getStage(),"bot");
			GameLoop.getPlayer().setDriving(false);
			GameLoop.getPlayer().moveToCheckpoint(300, 300, 180);
			GameLoop.getRoot().getChildren().removeAll();
		}
	}
}
