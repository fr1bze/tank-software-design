package ru.mipt.bit.platformer.abstractions.controllers;

import ru.mipt.bit.platformer.abstractions.models.Bullet;

import java.util.Iterator;
import java.util.List;

public class BulletController {
    private final List<Bullet> bullets;

    public BulletController(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public void update(float deltaTime, int mapWidth, int mapHeight) {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update(deltaTime);
            
            if (bullet.isOutOfBounds(mapWidth, mapHeight)) {
                iterator.remove();
            }
        }
    }

    public List<Bullet> getBullets() {
        return bullets;
    }
}
