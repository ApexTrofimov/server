package ru.trofimov.server.controller.screen;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import ru.trofimov.server.controller.utils.SpringUtils;
import ru.trofimov.server.domain.ContentEntity;
import ru.trofimov.server.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static ru.trofimov.server.controller.InputProcessor.STAR;

@RequiredArgsConstructor
public class ProfileScreen implements Screen {
    private static final String ONE = "1";
    private static final String TWO = "2";
    private static final String REMOVE_CONTENT_MESSAGE = "Контент %s удален из вашего личного кабинета. " +
                    "Для выхода в основное меню - нажмите *";
    private static final String NO_CONTENT_MESSAGE = "У вас нет купленных контентов. " +
            "Для выхода в основное меню - нажмите *";
    private static final String SELECT_CONTENT_MESSAGE = "Вы находитесь в меню управления услугой и редактирования персональной фонотеки." +
                    "Текущий контент: %s. " +
                    "Для перехода к следующей единице контента нажмите 1, 2 - для удаления текущей единицы контента." +
                    "Для выхода в основное меню - нажмите *";

    private final String userId;

    private int currentItem = 0;
    private List<ContentEntity> contents;

    private void changeCurrentItem() {
        currentItem = currentItem == (contents.size()-1) ? 0
                : ++currentItem;
    }

    public Screen processInput(String inputValue) {
        Screen nextScreen;
        switch (inputValue) {
            case STAR:
                nextScreen = new MainScreen(userId);
                break;
            case ONE:
                changeCurrentItem();
                nextScreen = this;
                break;
            case TWO:
                if (contents.size() != 0) {
                    removeContent();
                    nextScreen = new MessageScreen(userId,
                                    String.format(REMOVE_CONTENT_MESSAGE, contents.get(currentItem).getTitle()));
                    break;
                }
            default:
                nextScreen = this;
        }
        return nextScreen;
    }

    private void removeContent() {
        UserService userService = SpringUtils.getSpringBean(UserService.class);
        userService.removeContent(userId, contents.get(currentItem).getId());
    }

    @Override
    public String getMessage() {
        loadContentsIfNeed();
        return CollectionUtils.isEmpty(contents) ? NO_CONTENT_MESSAGE
                : String.format(SELECT_CONTENT_MESSAGE, contents.get(currentItem).getTitle());
    }

    private void loadContentsIfNeed() {
        if (contents == null) {
            UserService userService = SpringUtils.getSpringBean(UserService.class);
            contents = new ArrayList<>(userService.findUserById(userId).getContents());
        }
    }
}
