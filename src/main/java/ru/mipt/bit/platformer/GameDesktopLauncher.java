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
import ru.mipt.bit.platformer.abstractions.models.BaseModel;
import ru.mipt.bit.platformer.strategies.FileLevelStrategy;
import ru.mipt.bit.platformer.strategies.LevelStrategy;
import ru.mipt.bit.platformer.strategies.RandomLevelStrategy;
import ru.mipt.bit.platformer.util.TileMovement;

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

    public GameDesktopLauncher(LevelStrategy levelStrategy) {
        this.levelStrategy = levelStrategy;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        field = new Field("level.tmx", batch);
        TiledMapTileLayer groundLayer = field.getLayer();
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        models = new ArrayList<>();
        GraphicsController graphicsController = new GraphicsController();

        levelStrategy.generateLevel(groundLayer, models, graphicsController);

        modelController = new ModelController(models, tileMovement, graphicsController);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();

        modelController.updateModels(deltaTime);

        models.sort(Comparator.comparingInt(model -> -model.getPosition().y));
        field.render();

        batch.begin();
        modelController.renderModels(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        field.dispose();
        modelController.disposeModels();
    }

    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);

        if (args[0].equals("file"))
            new Lwjgl3Application(new GameDesktopLauncher(new FileLevelStrategy(args[1])), config);
        else
            new Lwjgl3Application(new GameDesktopLauncher(new RandomLevelStrategy()), config);
    }
}