package ru.mipt.bit.platformer.abstractions.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.abstractions.models.BaseModel;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class RenderableModel {
    private final BaseModel model;
    private Texture texture;
    private TextureRegion graphics;
    private Rectangle rectangle;
    private final GraphicsController graphicsController;

    public RenderableModel(BaseModel model, String texturePath, GraphicsController graphicsController) {
        this.model = model;
        this.texture = new Texture(texturePath);
        this.graphicsController = graphicsController;
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public void setGraphics(TextureRegion graphics) {
        this.graphics = graphics;
    }

    public void render(Batch batch) {
        graphicsController.render(batch, getGraphics(), getRectangle(), 0f);
    }

    public void dispose() {
        graphicsController.dispose();
        texture.dispose();
    }
}