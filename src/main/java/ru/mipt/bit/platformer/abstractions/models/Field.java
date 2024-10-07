package ru.mipt.bit.platformer.abstractions;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class Field {
    private TiledMap map;
    private MapRenderer renderer;

    public Field(String mapPath, Batch batch) {
        this.map = new TmxMapLoader().load(mapPath);
        this.renderer = createSingleLayerMapRenderer(map, batch);
    }

    public void render() {
        renderer.render();
    }

    public TiledMapTileLayer getLayer() {
        return getSingleLayer(map);
    }

    public void dispose() {
        map.dispose();
    }
}
