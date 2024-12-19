package ru.mipt.bit.platformer;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.springframework.stereotype.Component;

class WindowSize {
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 1024;

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }
}
public class GameLauncher {
    private final GameEngine gameEngine;

    public GameLauncher(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void launch() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(WindowSize.getWindowWidth(), WindowSize.getWindowHeight());
        new Lwjgl3Application(gameEngine, config);
    }
}
