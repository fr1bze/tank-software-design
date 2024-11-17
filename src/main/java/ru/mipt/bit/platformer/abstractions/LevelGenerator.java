package ru.mipt.bit.platformer.abstractions;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.abstractions.controllers.GraphicsController;
import ru.mipt.bit.platformer.abstractions.controllers.ModelController;
import ru.mipt.bit.platformer.abstractions.handlers.KeyboardInputHandler;
import ru.mipt.bit.platformer.abstractions.models.Tank;
import ru.mipt.bit.platformer.abstractions.models.Tree;
import ru.mipt.bit.platformer.ai.AIMotion;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.random;

public class LevelGenerator {
    private final Field field;
    private final GraphicsController graphicsController;
    private final ModelController modelController;
    private final List<AIMotion> AIControllers;

    public LevelGenerator(Field field, GraphicsController graphicsController, ModelController modelController) {
        this.field = field;
        this.graphicsController = graphicsController;
        this.modelController = modelController;
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

    public void loadLevelFromFile(String filePath) {
    }
}
