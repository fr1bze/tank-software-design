package ru.mipt.bit.platformer.strategies;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.abstractions.graphics.GraphicsController;
import ru.mipt.bit.platformer.abstractions.handlers.KeyboardInputHandler;
import ru.mipt.bit.platformer.abstractions.models.BaseModel;
import ru.mipt.bit.platformer.abstractions.models.MapModel;
import ru.mipt.bit.platformer.abstractions.models.Tank;
import ru.mipt.bit.platformer.abstractions.models.Tree;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomLevelStrategy implements LevelStrategy {
    @Override
    public void generateLevel(TiledMapTileLayer groundLayer, MapModel mapModel, List<BaseModel> models, GraphicsController graphicsController) {
        Set<GridPoint2> occupiedPositions = new HashSet<>();

        Random random = new Random();

        GridPoint2 playerPosition;
        playerPosition = new GridPoint2(random.nextInt(groundLayer.getWidth()), random.nextInt(groundLayer.getHeight()));
        models.add(new Tank("images/tank_blue.png", mapModel, playerPosition, graphicsController, new KeyboardInputHandler()));
        occupiedPositions.add(playerPosition);

        int numberOfTrees = random.nextInt(10) + 5;
        for (int i = 0; i < numberOfTrees; i++) {
            GridPoint2 treePosition;
            do {
                treePosition = new GridPoint2(random.nextInt(groundLayer.getWidth()), random.nextInt(groundLayer.getHeight()));
            } while (occupiedPositions.contains(treePosition));
            models.add(new Tree("images/greenTree.png", treePosition, groundLayer, graphicsController));
            occupiedPositions.add(treePosition);
        }

        int numAITanks = 5;
        for (int i = 0; i < numAITanks; i++) {
            GridPoint2 aiPosition;
            do {
                aiPosition = new GridPoint2(random.nextInt(groundLayer.getWidth()), random.nextInt(groundLayer.getHeight()));
            } while (aiPosition.equals(playerPosition) || isPositionOccupied(aiPosition, models));

            Tank aiTank = new Tank(
                    "images/tank_blue.png",
                    mapModel,
                    aiPosition,
                    graphicsController,
                    null
            );
            models.add(aiTank);
        }
    }

    private boolean isPositionOccupied(GridPoint2 position, List<BaseModel> models) {
        return models.stream().anyMatch(model -> model.getPosition().equals(position));
    }
}
