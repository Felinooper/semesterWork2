package ru.itis.grebenkov.net.client;

import lombok.Data;
import ru.itis.grebenkov.game.GameLoop;
import ru.itis.grebenkov.menu.WinMenu;

import java.io.*;

@Data
public class ClientThread implements Runnable {

	private final BufferedReader inputStream;
	private final BufferedWriter outputStream;
	private final Client client;
	private final GameLoop gameLoop = GameLoop.getInstance();
	private boolean isWorking = true;

	public ClientThread(BufferedReader input, BufferedWriter output, Client client) {
		this.inputStream = input;
		this.outputStream = output;
		this.client = client;
	}

	public void stop() {
		isWorking = false;
	}

	@Override
	public void run() {
		try {
			while (isWorking) {
				String message = inputStream.readLine();
				System.out.println(message);
				if (message != null) {
					String[] messageSplit = message.split(",");

					switch (messageSplit[0]) {
						case "connected":
							gameLoop.createAnotherPlayer(messageSplit[1]);
							break;
						case "move":
							String nick = messageSplit[1];
							double x = Double.parseDouble(messageSplit[2]);
							double y = Double.parseDouble(messageSplit[3]);
							boolean isDriving = Boolean.parseBoolean(messageSplit[4]);
							double rotation = Double.parseDouble(messageSplit[5]);
							gameLoop.moveAnotherPlayer(nick, rotation, isDriving);
							break;
						case "tp":
							String nick1 = messageSplit[1];
							double x1 = Double.parseDouble(messageSplit[2]);
							double y1 = Double.parseDouble(messageSplit[3]);
							double rotation1 = Double.parseDouble(messageSplit[5]);
							gameLoop.tpAnotherPlayer(x1, y1 ,rotation1, nick1);
							break;
						case "win":
							String nick2 = messageSplit[1];
							new WinMenu(GameLoop.getStage(),nick2);
							GameLoop.getPlayer().moveToCheckpoint(300, 300, 180);
							gameLoop.stopMove();
							GameLoop.getRoot().getChildren().removeAll();
							break;
					}

				}
			}
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
