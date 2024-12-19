package ru.mipt.bit.platformer.strategies;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.abstractions.ModelController;
import ru.mipt.bit.platformer.abstractions.graphics.GraphicsController;
import ru.mipt.bit.platformer.abstractions.handlers.KeyboardInputHandler;
import ru.mipt.bit.platformer.abstractions.interfaces.MapLoader;
import ru.mipt.bit.platformer.abstractions.models.BaseModel;
import ru.mipt.bit.platformer.abstractions.models.MapModel;
import ru.mipt.bit.platformer.abstractions.models.Tank;
import ru.mipt.bit.platformer.abstractions.models.Tree;
import ru.mipt.bit.platformer.util.ButtonHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class FileLevelStrategy implements MapLoader {
    private String filePath;
    private ButtonHandler buttonHandler;
    private ModelController modelController;

    @Value("${level.file.path}")
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public MapModel load() {
        Set<Tree> trees = new HashSet<>();
        Set<Tank> tanks = new HashSet<>();
        MapModel mapModel = new MapModel();
        Tank playerTank = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int y = 0;
            int rowCount = 0;
            int columnCount = 0;

            while ((line = reader.readLine()) != null) {
                columnCount = Math.max(columnCount, line.length());
                for (int x = 0; x < line.length(); ++x) {
                    char cell = line.charAt(x);
                    GridPoint2 position = new GridPoint2(x, y);
                    switch (cell) {
                        case 'T':
                            trees.add(new Tree("images/greenTree.png", position, null, null));
                            break;
                        case 'X':
                            if (playerTank == null) {
                                playerTank = new Tank("images/tank_blue.png", null, position, new KeyboardInputHandler(buttonHandler, modelController));
                            } else {
                                tanks.add(new Tank("images/tank_red.png", mapModel, position, null));
                            }
                            break;
                    }
                }
                y++;
                rowCount++;
            }

            mapModel.setPlayer(playerTank);
            mapModel.setTanks(tanks);
            mapModel.setTrees(trees);
            mapModel.setMapSize(rowCount, columnCount);
            return mapModel;

        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить карту из файла: " + filePath, e);
        }
    }
}
