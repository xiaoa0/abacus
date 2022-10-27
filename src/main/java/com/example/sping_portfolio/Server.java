package com.example.sping_portfolio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

    public void generateNewQuestion() {

        // generates a random answer from 0-1 trillion
        long newAnswer = (long) Math.floor(Math.random() * 999_999_999_999L);

        // generates two numbers when added equals the random answer
        long addend1 = (long) Math.floor(Math.random() * newAnswer);
        long addend2 = (long) newAnswer - addend1;

        // concatenates addends and answer into a string
        String newQuestion = addend1 + " + " + addend2 + " = " + newAnswer;

        // declares answer and question
        this.answer = newAnswer;
        this.question = newQuestion;
    }

    public void sendNewQuestion() {
        for (ClientHandler clientHandler : ClientHandler.clientHandlers) {
            try {
                // If its any client connected but this one
                clientHandler.bufferedWriter.write(this.question);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            } catch (IOException e) {

            }
        }
    }

    public void testAnswer(ClientHandler clientTester) {
        
        // if they answer correctly
        if (clientTester.answer == answer) {
            
            // increment client score
            clientTester.incrementScore(1);
            
            // decrement all other scores
            for (ClientHandler clientHandler : ClientHandler.clientHandlers) {
                if (clientHandler.clientUsername != clientTester.clientUsername) {
                    clientHandler.incrementScore(-1);
                }
            }

            // generate a new question
            this.generateNewQuestion();

            // send the new question
            this.sendNewQuestion();
        }
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
