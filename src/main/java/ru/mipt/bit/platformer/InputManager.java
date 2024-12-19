package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.abstractions.handlers.InputHandler;

public class InputManager {
    private final InputHandler inputHandler;

    public InputManager(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public void handleInput() {
        inputHandler.handleHealthInput();
    }
}
