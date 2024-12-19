package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.abstractions.ModelController;

public class GameEngine implements ApplicationListener {

    private final GameInitializer gameInitializer;
    private final GraphicsManager graphicsManager;
    private final InputManager inputManager;
    private final AIManager aiManager;
    private final ModelController modelController;

    public GameEngine(GameInitializer gameInitializer,
                      GraphicsManager graphicsManager,
                      InputManager inputManager,
                      AIManager aiManager,
                      ModelController modelController) {
        this.gameInitializer = gameInitializer;
        this.graphicsManager = graphicsManager;
        this.inputManager = inputManager;
        this.aiManager = aiManager;
        this.modelController = modelController;
    }

    @Override
    public void create() {
        gameInitializer.initialize();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        graphicsManager.clearScreen();
        float deltaTime = Gdx.graphics.getDeltaTime();
        modelController.updateModels(deltaTime);
        aiManager.updateAI(deltaTime);
        inputManager.handleInput();
        graphicsManager.renderModels(modelController.getModels(), modelController);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        graphicsManager.dispose();
        modelController.disposeModels();
    }

}
