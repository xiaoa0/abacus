package com.example.abacus.api;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.abacus.backend.Server;

@Repository
public class ServerRepository {
    List<Server> servers = new ArrayList<>();
    
    public ServerRepository() throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        servers.add(new Server(serverSocket, UUID.randomUUID().toString()));
    }

    public List<Server> findAll() {
        return servers;
    }

    public Server findByID(String id) {
        for (Server server : servers) {
            if (server.getId() == id) {
                return server;
            }
        }
        return servers.get(0);
    }

    public Server create(Server server) {
        servers.add(server);
        return server;
    }

    public void delete(String id) {
        servers.removeIf(server -> server.getId().equals(id));
    }

}
