import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.abstractions.models.Tree;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TreeModelTest {
    private Tree treeModel;
    private static final GridPoint2 INITIAL_POSITION = new GridPoint2(5, 10);

    @BeforeEach
    public void setUp() {
        treeModel = new Tree(INITIAL_POSITION);
    }

    @Test
    public void testInitialization() {
        assertEquals(INITIAL_POSITION, treeModel.getPosition());
    }

    @Test
    public void testGetCoordinatesReturnsNewInstance() {
        GridPoint2 coordinates = treeModel.getPosition();
        coordinates.set(0, 0);

        assertNotEquals(new GridPoint2(0, 0), treeModel.getCoordinates());
        assertEquals(INITIAL_POSITION, treeModel.getPosition());
    }
}
