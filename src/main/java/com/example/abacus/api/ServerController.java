package com.example.abacus.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.abacus.backend.Server;

@RestController
@RequestMapping("/servers")
public class ServerController {

    private final ServerRepository repository;

    public ServerController(ServerRepository repository) {
        this.repository = repository;
    }

    // hosts to local host
    @GetMapping
    public List<Server> findAll() {
        return repository.findAll();
    }

}