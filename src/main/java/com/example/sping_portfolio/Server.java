package com.example.sping_portfolio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    
    // Listens for incoming clients and creates a socket to communicate with them
    private ServerSocket serverSocket;
    private long answer;
    private String question;
    private int roundNum = 0;

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



    public String getQuestion() {
        return this.question;
    }



    public void testAnswer(ClientHandler clientTester) {
        
        // if they answer correctly

        System.out.println();
        System.out.println(clientTester.clientUsername + " guessed: " + clientTester.answer);
        if (clientTester.answer == answer) {

            // change all scores for objects
            clientTester.setScore(clientTester.getScore() + 1);
            for (ClientHandler clientHandler : ClientHandler.clientHandlers) {
                if (clientHandler.clientUsername != clientTester.clientUsername) {
                    clientHandler.setScore(clientHandler.getScore() - 1);
                }
            }

            this.newRound();

        } else {
            System.out.println("client answered incorrect");
            clientTester.refreshUI(clientTester);
        }
    }

    public void newRound() {

        this.generateNewQuestion();
        this.roundNum += 1;
        System.out.println("\033[H\033[2J");
        System.out.println("A new round has started: " + this.getQuestion());
        System.out.println("Answer: " + this.answer);
        System.out.println("Round: " + this.roundNum);
        
        for (ClientHandler clientHandler : ClientHandler.clientHandlers) {
            System.out.println(clientHandler.clientUsername + " score: " + clientHandler.getScore());
            clientHandler.refreshUI(clientHandler);
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

        server.generateNewQuestion();
        server.startServer();
    }
}
