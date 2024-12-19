package ru.mipt.bit.platformer.strategies;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.abstractions.graphics.GraphicsController;
import ru.mipt.bit.platformer.abstractions.models.BaseModel;
import ru.mipt.bit.platformer.abstractions.models.MapModel;

import java.util.List;

public interface LevelStrategy {
    void generateLevel(TiledMapTileLayer groundLayer, MapModel mapModel, List<BaseModel> models, GraphicsController graphicsController);
}
