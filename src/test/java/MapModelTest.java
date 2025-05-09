import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.abstractions.interfaces.Obstacleble;
import ru.mipt.bit.platformer.abstractions.models.MapModel;
import ru.mipt.bit.platformer.abstractions.models.Tank;
import ru.mipt.bit.platformer.abstractions.models.Tree;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MapModelTest {

    private MapModel mapModel;
    private Set<Tree> trees;
    private Set<Tank> tanks;
    private Tank player;

    @BeforeEach
    void setUp() {
        trees = new HashSet<>();
        tanks = new HashSet<>();
        player = new Tank(new GridPoint2(1, 1));
        trees.add(new Tree(new GridPoint2(0, 0)));
        trees.add(new Tree(new GridPoint2(0, 1)));
        tanks.add(new Tank(new GridPoint2(1, 0)));

        mapModel = new MapModel(trees, tanks, player, 5, 5);
    }

    @Test
    void testInitialization() {
        assertEquals(trees, mapModel.getTrees());
        assertEquals(tanks, mapModel.getTanks());
        assertEquals(player, mapModel.getPlayer());
    }

    @Test
    void testGetObstacles() {
        Set<Obstacleble> expectedObstacles = new HashSet<>(tanks);
        expectedObstacles.add(player);

        assertEquals(expectedObstacles, mapModel.getObstacles());
    }

    @Test
    void testGetTreesReturnsUnmodifiableSet() {
        Set<Tree> treesFromModel = mapModel.getTrees();
        assertThrows(UnsupportedOperationException.class, () -> treesFromModel.add(new Tree(new GridPoint2(2, 2))));
    }

    @Test
    void testGetTanksReturnsUnmodifiableSet() {
        Set<Tank> tanksFromModel = mapModel.getTanks();
        assertThrows(UnsupportedOperationException.class, () -> tanksFromModel.add(new Tank(new GridPoint2(2, 2))));
    }

    @Test
    void testGetObstaclesReturnsUnmodifiableSet() {
        Set<Obstacleble> obstaclesFromModel = mapModel.getObstacles();
        assertThrows(UnsupportedOperationException.class, () -> obstaclesFromModel.add(new Tree(new GridPoint2(2, 2))));
    }
}
