package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.abstractions.interfaces.Command;
import ru.mipt.bit.platformer.abstractions.interfaces.Shootable;

public class ShotCommand implements Command {

    private final Shootable model;

    public ShotCommand(Shootable model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.shoot();
    }
}
