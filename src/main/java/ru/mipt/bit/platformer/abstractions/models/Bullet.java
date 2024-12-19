package ru.mipt.bit.platformer.abstractions.models;


import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.abstractions.interfaces.ModelPlayable;
import ru.mipt.bit.platformer.abstractions.interfaces.MoveGraphics;
import ru.mipt.bit.platformer.abstractions.interfaces.Observerable;
import ru.mipt.bit.platformer.abstractions.interfaces.Obstacleble;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Bullet implements MoveGraphics {

    private static final float MOVEMENT_SPEED = 0.2f;
    private static final float MOVEMENT_COMPLETE = 1.0f;

    private final GridPoint2 currentCoordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress;
    private final Direction direction;

    private final MapModel map;
    private Observerable observer;

    public Bullet(GridPoint2 initialCoordinates, Direction direction, MapModel map) {
        this.currentCoordinates = new GridPoint2(initialCoordinates);
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
        this.movementProgress = MOVEMENT_COMPLETE;
        this.direction = direction;
        this.map = map;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public float getRotation() {
        return direction.getRotation();
    }

    @Override
    public void move(Direction direction) {
        if (isMovementComplete()) {
            GridPoint2 nextCoordinates = currentCoordinates.cpy().add(direction.getDirectionVector());

            if (canMoveTo(nextCoordinates)) {
                destinationCoordinates = currentCoordinates.cpy().add(direction.getDirectionVector());
                movementProgress = 0f;
            } else {
                handleCollision(nextCoordinates);
            }
        }
    }

    @Override
    public void update(TileMovement tileMovement, float deltaTime) {

    }

    @Override
    public ModelPlayable getModel() {
        return null;
    }

    @Override
    public Rectangle getRectangle() {
        return null;
    }

    @Override
    public void setObjectObserver(Observerable observer) {

    }

    private boolean isMovementComplete() {
        return isEqual(movementProgress, MOVEMENT_COMPLETE);
    }

    private boolean canMoveTo(GridPoint2 coordinates) {
        return !isObstacle(coordinates, map.getObstacles()) && !map.isOutOfBounds(coordinates);
    }

    private void handleCollision(GridPoint2 nextCoordinates) {
        destroy();
        Obstacleble obstacle = findObstacleAt(nextCoordinates);
        if (obstacle != null) {
            if (obstacle instanceof Bullet) {
                ((Bullet) obstacle).destroy();
            } else if (obstacle instanceof Tank) {
                ((Tank) obstacle).hit(30);
            }
        }
    }

    private Obstacleble findObstacleAt(GridPoint2 coordinates) {
        return map.getObstacles().stream()
                .filter(obstacle -> obstacle.getCoordinates().stream().anyMatch(coordinates::equals))
                .findFirst()
                .orElse(null);
    }

    private void destroy() {
        if (observer != null) {
            observer.objectDestroyed(this, "bullet");
        }
        map.removeBullet(this);
    }

    private boolean isObstacle(GridPoint2 coordinates, Set<Obstacleble> obstacles) {
        return obstacles.stream().anyMatch(obstacle ->
                obstacle.getCoordinates().stream().anyMatch(coordinates::equals));
    }

    public void update(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);

        if (isMovementComplete()) {
            currentCoordinates.set(destinationCoordinates);
            move(direction);
        }
    }

    @Override
    public Collection<GridPoint2> getCoordinates() {
        return List.of(currentCoordinates, destinationCoordinates);
    }

    public GridPoint2 getCoordinate() {
        return currentCoordinates;
    }

    public float getHealth() {
        return 100.0f;
    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }
}
