package com.example.sping_portfolio;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    
    // Listens for incoming clients and creates a socketto communicate with them
    private ServerSocket serverSocket;

    // Constructor for serverSocket
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {

        try {

            // Keeps looking for more clients to connect until server ends
            while(!serverSocket.isClosed()) {

                serverSocket.accept();
                System.out.println("A client has connected");
                ClientHandler clientHandler = new ClientHandler();

                // Creates a new thread to run asynch
                Thread thread = new Thread(clientHandler);
                thread.start();

            }

        } catch (IOException e) {

        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                server.Socket.close();
            }
        } catch(IOException e) {

        }

    }

    public static void main(String[] args) throws IOException{

        ServerSocket serverSocker = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();

    }
}
