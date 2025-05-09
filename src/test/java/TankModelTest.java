import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.abstractions.interfaces.Obstacleble;
import ru.mipt.bit.platformer.abstractions.models.Direction;
import ru.mipt.bit.platformer.abstractions.models.Tank;
import ru.mipt.bit.platformer.abstractions.models.Tree;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TankModelTest {
    private Tank tank;
    private Set<Obstacleble> obstacles;
    private static final int ROW_COUNT = 5;
    private static final int COLUMN_COUNT = 5;

    @BeforeEach
    void setUp() {
        GridPoint2 initialCoordinates = new GridPoint2(2, 2);
        tank = new Tank(initialCoordinates);
        obstacles = new HashSet<>();
    }

    @Test
    void testInitialization() {
        assertEquals(new GridPoint2(2, 2), tank.getCurrentCoordinates());
        assertEquals(0.0f, tank.getRotation());
    }

    @Test
    void testMoveWithoutObstacles() {
        tank.move(Direction.UP, obstacles, ROW_COUNT, COLUMN_COUNT);
        assertEquals(new GridPoint2(2, 3), tank.getCurrentCoordinates());
    }

    @Test
    void testMoveWithObstacles() {
        obstacles.add(new Tree(new GridPoint2(2, 3)));
        tank.move(Direction.UP, obstacles, ROW_COUNT, COLUMN_COUNT);
        assertEquals(new GridPoint2(2, 2), tank.getCurrentCoordinates());
    }

    @Test
    void testMoveOutOfBounds() {
        tank.move(Direction.RIGHT, obstacles, 3, 3);
        assertEquals(new GridPoint2(2, 2), tank.getCurrentCoordinates());
    }
}
