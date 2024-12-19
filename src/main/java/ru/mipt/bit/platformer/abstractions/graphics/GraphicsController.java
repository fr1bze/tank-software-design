package ru.mipt.bit.platformer.abstractions.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class GraphicsController {

    public void render(Batch batch, TextureRegion graphics, Rectangle rectangle, float rotation) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }

    public void dispose() {
    }
}
