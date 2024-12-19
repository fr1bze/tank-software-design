package ru.mipt.bit.platformer.abstractions.models;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.abstractions.Renderable;
import ru.mipt.bit.platformer.abstractions.graphics.GraphicsController;
import ru.mipt.bit.platformer.abstractions.handlers.InputHandler;
import ru.mipt.bit.platformer.abstractions.handlers.KeyboardInputHandler;
import ru.mipt.bit.platformer.abstractions.interfaces.*;
import ru.mipt.bit.platformer.abstractions.movement.Movable;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tank extends BaseModel implements Movable, Renderable, Obstacleble, Livable, Shootable, MoveGraphics {

    static class TankConstants {
        private static final float MOVEMENT_SPEED = 0.4f;
        private static final float MOVEMENT_COMPLETE = 1f;
        private static final float INITIAL_ROTATION = 0f;
        private static final float MAX_HEALTH = 100.0f;
        private static final float INITIAL_COOLDOWN = 1f;
    }

    private final float movementSpeed;
    private GridPoint2 currentCoordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress;
    private float rotation;
    private final InputHandler inputHandler;
    private float health;
    private float cooldown;
    private String texturePath;
    private final Rectangle rectangle;
    private Observerable observer;

    public Tank(String texturePath, MapModel map, GridPoint2 initialCoordinates, InputHandler inputHandler) {
        super(initialCoordinates, map);
        this.texturePath = texturePath;
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
        this.currentCoordinates = initialCoordinates;
        this.movementSpeed = TankConstants.MOVEMENT_SPEED;
        this.rotation = TankConstants.INITIAL_ROTATION;
        this.movementProgress = TankConstants.MOVEMENT_COMPLETE;
        this.inputHandler = inputHandler;
        this.cooldown = TankConstants.INITIAL_COOLDOWN;
        this.health = new Random().nextFloat(21.0f) + 80f;
        this.rectangle = createRectangle(initialCoordinates);
    }

    public Tank(GridPoint2 initialCoordinates, MapModel map) {
        super(initialCoordinates, map);
        this.movementSpeed = TankConstants.MOVEMENT_SPEED;
        currentCoordinates = initialCoordinates;
        this.inputHandler = null;
        this.health = TankConstants.MAX_HEALTH;
        this.rectangle = createRectangle(initialCoordinates);
    }

    @Override
    public void shoot() {

    }

    @Override
    public Rectangle getRectangle() {
        rectangle.setPosition(currentCoordinates.x, currentCoordinates.y);
        return rectangle;
    }

    private Rectangle createRectangle(GridPoint2 coordinates) {
        return new Rectangle(coordinates.x, coordinates.y, 1f, 1f);
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

    @Override
    public float getHealth() {
        return this.health;
    }

    public GridPoint2 getDestination() {
        return new GridPoint2(destinationCoordinates);
    }

    @Override
    public Collection<GridPoint2> getCoordinates() {
        return Arrays.asList(currentCoordinates, destinationCoordinates);
    }

    public void hit(int damage) {
        health = Math.max(0, health - damage);
        if (health <= 0) destroy();
    }

    public void destroy() {
        if (observer != null) {
            observer.objectDestroyed(this, "tank");
        }
        getMapModel().removeTank(this);
    }

    @Override
    public void render(Batch batch) {

    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {
    }

    @Override
    public void move(Direction direction) {

    }

    @Override
    public void update(TileMovement tileMovement, float deltaTime) {

    }

    @Override
    public ModelPlayable getModel() {
        return null;
    }

    @Override
    public void setObjectObserver(Observerable observer) {
        this.observer = observer;
    }

    private boolean isCompleteMovement() {
        return isEqual(movementProgress, TankConstants.MOVEMENT_COMPLETE);
    }

    private boolean canMoveToPoint(GridPoint2 coordinates) {
        return !isObstacle(coordinates, getMapModel().getObstacles());
    }

    private boolean isObstacle(GridPoint2 coordinates, Set<Obstacleble> obstacles) {
        for (Obstacleble obstacle : obstacles) {
            if (obstacle.getCoordinates().contains(coordinates)) {
                return true;
            }
        }
        return false;
    }

    public InputHandler getInputHandler() {
        return this.inputHandler;
    }
}
