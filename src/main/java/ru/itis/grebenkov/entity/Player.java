package ru.itis.grebenkov.entity;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import ru.itis.grebenkov.game.GameLoop;
import ru.itis.grebenkov.map.entity.Checkpoint;
import ru.itis.grebenkov.map.entity.Finish;
import ru.itis.grebenkov.map.entity.MapObject;
import ru.itis.grebenkov.menu.WinMenu;
import ru.itis.grebenkov.net.client.Client;

import java.io.File;

@Getter
@Setter
public class Player extends GameObject{

	private Point2D lastCheckpoint;
	private boolean isDriving;
	private Text nick;
	private boolean isMulti;
	private Client client;
	protected ImageView imageView;
	private final GameLoop GAME_LOOP = GameLoop.getInstance();

	public Player(double x, double y, Text nick , boolean isMulti) {
		super(new Rectangle(40,20, Color.TRANSPARENT));
		this.lastCheckpoint = new Point2D(x, y);
		this.getView().setTranslateX(lastCheckpoint.getX());
		this.getView().setTranslateY(lastCheckpoint.getY());
		this.getView().setRotate(90);
		this.isDriving = false;
		this.nick = nick;
		this.isMulti = isMulti;
		Image image = new Image(new File("src/main/resources/img.car/Car_3_01.png").toURI().toString());
		imageView = new ImageView(image);
		imageView.setViewport(new Rectangle2D(0, 0, 20, 40));
		imageView.setY(x);
		imageView.setX(y);
	}

	public void moveToCheckpoint(double x, double y, double rotation){
		getView().setTranslateX(x);
		getView().setTranslateY(y);
		getView().setRotate(rotation);
		nick.setX(x);
		nick.setY(y - 10);
		isDriving = false;
		imageView.setX(x - 10);
		imageView.setY(y - 10);
		if (isMulti){
			String message = "tp" +
					"," + getNick().getText() +
					"," + x +
					"," + y +
					"," + isDriving +
					"," + getRotate() + "\n";
			client.sendMessage(message);
		}
	}

	public boolean isCollideWithMap(MapObject object){
		return getView().getBoundsInParent().intersects(object.getView().getBoundsInParent());
	}

	public void moveForward(KeyCode keyCode) {

		isDriving = true;

		switch (keyCode){
			case W:
				getView().setRotate(90);
				break;
			case A:
				getView().setRotate(0);
				break;
			case D:
				getView().setRotate(180);
				break;
			case S:
				getView().setRotate(270);
				break;
		}

		double rotation = getView().getRotate();

		checkRotationAndMove(rotation);

	}

	public void moveForward(boolean isDriving){
		if (isDriving){
			double rotation = getView().getRotate();
			moveAndSendMessage(rotation);
		}
	}

	protected void moveAndSendMessage(double rotation){
		checkRotationAndMove(rotation);
		if (isMulti){
			String message = "move" + "," + getNick().getText() + "," + getView().getTranslateX() +
					"," + getView().getTranslateY()
					+ "," + isDriving + "," + getRotate() + "\n";
			client.sendMessage(message);
		}
	}

	protected void checkRotationAndMove(double rotation){
		if (rotation == 0){
			getView().setTranslateX(getView().getTranslateX() - 2);
			nick.setX(nick.getX() - 2);
		}

		if (rotation == 90){
			getView().setTranslateY(getView().getTranslateY() - 2);
			nick.setY(nick.getY() - 2);
		}

		if (rotation == 180){
			getView().setTranslateX(getView().getTranslateX() + 2);
			nick.setX(nick.getX() + 2);
		}

		if (rotation == 270) {
			getView().setTranslateY(getView().getTranslateY() + 2);
			nick.setY(nick.getY() + 2);
		}

		imageView.setRotate(rotation - 90);
		imageView.setX(getView().getTranslateX() + 10);
		imageView.setY(getView().getTranslateY() - 10);
	}

	public void checkCollisions(){
		moveForward(isDriving());
		for (MapObject object : GAME_LOOP.getMAP_OBJECTS()) {
			if (isCollideWithMap(object)) {
				Point2D lastCheckpoint = getLastCheckpoint();
				if (object instanceof Checkpoint) {
					setLastCheckpoint(new Point2D(object.getPosition().getX(), object.getPosition().getY()));
					break;
				}
				if (object instanceof Finish) {
					if (!isMulti){
						GameLoop.getBot().setDriving(false);
						GameLoop.getBot().moveToCheckpoint(300, 300, 180);
						GameLoop.getRoot().getChildren().removeAll();
					}
					new WinMenu(GameLoop.getStage(), nick.getText());
					if (isMulti){
						String message = "win" + "," + nick.getText() + "\n";
						client.sendMessage(message);
					}
				}
				moveToCheckpoint(lastCheckpoint.getX(), lastCheckpoint.getY(), getRotate());
			}
		}
	}
}
