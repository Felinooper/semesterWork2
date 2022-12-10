package ru.itis.grebenkov.map;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.Data;
import lombok.Getter;
import ru.itis.grebenkov.map.entity.Border;
import ru.itis.grebenkov.map.entity.Checkpoint;
import ru.itis.grebenkov.map.entity.Finish;
import ru.itis.grebenkov.map.entity.MapObject;

import java.util.ArrayList;
import java.util.List;

@Data
public class GameMap {
	@Getter
	private static List<ImageView> imageViewList = new ArrayList<>();

	public GameMap() {

	}

	public void createMap(List<MapObject> mapObjects){
		MapObject checkPoint = new Checkpoint(650, 500, 50, 25);
		MapObject border = new Border(310, 950, 90, 5, 200);
		MapObject border1 = new Border(410, 850, 0, 5, 200);
		MapObject border2 = new Border(210, 850, 0, 5, 200);
		MapObject border3 = new Border(235, 825, 90, 5, 50);
		MapObject border4 = new Border(385, 825, 90, 5, 50);
		MapObject border5 = new Border(385, 550, 0, 5, 300);
		MapObject border6 = new Border(235, 500, 0, 5, 350);
		MapObject border7 = new Border(410, 325, 90, 5, 350);
		MapObject border8 = new Border(560, 375, 90, 5, 350);
		MapObject border9 = new Border(585, 0, 0, 5, 500);
		MapObject border10 = new Border(735, 3, 0, 5, 550);
		MapObject border11 = new Border(710, 400, 90, 5, 50);
		MapObject border12 = new Border(630, 330, 90, 5, 90);
		MapObject finish = new Finish(610, 30, 100, 50);

		mapObjects.add(checkPoint);
		mapObjects.add(border);
		mapObjects.add(border1);
		mapObjects.add(border2);
		mapObjects.add(border3);
		mapObjects.add(border4);
		mapObjects.add(border5);
		mapObjects.add(border6);
		mapObjects.add(border7);
		mapObjects.add(border8);
		mapObjects.add(border9);
		mapObjects.add(border10);
		mapObjects.add(border11);
		mapObjects.add(border12);
		mapObjects.add(finish);

		imageViewList.add(checkPoint.getImageView());
		imageViewList.add(border.getImageView());
		imageViewList.add(border1.getImageView());
		imageViewList.add(border2.getImageView());
		imageViewList.add(border3.getImageView());
		imageViewList.add(border4.getImageView());
		imageViewList.add(border5.getImageView());
		imageViewList.add(border6.getImageView());
		imageViewList.add(border7.getImageView());
		imageViewList.add(border8.getImageView());
		imageViewList.add(border9.getImageView());
		imageViewList.add(border10.getImageView());
		imageViewList.add(border11.getImageView());
		imageViewList.add(border12.getImageView());
		imageViewList.add(finish.getImageView());
	}
}
