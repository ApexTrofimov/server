package ru.trofimov.server.controller.screen;

import lombok.RequiredArgsConstructor;

import static ru.trofimov.server.controller.InputProcessor.STAR;

@RequiredArgsConstructor
public class MessageScreen implements Screen {
    private final String userId;
    private final String message;

    @Override
    public Screen processInput(String inputValue) {
        if (STAR.equals(inputValue)) {
            return new MainScreen(userId);
        }
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
