package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.abstractions.Field;
import ru.mipt.bit.platformer.abstractions.ModelController;
import ru.mipt.bit.platformer.abstractions.graphics.GraphicsController;
import ru.mipt.bit.platformer.abstractions.handlers.InputHandler;
import ru.mipt.bit.platformer.abstractions.handlers.KeyboardInputHandler;
import ru.mipt.bit.platformer.abstractions.models.BaseModel;
import ru.mipt.bit.platformer.abstractions.models.MapModel;
import ru.mipt.bit.platformer.abstractions.models.Tank;
import ru.mipt.bit.platformer.ai.AIMotionTankController;
import ru.mipt.bit.platformer.strategies.FileLevelStrategy;
import ru.mipt.bit.platformer.strategies.LevelStrategy;
import ru.mipt.bit.platformer.strategies.RandomLevelStrategy;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.util.ToggleHealthBarCommand;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import java.util.*;

public class GameDesktopLauncher implements ApplicationListener {
    private LevelStrategy levelStrategy;
    private Batch batch;
    private Field field;
    private List<BaseModel> models;
    private TileMovement tileMovement;
    private ModelController modelController;
    private InputHandler inputHandler;
    private MapModel mapModel;
    private List<AIMotionTankController> aiControllers;

    public GameDesktopLauncher(LevelStrategy levelStrategy) {
        this.levelStrategy = levelStrategy;
    }


    @Override
    public void create() {
        initializeGameComponents();
    }

    @Override
    public void render() {
        clearScreen();
        updateGameState();
        renderModels();
    }

    @Override
    public void dispose() {
        disposeGameComponents();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}


    private void initializeGameComponents() {
        batch = new SpriteBatch();
        field = new Field("level.tmx", batch);

        TiledMapTileLayer groundLayer = field.getLayer();
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        models = new ArrayList<>();

        aiControllers = new ArrayList<>();
        for (BaseModel model : models) {
            if (model instanceof Tank tank && tank.getInputHandler() == null) {
                aiControllers.add(new AIMotionTankController(tank, tileMovement, mapModel));
            }
        }
        GraphicsController graphicsController = new GraphicsController();
        levelStrategy.generateLevel(groundLayer, mapModel, models, graphicsController);
        modelController = new ModelController(models, tileMovement, graphicsController);
        inputHandler  = new KeyboardInputHandler(new ToggleHealthBarCommand(modelController.getTanks()));
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void updateGameState() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        modelController.updateModels(deltaTime);
        for (AIMotionTankController controller : aiControllers) {
            controller.update(deltaTime);
        }
        inputHandler.handleHealthInput();
    }

    private void renderModels() {
        models.sort(Comparator.comparingInt(model -> -model.getPosition().y));
        field.render();

        batch.begin();
        modelController.renderModels(batch);
        batch.end();
    }

    private void disposeGameComponents() {
        batch.dispose();
        field.dispose();
        modelController.disposeModels();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);

        LevelStrategy levelStrategy = parseLevelStrategy(args);
        new Lwjgl3Application(new GameDesktopLauncher(levelStrategy), config);
    }

    private static LevelStrategy parseLevelStrategy(String[] args) {
        if (args.length > 0 && "file".equalsIgnoreCase(args[0])) {
            if (args.length > 1) {
                return new FileLevelStrategy(args[1]);
            } else {
                throw new IllegalArgumentException("Incorrect arguments :(");
            }
        }
        return new RandomLevelStrategy();
    }
}