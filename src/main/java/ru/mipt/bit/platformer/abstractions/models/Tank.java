package ru.mipt.bit.platformer.abstractions.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.abstractions.Renderable;
import ru.mipt.bit.platformer.abstractions.graphics.GraphicsController;
import ru.mipt.bit.platformer.abstractions.handlers.InputHandler;
import ru.mipt.bit.platformer.abstractions.movement.Movable;
import ru.mipt.bit.platformer.util.TileMovement;
import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tank extends BaseModel implements Movable, Renderable {
    private final float movementSpeed;

    private GridPoint2 currentCoordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    private final InputHandler inputHandler;

    public Tank(String texturePath, GridPoint2 initialCoordinates, float movementSpeed, GraphicsController graphicsController,
                InputHandler inputHandler) {
        super(texturePath, initialCoordinates, graphicsController);
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
        this.currentCoordinates = initialCoordinates;
        this.movementSpeed = movementSpeed;
        this.rotation = 0f;

        this.inputHandler = inputHandler;
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
        if (isEqual(movementProgress, 1f)) {
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
    public void render(Batch batch) {
        graphicsController.render(batch, getGraphics(), getRectangle(), rotation);
    }
}
