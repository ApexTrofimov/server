package ru.trofimov.server.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConnectionListenerRunner implements CommandLineRunner {
    private ConnectionListener connectionListener;

    @Override
    public void run(String... args) {
        connectionListener = new ConnectionListener();
        connectionListener.start();
    }
}
