package ru.trofimov.server.controller.utils;

import org.springframework.beans.BeansException;

public class SpringUtils {

    public static <T> T getSpringBean(Class<T> requiredType) throws BeansException {
        return ApplicationContextProvider.getApplicationContext().getBean(requiredType);
    }
}
