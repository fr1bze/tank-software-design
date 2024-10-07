package ru.mipt.bit.platformer.abstractions.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.abstractions.models.Direction;
import ru.mipt.bit.platformer.abstractions.graphics.GraphicsController;
import ru.mipt.bit.platformer.abstractions.movement.Moveable;
import ru.mipt.bit.platformer.util.TileMovement;
import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tank extends BaseModel implements Moveable {
    private final float movementSpeed;

    private GridPoint2 currentCoordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    public Tank(String texturePath, GridPoint2 initialCoordinates, float movementSpeed, GraphicsController graphicsController) {
        super(texturePath, initialCoordinates, graphicsController);
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
        this.currentCoordinates = initialCoordinates;
        this.movementSpeed = movementSpeed;
        this.rotation = 0f;
    }

    @Override
    public void handleInput() {
        if (isEqual(movementProgress, 1f)) {
            Direction direction = null;

            if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
                direction = Direction.UP;
            } else if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
                direction = Direction.LEFT;
            } else if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
                direction = Direction.DOWN;
            } else if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
                direction = Direction.RIGHT;
            }

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
