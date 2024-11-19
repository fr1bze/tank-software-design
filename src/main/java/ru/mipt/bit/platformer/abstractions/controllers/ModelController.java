package ru.mipt.bit.platformer.abstractions.controllers;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.abstractions.models.BaseModel;

import java.util.List;

public class ModelController {
    private List<BaseModel> models;

    public ModelController(List<BaseModel> models) {
        this.models = models;
    }

    public void updateModels(float deltaTime) {
    }

    public void renderModels(Batch batch) {
    }

    public void disposeModels() {

    }

    public List<BaseModel> getModels() {
        return this.models;
    }
}
