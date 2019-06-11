package ru.trofimov.server.controller;

import lombok.extern.slf4j.Slf4j;
import ru.trofimov.server.controller.screen.LoginScreen;
import ru.trofimov.server.controller.screen.Screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
public class InputProcessor extends Thread {
    public static final String STAR = "*";

    private final Socket socket;
    private Screen currentScreen;

    public InputProcessor(Socket socket) {
        this.socket = socket;
        this.currentScreen = new LoginScreen();
    }

    @Override
    public void run() {
        try (PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            do {
                sendMessageToClient(output);
                readAnswerFromClient(input);
            } while (true);
        } catch (IOException e) {
            log.error("Server error occurred", e);
        }
    }

    private void readAnswerFromClient(BufferedReader input) throws IOException {
        String inputMessage = input.readLine();
        log.debug(inputMessage);
        currentScreen = currentScreen.processInput(inputMessage);
    }

    private void sendMessageToClient(PrintWriter output) {
        String outputMessage = currentScreen.getMessage();
        log.debug(outputMessage);
        output.println(outputMessage);
    }
}
