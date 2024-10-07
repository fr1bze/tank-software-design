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
        switch (this) {
            case UP:
                return new GridPoint2(currentPosition.x, currentPosition.y + 1);
            case DOWN:
                return new GridPoint2(currentPosition.x, currentPosition.y - 1);
            case LEFT:
                return new GridPoint2(currentPosition.x - 1, currentPosition.y);
            case RIGHT:
                return new GridPoint2(currentPosition.x + 1, currentPosition.y);
            default:
                throw new IllegalArgumentException("Unknown direction");
        }
    }
}
