package ru.mipt.bit.platformer.abstractions.handlers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.abstractions.models.Direction;
import ru.mipt.bit.platformer.util.ToggleHealthBarCommand;

import java.util.concurrent.Executor;

import static com.badlogic.gdx.Input.Keys.*;

public class KeyboardInputHandler implements InputHandler {
    private ToggleHealthBarCommand toggleHealthBarCommand;
    public KeyboardInputHandler(ToggleHealthBarCommand toggleHealthBarCommand) {
        this.toggleHealthBarCommand = toggleHealthBarCommand;
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
        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            toggleHealthBarCommand.execute();
        }
    }
}
