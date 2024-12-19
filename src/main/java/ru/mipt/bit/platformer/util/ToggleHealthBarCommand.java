package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.abstractions.Renderable;
import ru.mipt.bit.platformer.abstractions.graphics.HealthBarDecorator;
import ru.mipt.bit.platformer.abstractions.interfaces.Command;
import ru.mipt.bit.platformer.abstractions.models.Tank;

import java.util.Collection;

public class ToggleHealthBarCommand implements Command {
        private final Collection<Renderable> tanks;
        private boolean healthbarVisible = false;

        public ToggleHealthBarCommand(Collection<Renderable> tanks) {
            this.tanks = tanks;
        }

        @Override
        public void execute() {
            healthbarVisible = !healthbarVisible;
            for (Renderable tank : tanks) {
                if (tank instanceof HealthBarDecorator) {
                    tanks.remove(tank);
                } else if (tank instanceof Tank) {
                    tanks.add(new HealthBarDecorator(tank));
                }
            }
        }
}
