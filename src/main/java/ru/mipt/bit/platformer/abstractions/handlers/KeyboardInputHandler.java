package ru.mipt.bit.platformer.abstractions.handlers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.springframework.boot.Banner;
import ru.mipt.bit.platformer.abstractions.ModelController;
import ru.mipt.bit.platformer.abstractions.models.Direction;
import ru.mipt.bit.platformer.util.ButtonHandler;
import ru.mipt.bit.platformer.util.ToggleHealthBarCommand;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

import static com.badlogic.gdx.Input.Keys.*;


public class KeyboardInputHandler implements InputHandler {

    private final ButtonHandler buttonHandler;
    private final ModelController modelController;

    public KeyboardInputHandler(ButtonHandler buttonHandler, ModelController modelController) {
        this.buttonHandler = buttonHandler;
        this.modelController = modelController;

        buttonHandler.addButtonAction(
                List.of(L),
                new ToggleHealthBarCommand(this.modelController.getTanks()),
                true
        );
    }

    @Override
    public Direction handleInput() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            return Direction.UP;
        } else if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            return Direction.LEFT;
        } else if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            return Direction.DOWN;
        } else if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            return Direction.RIGHT;
        }
        return null;
    }

    public void handleHealthInput() {
        buttonHandler.checkInput(Gdx.input);
    }
}
