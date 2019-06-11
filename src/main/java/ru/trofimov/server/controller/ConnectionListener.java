package ru.trofimov.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionListener extends Thread {
    private List<InputProcessor> processors = new ArrayList<>();

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(8080);
            log.info("listen port 8080");

            while (true) {
                Socket socket = server.accept();
                InputProcessor processor = new InputProcessor(socket);
                processors.add(processor);
                processor.start();
            }
        } catch (IOException e) {
            log.error("Server error occurred", e);
        }
    }
}
