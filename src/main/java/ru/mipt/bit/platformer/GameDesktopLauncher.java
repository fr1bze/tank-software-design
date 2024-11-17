package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.abstractions.Field;
import ru.mipt.bit.platformer.abstractions.LevelGenerator;
import ru.mipt.bit.platformer.abstractions.ModelController;
import ru.mipt.bit.platformer.abstractions.controllers.GraphicsController;
import ru.mipt.bit.platformer.abstractions.handlers.InputHandler;
import ru.mipt.bit.platformer.abstractions.handlers.KeyboardInputHandler;
import ru.mipt.bit.platformer.abstractions.models.BaseModel;
import ru.mipt.bit.platformer.abstractions.models.Tank;
import ru.mipt.bit.platformer.abstractions.models.Tree;
import ru.mipt.bit.platformer.ai.AIMotion;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.random;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GameDesktopLauncher implements ApplicationListener {
    Config config = Config.RANDOM;
    private Batch batch;
    private Field field;
    private List<BaseModel> models;
    private TileMovement tileMovement;
    private ModelController modelController;
    private InputHandler inputHandler;
    private GraphicsController graphicsController;

    GameDesktopLauncher (Config config) {
        this.config = config;
    }
    @Override
    public void create() {
        batch = new SpriteBatch();
        field = new Field("level.tmx", batch);
        graphicsController = new GraphicsController(batch);

        models = new ArrayList<>();
        modelController = new ModelController(models);

        LevelGenerator levelGenerator = new LevelGenerator(field, graphicsController, modelController);
        if (config == Config.RANDOM) {
            levelGenerator.generateRandomLevel();
        } else if (config == Config.FILE) {
            levelGenerator.loadLevelFromFile("level.txt");
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();
        modelController.updateModels(deltaTime);
        field.render();
        graphicsController.renderModels();
    }

    @Override
    public void dispose() {
        batch.dispose();
        field.dispose();
        modelController.disposeModels();
    }


    @Override
    public void create() {
        batch = new SpriteBatch();
        field = new Field("level.tmx", batch);

        TiledMapTileLayer groundLayer = field.getLayer();
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        models = new ArrayList<>();
        GraphicsController graphicsController = new GraphicsController();

        if (config == Config.RANDOM) {
            generateRandomLevel(groundLayer, graphicsController);
        } else if (config == Config.FILE) {
            loadLevelFromFile("level.txt");
        }

        modelController = new ModelController(models, tileMovement, graphicsController);
    }
    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();

        modelController.updateModels(deltaTime);

        for (AIMotion aiMotion : aiControllers) {
            aiMotion.update(deltaTime);
        }

        models.sort(Comparator.comparingInt(model -> -model.getPosition().y));
        field.render();

        batch.begin();
        modelController.renderModels(batch);
        batch.end();
    }

    private void generateRandomLevel(TiledMapTileLayer groundLayer, GraphicsController graphicsController) {
        Set<GridPoint2> occupiedPositions = new HashSet<>();

        GridPoint2 playerPosition;
        do {
            playerPosition = new GridPoint2(random.nextInt(groundLayer.getWidth()), random.nextInt(groundLayer.getHeight()));
        } while (occupiedPositions.contains(playerPosition));
        Tank playerTank = new Tank("images/tank_blue.png", playerPosition, 0.4f, graphicsController, new KeyboardInputHandler());
        models.add(playerTank);
        occupiedPositions.add(playerPosition);
        int numberOfAITanks = random.nextInt(5) + 3;
        for (int i = 0; i < numberOfAITanks; i++) {
            GridPoint2 aiTankPosition;
            do {
                aiTankPosition = new GridPoint2(random.nextInt(groundLayer.getWidth()), random.nextInt(groundLayer.getHeight()));
            } while (occupiedPositions.contains(aiTankPosition));
            Tank aiTank = new Tank("images/tank_blue.png", aiTankPosition, 0.4f, graphicsController, null); // AI танк без пользовательского управления
            AIMotion aiMotion = new AIMotion(aiTank, tileMovement);
            models.add(aiTank);
            occupiedPositions.add(aiTankPosition);

            aiControllers.add(aiMotion);
        }

        int numberOfTrees = random.nextInt(10) + 5;
        for (int i = 0; i < numberOfTrees; i++) {
            GridPoint2 treePosition;
            do {
                treePosition = new GridPoint2(random.nextInt(groundLayer.getWidth()), random.nextInt(groundLayer.getHeight()));
            } while (occupiedPositions.contains(treePosition));
            models.add(new Tree("images/greenTree.png", treePosition, groundLayer, graphicsController));
            occupiedPositions.add(treePosition);
        }
    }

    private void loadLevelFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int y = 0;
            while ((line = reader.readLine()) != null) {
                for (int x = 0; x < line.length(); ++x) {
                    char cell = line.charAt(x);
                    GridPoint2 position = new GridPoint2(x, y);
                    if (cell == 'T') {
                        models.add(new Tree("images/greenTree.png", position, field.getLayer(), new GraphicsController()));
                    } else if (cell == 'X') {
                        models.add(new Tank("images/tank_blue.png", position, 0.4f, new GraphicsController(), new KeyboardInputHandler()));
                    }
                }
                y++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        new Lwjgl3Application(new GameDesktopLauncher(Config.RANDOM), config);
    }
}