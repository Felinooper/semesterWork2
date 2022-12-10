package ru.itis.grebenkov.net.client;

import lombok.Data;
import ru.itis.grebenkov.net.server.Server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Data
public class Client {
	private Socket socket;
	private ClientThread clientThread;

	public void stop() {
		clientThread.stop();
	}

	public boolean sendMessage(String message) {
		boolean isSuccessful;

		try {
			clientThread.getOutputStream().write(message);
			//System.out.println(message);
			clientThread.getOutputStream().flush();

			isSuccessful = true;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		return isSuccessful;
	}

	public void start() throws IOException {
		socket = new Socket("127.0.0.1", Server.getPORT());

		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

		clientThread = new ClientThread(input, output, this);

		new Thread(clientThread).start();
	}
}
