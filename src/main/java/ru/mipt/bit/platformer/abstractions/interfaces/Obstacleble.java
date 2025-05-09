package ru.mipt.bit.platformer.abstractions.interfaces;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Collection;

public interface Obstacleble {
    public Collection<GridPoint2> getCoordinates();
}
