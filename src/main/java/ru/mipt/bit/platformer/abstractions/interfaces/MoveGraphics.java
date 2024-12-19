package ru.mipt.bit.platformer.abstractions.interfaces;

import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.abstractions.models.Direction;
import ru.mipt.bit.platformer.util.TileMovement;

public interface MoveGraphics extends Graphics, Obstacleble, Livable {
    void move(Direction direction);
    void update(TileMovement tileMovement, float deltaTime);
    ModelPlayable getModel();
    Rectangle getRectangle();
    void setObjectObserver(Observerable observer);
}
