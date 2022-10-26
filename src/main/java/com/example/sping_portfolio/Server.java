package com.example.sping_portfolio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.atomic.DoubleAccumulator;

public class Server {
    
    // Listens for incoming clients and creates a socket to communicate with them
    private ServerSocket serverSocket;
    private long answer;
    private String question;

    // Constructor for serverSocket
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {

        try {

            // Keeps looking for more clients to connect until server ends
            while(!serverSocket.isClosed()) {

                // Instantiates the client
                Socket socket = serverSocket.accept();
                System.out.println("A client has connected");
                ClientHandler clientHandler = new ClientHandler(socket, this);
                System.out.println(ClientHandler.clientHandlers);
                // Creates a new thread to run async
                Thread thread = new Thread(clientHandler);
                thread.start();

            }

        } catch (IOException e) {

        }
    }

    public boolean testAnswer(ClientHandler client) {
        return client.answer == answer;
    }

    public void generateNewQuestion() {
        long newAnswer = (long) Math.floor(Math.random() * 9999999999999L);

        long addend1 = (long) Math.floor(Math.random() * newAnswer);
        long addend2 = (long) newAnswer - addend1;

        String newQuestion = addend1 + " + " + addend2 + " = " + newAnswer;

        this.answer = newAnswer;
        this.question = newQuestion;
    }

    public void sendNewQuestion() {

    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch(IOException e) {

        }

    }

    public static void main(String[] args) throws IOException{

        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();

    }
}
