package ru.mipt.bit.platformer.abstractions.interfaces;

public interface Observerable {
        void objectAppeared(MoveGraphics model, String name);
        void objectDestroyed(MoveGraphics model, String name);
}
