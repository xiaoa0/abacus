package com.example.sping_portfolio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    
    // List of all of the clients connected
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    String clientUsername;
    private int score;
    private Server server;
    public int answer;

    public ClientHandler(Socket socket, Server server) {
        try {
            this.socket = socket;
            this.server = server;
            // socket.getOutputStream() gets a byte stream
            // Wrap it in OutputStreamWriter() to turn it into characters 
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername + " has entered the chat!");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    // This is a seperate thread to listen to messages
    @Override
    public void run() {
        String messageFromClient;

        // Listens for user input while the socket is connected
        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                System.out.print(messageFromClient);
                if (messageFromClient.equals("start game")) {
                    server.generateNewQuestion();
                    server.sendNewQuestion();
                }
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend) {

        // New ClientHandler object named clientHandler
        // For each clientHandler in arrayList clientHandlers
        // I dislike you java
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                // If its any client connected but this one
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUsername + "has disconnected");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();

        /* Only need to close buffered reader and writer and not source streams 
        because closing the buffers automatically close them */
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void incrementScore(int newScore) {
        this.score += newScore;
    }

    public int getScore() {
        return this.score;
    }
}