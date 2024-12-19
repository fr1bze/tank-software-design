package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.abstractions.ModelController;
import ru.mipt.bit.platformer.abstractions.models.BaseModel;
import ru.mipt.bit.platformer.abstractions.models.Field;

import java.util.Comparator;
import java.util.List;

@Component
public class GraphicsManager {
    private final SpriteBatch batch;
    private final Field field;

    public GraphicsManager() {
        this.batch = new SpriteBatch();
        this.field = new Field("level.tmx", batch);
    }

    public void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void renderModels(List<BaseModel> models, ModelController modelController) {
        models.sort(Comparator.comparingInt(model -> -model.getPosition().y));
        field.render();
        batch.begin();
        modelController.renderModels(batch);
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        field.dispose();
    }
}
