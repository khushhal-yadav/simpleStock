package com.stock.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Created by khush on 06/11/2016.
 */
public class StockApplicationContext {

    private static final String environment = System.getenv("env");
    private static AnnotationConfigApplicationContext instance;

    private StockApplicationContext() {}

    public synchronized static AbstractApplicationContext getInstance() {
        if (instance == null) {
            if (System.getProperty("spring.profiles.active") == null) {
                System.setProperty("spring.profiles.active", (environment == null) ? "test" : environment);
            }
            instance = new AnnotationConfigApplicationContext("com.stock");
        }
        return instance;
    }
}
