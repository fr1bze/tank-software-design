package ru.mipt.bit.platformer.strategies;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.abstractions.graphics.GraphicsController;
import ru.mipt.bit.platformer.abstractions.models.BaseModel;

import java.util.List;

public interface LevelStrategy {
    void generateLevel(TiledMapTileLayer groundLayer, List<BaseModel> models, GraphicsController graphicsController);
}
