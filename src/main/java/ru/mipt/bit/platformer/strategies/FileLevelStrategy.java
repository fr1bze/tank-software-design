package ru.mipt.bit.platformer.strategies;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.abstractions.graphics.GraphicsController;
import ru.mipt.bit.platformer.abstractions.handlers.KeyboardInputHandler;
import ru.mipt.bit.platformer.abstractions.models.BaseModel;
import ru.mipt.bit.platformer.abstractions.models.MapModel;
import ru.mipt.bit.platformer.abstractions.models.Tank;
import ru.mipt.bit.platformer.abstractions.models.Tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileLevelStrategy implements LevelStrategy {
    private String filePath;

    public FileLevelStrategy(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void generateLevel(TiledMapTileLayer groundLayer, MapModel mapModel, List<BaseModel> models, GraphicsController graphicsController) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int y = 0;
            while ((line = reader.readLine()) != null) {
                for (int x = 0; x < line.length(); ++x) {
                    char cell = line.charAt(x);
                    GridPoint2 position = new GridPoint2(x, y);
                    if (cell == 'T') {
                        models.add(new Tree("images/greenTree.png", position, groundLayer, graphicsController));
                    } else if (cell == 'X') {
                        models.add(new Tank("images/tank_blue.png", mapModel, position, graphicsController, new KeyboardInputHandler()));
                    }
                }
                y++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
