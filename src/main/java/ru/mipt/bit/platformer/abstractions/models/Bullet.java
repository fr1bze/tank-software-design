package ru.mipt.bit.platformer.abstractions.models;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private final Vector2 position;
    private final Vector2 velocity;
    private final Rectangle boundingBox;
    private final float speed;
    public Bullet(Vector2 startPosition, Vector2 direction, float speed) {
        this.position = startPosition.cpy();
        this.velocity = direction.nor().scl(speed);
        this.speed = speed;
        this.boundingBox = new Rectangle(startPosition.x, startPosition.y, 0.1f, 0.1f); // Размер пули
    }

    public void update(float deltaTime) {
        position.mulAdd(position, deltaTime);
        boundingBox.setPosition(position);
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public boolean isOutOfBounds(int mapWidth, int mapHeight) {
        return position.x < 0 || position.y < 0 || position.x >= mapWidth || position.y >= mapHeight;
    }
}
