package ru.mipt.bit.platformer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GameApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(GameApplication.class, args);
        GameLauncher gameLauncher = context.getBean(GameLauncher.class);

        gameLauncher.launch();
    }
}
