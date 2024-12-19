package ru.mipt.bit.platformer.abstractions;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public interface Renderable {
        void render(Batch batch);
        void dispose();

    Rectangle getRectangle();
}

