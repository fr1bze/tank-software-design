package ru.mipt.bit.platformer.abstractions;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.abstractions.controllers.GraphicsController;
import ru.mipt.bit.platformer.abstractions.controllers.ModelController;
import ru.mipt.bit.platformer.abstractions.handlers.KeyboardInputHandler;
import ru.mipt.bit.platformer.abstractions.models.Tank;
import ru.mipt.bit.platformer.abstractions.models.Tree;
import ru.mipt.bit.platformer.ai.AIMotion;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.abstractions.models.Field;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.random;

public class LevelGenerator {
    private final Field field;
    private final GraphicsController graphicsController;
    private final ModelController modelController;
    private final List<AIMotion> AIControllers;

    public LevelGenerator(Field field, GraphicsController graphicsController, ModelController modelController, List<AIMotion> aiControllers) {
        this.field = field;
        this.graphicsController = graphicsController;
        this.modelController = modelController;
        AIControllers = aiControllers;
    }

    private void generateRandomLevel(TiledMapTileLayer groundLayer, GraphicsController graphicsController) {
        Set<GridPoint2> occupiedPositions = new HashSet<>();

        GridPoint2 playerPosition;
        do {
            playerPosition = new GridPoint2(random.nextInt(groundLayer.getWidth()), random.nextInt(groundLayer.getHeight()));
        } while (occupiedPositions.contains(playerPosition));
        Tank playerTank = new Tank("images/tank_blue.png", playerPosition, 0.4f, graphicsController, new KeyboardInputHandler());
        modelController.getModels().add(playerTank);
        occupiedPositions.add(playerPosition);
        int numberOfAITanks = random.nextInt(5) + 3;
        for (int i = 0; i < numberOfAITanks; i++) {
            GridPoint2 aiTankPosition;
            do {
                aiTankPosition = new GridPoint2(random.nextInt(groundLayer.getWidth()), random.nextInt(groundLayer.getHeight()));
            } while (occupiedPositions.contains(aiTankPosition));
            Tank aiTank = new Tank("images/tank_blue.png", aiTankPosition, 0.4f, graphicsController, null); // AI танк без пользовательского управления
            TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.linear);
            AIMotion aiMotion = new AIMotion(aiTank, tileMovement);
            modelController.getModels().add(aiTank);
            occupiedPositions.add(aiTankPosition);

            AIControllers.add(aiMotion);
        }

        int numberOfTrees = random.nextInt(10) + 5;
        for (int i = 0; i < numberOfTrees; i++) {
            GridPoint2 treePosition;
            do {
                treePosition = new GridPoint2(random.nextInt(groundLayer.getWidth()), random.nextInt(groundLayer.getHeight()));
            } while (occupiedPositions.contains(treePosition));
            modelController.getModels().add(new Tree("images/greenTree.png", treePosition, groundLayer, graphicsController));
            occupiedPositions.add(treePosition);
        }
    }

    public void loadLevelFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int y = 0;
            while ((line = reader.readLine()) != null) {
                for (int x = 0; x < line.length(); ++x) {
                    char cell = line.charAt(x);
                    GridPoint2 position = new GridPoint2(x, y);
                    if (cell == 'T') {
                        modelController.getModels().add(new Tree("images/greenTree.png", position, field.getLayer(), new GraphicsController()));
                    } else if (cell == 'X') {
                        modelController.getModels().add(new Tank("images/tank_blue.png", position, 0.4f, new GraphicsController(), new KeyboardInputHandler()));
                    }
                }
                y++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
