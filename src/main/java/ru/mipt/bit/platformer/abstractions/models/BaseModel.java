package ru.mipt.bit.platformer.abstractions.models;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.abstractions.graphics.GraphicsController;

public abstract class BaseModel {
    private GridPoint2 position;
    private MapModel mapModel;

    protected BaseModel(GridPoint2 initialPosition, MapModel map) {
        this.position = new GridPoint2(initialPosition);
        this.mapModel = map;
    }

    public GridPoint2 getPosition() {
        return position;
    }

    public void setPosition(GridPoint2 position) {
        this.position = position;
    }

    public boolean collidesWith(GridPoint2 point) {
        return getPosition().equals(point);
    }

    public void dispose() {
    }

    public MapModel getMapModel() {
        return mapModel;
    }
}