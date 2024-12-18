package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.abstractions.interfaces.Command;
import ru.mipt.bit.platformer.abstractions.interfaces.MoveGraphics;
import ru.mipt.bit.platformer.abstractions.models.Direction;

public class TankMoveCommand implements Command{
    private final MoveGraphics tank;
    private final Direction direction;

    public TankMoveCommand(MoveGraphics tank, Direction direction) {
        this.tank = tank;
        this.direction = direction;
    }

    @Override
    public void execute() {
        tank.move(direction);
    }
}

