package ru.mipt.bit.platformer.abstractions.models;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.abstractions.Renderable;
import ru.mipt.bit.platformer.abstractions.graphics.GraphicsController;
import ru.mipt.bit.platformer.abstractions.handlers.InputHandler;
import ru.mipt.bit.platformer.abstractions.interfaces.Obstacleble;
import ru.mipt.bit.platformer.abstractions.movement.Movable;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tank extends BaseModel implements Movable, Renderable, Obstacleble {

    static class TankConstants {
        private static final float MOVEMENT_SPEED = 0.4f;
        private static final float MOVEMENT_COMPLETE = 1f;
        private static final float INITIAL_ROTATION = 0f;
    }

    private final float movementSpeed;
    private GridPoint2 currentCoordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress;
    private float rotation;
    private final InputHandler inputHandler;
    private final MapModel map;

    public Tank(String texturePath, MapModel map, GridPoint2 initialCoordinates, GraphicsController graphicsController,
                InputHandler inputHandler) {
        super(texturePath, initialCoordinates, graphicsController);
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
        this.currentCoordinates = initialCoordinates;
        this.movementSpeed = TankConstants.MOVEMENT_SPEED;
        this.rotation = TankConstants.INITIAL_ROTATION;
        this.movementProgress = TankConstants.MOVEMENT_COMPLETE;
        this.inputHandler = inputHandler;
        this.map = map;
    }

    public Tank(GridPoint2 initialCoordinates) {
        super(initialCoordinates);
        this.movementSpeed = TankConstants.MOVEMENT_SPEED;
        currentCoordinates = initialCoordinates;
        this.inputHandler = null;
        this.map = null;
    }

    public float getRotation() {
        return rotation;
    }

    public GridPoint2 getCurrentCoordinates() {
        return this.currentCoordinates;
    }

    public void move(Direction direction, Set<Obstacleble> obstacles, int rowCount, int columnCount) {
        GridPoint2 newCoordinates = direction.move(currentCoordinates);

        if (newCoordinates.x < 0 || newCoordinates.x >= columnCount || newCoordinates.y < 0 || newCoordinates.y >= rowCount) {
            cancelMovement();
            return;
        }
        for (Obstacleble obstacle : obstacles) {
            if (obstacle.getCoordinates().contains(newCoordinates)) {
                cancelMovement();
                return;
            }
        }
        destinationCoordinates.set(newCoordinates);
        movementProgress = 0f;
    }

    public boolean isReadyForNextMove() {
        boolean movementComplete = isCompleteMovement();
        boolean canMoveToNextPoint = canMoveToPoint(destinationCoordinates);

        return movementComplete && canMoveToNextPoint;
    }

    @Override
    public void handleInput() {
        if (isEqual(movementProgress, 1f)) {
            Direction direction = inputHandler.handleInput();
            if (direction != null) {
                destinationCoordinates = direction.move(currentCoordinates);
                rotation = direction.getRotation();
                movementProgress = 0f;
            }
        }
    }

    @Override
    public void updatePosition(TileMovement tileMovement, float deltaTime) {
        tileMovement.moveRectangleBetweenTileCenters(getRectangle(), currentCoordinates, destinationCoordinates, movementProgress);

        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isEqual(movementProgress, TankConstants.MOVEMENT_COMPLETE)) {
            currentCoordinates.set(destinationCoordinates);
        }
    }

    public void cancelMovement() {
        movementProgress = 1f;
    }

    public GridPoint2 getDestination() {
        return new GridPoint2(destinationCoordinates);
    }

    @Override
    public Collection<GridPoint2> getCoordinates() {
        return Arrays.asList(currentCoordinates, destinationCoordinates);
    }

    @Override
    public void render(Batch batch) {
        graphicsController.render(batch, getGraphics(), getRectangle(), rotation);
    }

    private boolean isCompleteMovement() {
        return isEqual(movementProgress, TankConstants.MOVEMENT_COMPLETE);
    }

    private boolean canMoveToPoint(GridPoint2 coordinates) {
        return !isObstacle(coordinates, map.getObstacles());
    }

    private boolean isObstacle(GridPoint2 coordinates, Set<Obstacleble> obstacles) {
        for (Obstacleble obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(coordinates)) {
                return true;
            }
        }
        return false;
    }

    public InputHandler getInputHandler() {
        return this.inputHandler;
    }
}
