package ru.mipt.bit.platformer.abstractions.models;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.abstractions.Renderable;
import ru.mipt.bit.platformer.abstractions.graphics.GraphicsController;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tree extends BaseModel implements Renderable {

    public Tree(String texturePath, GridPoint2 initialPosition, TiledMapTileLayer layer, GraphicsController graphicsController) {
        super(texturePath, initialPosition, graphicsController);
        moveRectangleAtTileCenter(layer, getRectangle(), getPosition());
    }

    public void render(Batch batch) {
        graphicsController.render(batch, getGraphics(), getRectangle(), 0f);
    }

    public void dispose() {
        graphicsController.dispose();
        getTexture().dispose();
    }

    public boolean collidesWith(GridPoint2 point) {
        return getPosition().equals(point);
    }
}
