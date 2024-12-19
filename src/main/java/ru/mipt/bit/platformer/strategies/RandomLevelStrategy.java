package ru.mipt.bit.platformer.strategies;

import com.badlogic.gdx.math.GridPoint2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.abstractions.ModelController;
import ru.mipt.bit.platformer.abstractions.handlers.KeyboardInputHandler;
import ru.mipt.bit.platformer.abstractions.interfaces.MapLoader;
import ru.mipt.bit.platformer.abstractions.models.BaseModel;
import ru.mipt.bit.platformer.abstractions.models.MapModel;
import ru.mipt.bit.platformer.abstractions.models.Tank;
import ru.mipt.bit.platformer.abstractions.models.Tree;
import ru.mipt.bit.platformer.util.ButtonHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class RandomLevelStrategy implements MapLoader {
    @Value("${app.loader.rows}")
    private int rows;
    @Value("${app.loader.columns}")
    private int columns;
    @Value("${app.loader.tanks}")
    private int numAITanks;

    private ButtonHandler buttonHandler;
    private ModelController modelController;

    private boolean isPositionOccupied(GridPoint2 position, List<BaseModel> models) {
        return models.stream().anyMatch(model -> model.getPosition().equals(position));
    }

    @Override
    public MapModel load() {
        Random random = new Random();

        Set<Tree> trees = new HashSet<>();
        Set<Tank> tanks = new HashSet<>();
        MapModel map = new MapModel();

        GridPoint2 playerPosition = new GridPoint2(random.nextInt(columns), random.nextInt(rows));
        Tank playerTank = new Tank("images/tank_blue.png", map, playerPosition, new KeyboardInputHandler(buttonHandler, modelController));

        int numberOfTrees = random.nextInt(10) + 5;
        Set<GridPoint2> occupiedPositions = new HashSet<>();
        occupiedPositions.add(playerPosition);

        for (int i = 0; i < numberOfTrees; i++) {
            GridPoint2 treePosition;
            do {
                treePosition = new GridPoint2(random.nextInt(columns), random.nextInt(rows));
            } while (occupiedPositions.contains(treePosition));
            trees.add(new Tree("images/greenTree.png", treePosition, null, null));
            occupiedPositions.add(treePosition);
        }

        for (int i = 0; i < numAITanks; i++) {
            GridPoint2 aiPosition;
            do {
                aiPosition = new GridPoint2(random.nextInt(columns), random.nextInt(rows));
            } while (occupiedPositions.contains(aiPosition));
            tanks.add(new Tank("images/tank_red.png", map, aiPosition, null));
            occupiedPositions.add(aiPosition);
        }
        map.setMapSize(rows, columns);
        map.setTanks(tanks);
        map.setTrees(trees);
        map.setPlayer(playerTank);

        return map;
    }
}
