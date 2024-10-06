package ru.mipt.bit.platformer.abstractions.movement;

import ru.mipt.bit.platformer.util.TileMovement;

public interface Movable {
    void handleInput();
    void updatePosition(TileMovement tileMovement, float deltaTime);

    void cancelMovement();
}