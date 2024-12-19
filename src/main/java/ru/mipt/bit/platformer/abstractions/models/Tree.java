package ru.mipt.bit.platformer.abstractions.models;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.abstractions.Renderable;
import ru.mipt.bit.platformer.abstractions.interfaces.Obstacleble;

import java.util.Collection;
import java.util.Collections;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tree extends BaseModel implements Renderable, Obstacleble {

    public Tree(String texturePath, GridPoint2 initialPosition, TiledMapTileLayer layer, MapModel mapModel) {
        super(initialPosition, mapModel);
        moveRectangleAtTileCenter(layer, getRectangle(), getPosition());
    }

    public Tree(GridPoint2 gridPoint2, MapModel mapModel) {
        super(gridPoint2, mapModel);
    }

    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, new TextureRegion(), getRectangle(), 0f);
    }


    public void dispose() {
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle();
    }

    public boolean collidesWith(GridPoint2 point) {
        return getPosition().equals(point);
    }

    @Override
    public Collection<GridPoint2> getCoordinates() {
        return Collections.singletonList(getPosition());
    }
}
