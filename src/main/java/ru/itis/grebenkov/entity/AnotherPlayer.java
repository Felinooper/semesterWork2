package ru.itis.grebenkov.entity;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import ru.itis.grebenkov.game.GameLoop;
import ru.itis.grebenkov.map.GameMap;

import java.io.File;

@Getter
@Setter
public class AnotherPlayer extends Player{
	private final GameMap map = new GameMap();
	private String name;
	private GameLoop gameLoop;

	public AnotherPlayer(Text nick, String name) {
		super(300, 930, nick, false);
		this.name = name;
		Image image = new Image(new File("src/main/resources/img.car/Car_1_01.png").toURI().toString());
		imageView = new ImageView(image);
		imageView.setViewport(new Rectangle2D(0, 0, 20, 40));
		imageView.setY(930);
		imageView.setX(300);
	}


	public synchronized void move(double rotation) {
		moveAndSendMessage(rotation);
		this.getView().setRotate(rotation);
	}

	public synchronized void tpPlayer(double x, double y, double rotation){
		getView().setTranslateX(x);
		getView().setTranslateY(y);
		getView().setRotate(rotation);
		getNick().setX(x);
		getNick().setY(y - 10);
	}

	public synchronized void stopRendering() {
		javafx.application.Platform.runLater(() -> {
			getNick().setText("");
			GameLoop.getRoot().getChildren().remove(getNick());
			GameLoop.getRoot().getChildren().remove(imageView);
		});
	}


}

