package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.abstractions.ModelController;
import ru.mipt.bit.platformer.abstractions.interfaces.MapLoader;
import ru.mipt.bit.platformer.abstractions.models.BaseModel;
import ru.mipt.bit.platformer.abstractions.models.Field;
import ru.mipt.bit.platformer.abstractions.models.MapModel;

import java.util.ArrayList;
import java.util.List;

public class GameInitializer {
    private final MapLoader mapLoader;
    private final GraphicsManager graphicsManager;
    private final ModelController modelController;
    private final MapModel mapModel;
    private final List<BaseModel> models;

    public GameInitializer(MapLoader mapLoader,
                           GraphicsManager graphicsManager,
                           ModelController modelController,
                           MapModel mapModel) {
        this.mapLoader = mapLoader;
        this.graphicsManager = graphicsManager;
        this.modelController = modelController;
        this.mapModel = mapModel;
        this.models = new ArrayList<>();
    }

    public void initialize() {
        mapLoader.load();
        modelController.initialize(models);
    }
}
