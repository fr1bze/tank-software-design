package ru.mipt.bit.platformer.ai;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.abstractions.interfaces.Obstacleble;
import ru.mipt.bit.platformer.abstractions.models.Direction;
import ru.mipt.bit.platformer.abstractions.models.MapModel;
import ru.mipt.bit.platformer.abstractions.models.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.Random;

public class AIMotionTankController {
    private final Tank aiTank;
    private final TileMovement tileMovement;
    private final MapModel mapModel;
    private final Random random = new Random();

    public AIMotionTankController(Tank tank, TileMovement tileMovement, MapModel mapModel) {
        this.aiTank = tank;
        this.tileMovement = tileMovement;
        this.mapModel = mapModel;
    }

    public void update(float deltaTime) {
        if (aiTank.isReadyForNextMove()) {
            Direction nextDirection = getRandomDirection();
            GridPoint2 nextPosition = nextDirection.move(aiTank.getCurrentCoordinates());

            if (canMoveTo(nextPosition)) {
                aiTank.updatePosition(tileMovement, deltaTime);
            }
        }

        aiTank.updatePosition(tileMovement, deltaTime);
    }

    private boolean canMoveTo(GridPoint2 nextPosition) {
        return isPositionWithinBounds(nextPosition) && !isObstacle(nextPosition);
    }

    private boolean isObstacle(GridPoint2 nextPosition) {
        for (Obstacleble obstacle: mapModel.getObstacles()) {
            if (obstacle.getCoordinates().contains(nextPosition))
                return false;
        }
        return true;
    }

    private boolean isPositionWithinBounds(GridPoint2 nextPosition) {
        int mapWidth = tileMovement.getTileLayer().getWidth();
        int mapHeight = tileMovement.getTileLayer().getHeight();

        return nextPosition.x >= 0 && nextPosition.x < mapWidth && nextPosition.y >= 0 && nextPosition.y < mapHeight;
    }

    private Direction getRandomDirection() {
        Direction[] directions = Direction.values();
        return directions[random.nextInt(directions.length)];
    }
    

}