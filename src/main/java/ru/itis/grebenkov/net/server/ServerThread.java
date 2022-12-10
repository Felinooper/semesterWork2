package ru.itis.grebenkov.net.server;

import lombok.Data;

import java.io.*;
import java.net.SocketException;

@Data
public class ServerThread implements Runnable{
	private final BufferedReader inputStream;
	private final BufferedWriter outputStream;
	private final Server server;
	private boolean isWorking = true;

	public ServerThread(BufferedReader input, BufferedWriter output, Server server) {
		this.inputStream = input;
		this.outputStream = output;
		this.server = server;
	}

	public void stop() {
		isWorking = false;
	}

	@Override
	public void run() {
		System.out.println("someone connected");
		try {
			while (isWorking) {
				String message = inputStream.readLine();
				System.out.println(message);
				server.sendMessage(message, this);
			}
		} catch (SocketException socketException) {
			server.removeClient(this);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
