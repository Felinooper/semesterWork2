package ru.itis.grebenkov.net.server;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "clients")
public class Server {
	@Getter
	private static final int PORT = 5555;
	private ServerSocket socket;
	private final List<ServerThread> clients = new ArrayList<>();
	private String message = "";
	private boolean isWorking = true;

	public void start() throws IOException {
		socket = new ServerSocket(PORT);
		while (isWorking) {
			Socket clientSocket = socket.accept();

			BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));

			ServerThread serverThread = new ServerThread(input, output, this);

			clients.add(serverThread);

			new Thread(serverThread).start();

		}
	}

	public void sendMessage(String message, ServerThread sender) throws IOException {
		this.message = message;
		System.out.println(clients);
		for (ServerThread client : clients) {
			if (client.equals(sender)){
				continue;
			}
			client.getOutputStream().write(message + "\n");
			client.getOutputStream().flush();
		}
	}

	public void removeClient(ServerThread gameServerThread) {
		clients.remove(gameServerThread);
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.start();
	}

	public void stop() {
		for (ServerThread serverThread : clients) {
			serverThread.stop();
		}
		isWorking = false;
	}
}
