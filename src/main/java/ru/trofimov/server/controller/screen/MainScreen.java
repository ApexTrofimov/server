package ru.trofimov.server.controller.screen;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MainScreen implements Screen {
    private final String userId;

    @Override
    public Screen processInput(String inputValue) {
        Screen nextScreen;
        switch (inputValue) {
            case "1":
                nextScreen = new ContentViewScreen(userId);
                break;
            case "2":
                nextScreen = new ProfileScreen(userId);
                break;
            default:
                nextScreen = this;
        }
        return nextScreen;
    }

    @Override
    public String getMessage() {
        return "Выбрать раздел: 1 - для просмотра контента; 2 - для входа в Личный Кабинет";
    }
}
