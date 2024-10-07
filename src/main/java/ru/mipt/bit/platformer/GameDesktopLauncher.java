package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.compression.lzma.Base;
import ru.mipt.bit.platformer.abstractions.Field;
import ru.mipt.bit.platformer.abstractions.graphics.GraphicsController;
import ru.mipt.bit.platformer.abstractions.models.BaseModel;
import ru.mipt.bit.platformer.abstractions.models.Tank;
import ru.mipt.bit.platformer.abstractions.models.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;
    private Field field;
    private List<BaseModel> models;
    private TileMovement tileMovement;

    @Override
    public void create() {
        batch = new SpriteBatch();
        field = new Field("level.tmx", batch);

        TiledMapTileLayer groundLayer = field.getLayer();
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        models = new ArrayList<>();
        GraphicsController graphicsController = new GraphicsController();

        models.add(new Tank("images/tank_blue.png", new GridPoint2(1, 1), 0.4f, graphicsController));
        models.add(new Tree("images/greenTree.png", new GridPoint2(1, 3), groundLayer, graphicsController));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();

        for (BaseModel model : models) {
            if (model instanceof Tank) {
                Tank tank = (Tank) model;
                tank.handleInput();

                if (isColliding(tank)) {
                    tank.cancelMovement();
                }
                else {
                    tank.updatePosition(tileMovement, deltaTime);
                }
            }
        }

        models.sort(Comparator.comparingInt(model -> -model.getPosition().y));
        field.render();

        batch.begin();
        for (BaseModel model : models) {
            model.render(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        field.dispose();
        for (BaseModel model : models) {
            model.dispose();
        }
    }

    private boolean isColliding(Tank tank) {
        for (BaseModel model : models) {
            if (model instanceof Tree && model.collidesWith(tank.getDestination())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
