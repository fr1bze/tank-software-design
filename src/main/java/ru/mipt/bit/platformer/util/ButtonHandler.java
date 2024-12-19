package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.abstractions.handlers.InputHandler;
import ru.mipt.bit.platformer.abstractions.interfaces.Command;
import ru.mipt.bit.platformer.abstractions.models.Direction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

class ButtonAction {

    private final Collection<Integer> buttons;
    private final Command command;
    private final boolean toggleOnEveryRender;

    public ButtonAction(Collection<Integer> buttons, Command command, boolean toggleOnEveryRender) {
        this.buttons = List.copyOf(buttons);
        this.command = command;
        this.toggleOnEveryRender = toggleOnEveryRender;
    }

    public Collection<Integer> getButtons() {
        return buttons;
    }

    public Command getCommand() {
        return command;
    }

    public boolean isToggleOnEveryRender() {
        return toggleOnEveryRender;
    }
}

public class ButtonHandler {

    private final List<ButtonAction> buttonActions;

    public ButtonHandler() {
        this.buttonActions = new ArrayList<>();
    }

    public void addButtonAction(Collection<Integer> buttons, Command command, boolean toggleOnEveryRender) {
        buttonActions.add(new ButtonAction(buttons, command, toggleOnEveryRender));
    }

    public void checkInput(Input input) {
        for (ButtonAction buttonAction : buttonActions) {
            if (buttonAction.isToggleOnEveryRender()) {
                executeCommandIfPressed(buttonAction, input::isKeyPressed);
            } else {
                executeCommandIfJustPressed(buttonAction, input::isKeyJustPressed);
            }
        }
    }

    private void executeCommandIfPressed(ButtonAction buttonAction, Predicate<Integer> keyPressPredicate) {
        if (buttonAction.getButtons().stream().anyMatch(keyPressPredicate)) {
            buttonAction.getCommand().execute();
        }
    }

    private void executeCommandIfJustPressed(ButtonAction buttonAction, Predicate<Integer> keyPressPredicate) {
        if (buttonAction.getButtons().stream().anyMatch(keyPressPredicate)) {
            buttonAction.getCommand().execute();
        }
    }
}