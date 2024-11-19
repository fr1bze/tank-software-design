package ru.mipt.bit.platformer.ai;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.abstractions.models.Direction;
import ru.mipt.bit.platformer.abstractions.models.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.Random;

public class AIMotion {
    private final Tank aiTank;
    private final TileMovement tileMovement;
    private final Random random = new Random();

    public AIMotion(Tank tank, TileMovement tileMovement) {
        this.aiTank = tank;
        this.tileMovement = tileMovement;
    }

    public void update(float deltaTime) {
        if (aiTank.isReadyForNextMove()) {
            Direction nextDirection = getRandomDirection();
            GridPoint2 nextPosition = nextDirection.move(aiTank.getCurrentCoordinates());

            if (canMoveTo(nextPosition)) {
                aiTank.setDestination(nextPosition, nextDirection.getRotation());
            }
        }

        aiTank.updatePosition(tileMovement, deltaTime);
    }

    private boolean canMoveTo(GridPoint2 nextPosition) {
        return isPositionWithinBounds(nextPosition) && !isObstacle(nextPosition);
    }

    private Direction getRandomDirection() {
        Direction[] directions = Direction.values();
        return directions[random.nextInt(directions.length)];
    }

    private boolean isPositionWithinBounds(GridPoint2 position) {
        return position.x >= 0 && position.y >= 0;
    }

    private boolean isObstacle(GridPoint2 position) {
        return false;
    }


}
