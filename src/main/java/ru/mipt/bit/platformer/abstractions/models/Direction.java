package ru.mipt.bit.platformer.abstractions.models;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    UP(90), DOWN(-90), LEFT(-180), RIGHT(0);

    private final float rotation;

    Direction(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public GridPoint2 move(GridPoint2 currentPosition) {
        return switch (this) {
            case UP -> new GridPoint2(currentPosition.x, currentPosition.y + 1);
            case DOWN -> new GridPoint2(currentPosition.x, currentPosition.y - 1);
            case LEFT -> new GridPoint2(currentPosition.x - 1, currentPosition.y);
            case RIGHT -> new GridPoint2(currentPosition.x + 1, currentPosition.y);
        };
    }
}
