package com.epam;

import com.epam.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(AppConfig.class);

    }
}
