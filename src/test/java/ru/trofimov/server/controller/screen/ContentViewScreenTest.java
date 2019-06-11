package ru.trofimov.server.controller.screen;

import org.junit.Assert;
import org.junit.Test;

import static ru.trofimov.server.controller.InputProcessor.STAR;

public class ContentViewScreenTest {

    private static final String TEST_USER = "testUser";
    public static final String ONE = "1";
    public static final String TWO = "2";

    @Test
    public void processInput_noContents() {
        ContentViewScreen contentViewScreen = new ContentViewScreen(TEST_USER);
        // don't need to do anything
        Screen nextScreen = contentViewScreen.processInput("some_value");
        Assert.assertEquals(contentViewScreen, nextScreen);

        // should go to main screen
        nextScreen = contentViewScreen.processInput(STAR);
        Assert.assertEquals(MainScreen.class, nextScreen.getClass());

        // have no contents. Don't need to do anything
        nextScreen = contentViewScreen.processInput(ONE);
        Assert.assertEquals(contentViewScreen, nextScreen);
        Assert.assertEquals(0, contentViewScreen.getCurrentItem());

        // have no contents. Don't need to do anything
        nextScreen = contentViewScreen.processInput(TWO);
        Assert.assertEquals(contentViewScreen, nextScreen);
    }
}