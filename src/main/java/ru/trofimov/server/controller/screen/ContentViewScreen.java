package ru.trofimov.server.controller.screen;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import ru.trofimov.server.controller.utils.SpringUtils;
import ru.trofimov.server.domain.ContentEntity;
import ru.trofimov.server.service.ContentService;
import ru.trofimov.server.service.UserService;

import java.util.List;

import static ru.trofimov.server.controller.InputProcessor.STAR;

@RequiredArgsConstructor
public class ContentViewScreen implements Screen {
    private static final String ONE = "1";
    private static final String TWO = "2";
    private static final String NO_CONTENT_MESSAGE = "Нет доступных контентов для покупки. " +
                    "Для выхода в основное меню - нажмите *";
    private static final String SELECT_CONTENT_MESSAGE = "Текущий контент: %s. " +
                    "Нажмите 1 для перехода к следующей единице контента, 2 - для покупки текущего контента." +
                    "Для выхода в основное меню - нажмите *";
    private static final String ADD_CONTENT_MESSAGE = "Контент %s добавлен в ваш личный кабинет. " +
                    "Для выхода в основное меню - нажмите *";

    private final String userId;
    @Getter
    private int currentItem = 0;
    private List<ContentEntity> contents;

    @Override
    public Screen processInput(String inputValue) {
        Screen nextScreen;
        switch (inputValue) {
            case STAR:
                nextScreen = new MainScreen(userId);
                break;
            case ONE:
                setNextItem();
                nextScreen = this;
                break;
            case TWO:
                if (!CollectionUtils.isEmpty(contents)) {
                    addContent();
                    nextScreen = new MessageScreen(userId,
                                    String.format(ADD_CONTENT_MESSAGE, contents.get(currentItem).getTitle()));
                    break;
                }
            default:
                nextScreen = this;
        }
        return nextScreen;
    }

    private void addContent() {
        UserService userService = SpringUtils.getSpringBean(UserService.class);
        userService.addContent(userId, contents.get(currentItem).getId());
    }

    private void setNextItem() {
        if (CollectionUtils.isEmpty(contents)) {
            return;
        }
        currentItem = currentItem == (contents.size() - 1) ? 0
                        : ++currentItem;
    }

    @Override
    public String getMessage() {
        loadContentsIfNeed();
        return CollectionUtils.isEmpty(contents) ? NO_CONTENT_MESSAGE
                : String.format(SELECT_CONTENT_MESSAGE, contents.get(currentItem).getTitle());
    }

    private void loadContentsIfNeed() {
        if (contents == null) {
            ContentService contentService = SpringUtils.getSpringBean(ContentService.class);
            contents = contentService.getContents();
        }
    }
}
