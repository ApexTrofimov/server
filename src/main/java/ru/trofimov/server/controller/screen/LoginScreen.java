package ru.trofimov.server.controller.screen;

import ru.trofimov.server.controller.utils.SpringUtils;
import ru.trofimov.server.domain.UserEntity;
import ru.trofimov.server.service.UserService;

import java.util.Optional;

public class LoginScreen implements Screen {
    private static final String INPUT_LOGIN_MESSAGE = "Введите логин:";

    @Override
    public Screen processInput(String inputValue) {
        UserService userService = SpringUtils.getSpringBean(UserService.class);
        Optional<UserEntity> user = userService.findUserByLogin(inputValue);
        return user.<Screen>map(userEntity -> new MainScreen(userEntity.getId()))
                .orElse(this);
    }

    @Override
    public String getMessage() {
        return INPUT_LOGIN_MESSAGE;
    }
}
