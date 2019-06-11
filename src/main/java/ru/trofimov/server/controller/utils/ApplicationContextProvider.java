package ru.trofimov.server.controller.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * Getting application context
     *
     * @return Application context
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Setting application context
     *
     * @param applicationContext Application context
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        ApplicationContextProvider.setStaticApplicationContext(applicationContext);
    }

    /**
     * Setting application context
     *
     * @param applicationContext Application context
     */
    public static void setStaticApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextProvider.applicationContext = applicationContext;
    }
}
