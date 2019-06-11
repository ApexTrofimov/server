package ru.trofimov.server.controller.screen;

public interface Screen {

    Screen processInput(String inputValue);

    String getMessage();
}
