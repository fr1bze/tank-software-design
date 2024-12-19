package ru.mipt.bit.platformer.cofiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.mipt.bit.platformer.abstractions.Renderable;
import ru.mipt.bit.platformer.abstractions.graphics.HealthBarDecorator;
import ru.mipt.bit.platformer.abstractions.interfaces.Livable;
import ru.mipt.bit.platformer.abstractions.interfaces.MapLoader;
import ru.mipt.bit.platformer.abstractions.models.Direction;
import ru.mipt.bit.platformer.abstractions.models.Tank;
import ru.mipt.bit.platformer.strategies.FileLevelStrategy;
import ru.mipt.bit.platformer.strategies.RandomLevelStrategy;
import ru.mipt.bit.platformer.util.ButtonHandler;
import ru.mipt.bit.platformer.util.ShotCommand;
import ru.mipt.bit.platformer.util.TankMoveCommand;
import ru.mipt.bit.platformer.util.ToggleHealthBarCommand;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;
import static javax.swing.SwingConstants.LEFT;

@org.springframework.context.annotation.Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "ru.mipt.bit.platformer.abstractions.models")
public class GameConfiguration {

    @Value("${app.loader.type}")
    private String type;

    @Value("${app.loader.path}")
    private String path;

    @Value("${app.loader.tanks}")
    private String tanks;

    @Value("${app.loader.rows}")
    private String rows;

    @Value("${app.loader.columns}")
    private String columns;

    @Bean
    public MapLoader mapLoader() {
        switch (type) {
            case "File":
                return new FileLevelStrategy();
            case "Random":
                return new RandomLevelStrategy();
        }
        return null;
    }

    public ButtonHandler buttonHandle(MapLoader mapLoader) {
        ButtonHandler inputHandler = new ButtonHandler();
        Tank playerTank = mapLoader.load().getPlayer();

        Map<Direction, List<Integer>> controls = Map.of(
                Direction.UP, List.of(UP, W),
                Direction.LEFT, List.of(LEFT, A),
                Direction.DOWN, List.of(DOWN, S),
                Direction.RIGHT, List.of(RIGHT, D)
        );

        controls.forEach((direction, keys) ->
                inputHandler.addButtonAction(keys,
                        new TankMoveCommand(playerTank, direction), true));

        inputHandler.addButtonAction(List.of(L), new ToggleHealthBarCommand((Collection<Renderable>) new HealthBarDecorator(playerTank)), false);
        inputHandler.addButtonAction(List.of(SPACE), new ShotCommand(playerTank), false);

        return inputHandler;
    }
}
